/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gachanja
 */
@Stateless
public class TransactionfileFacade extends AbstractFacade<Transactionfile> {
    @PersistenceContext(unitName = "adminPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TransactionfileFacade() {
        super(Transactionfile.class);
    }
    
}
