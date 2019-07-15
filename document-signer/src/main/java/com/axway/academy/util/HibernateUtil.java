package com.axway.academy.util;

import com.axway.academy.model.entity.Document;
import com.axway.academy.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static Session getHibernateSession() {
        final SessionFactory sf = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class).addAnnotatedClass(Document.class).buildSessionFactory();
        final Session session = sf.openSession();
        return session;
    }
}