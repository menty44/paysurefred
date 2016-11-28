/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.objects;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import pg.objects.exceptions.NonexistentEntityException;
import pg.objects.exceptions.RollbackFailureException;

/**
 *
 * @author Joseph
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
        if (rate.getMerchantCollection() == null) {
            rate.setMerchantCollection(new ArrayList<Merchant>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Merchant> attachedMerchantCollection = new ArrayList<Merchant>();
            for (Merchant merchantCollectionMerchantToAttach : rate.getMerchantCollection()) {
                merchantCollectionMerchantToAttach = em.getReference(merchantCollectionMerchantToAttach.getClass(), merchantCollectionMerchantToAttach.getId());
                attachedMerchantCollection.add(merchantCollectionMerchantToAttach);
            }
            rate.setMerchantCollection(attachedMerchantCollection);
            em.persist(rate);
            for (Merchant merchantCollectionMerchant : rate.getMerchantCollection()) {
                Rate oldRateidOfMerchantCollectionMerchant = merchantCollectionMerchant.getRateid();
                merchantCollectionMerchant.setRateid(rate);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
                if (oldRateidOfMerchantCollectionMerchant != null) {
                    oldRateidOfMerchantCollectionMerchant.getMerchantCollection().remove(merchantCollectionMerchant);
                    oldRateidOfMerchantCollectionMerchant = em.merge(oldRateidOfMerchantCollectionMerchant);
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
            Collection<Merchant> merchantCollectionOld = persistentRate.getMerchantCollection();
            Collection<Merchant> merchantCollectionNew = rate.getMerchantCollection();
            Collection<Merchant> attachedMerchantCollectionNew = new ArrayList<Merchant>();
            for (Merchant merchantCollectionNewMerchantToAttach : merchantCollectionNew) {
                merchantCollectionNewMerchantToAttach = em.getReference(merchantCollectionNewMerchantToAttach.getClass(), merchantCollectionNewMerchantToAttach.getId());
                attachedMerchantCollectionNew.add(merchantCollectionNewMerchantToAttach);
            }
            merchantCollectionNew = attachedMerchantCollectionNew;
            rate.setMerchantCollection(merchantCollectionNew);
            rate = em.merge(rate);
            for (Merchant merchantCollectionOldMerchant : merchantCollectionOld) {
                if (!merchantCollectionNew.contains(merchantCollectionOldMerchant)) {
                    merchantCollectionOldMerchant.setRateid(null);
                    merchantCollectionOldMerchant = em.merge(merchantCollectionOldMerchant);
                }
            }
            for (Merchant merchantCollectionNewMerchant : merchantCollectionNew) {
                if (!merchantCollectionOld.contains(merchantCollectionNewMerchant)) {
                    Rate oldRateidOfMerchantCollectionNewMerchant = merchantCollectionNewMerchant.getRateid();
                    merchantCollectionNewMerchant.setRateid(rate);
                    merchantCollectionNewMerchant = em.merge(merchantCollectionNewMerchant);
                    if (oldRateidOfMerchantCollectionNewMerchant != null && !oldRateidOfMerchantCollectionNewMerchant.equals(rate)) {
                        oldRateidOfMerchantCollectionNewMerchant.getMerchantCollection().remove(merchantCollectionNewMerchant);
                        oldRateidOfMerchantCollectionNewMerchant = em.merge(oldRateidOfMerchantCollectionNewMerchant);
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
            Collection<Merchant> merchantCollection = rate.getMerchantCollection();
            for (Merchant merchantCollectionMerchant : merchantCollection) {
                merchantCollectionMerchant.setRateid(null);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
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
