package Controller;

import DataBase.ChangeFactory;
import DataBase.HibernateConnector;
import Model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Евгений on 02.06.2016.
 */
public class TestController {
    public static void main(String[] args) {
        User user = new User("Djony", "1234");

        Schedule ancor = new Schedule("Ancor", user);

        Worker worker1 = new Worker("Djony", ancor, 1.0d, new Color(1, 2, 3));
        Worker worker2 = new Worker("Andrew", ancor, 1.1d, new Color(1, 2, 3));
        Worker worker3 = new Worker("Nicolya", ancor, 1.0d, new Color(1, 2, 3));
        Worker worker4 = new Worker("Maks", ancor, 1.0d, new Color(1, 2, 3));
        ArrayList<Integer> workers = new ArrayList<>();
        workers.add(worker1.getId());
        workers.add(worker2.getId());
        workers.add(worker3.getId());
        ancor.setWorkerScheduleStartData(new Date(116, 5, 4));
        ancor.setWorkerSchedule(workers);

        Shift shift1 = new Shift("Выходной", ancor, 2740, new Color(1, 2, 3));
        Shift shift2 = new Shift("Будни", ancor, 1870, new Color(1, 2, 3));
        ArrayList<Integer> shifts= new ArrayList<>();
        shifts.add(shift1.getId());
        shifts.add(shift1.getId());
        shifts.add(shift2.getId());
        shifts.add(shift2.getId());
        shifts.add(shift2.getId());
        shifts.add(shift2.getId());
        shifts.add(shift2.getId());
        ancor.setShiftScheduleStartData(new Date(116, 5, 4));
        ancor.setShiftSchedule(shifts);

        ancor.update();

        new Change(ancor, new Date(2016, 5, 1), worker4, shift1);
        new Change(ancor, new Date(2016, 5, 11), worker4, shift2);
        new Change(ancor, new Date(2016, 5, 21), null, null);

        HibernateConnector.closeSessionFactory();
    }
}
