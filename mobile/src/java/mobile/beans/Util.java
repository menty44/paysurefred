package mobile.beans;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mobile.objects.*;

/**
 *
 * @author Joseph
 */

@Stateless
@LocalBean
public class Util {
    @PersistenceContext
    private EntityManager em;
    
    public Purchase findPurchase(String referenceno){
        return (Purchase)em.createNamedQuery("Purchase.findByReferenceno").setParameter("referenceno", referenceno).setMaxResults(1).getSingleResult();
    }
    
    public List findP(String referenceno){
        return em.createNamedQuery("Purchase.findpurchase").setParameter("referenceno", referenceno).getResultList();
    }

    public void createPurchase(Purchase p){
        em.persist(p);        
    }
    
    public Transaction findTransaction(int id){
        return (Transaction)em.createNamedQuery("Transaction.findById").setParameter("id", id).setMaxResults(1).getSingleResult();        
    }
    
    public void createTransaction(Transaction t){
        em.persist(t);
    }
    
    public Status findStatus(){
        return (Status)em.createNamedQuery("Status.findById").setParameter("id", 1).getSingleResult();         
    }
    
    public Status findStatusClosed(){
        return (Status)em.createNamedQuery("Status.findById").setParameter("id", 5).getSingleResult();
    }
    
    public Responsecode findResponsecode(String code){
        return (Responsecode) em.createNamedQuery("Responsecode.findByCode").setParameter("code", code).getSingleResult();                
    }
    
    public String findCardtype(){
        Cardtype ct=(Cardtype) em.createNamedQuery("Cardtype.findById").setParameter("id", 2).getSingleResult();
        return ct.getDescription();
    }    
}
