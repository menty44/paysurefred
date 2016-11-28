/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.iveri.enterprise.Enterprise;
import com.iveri.enterprise.ResultException;
import com.iveri.enterprise.ResultExceptionAction;
import com.iveri.enterprise.ResultStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Merchant;
import paygate.objects.Purchase;
import paygate.objects.Transactionsource;
import paygate.objects.Transactiontype;

/**
 *
 * @author Joseph
 */
@WebService(serviceName = "Onlinepay")
public class Onlinepay {
    @PersistenceContext(unitName = "mobiwebdirectPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
        
    /**
     * Web service operation
     */
    @WebMethod(operationName = "test")
    public String test() {
        //TODO write your implementation code here:
        return "Your Ping Worked";
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "android")
    public String android(@WebParam(name = "amount") String amount, @WebParam(name = "cardno") String cardno, @WebParam(name = "expirydate") String expirydate) throws ResultException {
        String date = expirydate;
        StringBuilder sb = new StringBuilder();
        /*sb.append(date.charAt(2));
        sb.append(date.charAt(3));
        sb.append("20");
        sb.append(date.charAt(0));
        sb.append(date.charAt(1));
        String d = sb.toString();*/
        sb.append(date.charAt(2));
        sb.append(date.charAt(3));
        sb.append(date.charAt(0));
        sb.append(date.charAt(1));
        String d = sb.toString();
        //String mode = "Test";
        String tindex = "";
        /*String mode = "Test";
        Enterprise e = new Enterprise("host", "b3ab28b4-8860-4bc1-902f-c02ed01749f0", ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
        e.prepare("Transaction", "Debit", "3a687c08-c840-43d6-ae4f-71a3fd619b6e", mode);
        e.setAttribute("MerchantReference", String.valueOf(System.currentTimeMillis()));
        e.setTag("Amount", amount); // R1.23
        e.setTag("Currency", "KES");
        e.setTag("ElectronicCommerceIndicator", "SecureChannel");
        e.setTag("CCNumber", "4242424242424242");
        e.setTag("ExpiryDate", "122013");
        e.setTag("Email", "josetosh06@gmail.com");*/
        String mode = "Live";
        Enterprise e = new Enterprise("host", "b3ab28b4-8860-4bc1-902f-c02ed01749f0", ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
        e.prepare("Transaction", "Debit", "c41bdbb6-0277-4342-a231-0d6e71ff10ab", mode);
        e.setAttribute("MerchantReference", String.valueOf(System.currentTimeMillis()));
        e.setTag("Amount", amount); // R1.23
        e.setTag("Currency", "KES");
        e.setTag("ElectronicCommerceIndicator", "SecureChannel");
        e.setTag("CCNumber", cardno);
        e.setTag("ExpiryDate", d);
        e.setTag("Email", "josetosh06@gmail.com");
        System.out.println(e.getLoggableRequest());
        ResultStatus resultStatus = e.execute();
        System.out.println("Result : " + resultStatus.getValue());
        if (resultStatus == ResultStatus.UNSUCCESSFUL) {
            // Display the result (eg. DENIED or ApplicationID not configured)
            System.out.println("Result code : " + e.getResultCode());
            System.out.println("Result source : " + e.getResultSource());
            System.out.println("Result description : " + e.getResultDescription());
        } else if (resultStatus == ResultStatus.SUCCESSFUL || resultStatus == ResultStatus.SUCCESSFUL_WITH_WARNING) {
            System.out.println("Successful Transaction Test");
            System.out.println("RequestID : " + e.getAttribute("RequestID"));
            System.out.println("Acquirer Reference : " + e.getTag("AcquirerReference"));
            System.out.println("Acquirer Date : " + e.getTag("AcquirerDate"));
            System.out.println("Acquirer Time : " + e.getTag("AcquirerTime"));
            System.out.println("Authorisation Code : " + e.getTag("AuthorisationCode"));
            System.out.println("Amount : " + e.getTag("Amount"));
            System.out.println("Terminal : " + e.getTag("Terminal"));
            System.out.println("Transaction Index : " + e.getTag("TransactionIndex"));
            tindex = e.getTag("TransactionIndex");
            System.out.println("MyEmail : " + e.getTag("Email"));
            System.out.println(System.getProperty("java.home"));
        }
        System.out.println("Working...from android method :)");
        int code = resultStatus.getValue();
        return resultStatus.ToString();
        //return "CardNo=>"+cardno+"ExpiryDate=>"+expirydate+"DateFormat=>"+d+"TranIndex+>"+tindex;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "mobilepay")
    public String mobilepay(@WebParam(name = "mname") String mname, @WebParam(name = "name") String name, @WebParam(name = "email") String email, @WebParam(name = "amount") String amount, @WebParam(name = "description") String description, @WebParam(name = "currency") String currency) {
        
        String mnameform = mname;
        String nameform = name;
        String emailform = email;
        int formamount = Integer.parseInt(amount);
        String formdescription = description;        
        String curr = currency;
        
        Purchase p2 = new Purchase();
        
        try {            
            utx.begin();
            Merchant m = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mnameform).getSingleResult();
            
            /*name = m.getEmail();
            p2.setMerchantid(m);
            p2.setRecievinginstitution(m.getId().toString());
            Transactiontype transactiontype = new Transactiontype();
            transactiontype.setId(1);
            p2.setTransactiontype(transactiontype);*/
            
            
            /*em.persist(p2);
            utx.commit();*/
            p2.setId(2864);
            p2.setMerchantid(m);
            p2.setRecievinginstitution(m.getId().toString());
            Transactiontype transactiontype = new Transactiontype();
            transactiontype.setId(1);
            p2.setTransactiontype(transactiontype);
            paygate.objects.Status status = (paygate.objects.Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
            p2.setStatusid(status);
            p2.setToken("paysure");
            p2.setSecret("secret");
            Date date = new Date();
            SimpleDateFormat sdfn = new SimpleDateFormat("MMddHHmmss");
            StringBuilder trann = new StringBuilder(sdfn.format(date));
            String transmissionn = trann.toString();
            p2.setTransmission(transmissionn);
            p2.setPurchasedate(date);
            p2.setPaysuredate(date);
            Transactionsource transactionsource = new Transactionsource();
            transactionsource.setId(2);
            p2.setTransactionsourceid(transactionsource);
            p2.setAmount(formamount * 100);
            p2.setClientname(nameform);
            p2.setEmail(emailform);
            p2.setCurrency(curr);
            p2.setDescription(formdescription);
            
            List<Purchase> purchaseList = (List<Purchase>) em.createNamedQuery("Purchase.findLatest").setParameter("merchantid", m).setParameter("transactionsourceid", transactionsource).setMaxResults(1).getResultList();
            String referenceno = "";
            int systemtraceno;
            if (purchaseList.size() > 0) {
                Iterator i = purchaseList.iterator();
                Purchase p = (Purchase) i.next();
                systemtraceno = Integer.valueOf(p.getSystemtraceno());
                referenceno = String.valueOf(m.getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", String.valueOf(Integer.valueOf(p.getSystemtraceno()) + 1)).replaceAll(" ", "0");
                p2.setSystemtraceno(String.format("%1$" + 6 + "s", String.valueOf(Integer.valueOf(p.getSystemtraceno()) + 1)).replaceAll(" ", "0"));
                p2.setReferenceno(referenceno);
            } else {
                referenceno = String.valueOf(m.getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", "1").replaceAll(" ", "0");
                p2.setReferenceno(referenceno);
                p2.setSystemtraceno(String.format("%1$" + 6 + "s", "1").replaceAll(" ", "0"));
            }
            
            System.out.println("Mobile Pay Called Successfully from Mobile Form...");
            
            em.persist(p2);
            utx.commit();            
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        //return "visa.xhtml?faces-redirect=true";
        //return name ;
        return p2.getId().toString();
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
