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
 * @author Joseph
 */
@Stateless
public class MerchantFacade extends AbstractFacade<Merchant> {
    @PersistenceContext(unitName = "openmerchantPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MerchantFacade() {
        super(Merchant.class);
    }
    
}