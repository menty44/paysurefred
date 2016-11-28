package paygate.utils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Responsecode;
import paygate.objects.Status;
import paygate.objects.Transaction;

/**
 *
 * @author Joseph
 */
@Stateless
@LocalBean
public class UtilToken {
    @PersistenceContext
    private EntityManager em;    
    
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
}
