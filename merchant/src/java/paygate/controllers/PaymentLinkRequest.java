package paygate.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Purchase;
import paygate.objects.Siteuser;
import paygate.objects.Transactionsource;
import paygate.objects.Transactiontype;
import paygate.utils.Email;

/**
 *
 * @author Joseph
 */
@Named(value = "paymentLinkRequest")
@SessionScoped
//@RequestScoped
public class PaymentLinkRequest implements Serializable {
    
    private String clientname;
    private String email;
    private String description;
    private String value;
    private String currency;
    @EJB
    private paygate.controllers.PurchaseFacade ejbFacade;
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
            
    public PaymentLinkRequest() {
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    private PurchaseFacade getFacade() {
        return ejbFacade;
    }
    
    public String savePurchase(){
        String page=null;
        Purchase purchase = new Purchase();
        
        try{
            
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
            
            purchase.setMerchantid(siteuser.getMerchant());
            purchase.setRecievinginstitution(siteuser.getMerchant().getId().toString());
            Transactiontype transactiontype = new Transactiontype();
            transactiontype.setId(1);
            purchase.setTransactiontype(transactiontype);
            
            purchase.setToken("paysure");
            purchase.setSecret("secret");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
            purchase.setTransmission(sdf.format(date));
            purchase.setPurchasedate(date);
            purchase.setPaysuredate(date);
            
            // Set Values from the form
            
            purchase.setClientname(clientname);
            purchase.setEmail(email);
            purchase.setDescription(description);
            purchase.setCurrency(currency);
            
            
            if(value.contains(".")){
                purchase.setAmount(Integer.valueOf(value.replaceAll("\\.", "")));
            }else{
                purchase.setAmount(Integer.valueOf(value.replaceAll("\\.", ""))*100);
            }    
            
            if(!siteuser.getMerchant().isUsd()){
                purchase.setCurrency("KES");
            }else{
                purchase.setCurrency(currency);
            }            
            
            Transactionsource transactionsource = new Transactionsource();
            transactionsource.setId(2);
            purchase.setTransactionsourceid(transactionsource);
            
            List<Purchase> purchaseList = (List<Purchase>) em.createNamedQuery("Purchase.findLatest").setParameter("merchantid", siteuser.getMerchant()).setParameter("transactionsourceid", transactionsource).setMaxResults(1).getResultList();
            String referenceno = "";
            int systemtraceno;

            if (purchaseList.size() > 0) {
                Iterator i = purchaseList.iterator();
                Purchase purch = (Purchase) i.next();
                systemtraceno = Integer.valueOf(purch.getSystemtraceno());
                referenceno = String.valueOf(siteuser.getMerchant().getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", String.valueOf(Integer.valueOf(purch.getSystemtraceno()) + 1)).replaceAll(" ", "0");

                purchase.setSystemtraceno(String.format("%1$" + 6 + "s", String.valueOf(Integer.valueOf(purch.getSystemtraceno()) + 1)).replaceAll(" ", "0"));
                purchase.setReferenceno(referenceno);


            } else {
                referenceno = String.valueOf(siteuser.getMerchant().getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", "1").replaceAll(" ", "0");
                purchase.setReferenceno(referenceno);
                purchase.setSystemtraceno(String.format("%1$" + 6 + "s", "1").replaceAll(" ", "0"));
            }
            
            utx.begin();
            em.persist(purchase);
            utx.commit();            
            Email email = new Email();
            email.sendEmail(purchase);
            
            this.clientname=null;
            this.email=null;
            this.description=null;
            this.currency=null;
            this.value=null;            
            
            return null;
        }catch(Exception e){            
            e.printStackTrace();
            return null;
        }
                
        /*System.out.println("Client Name : "+clientname);
        System.out.println("Email : "+email);
        System.out.println("Description : "+description);
        System.out.println("Value : "+value);
        System.out.println("Currency : "+currency);*/        
        
        //return null;
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
