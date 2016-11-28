/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import pg.objects.*;

/**
 *
 * @author Joseph
 */
public class Utilities {
    
    @EJB
    private ConfigFacade configFacade;
    
    public ConfigFacade getConfigFacade() {
        return configFacade;
    }
    
    public HashMap insertConfigs(HashMap configs) {
        List<Config> configurations = getConfigFacade().findAll();
        System.out.println(configurations.size());
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
        String filename = "https://epayments.co.ke/home/admin/files/paysure_" + sdf.format(new Date()) + ".csv";
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
    
    public Date getEndTime() {
        Calendar calendar = this.getFourPm();
        return calendar.getTime();
    }
    
    public BigDecimal computePaysureCommission(Merchant merchant, Transaction transaction, HashMap configs) {
        Rate rate = new Rate();
        rate = merchant.getRateid();
        BigDecimal paysureCommission = new BigDecimal(transaction.getCommission()).divide(new BigDecimal(rate.getMerchantrate()), 40, RoundingMode.HALF_EVEN).multiply(new BigDecimal(rate.getPaysurerate()));
        return paysureCommission;
    }
    
    public BigDecimal computeChasebankCommission(Merchant merchant, Transaction transaction, HashMap configs) {
        Rate rate = new Rate();
        rate = merchant.getRateid();
        BigDecimal chasebankRate = BigDecimal.valueOf(Double.parseDouble(configs.get("chasebankcommission").toString()));
        BigDecimal chasebankCommission = new BigDecimal(transaction.getCommission()).divide(new BigDecimal(rate.getMerchantrate()), 40, RoundingMode.HALF_EVEN).multiply(chasebankRate);
        return chasebankCommission;
    }

    public BigDecimal computeKenswitchCommission(Merchant merchant, Transaction transaction, HashMap configs) {
        Rate rate = new Rate();
        rate = merchant.getRateid();
    
        BigDecimal kenswitchRate = BigDecimal.valueOf(Double.parseDouble(configs.get("kenswitchcommission").toString()));
        BigDecimal kenswitchCommission = new BigDecimal(transaction.getCommission()).divide(new BigDecimal(rate.getMerchantrate()), 40, RoundingMode.HALF_EVEN).multiply(kenswitchRate);
        return kenswitchCommission;
    }
    
    public void sendEmail(String filename, HashMap configs, BigDecimal mt, int num) throws Exception{
        
        File file = new File(filename);
        BufferedReader CSVFile = new BufferedReader(new FileReader(file));
        String dataRow = CSVFile.readLine();
        String content="";
        while (dataRow != null) {
            String[] dataArray = dataRow.split(",");
            for (String item : dataArray) {
                System.out.print(item + ",");
                content=content+item+",";
            }
            System.out.println(); // Print the data line.
            content=content+"\n";
            dataRow = CSVFile.readLine(); // Read next line of data.
        }
        System.out.println(content);
        
        
         // Emailing Part
        final String username = (String) configs.get("username2");
        final String password = (String) configs.get("password2");
        String smtp = (String) configs.get("smtphost2");
        String from = (String) configs.get("mailfrom3");
        String port = (String) configs.get("smtpport");
        String subject = (String) configs.get("subject2");

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
        String textContent = "Total Number of Transactions Processed: "+num+"\n"+"Total for All Merchants Transactions Processed: "+mt+"\n"+"CSV Filename: "+file.getName();
        textPart.setText(textContent);

        MimeBodyPart htmlPart = new MimeBodyPart();
        //String msg = (String) configs.get("message");
        //htmlPart.setContent(msg, "text/html"); 
        //htmlPart.attachFile(filename);
        FileDataSource fds=new FileDataSource(filename);
        htmlPart.setDataHandler(new DataHandler(fds));
        htmlPart.setFileName(fds.getName());

        MimeBodyPart finalPart = new MimeBodyPart();
        String fmsg="Bye";
        finalPart.setText(fmsg);
        
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(htmlPart);
        //multipart.addBodyPart(finalPart);
        message.setContent(multipart);

        message.setFrom(new InternetAddress(from, "Paysure Limited"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(configs.get("address2").toString()));
        message.setSubject(subject);
        Date dateStamp = new Date();
        message.setSentDate(dateStamp);
        Transport.send(message);        
    }        
}
