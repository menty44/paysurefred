/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import paygate.objects.Config;
import paygate.objects.Purchase;

/**
 *
 * @author gachanja
 */
public class Email {

    public String sendEmail(Purchase purchase, String url) throws UnsupportedEncodingException {

        try {
            Email email = new Email();
            HashMap<String, String> configs = email.getConfigs();
            //String smtp = configs.get("smtphost2");
            final String username = configs.get("username2");
            final String password = configs.get("password2");
            //String from = configs.get("mailfrom2");
            //String port = configs.get("smtpport");
            //String subject = configs.get("subject2");
            //String subject=configs.get("Payment Link Request from"+purchase.getMerchantid().getName());

            String smtp = configs.get("smtphost2");
            //final String username=configs.get("username3");
            //final String password=configs.get("password3");
            String from = configs.get("mailfrom3");
            String port = configs.get("smtpport");
            String subject = configs.get("emailconfirmationsubject");

            Properties props = new Properties();
            props.put("mail.smtp.host", smtp);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            Double amt = Double.valueOf(purchase.getAmount()) / 100;
            String msg = "Dear " + purchase.getClientname() + "," + "<br><br>";
            msg = msg + "Your VISA payment, ref: " + purchase.getReferenceno() + " for "+purchase.getCurrency() + amt + " to " + purchase.getMerchantid().getName() + " has been processed.<br><br> Redirect URL Link :"+url;
            msg = msg + "Processed by...<br><br><img src='https://epayments.paysure.co.ke/logo/openmerchant/logo.png'>";
            Message message = new MimeMessage(session);
            //message.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            String textContent = "Visa Payment Notification Email";
            textPart.setText(textContent);

            MimeBodyPart htmlPart = new MimeBodyPart();
            //String htmlContent = "<html><h1>Hi</h1><p>Nice to meet you!</p></html>";
            htmlPart.setContent(msg, "text/html");

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            message.setFrom(new InternetAddress(from, "Paysure Limited"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(purchase.getEmail()));
            message.setSubject(subject + " " + purchase.getMerchantid().getName());
//          message.setText(msg);
            Date dateStamp = new Date();
            message.setSentDate(dateStamp);

            //message.setContent(msg, "text/html");         

            Transport.send(message);

            return "Done";

        } catch (MessagingException e) {
            return e.getMessage().toString();
            
            
//            throw new RuntimeException(e);
        }

    }

    private HashMap<String, String> getConfigs() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("iveriPU");
        EntityManager em = emf.createEntityManager();
        Iterator i = em.createNamedQuery("Config.findAll").getResultList().iterator();
        HashMap<String, String> configs = new HashMap<String, String>();
        while (i.hasNext()) {
            Config config = (Config) i.next();
            configs.put(config.getConfig(), config.getValue());
        }
        return configs;
    }
}
