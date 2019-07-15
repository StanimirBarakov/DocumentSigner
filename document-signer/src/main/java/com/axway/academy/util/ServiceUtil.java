package com.axway.academy.util;

import com.axway.academy.model.dao.DocumentDao;
import com.axway.academy.model.entity.Document;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServiceUtil extends Thread {

    private static String DOCUMENT_SIGNER_FOLDER = System.getProperty("user.home") + File.separator +
            "DOCUMENT_SIGNER_ROOT";

    public void run() {
        DocumentDao documentDao = new DocumentDao();
        Transaction tx = null;
        Session session = null;
        while (true) {
            System.out.println("I'm starting");

            try {
                session = HibernateUtil.getHibernateSession();
                tx = session.beginTransaction();
                List<Document> list = documentDao.listRejectedDocuments(session);
                documentDao.deleteDocuments(session, list);
                for (Document document : list) {
                    File file = new File(DOCUMENT_SIGNER_FOLDER + File.separator + document.getName());
                    file.delete();
                }
                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(200);
            }catch(InterruptedException e){
                //
            }
        }

    }
}