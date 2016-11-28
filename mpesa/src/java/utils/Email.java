/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Joseph
 */
public class Email {

    public String sendEmail(String msg) throws MessagingException, UnsupportedEncodingException {

        final String username = "settlements+paysure.co.ke";
        final String password = "Notify2012";
        String smtp = "mail.paysure.co.ke";
        String from = "settlements@paysure.co.ke";
        String port = "26";
        String subject = "Mpesa PayBill Confirmation Notification";

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
        Message message = new MimeMessage(session);
        Multipart multipart = new MimeMultipart("alternative");
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(msg, "text/html");
        message.setContent(textPart, "text/html");
        message.setFrom(new InternetAddress(from, "M-pesa Module"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("josetosh06@yahoo.com"));
        message.setSubject(subject);
        Date dateStamp = new Date();
        message.setSentDate(dateStamp);
        Transport.send(message);
        return "Done";
    }
}
