/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Status;

/**
 *
 * @author gachanja
 */
@Stateless
public class StatusFacade extends AbstractFacade<Status> {
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatusFacade() {
        super(Status.class);
    }
    
}
