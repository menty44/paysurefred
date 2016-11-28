/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Gender;

/**
 *
 * @author gachanja
 */
@Stateless
public class GenderFacade extends AbstractFacade<Gender> {
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenderFacade() {
        super(Gender.class);
    }
    
}
