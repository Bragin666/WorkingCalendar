package DataBase;

import Model.Schedule;
import Model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Евгений on 17.04.2016.
 */
public class ScheduleFactory {
    /**
     * Добавление новый график в базу данных
     * @param schedule Новый работник
     */
    public static void add(Schedule schedule) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(schedule);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
            session.getTransaction().rollback();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    /**
     * Возвращает график из базы данных по id
     * @param id Индентификационный номер работника
     * @return график с данным id
     */
    public static Schedule get(int id) {
        Schedule schedule = null;
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            schedule = (Schedule) session.get(Schedule.class, id);
            if (schedule.getShiftSchedule().size() != 0) schedule.getShiftSchedule().get(0);
            if (schedule.getWorkerSchedule().size() != 0) schedule.getWorkerSchedule().get(0);
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            session.close();
        }

        return schedule;
    }

    /**
     * Обнавляет график
     * @param schedule график
     */
    public static void update(Schedule schedule) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            Schedule schedule0 = (Schedule) session.load(Schedule.class, schedule.getId());
            schedule0.setName(schedule.getName());
            schedule0.setWorkerScheduleStartData(schedule.getWorkerScheduleStartData());
            schedule0.setWorkerSchedule(schedule.getWorkerSchedule());
            schedule0.setShiftScheduleStartData(schedule.getShiftScheduleStartData());
            schedule0.setShiftSchedule(schedule.getShiftSchedule());
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
            session.getTransaction().rollback();
        }
        finally {
            session.close();
        }
    }

    /**
     * Удаляет график из базы данных по id
     * @param id Идентификационный номер работника
     */
    public static void delete(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(session.get(Schedule.class, id));
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
            session.getTransaction().rollback();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    /**
     * Возвращает список всех графиков у этого пользователя
     * @param user пользователь
     * @return список всех графиков
     */
    public static List<Schedule> getAllSchedules(User user) {
        List<Schedule> schedules = null;

        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Schedule.class);
            criteria.add(Restrictions.eq("user", user));
            schedules = criteria.list();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }

        return schedules;
    }
}
