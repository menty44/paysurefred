/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Joseph
 */
@Stateless
public class RateFacade extends AbstractFacade<Rate> {
    @PersistenceContext(unitName = "dailyProcessorWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RateFacade() {
        super(Rate.class);
    }
    
}
