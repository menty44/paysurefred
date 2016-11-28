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
public class TransactionsourceFacade extends AbstractFacade<Transactionsource> {
    @PersistenceContext(unitName = "dailyProcessorWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransactionsourceFacade() {
        super(Transactionsource.class);
    }
    
}
