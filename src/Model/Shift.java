package Model;

import DataBase.ShiftFactory;
import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Смена
 * Created by Евгений on 16.05.2016.
 */
@Entity
@Table(name = "shifts")
public class Shift implements Serializable {
    /** Параметр - индетификационный номер*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /** Параметр - название смены*/
    private String name;
    /** Параметр - график, к которому относится смена*/
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    /** Параметр - стоимость*/
    private int cost;
    /** Параметр - красный оттенок отрисовки*/
    private int r;
    /** Параметр - зеленый оттенок отрисовки*/
    private int g;
    /** Параметр - синий оттенок отрисовки*/
    private int b;

    /**
     * Создание новой смены и занесение ее в базу
     * @param name Название смены
     * @param schedule график, к которому относится схема
     * @param cost Стоимость смены
     * @param color Цвет отображения смены
     */
    public Shift(String name, Schedule schedule, int cost, Color color) {
        this.name = name;
        this.schedule = schedule;
        this.cost = cost;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        ShiftFactory.add(this);
    }

    /**
     * Обновляет смену в базе данных
     */
    public void update() {
        ShiftFactory.update(this);
    }

    public Shift () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Color getColor() { return new Color(r, g, b); }

    public void setColor(Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
