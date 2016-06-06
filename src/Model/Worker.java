package Model;

import DataBase.WorkerFactory;
import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Работник
 * Created by Евгений on 16.05.2016.
 */
@Entity
@Table(name = "workers")
public class Worker implements Serializable {
    /** Параметр - индетификационный номер*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /** Параметр - имя*/
    private String name;
    /** Параметр - график, к которому относится работник*/
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    /** Параметр - тарифный уравнительный коэффициент*/
    private double TLF;
    /** Параметр - красный оттенок отрисовки*/
    private int r;
    /** Параметр - зеленый оттенок отрисовки*/
    private int g;
    /** Параметр - синий оттенок отрисовки*/
    private int b;

    /**
     * Создает нового работника и заносит его в базу данных
     * @param name Имя работника
     * @param schedule кгафик,к которому относится работник
     * @param TLF Тарифный коэффициент
     * @param color Цвет отрисовки
     */
    public Worker(String name, Schedule schedule, double TLF, Color color) {
        this.name = name;
        this.schedule = schedule;
        this.TLF = TLF;
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        WorkerFactory.add(this);
    }

    /**
     * Обновляет работника в базе данных
     */
    public void update() {
        WorkerFactory.update(this);
    }

    public Worker() {}

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

    public double getTLF() {
        return TLF;
    }

    public void setTLF(double TLF) {
        this.TLF = TLF;
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

    public Color getColor () { return new Color(r, g, b); }

    public void setColor (Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", TLF=" + TLF +
                ", r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
