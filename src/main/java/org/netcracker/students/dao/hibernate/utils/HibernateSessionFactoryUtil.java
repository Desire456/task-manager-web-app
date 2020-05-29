package org.netcracker.students.dao.hibernate.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.model.User;

public class HibernateSessionFactoryUtil {
    private SessionFactory sessionFactory;

    private static HibernateSessionFactoryUtil instance;

    public static HibernateSessionFactoryUtil getInstance(){
        if(instance == null){
            instance = new HibernateSessionFactoryUtil();
        }
        return instance;
    }

    private HibernateSessionFactoryUtil() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Task.class);
        configuration.addAnnotatedClass(Journal.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
