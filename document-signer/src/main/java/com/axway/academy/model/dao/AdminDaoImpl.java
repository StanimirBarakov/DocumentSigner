package com.axway.academy.model.dao;

import com.axway.academy.model.dto.UpdateDocumentDTO;
import com.axway.academy.model.entity.Document;
import com.axway.academy.model.exception.DocumentTypeException;
import com.axway.academy.util.HibernateUtil;
import com.axway.academy.util.MailSender;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdminDaoImpl implements AdminDao {

    @Override
    public List<Document> getAllFiles() {
        Session session = null;

        List<Document> documents = null;
        try {
            session = HibernateUtil.getHibernateSession();
            documents = (List<Document>) session.createQuery("from documents").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return documents;
    }

    @Override
    public void updateDocumentType(UpdateDocumentDTO documentDTO) throws DocumentTypeException {
        Session session = null;
        Document existingDocument = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getHibernateSession();
            transaction = session.beginTransaction();
            //if not existing it throws exception
            existingDocument = (Document) session
            .createQuery("from documents where id = :id")
            .setParameter("id", documentDTO.getId()).uniqueResult();
            existingDocument.setDecisionType(documentDTO.getDecisionType());
            session.update(existingDocument);
            transaction.commit();
            MailSender sender = new MailSender();
            sender.sendMail(existingDocument.getUser().getEmail(),documentDTO.getDecisionType().name());
        } catch (Exception e) {
            throw new DocumentTypeException("Not existing document!");
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


}
