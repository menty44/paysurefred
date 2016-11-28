/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.AbstractFacade;
import paygate.objects.Cardtype;

/**
 *
 * @author paysure
 */
@Stateless
public class CardtypeFacade extends AbstractFacade<Cardtype> {
    @PersistenceContext(unitName = "adminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CardtypeFacade() {
        super(Cardtype.class);
    }
    
}
