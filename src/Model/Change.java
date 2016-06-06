package Model;

import DataBase.ChangeFactory;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Замена в графике
 * Может быть заменв в работнике, смене или их обоих
 * Created by Евгений on 29.05.2016.
 */
@Entity
@Table(name = "changes")
public class Change implements Serializable {
    /** Параметр - индетификационный номер*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /** Параметр - график, к которому принадлежит замена */
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    /** Параметр - Год замены */
    private int year;
    /** Параметр - Месяц замены */
    private int month;
    /** Параметр - День заменв */
    private int day;
    /** Параметр - Работник, работающий в день замены */
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;
    /** Параметр - Смена в день замены */
    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    /**
     * Дабавляет новую замену в базу данных
     * @param schedule график, к которому относится замена
     * @param date дата замены
     * @param worker работник, который будет работать в этот день
     * @param shift смена, которая будет в этот день
     */
    public Change(Schedule schedule, Date date, Worker worker, Shift shift) {
        this.schedule = schedule;
        year = date.getYear();
        month = date.getMonth();
        day = date.getDate();
        this.worker = worker;
        this.shift = shift;
        ChangeFactory.add(this);
    }

    /**
     * Обновляет замену в базе данных
     */
    public void update() {
        ChangeFactory.update(this);
    }

    public Change() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Date getDate() {
        return new Date(year, month, day);
    }

    public void setDate(Date date) {
        year = date.getYear();
        month = date.getMonth();
        day = date.getDate();
    }

    @Override
    public String toString() {
        return "Change{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", worker=" + worker.getId() +
                ", shift=" + shift.getId() +
                '}';
    }
}
