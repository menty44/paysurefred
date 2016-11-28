/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pg.objects.Config;
import pg.objects.Merchant;
import pg.objects.Rate;
import pg.objects.Transaction;

/**
 *
 * @author gachanja
 */
public class Utilities {

    public HashMap insertConfigs(HashMap configs) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
        EntityManager em = emf.createEntityManager();
        List<Config> configurations = (List<Config>) em.createNamedQuery("Config.findAll").getResultList();
        Iterator i = configurations.iterator();
        while (i.hasNext()) {
            Config config = new Config();
            config = (Config) i.next();
            configs.put(config.getConfig(), config.getValue());
        }
        return configs;
    }

    public Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        return currentTime;
    }

    public String getFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String filename = "C://files/paysure_" + sdf.format(new Date()) + ".csv";
        return filename;
    }

    public Calendar getFourPm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public Date getStartTime() {
        Calendar calendar = this.getFourPm();
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date yesterdayFourPm = calendar.getTime();
        return yesterdayFourPm;
    }
    
    public Date getEndTime() {
        Calendar calendar = this.getFourPm();
        return calendar.getTime();
    }


    public BigDecimal computePaysureCommission(Merchant merchant, Transaction transaction, HashMap configs) {
        Rate rate = new Rate();
        rate = merchant.getRate();
        BigDecimal paysureCommission = transaction.getCommission().divide(rate.getMerchantrate(), 40, RoundingMode.HALF_EVEN).multiply(rate.getPaysurerate());
        return paysureCommission;
    }

    public BigDecimal computeChasebankCommission(Merchant merchant, Transaction transaction, HashMap configs) {
        Rate rate = new Rate();
        rate = merchant.getRate();
        BigDecimal chasebankRate = BigDecimal.valueOf(Double.parseDouble(configs.get("chasebankcommission").toString()));
        BigDecimal chasebankCommission = transaction.getCommission().divide(rate.getMerchantrate(), 40, RoundingMode.HALF_EVEN).multiply(chasebankRate);
        return chasebankCommission;
    }

    public BigDecimal computeKenswitchCommission(Merchant merchant, Transaction transaction, HashMap configs) {
        Rate rate = new Rate();
        rate = merchant.getRate();
    
        BigDecimal kenswitchRate = BigDecimal.valueOf(Double.parseDouble(configs.get("kenswitchcommission").toString()));
        BigDecimal kenswitchCommission = transaction.getCommission().divide(rate.getMerchantrate(), 40, RoundingMode.HALF_EVEN).multiply(kenswitchRate);
        return kenswitchCommission;
    }

    public void sendEmail(String filename, HashMap configs) throws Exception{
        Properties props = System.getProperties();
        props.put("mail.smtp.host", configs.get("smtphost"));
        Session session = Session.getInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress((String) configs.get("mailfrom")));
        InternetAddress[] address = {new InternetAddress((String) configs.get("address1"))};
        InternetAddress[] address2 = {new InternetAddress((String) configs.get("address2"))};
        message.setRecipients(Message.RecipientType.TO, address);
        message.setRecipients(Message.RecipientType.CC, address2);
        message.setSubject((String) configs.get("subject"));
        MimeBodyPart messageText = new MimeBodyPart();
        messageText.setText((String) configs.get("message"));
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(filename);
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(messageText);
        mp.addBodyPart(attachment);
        message.setContent(mp);
        message.setSentDate(new Date());
        Transport.send(message);
    }
}
