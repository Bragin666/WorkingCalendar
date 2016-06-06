package DataBase;

import Model.Schedule;
import Model.Shift;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Евгений on 16.05.2016.
 */
public class ShiftFactory {
    /**
     * Добавление нового смены в базу данных
     * @param shift Новая смена
     */
    public static void add(Shift shift) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(shift);
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
     * Возвращает смену из базы данных по id
     * @param id Индентификационный номер смены
     * @return Смену с данным id
     */
    public static Shift get(int id) {
        Shift shift = null;
        Session session = null;
        try {

            session = HibernateConnector.getSessionFactory().openSession();
            shift = (Shift) session.get(Shift.class, id);
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            session.close();
        }

        return shift;
    }

    /**
     * Обновляет смену
     * @param shift смена
     */
    public static void update(Shift shift) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            Shift shift0 = (Shift) session.load(Shift.class, shift.getId());
            shift0.setName(shift.getName());
            shift0.setCost(shift.getCost());
            shift0.setR(shift.getColor().getRed());
            shift0.setG(shift.getColor().getGreen());
            shift0.setB(shift.getColor().getBlue());
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
     * Удаляет смену из базы данных по id
     * @param id Идентификационный номер смены
     */
    public static void delete(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(session.get(Shift.class, id));
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
     * Возвращает список всех смен, которые есть этого графика
     * @param schedule график
     * @return список смен
     */
    public static List<Shift> getAllShifts(Schedule schedule) {
        List<Shift> shifts = null;

        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Shift.class);
            criteria.add(Restrictions.eq("schedule", schedule));
            shifts = criteria.list();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }

        return shifts;
    }
}
