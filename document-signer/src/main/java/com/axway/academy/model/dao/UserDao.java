package com.axway.academy.model.dao;

import com.axway.academy.model.entity.User;
import com.axway.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class UserDao {


    public User registerUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getHibernateSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    public User getUserByEmail(String email) {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<User> cr = cb.createQuery(User.class);
        final Root<User> root = cr.from(User.class);
        Path path = root.get("email");
        cr.select(root).where(cb.equal(path, email));
        Query<User> query = session.createQuery(cr);
        User user = null;
        try {
            user = query.getSingleResult();

        }catch (NoResultException e ){

        }
        return user;
    }
}
