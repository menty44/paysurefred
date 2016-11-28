/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import objects.Merchant;
import objects.Transactionmpesa;

/**
 *
 * @author Joseph
 */
@Stateless
@LocalBean
public class Transaction {
    @PersistenceContext(unitName = "mpesaPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    
    public Merchant pullMerchant(String mpesaid){
        //return em.find(Merchant.class, mpesaid);
        //return em.find(Merchant.class, 3);
        return (Merchant) em.createNamedQuery("Merchant.findByMpesaid").setParameter("mpesaid", mpesaid).getSingleResult();
    }
    
    public Transactionmpesa pullMpesaRecord(String mpesacode){                
        return (Transactionmpesa) em.createNamedQuery("Transactionmpesa.findByMpesacode").setParameter("mpesacode", mpesacode).getSingleResult();        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
