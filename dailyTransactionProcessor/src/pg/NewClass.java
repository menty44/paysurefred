package pg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
//
//public class NewClass {
//
//    public static void main(String[] args) throws Exception {
//        // Declaring variables
//        BigDecimal merchantTotal = new BigDecimal(0);
//        BigDecimal paysureTotal = new BigDecimal(0);
//        BigDecimal chasebankTotal = new BigDecimal(0);
//        BigDecimal kenswitchTotal = new BigDecimal(0);
//        Utilities utils = new Utilities();
//        String filename = utils.getFileName();
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
//        HashMap configs = new HashMap(30);
//        Transaction transaction = new Transaction();
//        Merchant merchant = new Merchant();
//        BigDecimal eft = new BigDecimal(0);
//        Status statusClosed = new Status(0);
//        //String CODE=(String)configs.get("chasebankbankcode").toString();
//        Transactionfile transactionfile = new Transactionfile();
//        TransactionfileJpaController transactionfileController = new TransactionfileJpaController();
//
//        // connecting to database
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
//        EntityManager em = emf.createEntityManager();
//
//        // retrieving configurations
//        configs = utils.insertConfigs(configs);
//
//        // setting search parameters
//        Date endtime = utils.getEndTime();
////        Date starttime = utils.getStartTime();
////        System.out.println("START TIME IS: " + starttime + " ENDTIME IS:" + endtime);
//        Date currentTime = utils.getCurrentDate();
//        Query query = em.createNamedQuery("Status.findById");
//        query.setParameter("id", 5);
//        Status status = (Status) query.getSingleResult();
//
//        // retrieving data
//        query = em.createNamedQuery("Transaction.findAll");
//        query.setParameter("status", status);
//        List<Transaction> transactions = query.getResultList();
//        Iterator i = transactions.iterator();
//
//        // counting number of transactions found
//        //System.out.println(transactions.size());       
//        Integer numberOfTransactions = transactions.size();
//
//        // getting chase bank bank code        
//        String code = (String) configs.get("chasebankbankcode");
//        int chaseBankCode = Integer.parseInt(code);
//        String chaseBankEft = (String) configs.get("eftcharge");
//        BigDecimal ceft = new BigDecimal(chaseBankEft);
//
//        //int charge=Integer.parseInt(chaseBankEft);
//
//        // looping through transactions found
//        em.getTransaction().begin();
//        int flag;
//        if (numberOfTransactions==0) {
//            flag=0;
//            
//        } else  {
//            flag = transactions.get(1).getMerchant().getId();
//        }
//
//        //int flag=0;
//        //System.out.println(flag);
//         
//        
//        BigDecimal n = new BigDecimal(numberOfTransactions);
//        for (Transaction t : transactions) {
//                       
//
//            merchant = t.getMerchant();
//            eft = merchant.getEft();
//            Query q = em.createNamedQuery("Status.findByCode");
//            q.setParameter("code", 4);
//            statusClosed = (Status) q.getSingleResult();
//            if (t.getStatus().getCode().compareTo(statusClosed.getCode()) == 0) {
//
//                //computing totals
//                if (merchant.getId()==flag) {
//
//                    merchantTotal = merchantTotal.add(t.getAmount().subtract(t.getCommission()));
//                    paysureTotal = paysureTotal.add(utils.computePaysureCommission(merchant, t, configs));
//                    chasebankTotal = chasebankTotal.add(utils.computeChasebankCommission(merchant, t, configs));
//                    kenswitchTotal = kenswitchTotal.add(utils.computeKenswitchCommission(merchant, t, configs));
//                    //System.out.println(t.getId() + " " + t.getMerchantname());
//                } else {
//                    if (merchant.getBankcode().compareTo(code) == 0) {
//
//                        //System.out.println("Amount: "+t.getAmount()+" Ref No: "+t.getRefno()+" Commission: "+t.getCommission());
//                        out.print(merchant.getName() + "," + merchant.getBankaccount() + "," + t.getAmount().subtract(t.getCommission()) + "," + t.getRefno() + "," + merchant.getBankcode() + "," + merchant.getBranchcode() + "\n");
//                        out.print(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(merchant, t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode") + "\n");
//                        out.print(configs.get("chasebankmerchantname") + "," + configs.get("chasebankbankaccount") + "," + utils.computeChasebankCommission(merchant, t, configs) + "," + t.getRefno() + "," + configs.get("chasebankbankcode") + "," + configs.get("chasebankbankbranchcode") + "\n");
//                        out.println(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(merchant, t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n");
//
//                    } else {
//                        //System.out.println("amt below eft");
//
//                        merchantTotal = merchantTotal.subtract(ceft);
//                        paysureTotal = paysureTotal.add(ceft);
//
//
//                        out.print(merchant.getName() + "," + merchant.getBankaccount() + "," + t.getAmount().subtract(t.getCommission()) + "," + t.getRefno() + "," + merchant.getBankcode() + "," + merchant.getBranchcode() + "\n");
//                        out.print(configs.get("paysuremerchantname") + "," + configs.get("paysurebankaccount") + "," + utils.computePaysureCommission(merchant, t, configs) + "," + t.getRefno() + "," + configs.get("paysurebankcode") + "," + configs.get("paysurebankbranchcode") + "\n");
//                        out.print(configs.get("chasebankmerchantname") + "," + configs.get("chasebankbankaccount") + "," + utils.computeChasebankCommission(merchant, t, configs) + "," + t.getRefno() + "," + configs.get("chasebankbankcode") + "," + configs.get("chasebankbankbranchcode") + "\n");
//                        out.println(configs.get("kenswitchmerchantname") + "," + configs.get("kenswitchbankaccount") + "," + utils.computeKenswitchCommission(merchant, t, configs) + "," + t.getRefno() + "," + configs.get("kenswitchbankcode") + "," + configs.get("kenswitchbankbranchcode") + "\n");
//
//
//                        merchantTotal = t.getAmount().subtract(t.getCommission());
//                        paysureTotal = utils.computePaysureCommission(merchant, t, configs);
//                        chasebankTotal = utils.computeChasebankCommission(merchant, t, configs);
//                        kenswitchTotal = utils.computeKenswitchCommission(merchant, t, configs);
//
//
//                    }
//                }
//                //t.setProcessed(Boolean.TRUE);
//                em.merge(t);
//                flag = merchant.getId();
//            }
//
//        }
//        //System.out.println(merchantTotal);
//        //System.out.println(eft);
//
//
//        /*
//         * if ((merchant.getStatus().getCode().compareTo(101) == 0) ||
//         * ((merchant.getStatus().getCode().compareTo(100) == 0) &&
//         * (transaction.getDelivered().compareTo("Delivered") == 0))) { //
//         * writing to file out.print(merchant.getName() + "," +
//         * merchant.getTerminalid() + "," + merchant.getBankaccount() + "," +
//         * transaction.getAmount().subtract(transaction.getCommission()) + "," +
//         * merchant.getBankcode() + "," + merchant.getBranchcode() + "\n");
//         * out.print(configs.get("paysuremerchantname") + "," +
//         * configs.get("paysureterminalid") + "," +
//         * configs.get("paysurebankaccount") + "," +
//         * utils.computePaysureCommission(merchant, transaction, configs) + ","
//         * + configs.get("paysurebankcode") + "," +
//         * configs.get("paysurebankbranchcode") + "\n");
//         * out.print(configs.get("chasebankmerchantname") + "," +
//         * configs.get("chasebankterminalid") + "," +
//         * configs.get("chasebankbankaccount") + "," +
//         * utils.computeChasebankCommission(merchant, transaction, configs) +
//         * "," + configs.get("chasebankbankcode") + "," +
//         * configs.get("chasebankbankbranchcode") + "\n");
//         * out.print(configs.get("kenswitchmerchantname") + "," +
//         * configs.get("kenswitchterminalid") + "," +
//         * configs.get("kenswitchbankaccount") + "," +
//         * utils.computeKenswitchCommission(merchant, transaction, configs) +
//         * "," + configs.get("kenswitchbankcode") + "," +
//         * configs.get("kenswitchbankbranchcode") + "\n");          *
//         * // computing totals merchantTotal =
//         * merchantTotal.add(transaction.getAmount().subtract(transaction.getCommission()));
//         * paysureTotal =
//         * paysureTotal.add(utils.computePaysureCommission(merchant,
//         * transaction, configs)); chasebankTotal =
//         * chasebankTotal.add(utils.computeChasebankCommission(merchant,
//         * transaction, configs)); kenswitchTotal =
//         * kenswitchTotal.add(utils.computeKenswitchCommission(merchant,
//         * transaction, configs)); System.out.println(transaction.getId() + " "
//         * + transaction.getMerchantname());
//         * transaction.setProcessed(Boolean.TRUE); em.merge(transaction);
//         *
//         * }
//         * }
//         */
//
//
//
//        em.getTransaction().commit();
//
//        // closing file
//        out.close();
//
//        // settting record values        
//        transactionfile.setFiledate(endtime);
//        transactionfile.setTransactions(numberOfTransactions);
//        transactionfile.setMerchant(merchantTotal);
//        transactionfile.setKenswitch(kenswitchTotal);
//        transactionfile.setChase(chasebankTotal);
//        transactionfile.setPaysure(paysureTotal);
//        transactionfile.setFilename(filename);
//        transactionfile.setFiledate(currentTime);
//        transactionfile.setCreated(currentTime);
//        transactionfile.setModified(currentTime);
//
//        // saving to database        
//        transactionfileController.create(transactionfile);
//
//        // sending email
//        if (numberOfTransactions.compareTo(0) > 0) {
////            utils.sendEmail(filename, configs);
//        }
//
//        //System.out.println("Chase Bank EFT Charge is: "+configs.get("eftcharge"));  
//
//    }
//}
