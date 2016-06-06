package DataBase;

import Model.Schedule;
import Model.Worker;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;


/**
 * Created by Евгений on 17.04.2016.
 */
public class WorkerFactory {
    /**
     * Добавление нового работника в базу данных
     * @param worker Новый работник
     */
    public static void add(Worker worker) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(worker);
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
     * Возвращает работника из базы данных по id
     * @param id Индентификационный номер работника
     * @return Работник с данным id
     */
    public static Worker get(int id) {
        Worker worker = null;
        Session session = null;
        try {

            session = HibernateConnector.getSessionFactory().openSession();
            worker = (Worker) session.get(Worker.class, id);
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            session.close();
        }

        return worker;
    }

    /**
     * Обновляет работника
     * @param worker
     */
    public static void update(Worker worker) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            Worker worker0 = (Worker) session.load(Worker.class, worker.getId());
            worker0.setName(worker.getName());
            worker0.setTLF(worker.getTLF());
            worker0.setR(worker.getColor().getRed());
            worker0.setG(worker.getColor().getGreen());
            worker0.setB(worker.getColor().getBlue());
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
     * Удаляет работника из базы данных по id
     * @param id Идентификационный номер работника
     */
    public static void delete(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(session.get(Worker.class, id));
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
     * Возвращает список всех работников, которые есть у этого графика
     * @param schedule график
     * @return список рабочих
     */
    public static List<Worker> getAllWorkers(Schedule schedule) {
        List<Worker> workers = null;

        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Worker.class);
            criteria.add(Restrictions.eq("schedule", schedule));
            workers = criteria.list();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }

        return workers;
    }
}
