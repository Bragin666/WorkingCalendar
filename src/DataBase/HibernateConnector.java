package DataBase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Евгений on 22.05.2016.
 */
public class HibernateConnector {
    /** Параметр - фабрика сессий */
    private static SessionFactory sessionFactory;

    private HibernateConnector() {}

    /** Осуществляет подключение к базе данных */
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Закрывает фабрику сессий
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
