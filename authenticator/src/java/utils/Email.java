/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import objects.Config;
import objects.Purchase;

/**
 *
 * @author gachanja
 */
public class Email {

    public String sendEmail(Purchase purchase, String to, String refno, String tdt) {

        Email email = new Email();
        HashMap<String, String> configs = email.getConfigs();
        String smtp = configs.get("smtphost2");
        final String username = configs.get("username2");
        final String password = configs.get("password2");
        String from = configs.get("mailfrom2");
        String port = configs.get("smtpport");
        String msg = configs.get("message2");
        String subject = configs.get("subject2");

        Properties props = new Properties();
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            msg= msg + refno + "&tdt=" + tdt;
            msg = msg + "\n" + "To view your invoice and track your goods please use the following link \n" + purchase.getMerchantid().getReturnurl();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

            return "Done";

        } catch (MessagingException e) {
            return e.getMessage().toString();
//            throw new RuntimeException(e);
        }

    }

    private HashMap<String, String> getConfigs() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("authenticatorPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Iterator i = em.createNamedQuery("Config.findAll").getResultList().iterator();
        HashMap<String, String> configs = new HashMap<String, String>();
        while(i.hasNext()){
            Config config = (Config) i.next();
            configs.put(config.getConfig(), config.getValue());
        }
        em.close();
        return configs;
    }
//    private String getValue(String config) {
//        Config conf;
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("authenticatorPU");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        conf = (Config) em.createNamedQuery("Config.findByConfig").setParameter("config", config).getSingleResult();
//        em.close();
//        return conf.getValue().toString();
//    }
}
