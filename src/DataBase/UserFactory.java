package DataBase;

import Model.User;
import org.hibernate.Session;
import java.util.List;

/**
 * Created by Евгений on 17.04.2016.
 */
public class UserFactory {
    /**
     * Добавление нового пользователя в базу данных
     * @param user Новый пользователь
     */
    public static void add(User user) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
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
     * Возвращает пользователя из базы данных по id
     * @param id Индентификационный номер пользователя
     * @return Пользователь с данным id
     */
    public static User get(int id) {
        User user = null;
        Session session = null;
        try {

            session = HibernateConnector.getSessionFactory().openSession();
            user = (User) session.get(User.class, id);
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            session.close();
        }

        return user;
    }

    /**
     * Обновляет пользователя
     * @param user обновляемый пользователь
     */
    public static void update(User user) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            User user0 = (User) session.load(User.class, user.getId());
            user0.setLogin(user.getLogin());
            user0.setPassword(user.getPassword());
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
     * Удаляет пользователя из базы данных по id
     * @param id Идентификационный номер пользователя
     */
    public static void delete(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(session.get(User.class, id));
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

    public static List<User> getAllUsers() {
        List<User> users = null;

        Session session = null;
        try {
            session = HibernateConnector.getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();
        }
        catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println();
        }
        finally {
            if (session != null && session.isOpen()) session.close();
        }

        return users;
    }
}
