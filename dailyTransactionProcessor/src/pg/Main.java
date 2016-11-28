package pg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import pg.objects.Merchant;
import pg.objects.Status;
import pg.objects.Transaction;
import pg.objects.Transactionfile;
import pg.objects.TransactionfileJpaController;
import pg.utils.Utilities;
import pg.objects.Config;

/**
 * @author gachanja
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // Declaring variables
        BigDecimal merchantTotal = new BigDecimal(0);
        BigDecimal paysureTotal = new BigDecimal(0);
        BigDecimal chasebankTotal = new BigDecimal(0);
        BigDecimal kenswitchTotal = new BigDecimal(0);
        Utilities utils = new Utilities();
        String filename = utils.getFileName();
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        HashMap configs = new HashMap(30);
        Transaction transaction = new Transaction();
        //Merchant merchant = new Merchant();
        BigDecimal eft = new BigDecimal(0);
        Status statusClosed = new Status(0);
        //String CODE=(String)configs.get("chasebankbankcode").toString();
        Transactionfile transactionfile = new Transactionfile();
        TransactionfileJpaController transactionfileController = new TransactionfileJpaController();

        // connecting to database
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
        EntityManager em = emf.createEntityManager();

        // retrieving configurations
        configs = utils.insertConfigs(configs);

        // setting search parameters
        Date endtime = utils.getEndTime();
//        Date starttime = utils.getStartTime();
//        System.out.println("START TIME IS: " + starttime + " ENDTIME IS:" + endtime);
        Date currentTime = utils.getCurrentDate();
        Query query = em.createNamedQuery("Status.findById");
        query.setParameter("id", 5);
        Status status = (Status) query.getSingleResult();

        // retrieving data
        //query = em.createNamedQuery("Transaction.findAll");
        //query.setParameter("status", status);
        //List<Transaction> transactions = query.getResultList();

        //System.out.println(transactions);
        //Iterator i = transactions.iterator();
        
        List<Object[]> results = em.createNamedQuery("Transaction.findAll").setParameter("status", status).getResultList();
        for (Object[] result : results) {
            String mname = (String) result[0];
            //System.out.println(mname);            
            BigDecimal mtotal = (BigDecimal) result[1];
            //System.out.println("Merchant Name:" + mname + "===Merchant Total:" + mtotal);
            Merchant m = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mname).getSingleResult();
            BigDecimal meft = m.getEft();
            //System.out.println(meft);
            if (mtotal.compareTo(meft) > 0) {
                //System.out.println("You can process merchant " + mname);
                query = em.createNamedQuery("Transaction.findMerchantTransactions");
                query.setParameter("status", status);
                query.setParameter("merchantname", mname);
                query.setParameter("cardtype", "kenswitch");
                List<Transaction> transactions = query.getResultList();
                System.out.println(mname + " has " + transactions.size() + " transactions processed");
                for (Transaction t : transactions) {
                    
                    merchantTotal = merchantTotal.add(t.getAmount().subtract(t.getCommission()));
                    paysureTotal = paysureTotal.add(utils.computePaysureCommission(t.getMerchant(), t, configs));
                    chasebankTotal = chasebankTotal.add(utils.computeChasebankCommission(t.getMerchant(), t, configs));
                    kenswitchTotal = kenswitchTotal.add(utils.computeKenswitchCommission(t.getMerchant(), t, configs));

                    System.out.println(t.getMerchantname() + "," + t.getMerchant().getBankaccount() + "," + t.getAmount().subtract(t.getCommission()) + "," + t.getRefno() + "," + t.getMerchant().getBankcode() + "," + t.getMerchant().getBranchcode());
                    System.out.println(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode"));
                    System.out.println(configs.get("chasebankmerchantname") + "," + configs.get("chasebankbankaccount") + "," + utils.computeChasebankCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("chasebankbankcode") + "," + configs.get("chasebankbankbranchcode"));
                    System.out.println(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n");

                    out.print(t.getMerchantname() + "," + t.getMerchant().getBankaccount() + "," + t.getAmount().subtract(t.getCommission()) + "," + t.getRefno() + "," + t.getMerchant().getBankcode() + "," + t.getMerchant().getBranchcode() + "\n");
                    out.print(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode") + "\n");
                    out.print(configs.get("chasebankmerchantname") + "," + configs.get("chasebankbankaccount") + "," + utils.computeChasebankCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("chasebankbankcode") + "," + configs.get("chasebankbankbranchcode") + "\n");
                    out.print(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n\n");

                    em.getTransaction().begin();
                    //t.setProcessed(Boolean.TRUE);
                    //t.setThreshold(Boolean.TRUE);
                    em.merge(t);
                    em.getTransaction().commit();

                    //out.close();
                }

            } else {
                
                
                query = em.createNamedQuery("Transaction.findMerchantTransactions");
                query.setParameter("status", status);
                query.setParameter("merchantname", mname);
                query.setParameter("cardtype", "kenswitch");
                List<Transaction> transactions = query.getResultList();
                
                for (Transaction t : transactions) {
                    
                    paysureTotal = paysureTotal.add(utils.computePaysureCommission(t.getMerchant(), t, configs));
                    kenswitchTotal = kenswitchTotal.add(utils.computeKenswitchCommission(t.getMerchant(), t, configs));
                
                    
                    out.print(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode") + "\n");
                    out.print(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(t.getMerchant(), t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n\n");
                
                    em.getTransaction().begin();
                    //t.setProcessed(Boolean.TRUE);
                    em.merge(t);
                    em.getTransaction().commit();
                
                    
                
                }
                
                
                
                
                System.out.println("No entries generated for merchant " + mname + " because the total is less than the threshold amount");
            }

        }
        out.close();

        //settting record values        
        transactionfile.setFiledate(endtime);
        //transactionfile.setTransactions(numberOfTransactions);
        transactionfile.setMerchant(merchantTotal);
        transactionfile.setKenswitch(kenswitchTotal);
        transactionfile.setChase(chasebankTotal);
        transactionfile.setPaysure(paysureTotal);
        transactionfile.setFilename(filename);
        transactionfile.setFiledate(currentTime);
        transactionfile.setCreated(currentTime);
        transactionfile.setModified(currentTime);

        // saving to database        
        transactionfileController.create(transactionfile);
        utils.sendEmail(filename, configs);






        //for (Transaction t : transactions) {
        //System.out.println(t.getMerchantname() + " " + t.getAmount());
        //System.out.println(t.getMerchant().getName());            
        //}


        //Query q= em.createQuery("SELECT t FROM Transaction t WHERE t.processed=false GROUP BY t.merchantname");

        //System.out.println(q.getResultList());



        //System.out.println("Number of Transactions Processed: "+numberOfTransactions);
        //System.out.println("Number of Merchants: "+numberOfMerchants);

    }
}

/* JN,25-09-2012........changed file format...removed terminal id and added reference number column
 * 
 */