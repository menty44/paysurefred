package paygate.controllers;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Batchheader;


@Stateless
@LocalBean
public class Bean {
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public Batchheader findHeader(){
        return (Batchheader) em.createNamedQuery("Batchheader.findById").setParameter("id", 1).setMaxResults(1).getSingleResult();
    }
    
}
