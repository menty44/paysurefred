/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package authenticator;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import objects.*;

/**
 *
 * @author Joseph
 */
@Stateless
@LocalBean
public class Db {

    @PersistenceContext
    private EntityManager em;

    public Purchase findPurchase(String referenceno, String amount, String systemtraceno) {
        //Purchase p=new Purchase();        
        return (Purchase) em.createNamedQuery("Purchase.findpurchase").setParameter("referenceno", referenceno).setParameter("amount", Integer.parseInt(amount)).setParameter("systemtraceno", systemtraceno).getSingleResult();
    }
    
    public Purchase findPurchaseById(int id){
        return (Purchase) em.createNamedQuery("Purchase.findById").setParameter("id", 244).getSingleResult();
    }
    
    public Status findStatus(int id){
        return (Status)em.createNamedQuery("Status.findById").setParameter("id", 1).getSingleResult();
    }
    
    public void createTransaction(Transaction transaction){
        em.persist(transaction);
    }
    
    public Responsecode findResponsecode(String code){
        return (Responsecode) em.createNamedQuery("Responsecode.findByCode").setParameter("code", code).getSingleResult();                
    }
    
    public Responsecode findResCode(Cardtype c, String code){
        return (Responsecode) em.createNamedQuery("Responsecode.findByCodeCard").setParameter("code", code).setParameter("cardtype", c.getId()).getSingleResult();
    }
    
    public Purchase findByRefNo(String referenceno){
        return (Purchase) em.createNamedQuery("Purchase.findByReferenceno").setParameter("referenceno", referenceno).getSingleResult();
    }
}
