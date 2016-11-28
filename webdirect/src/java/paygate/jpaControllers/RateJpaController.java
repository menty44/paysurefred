/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.jpaControllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import paygate.objects.Merchant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Rate;

/**
 *
 * @author paysure
 */
public class RateJpaController implements Serializable {

    public RateJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rate rate) throws RollbackFailureException, Exception {
        if (rate.getMerchantList() == null) {
            rate.setMerchantList(new ArrayList<Merchant>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Merchant> attachedMerchantList = new ArrayList<Merchant>();
            for (Merchant merchantListMerchantToAttach : rate.getMerchantList()) {
                merchantListMerchantToAttach = em.getReference(merchantListMerchantToAttach.getClass(), merchantListMerchantToAttach.getId());
                attachedMerchantList.add(merchantListMerchantToAttach);
            }
            rate.setMerchantList(attachedMerchantList);
            em.persist(rate);
            for (Merchant merchantListMerchant : rate.getMerchantList()) {
                Rate oldRateidOfMerchantListMerchant = merchantListMerchant.getRateid();
                merchantListMerchant.setRateid(rate);
                merchantListMerchant = em.merge(merchantListMerchant);
                if (oldRateidOfMerchantListMerchant != null) {
                    oldRateidOfMerchantListMerchant.getMerchantList().remove(merchantListMerchant);
                    oldRateidOfMerchantListMerchant = em.merge(oldRateidOfMerchantListMerchant);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rate rate) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Rate persistentRate = em.find(Rate.class, rate.getId());
            List<Merchant> merchantListOld = persistentRate.getMerchantList();
            List<Merchant> merchantListNew = rate.getMerchantList();
            List<Merchant> attachedMerchantListNew = new ArrayList<Merchant>();
            for (Merchant merchantListNewMerchantToAttach : merchantListNew) {
                merchantListNewMerchantToAttach = em.getReference(merchantListNewMerchantToAttach.getClass(), merchantListNewMerchantToAttach.getId());
                attachedMerchantListNew.add(merchantListNewMerchantToAttach);
            }
            merchantListNew = attachedMerchantListNew;
            rate.setMerchantList(merchantListNew);
            rate = em.merge(rate);
            for (Merchant merchantListOldMerchant : merchantListOld) {
                if (!merchantListNew.contains(merchantListOldMerchant)) {
                    merchantListOldMerchant.setRateid(null);
                    merchantListOldMerchant = em.merge(merchantListOldMerchant);
                }
            }
            for (Merchant merchantListNewMerchant : merchantListNew) {
                if (!merchantListOld.contains(merchantListNewMerchant)) {
                    Rate oldRateidOfMerchantListNewMerchant = merchantListNewMerchant.getRateid();
                    merchantListNewMerchant.setRateid(rate);
                    merchantListNewMerchant = em.merge(merchantListNewMerchant);
                    if (oldRateidOfMerchantListNewMerchant != null && !oldRateidOfMerchantListNewMerchant.equals(rate)) {
                        oldRateidOfMerchantListNewMerchant.getMerchantList().remove(merchantListNewMerchant);
                        oldRateidOfMerchantListNewMerchant = em.merge(oldRateidOfMerchantListNewMerchant);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rate.getId();
                if (findRate(id) == null) {
                    throw new NonexistentEntityException("The rate with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Rate rate;
            try {
                rate = em.getReference(Rate.class, id);
                rate.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rate with id " + id + " no longer exists.", enfe);
            }
            List<Merchant> merchantList = rate.getMerchantList();
            for (Merchant merchantListMerchant : merchantList) {
                merchantListMerchant.setRateid(null);
                merchantListMerchant = em.merge(merchantListMerchant);
            }
            em.remove(rate);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rate> findRateEntities() {
        return findRateEntities(true, -1, -1);
    }

    public List<Rate> findRateEntities(int maxResults, int firstResult) {
        return findRateEntities(false, maxResults, firstResult);
    }

    private List<Rate> findRateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rate.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rate findRate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rate.class, id);
        } finally {
            em.close();
        }
    }

    public int getRateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rate> rt = cq.from(Rate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
