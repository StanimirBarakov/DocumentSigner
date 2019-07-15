package com.axway.academy.model.dao;

import com.axway.academy.model.entity.DecisionType;
import com.axway.academy.model.entity.Document;
import com.axway.academy.model.exception.DocumentTypeException;
import com.axway.academy.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class DocumentDao {
    public Document saveDocument(Document document) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getHibernateSession();
            transaction = session.beginTransaction();
            session.persist(document);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return document;
    }

    public boolean deleteDocuments(final Session session, final List<Document> documents) {
        boolean deletedSuccessfully = false;
            if (documents != null) {
                for (Document doc: documents) {
                    session.delete(doc);
                }
                deletedSuccessfully = true;
            }
        return deletedSuccessfully;
    }


    public List<Document> listDocuments() {
        Session session = null;
        Transaction tx = null;
        List<Document> documents = null;
        try {
            session = HibernateUtil.getHibernateSession();
            tx = session.beginTransaction();
            documents = (List<Document>) session.createQuery("from documents").list();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return documents;
    }

    public List<Document> listRejectedDocuments(final Session session){
            return  session
                    .createQuery("from documents as d where d.decisionType = :type")
                    .setParameter("type", DecisionType.REJECTED)
                    .list();
    }
    public void signDocument(String name,byte[]publicKey,byte[]signature) throws DocumentTypeException {
        Session session = null;
        Document existingDocument = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getHibernateSession();
            transaction = session.beginTransaction();
            //if not existing it throws exception
            existingDocument = (Document) session
                    .createQuery("from documents where name = :name")
                    .setParameter("name", name).uniqueResult();
            existingDocument.setPublicKey(publicKey);
            existingDocument.setSignature(signature);
            existingDocument.setSignedType("SIGNED");
            session.update(existingDocument);
            transaction.commit();
        } catch (Exception e) {
            throw new DocumentTypeException("Not existing document!");
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
    public Document getDocumentByName(String name) {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Document> cr = cb.createQuery(Document.class);
        final Root<Document> root = cr.from(Document.class);
        Path path = root.get("name");
        cr.select(root).where(cb.equal(path, name));
        Query<Document> query = session.createQuery(cr);
        Document document = null;
        try {
            document = query.getSingleResult();

        }catch (NoResultException e ){

        }
        return document;
    }


}
