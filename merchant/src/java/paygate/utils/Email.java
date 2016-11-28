/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.utils;

import com.sun.mail.smtp.SMTPMessage;
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

    public String sendEmail(Purchase purchase) throws UnsupportedEncodingException {

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

            
            String smtp=configs.get("smtphost2");
            //final String username=configs.get("username3");
            //final String password=configs.get("password3");
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
            String mname=purchase.getMerchantid().getName().toLowerCase();
            String urlpart=mname.replace("+", "%20");
            String msg = "<img align='right' src='https://epayments.paysure.co.ke/logo/"+mname.replaceAll("\\s", "") +".png'/><br><br><br><br><br>";
            msg = msg + "Dear "+purchase.getClientname()+",<br><br>You have received a payment link request from <strong>" + purchase.getMerchantid().getName() + "</strong>. Please select one of the options below to make payment using your credit or debit card.<br><br>";                    
            //msg = msg + "Choose your <a href='https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=00&tid92012=Paysure@Ltd&mid=" + purchase.getMerchantid().getMerchantidentifier() + "&mname=" + purchase.getMerchantid().getName() + "&tamt=" + purchase.getAmount() + "&recinst=&trace=" + purchase.getSystemtraceno() + "&reference=" + purchase.getReferenceno() + "&tdt=" + purchase.getTransmission() + "&ldate=" + purchase.getTransmission().substring(0, 4) + "&ltime=" + purchase.getTransmission().substring(4, purchase.getTransmission().length()) + "&anonymous=&email="+purchase.getMerchantid().getEmail()+"'><strong>KENSWITCH</strong></a> or";
            //msg = msg + "<a href='http://196.216.64.237:8080/iveri/iveritest.jsp?tamt="+purchase.getAmount()+"&reference="+purchase.getReferenceno()+"'><strong>VISA</strong></a> card to settle the payment request.<br>";
            msg = msg + "<strong>DETAILS</strong><br><hr>";
            
            //msg = msg + "Boolean USD" +purchase.getMerchantid().getUsd();
            //msg = msg + "USD ID" + purchase.getMerchantid().getLiveidusd()+ "Currency"+purchase.getCurrency();
            
            /*if(purchase.getCurrency().compareTo("USD")==0){
                msg = msg + "Currency Check!!"+ " <a href='https://epayments.paysure.co.ke/visa/visaepay.jsp?appid="+purchase.getMerchantid().getLiveidusd()+"&tamt="+purchase.getAmount()+"&reference="+purchase.getReferenceno()+"&email=" + purchase.getMerchantid().getEmail() + "&desc=" + purchase.getDescription() + "&mname=" + purchase.getMerchantid().getName()+"'> Pay using your Visa/Mastercard Card <img src='http://196.216.64.237/logo/visa-mastercard.png' alt='Visa' /></a><br><p>";
            }*/
            
            Double amt=Double.valueOf(purchase.getAmount())/100;
            msg = msg + "<strong>DESCRIPTION:</strong> " + purchase.getDescription() + "<br>";
            /*if(purchase.getMerchantid().getLiveidusd().compareTo("USD")==0){
                msg = msg + "<strong>AMOUNT: USD </strong> " + amt + "<hr><br>";
            }else{
                msg = msg + "<strong>AMOUNT: KES/USD </strong> " + amt + "<hr><br>";
            }*/
            if(purchase.getCurrency().compareTo("USD")==0){
                msg = msg + "<strong>AMOUNT: USD </strong> " + amt + "<hr><br>";                
            }else{
                msg = msg + "<strong>AMOUNT: KES </strong> " + amt + "<hr><br>";
            }            
            msg = msg + "For more information please contact: <strong> "+ purchase.getMerchantid().getContactperson()+"</strong> from <strong>"+purchase.getMerchantid().getName()+"</strong> online merchant via email: <strong>" +purchase.getMerchantid().getEmail()+"</strong>"+" or via mobile number: <strong>"+purchase.getMerchantid().getMobile()+".</strong><br><br>";
            
            msg = msg + "<p style='text-indent: 1em;'><b>Payment Options</b></p>";
            //purchase.getMerchantid().getLiveid();
            
            if(purchase.getCurrency().compareTo("USD")==0){
                msg = msg + " <a href='https://epayments.paysure.co.ke/visa/visaepay.jsp?appid="+purchase.getMerchantid().getLiveidusd()+"&tamt="+purchase.getAmount()+"&reference="+purchase.getReferenceno()+"&email=" + purchase.getMerchantid().getEmail() + "&desc=" + purchase.getDescription() + "&mname=" + purchase.getMerchantid().getName()+"'> Pay using your Visa/Mastercard Card <img src='http://196.216.64.237/logo/visa-mastercard.png' alt='Visa' /></a><br><p>";
            }else{
                msg = msg + " <a href='https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=00&tid92012=Paysure@Ltd&mid=" + purchase.getMerchantid().getMerchantidentifier() + "&mname=" + purchase.getMerchantid().getName() + "&tamt=" + purchase.getAmount()/100 + "&recinst=&trace=" + purchase.getSystemtraceno() + "&reference=" + purchase.getReferenceno() + "&tdt=" + purchase.getTransmission() + "&ldate=" + purchase.getTransmission().substring(0, 4) + "&ltime=" + purchase.getTransmission().substring(4, purchase.getTransmission().length()) + "&anonymous=&email="+purchase.getMerchantid().getEmail()+"'>Pay using your Kenswitch Card <img src='http://196.216.64.237/logo/kenswitch.png' alt='Kenswitch' /></a><br><br> <p style='text-indent: 8em;'>or</p><br>";
                msg = msg + " <a href='https://epayments.paysure.co.ke/visa/visaepay.jsp?appid="+purchase.getMerchantid().getLiveid()+"&tamt="+purchase.getAmount()+"&reference="+purchase.getReferenceno()+"&email=" + purchase.getMerchantid().getEmail() + "&desc=" + purchase.getDescription() + "&mname=" + purchase.getMerchantid().getName()+"'> Pay using your VISA/MasterCard Card <img src='http://196.216.64.237/logo/visa-mastercard.png' alt='Visa' /></a><br><p>";
            }
                   
            //msg = msg + " <a href='https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=00&tid92012=Paysure@Ltd&mid=" + purchase.getMerchantid().getMerchantidentifier() + "&mname=" + purchase.getMerchantid().getName() + "&tamt=" + purchase.getAmount()/100 + "&recinst=&trace=" + purchase.getSystemtraceno() + "&reference=" + purchase.getReferenceno() + "&tdt=" + purchase.getTransmission() + "&ldate=" + purchase.getTransmission().substring(0, 4) + "&ltime=" + purchase.getTransmission().substring(4, purchase.getTransmission().length()) + "&anonymous=&email="+purchase.getMerchantid().getEmail()+"'>Pay using your Kenswitch Card <img src='http://196.216.64.237/logo/kenswitch.png' alt='Kenswitch' /></a><br><br> <p style='text-indent: 8em;'>or</p><br>";
            //msg = msg + " <a href='https://epayments.paysure.co.ke/visa/visaepay.jsp?appid="+purchase.getMerchantid().getLiveid()+"&tamt="+purchase.getAmount()+"&reference="+purchase.getReferenceno()+"&email=" + purchase.getMerchantid().getEmail() + "&desc=" + purchase.getDescription() + "&mname=" + purchase.getMerchantid().getName()+"'> Pay using your Visa/Mastercard Card <img src='http://196.216.64.237/logo/visa-mastercard.png' alt='Visa' /></a><br><p>";
            
            Message message = new MimeMessage(session);
            //message.setContent(msg, "text/html");            
            
            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            String textContent = "Payment Request";
            textPart.setText(textContent);
            
            MimeBodyPart htmlPart = new MimeBodyPart();
            //String htmlContent = "<html><h1>Hi</h1><p>Nice to meet you!</p></html>";
            htmlPart.setContent(msg, "text/html");
                        
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart, "text/html");            
            
            message.setFrom(new InternetAddress(from,"Paysure Limited"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(purchase.getEmail()));
            message.setSubject(subject+" "+purchase.getMerchantid().getName());
//            message.setText(msg);
            Date dateStamp=new Date();
            message.setSentDate(dateStamp);
            
            //message.setContent(msg, "text/html");         

            //Transport.send(message);
            
            
            Transport t = session.getTransport("smtp");
            t.connect("mail.paysure.co.ke", "settlements+paysure.co.ke", "Notify2012");
            t.sendMessage(message, InternetAddress.parse(purchase.getEmail()));
            t.close();
            //SMTPMessage smtpm = new SMTPMessage(message);

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
