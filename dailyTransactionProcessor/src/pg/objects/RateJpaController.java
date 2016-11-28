/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pg.objects;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import pg.objects.exceptions.NonexistentEntityException;

/**
 *
 * @author gachanja
 */
public class RateJpaController {

    public RateJpaController() {
        emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rate rate) {
        if (rate.getMerchantCollection() == null) {
            rate.setMerchantCollection(new ArrayList<Merchant>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Merchant> attachedMerchantCollection = new ArrayList<Merchant>();
            for (Merchant merchantCollectionMerchantToAttach : rate.getMerchantCollection()) {
                merchantCollectionMerchantToAttach = em.getReference(merchantCollectionMerchantToAttach.getClass(), merchantCollectionMerchantToAttach.getId());
                attachedMerchantCollection.add(merchantCollectionMerchantToAttach);
            }
            rate.setMerchantCollection(attachedMerchantCollection);
            em.persist(rate);
            for (Merchant merchantCollectionMerchant : rate.getMerchantCollection()) {
                Rate oldRateOfMerchantCollectionMerchant = merchantCollectionMerchant.getRate();
                merchantCollectionMerchant.setRate(rate);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
                if (oldRateOfMerchantCollectionMerchant != null) {
                    oldRateOfMerchantCollectionMerchant.getMerchantCollection().remove(merchantCollectionMerchant);
                    oldRateOfMerchantCollectionMerchant = em.merge(oldRateOfMerchantCollectionMerchant);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rate rate) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                    merchantCollectionOldMerchant.setRate(null);
                    merchantCollectionOldMerchant = em.merge(merchantCollectionOldMerchant);
                }
            }
            for (Merchant merchantCollectionNewMerchant : merchantCollectionNew) {
                if (!merchantCollectionOld.contains(merchantCollectionNewMerchant)) {
                    Rate oldRateOfMerchantCollectionNewMerchant = merchantCollectionNewMerchant.getRate();
                    merchantCollectionNewMerchant.setRate(rate);
                    merchantCollectionNewMerchant = em.merge(merchantCollectionNewMerchant);
                    if (oldRateOfMerchantCollectionNewMerchant != null && !oldRateOfMerchantCollectionNewMerchant.equals(rate)) {
                        oldRateOfMerchantCollectionNewMerchant.getMerchantCollection().remove(merchantCollectionNewMerchant);
                        oldRateOfMerchantCollectionNewMerchant = em.merge(oldRateOfMerchantCollectionNewMerchant);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rate rate;
            try {
                rate = em.getReference(Rate.class, id);
                rate.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rate with id " + id + " no longer exists.", enfe);
            }
            Collection<Merchant> merchantCollection = rate.getMerchantCollection();
            for (Merchant merchantCollectionMerchant : merchantCollection) {
                merchantCollectionMerchant.setRate(null);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
            }
            em.remove(rate);
            em.getTransaction().commit();
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
