package utils;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Purchase;

@Stateless
@LocalBean
public class Helper {

    @PersistenceContext(unitName = "webdirectPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public List findP(String referenceno) {
        return em.createNamedQuery("Purchase.findByReferenceno").setParameter("referenceno", referenceno).getResultList();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void removePurchase(Purchase p){
        em.remove(p);
        em.flush();
    }
    
    
}
