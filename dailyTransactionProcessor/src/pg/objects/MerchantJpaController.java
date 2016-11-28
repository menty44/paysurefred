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
public class MerchantJpaController {

    public MerchantJpaController() {
        emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Merchant merchant) {
        if (merchant.getTransactionCollection() == null) {
            merchant.setTransactionCollection(new ArrayList<Transaction>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Status status = merchant.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getId());
                merchant.setStatus(status);
            }
            Rate rate = merchant.getRate();
            if (rate != null) {
                rate = em.getReference(rate.getClass(), rate.getId());
                merchant.setRate(rate);
            }
            Country country = merchant.getCountry();
            if (country != null) {
                country = em.getReference(country.getClass(), country.getId());
                merchant.setCountry(country);
            }
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : merchant.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getId());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            merchant.setTransactionCollection(attachedTransactionCollection);
            em.persist(merchant);
            if (status != null) {
                status.getMerchantCollection().add(merchant);
                status = em.merge(status);
            }
            if (rate != null) {
                rate.getMerchantCollection().add(merchant);
                rate = em.merge(rate);
            }
            if (country != null) {
                country.getMerchantCollection().add(merchant);
                country = em.merge(country);
            }
            for (Transaction transactionCollectionTransaction : merchant.getTransactionCollection()) {
                Merchant oldMerchantOfTransactionCollectionTransaction = transactionCollectionTransaction.getMerchant();
                transactionCollectionTransaction.setMerchant(merchant);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldMerchantOfTransactionCollectionTransaction != null) {
                    oldMerchantOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldMerchantOfTransactionCollectionTransaction = em.merge(oldMerchantOfTransactionCollectionTransaction);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Merchant merchant) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Merchant persistentMerchant = em.find(Merchant.class, merchant.getId());
            Status statusOld = persistentMerchant.getStatus();
            Status statusNew = merchant.getStatus();
            Rate rateOld = persistentMerchant.getRate();
            Rate rateNew = merchant.getRate();
            Country countryOld = persistentMerchant.getCountry();
            Country countryNew = merchant.getCountry();
            Collection<Transaction> transactionCollectionOld = persistentMerchant.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = merchant.getTransactionCollection();
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getId());
                merchant.setStatus(statusNew);
            }
            if (rateNew != null) {
                rateNew = em.getReference(rateNew.getClass(), rateNew.getId());
                merchant.setRate(rateNew);
            }
            if (countryNew != null) {
                countryNew = em.getReference(countryNew.getClass(), countryNew.getId());
                merchant.setCountry(countryNew);
            }
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getId());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            merchant.setTransactionCollection(transactionCollectionNew);
            merchant = em.merge(merchant);
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getMerchantCollection().remove(merchant);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getMerchantCollection().add(merchant);
                statusNew = em.merge(statusNew);
            }
            if (rateOld != null && !rateOld.equals(rateNew)) {
                rateOld.getMerchantCollection().remove(merchant);
                rateOld = em.merge(rateOld);
            }
            if (rateNew != null && !rateNew.equals(rateOld)) {
                rateNew.getMerchantCollection().add(merchant);
                rateNew = em.merge(rateNew);
            }
            if (countryOld != null && !countryOld.equals(countryNew)) {
                countryOld.getMerchantCollection().remove(merchant);
                countryOld = em.merge(countryOld);
            }
            if (countryNew != null && !countryNew.equals(countryOld)) {
                countryNew.getMerchantCollection().add(merchant);
                countryNew = em.merge(countryNew);
            }
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setMerchant(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    Merchant oldMerchantOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getMerchant();
                    transactionCollectionNewTransaction.setMerchant(merchant);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldMerchantOfTransactionCollectionNewTransaction != null && !oldMerchantOfTransactionCollectionNewTransaction.equals(merchant)) {
                        oldMerchantOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldMerchantOfTransactionCollectionNewTransaction = em.merge(oldMerchantOfTransactionCollectionNewTransaction);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = merchant.getId();
                if (findMerchant(id) == null) {
                    throw new NonexistentEntityException("The merchant with id " + id + " no longer exists.");
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
            Merchant merchant;
            try {
                merchant = em.getReference(Merchant.class, id);
                merchant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The merchant with id " + id + " no longer exists.", enfe);
            }
            Status status = merchant.getStatus();
            if (status != null) {
                status.getMerchantCollection().remove(merchant);
                status = em.merge(status);
            }
            Rate rate = merchant.getRate();
            if (rate != null) {
                rate.getMerchantCollection().remove(merchant);
                rate = em.merge(rate);
            }
            Country country = merchant.getCountry();
            if (country != null) {
                country.getMerchantCollection().remove(merchant);
                country = em.merge(country);
            }
            Collection<Transaction> transactionCollection = merchant.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setMerchant(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            em.remove(merchant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Merchant> findMerchantEntities() {
        return findMerchantEntities(true, -1, -1);
    }

    public List<Merchant> findMerchantEntities(int maxResults, int firstResult) {
        return findMerchantEntities(false, maxResults, firstResult);
    }

    private List<Merchant> findMerchantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Merchant.class));
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

    public Merchant findMerchant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Merchant.class, id);
        } finally {
            em.close();
        }
    }

    public int getMerchantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Merchant> rt = cq.from(Merchant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
