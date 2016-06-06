package DataBase;

import Model.Change;
import Model.Schedule;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Евгений on 29.05.2016.
 */
public class ChangeFactory {
    /**
     * Добавление новоq смены в базу данных
     * @param change Новая смена
     */
    public static void add(Change change) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(change);
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
     * Возвращает замену из базы данных по id
     * @param id Индентификационный номер замены
     * @return Замену с данным id
     */
    public static Change get(int id) {
        Change change = null;
        Session session = null;
        try {

            session = HibernateConnector.getSessionFactory().openSession();
            change = (Change) session.get(Change.class, id);
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            session.close();
        }

        return change;
    }

    /**
     * Обновляет замену
     * @param change замена
     */
    public static void update(Change change) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            Change change0 = (Change) session.load(Change.class, change.getId());
            change0.setYear(change.getYear());
            change0.setMonth(change.getMonth());
            change0.setDay(change.getDay());
            change0.setWorker(change.getWorker());
            change0.setShift(change.getShift());
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
     * Удаляет замену из базы данных по id
     * @param id Идентификационный номер замены
     */
    public static void delete(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(session.get(Change.class, id));
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
     * Возвращает все замены графика
     * @param schedule график
     * @return список замен
     */
    public static List<Change> getAllChanges(Schedule schedule) {
        List<Change> changes = null;

        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Change.class);
            criteria.add(Restrictions.eq("schedule", schedule));
            changes = criteria.list();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }

        return changes;
    }

    /**
     * Возвращает список всех замен в рассматриваемом месяце
     * @param schedule график, к которому относятся замены
     * @param year рассматриваемый год
     * @param month рассматриваемый месяц
     * @return список замен
     */
    public static List<Change> getThisMonthChanges(Schedule schedule, int year, int month) {
        List<Change> changes = null;

        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Change.class);
            criteria.add(Restrictions.eq("schedule", schedule));
            criteria.add(Restrictions.eq("year", year));
            criteria.add(Restrictions.eq("month", month));
            changes = criteria.list();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }

        return changes;
    }
}
