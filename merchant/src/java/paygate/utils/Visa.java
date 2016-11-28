/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
public class Visa {

    public String sendEmail(Purchase purchase) {

        try {
            Visa visa = new Visa();
            HashMap<String, String> configs = visa.getConfigs();
            //String smtp = configs.get("smtphost2");
            //final String username = configs.get("username2");
            //final String password = configs.get("password2");
            //String from = configs.get("mailfrom2");
            //String port = configs.get("smtpport");
            //String subject = configs.get("subject2");
            //String subject=configs.get("Payment Link Request from"+purchase.getMerchantid().getName());

            
            String smtp=configs.get("smtphost2");
            final String username=configs.get("username3");
            final String password=configs.get("password3");
            String from=configs.get("mailfrom3");
            String port=configs.get("smtpport");
            String subject=configs.get("subject3");            
            
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
            String msg = "Dear "+purchase.getClientname()+",<br><br>You have received a payment link request from <strong>" + purchase.getMerchantid().getName() + "</strong>.<br><br>Please click on the link below to make the payment.<br><br>";                    
            msg = msg + "Click <a href='https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=00&tid92012=Paysure@Ltd&mid=" + purchase.getMerchantid().getMerchantidentifier() + "&mname=" + purchase.getMerchantid().getName() + "&tamt=" + purchase.getAmount() + "&recinst=&trace=" + purchase.getSystemtraceno() + "&reference=" + purchase.getReferenceno() + "&tdt=" + purchase.getTransmission() + "&ldate=" + purchase.getTransmission().substring(0, 4) + "&ltime=" + purchase.getTransmission().substring(4, purchase.getTransmission().length()) + "&anonymous=&email="+purchase.getMerchantid().getEmail()+"'><strong>HERE</strong></a> to pay<br><br>";
            msg = msg + "<strong>DETAILS</strong><p><hr><br>";
            msg = msg + "<strong>DESCRIPTION:</strong> " + purchase.getDescription() + "<br>";
            msg = msg + "<strong>AMOUNT:</strong> " + purchase.getAmount() + "<br><br>";
            msg = msg + "For more information please contact: <strong> "+ purchase.getMerchantid().getContactperson()+"</strong> from <strong>"+purchase.getMerchantid().getName()+"</strong> online merchant via email: <strong>" +purchase.getMerchantid().getEmail()+"</strong>"+" or via mobile number: <strong>"+purchase.getMerchantid().getMobile()+".</strong>";

            Message message = new MimeMessage(session);
            //message.setContent(msg, "text/html");
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(purchase.getEmail()));
            message.setSubject(subject+" "+purchase.getMerchantid().getName());
//            message.setText(msg);
            Date dateStamp=new Date();
            message.setSentDate(dateStamp);
            
            message.setContent(msg, "text/html");         

            Transport.send(message);

            return "Done";

        } catch (MessagingException e) {
            return e.getMessage().toString();
//            throw new RuntimeException(e);
        }

    }

    private HashMap<String, String> getConfigs() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("merchantPU");
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
