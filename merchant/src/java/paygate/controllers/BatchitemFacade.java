/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Batchitem;

/**
 *
 * @author paysure
 */
@Stateless
public class BatchitemFacade extends AbstractFacade<Batchitem> {
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BatchitemFacade() {
        super(Batchitem.class);
    }
    
}
