/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
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
@Stateless
@LocalBean
public class Work {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void workImpl() {
        PrintWriter out = null;
        try {
            // Declaring variables
            BigDecimal merchantTotal = new BigDecimal(0);
            BigDecimal paysureTotal = new BigDecimal(0);
            BigDecimal chasebankTotal = new BigDecimal(0);
            BigDecimal kenswitchTotal = new BigDecimal(0);
            Utilities utils = new Utilities();
            String filename = utils.getFileName();
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            HashMap configs = new HashMap(30);
            Transaction transaction = new Transaction();
            //Merchant merchant = new Merchant();
            BigDecimal eft = new BigDecimal(0);
            Status statusClosed = new Status(0);
            //String CODE=(String)configs.get("chasebankbankcode").toString();
            Transactionfile transactionfile = new Transactionfile();
            TransactionfileJpaController transactionfileController = new TransactionfileJpaController();
            // connecting to database
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("dailyProcessorPU");
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
            List<Object[]> results = em.createNamedQuery("Transaction.findAll").setParameter("status", status).getResultList();
            for (Object[] result : results) {
                String mname = (String) result[0];
                System.out.println(mname); 
                BigInteger mt = (BigInteger) result[1];
                BigDecimal mtotal = (new BigDecimal(mt));
                System.out.println("Merchant Name:" + mname + "===Merchant Total:" + mtotal);
                Merchant m = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mname).getSingleResult();
                BigDecimal meft = new BigDecimal(m.getEft());
                System.out.println(meft);            
                
            }
            

            
              System.out.println(filename);
              System.out.println(status.getCode());
              System.out.println(results.size());
             

        } catch (IOException ex) {
            Logger.getLogger(Work.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }

    }
}
