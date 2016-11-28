/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import pg.objects.*;
import pg.utils.Utilities;

/**
 *
 * @author Joseph
 */
@Singleton
@LocalBean
public class Tset {
    @EJB
    private TransactionfileFacade transactionfileFacade;
    @EJB
    private ConfigFacade configFacade;
    @EJB
    private StatusFacade statusFacade;
    
    Status status;
    HashMap configs = new HashMap(30);

    public TransactionfileFacade getTransactionfileFacade() {
        return transactionfileFacade;
    }
        
    public ConfigFacade getConfigFacade() {
        return configFacade;
    }

    public StatusFacade getStatusFacade() {
        return statusFacade;
    }

    @Schedule(minute = "*", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "9-17", dayOfWeek = "Mon-Fri")
    public void myTimer() throws IOException, Exception {
        System.out.println("Timer event from Test Class: " + new Date());
        // Declaring variables
        String extra = "";
        int not=0;    // Number of Transactions Processed
        BigDecimal merchantTotal = new BigDecimal(0);
        BigDecimal mtf = new BigDecimal(0);
        BigDecimal paysureTotal = new BigDecimal(0);
        BigDecimal chasebankTotal = new BigDecimal(0);
        BigDecimal kenswitchTotal = new BigDecimal(0);
        Utilities utils = new Utilities();
        String filename = utils.getFileName();
        System.out.println(filename);
        extra = extra + "Filename: " + filename + "\n";
        //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        FileWriter writer = new FileWriter(filename);
        System.out.println("here");
        HashMap configs = new HashMap(30);
        List<Config> configurations = getConfigFacade().findAll();
        System.out.println(configurations.size());
        Iterator i = configurations.iterator();
        while (i.hasNext()) {
            Config config = new Config();
            config = (Config) i.next();
            configs.put(config.getConfig(), config.getValue());
        }
        System.out.println(configs);
        System.out.println("am here");
        Transaction transaction = new Transaction();
        BigDecimal eft = new BigDecimal(0);
        Status statusClosed = new Status(0);
        Transactionfile transactionfile = new Transactionfile();
        // retrieving configurations
        //configs = utils.insertConfigs(configs);
        System.out.println(configs);
        // setting search parameters
        Date endtime = utils.getEndTime();
        System.out.println("End Time" + endtime);
        Date currentTime = utils.getCurrentDate();
        System.out.println("Current Time" + currentTime);
        Status status = getStatusFacade().find(5);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dailyProcessorWebPU");
        EntityManager em = emf.createEntityManager();
        List<Object[]> results = em.createNamedQuery("Transaction.findAll").setParameter("status", status).getResultList();
        System.out.println(results.size());
        System.out.println(status + "" + status.getCode());

        for (Object[] result : results) {
            String mname = (String) result[0];
            System.out.println(mname);
            BigInteger mt = (BigInteger) result[1];
            BigDecimal mtotal = (new BigDecimal(mt));
            System.out.println("Merchant Name:" + mname + "===Merchant Total:" + mtotal);
            Merchant m = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("mname", mname).getSingleResult();

            BigDecimal meft = new BigDecimal(m.getEft());
            System.out.println(meft);

            if (mtotal.compareTo(meft) > 0) {

                System.out.println("You can process merchant " + mname);
                Query query = em.createNamedQuery("Transaction.findMerchantTransactions");
                query.setParameter("status", status);
                query.setParameter("merchantname", mname);
                query.setParameter("cardtype", "kenswitch");
                List<Transaction> transactions = query.getResultList();
                System.out.println(mname + " has " + transactions.size() + " transactions processed");

                for (Transaction t : transactions) {

                    merchantTotal = merchantTotal.add(new BigDecimal(t.getAmount()).subtract(new BigDecimal(t.getCommission())));
                    paysureTotal = paysureTotal.add(utils.computePaysureCommission(t.getMerchantid(), t, configs));
                    chasebankTotal = chasebankTotal.add(utils.computeChasebankCommission(t.getMerchantid(), t, configs));
                    kenswitchTotal = kenswitchTotal.add(utils.computeKenswitchCommission(t.getMerchantid(), t, configs));

                    System.out.println(t.getMerchantname() + "," + t.getMerchantid().getBankaccount() + "," + t.getAmount().subtract(t.getCommission()) + "," + t.getRefno() + "," + t.getMerchantid().getBankcode() + "," + t.getMerchantid().getBranchcode());
                    System.out.println(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode"));
                    System.out.println(configs.get("chasebankmerchantname") + "," + configs.get("chasebankbankaccount") + "," + utils.computeChasebankCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("chasebankbankcode") + "," + configs.get("chasebankbankbranchcode"));
                    System.out.println(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n");

                    //out.print(t.getMerchantname() + "," + t.getMerchantid().getBankaccount() + "," + t.getAmount().subtract(t.getCommission()) + "," + t.getRefno() + "," + t.getMerchantid().getBankcode() + "," + t.getMerchantid().getBranchcode() + "\n");
                    //out.print(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode") + "\n");
                    //out.print(configs.get("chasebankmerchantname") + "," + configs.get("chasebankbankaccount") + "," + utils.computeChasebankCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("chasebankbankcode") + "," + configs.get("chasebankbankbranchcode") + "\n");
                    //out.print(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n\n");

                    writer.append(t.getMerchantname() + "," + t.getMerchantid().getBankaccount() + "," + t.getAmount().subtract(t.getCommission()) + "," + t.getRefno() + "," + t.getMerchantid().getBankcode() + "," + t.getMerchantid().getBranchcode() + "\n");
                    writer.append(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode") + "\n");
                    writer.append(configs.get("chasebankmerchantname") + "," + configs.get("chasebankbankaccount") + "," + utils.computeChasebankCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("chasebankbankcode") + "," + configs.get("chasebankbankbranchcode") + "\n");
                    writer.append(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n\n");
                    mtf=mtf.add(merchantTotal);
                    not++;
                    //em.getTransaction().begin();
                    //t.setProcessed(Boolean.TRUE);
                    //t.setThreshold(Boolean.TRUE);
                    //em.merge(t);
                    //em.getTransaction().commit();
                    //out.close();                    
                    //writer.flush();
                    //writer.close();                    

                    System.out.println("Bytes : " + filename.getBytes());

                }

            } else {


                Query query = em.createNamedQuery("Transaction.findMerchantTransactions");
                query.setParameter("status", status);
                query.setParameter("merchantname", mname);
                query.setParameter("cardtype", "kenswitch");
                List<Transaction> transactions = query.getResultList();

                for (Transaction t : transactions) {

                    paysureTotal = paysureTotal.add(utils.computePaysureCommission(t.getMerchantid(), t, configs));
                    kenswitchTotal = kenswitchTotal.add(utils.computeKenswitchCommission(t.getMerchantid(), t, configs));

                    writer.append(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode") + "\n");
                    writer.append(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(t.getMerchantid(), t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n\n");

                    em.getTransaction().begin();
                    t.setProcessed(Boolean.TRUE);
                    em.merge(t);
                    em.getTransaction().commit();

                }

                System.out.println("No entries generated for merchant " + mname + " because the total is less than the threshold amount");
            }

        }
        writer.flush();
        writer.close();
        
        //settting record values        
        transactionfile.setFiledate(endtime);
        //transactionfile.setTransactions(numberOfTransactions);
        transactionfile.setMerchant(merchantTotal.toBigInteger());
        transactionfile.setKenswitch(kenswitchTotal.toBigInteger());
        transactionfile.setChase(chasebankTotal.toBigInteger());
        transactionfile.setPaysure(paysureTotal.toBigInteger());
        transactionfile.setFilename(filename);
        transactionfile.setFiledate(currentTime);
        transactionfile.setCreated(currentTime);
        transactionfile.setModified(currentTime);
        
        // saving to database
        getTransactionfileFacade().create(transactionfile);
        
        
        
        
        utils.sendEmail(filename, configs, mtf, not);
        System.out.println("The End");
        System.out.println(not);
        System.out.println(mtf);






    }
}
