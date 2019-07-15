package com.axway.academy.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailSender {
    final String username = "stavri69@yahoo.com";
    final String password = "knmmskfplhkqxakp";
    private Properties prop;
    private Session session;
    private MimeMessage msg;

   public MailSender() {
       this.prop = new Properties();
        prop.put("mail.smtp.host", "smtp.mail.yahoo.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
        public void sendMail(String receiver,String decision){
            try {

               this.msg = new MimeMessage(this.session);
                msg.setFrom(new InternetAddress("stavri69@yahoo.com"));
                msg.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(receiver)
                );
                msg.setSubject("Decision type!");
                msg.setText("Your document was " + decision);

                Transport.send(msg);

                System.out.println("Done");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
