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
public class StatusJpaController {

    public StatusJpaController() {
        emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Status status) {
        if (status.getMerchantCollection() == null) {
            status.setMerchantCollection(new ArrayList<Merchant>());
        }
        if (status.getTransactionCollection() == null) {
            status.setTransactionCollection(new ArrayList<Transaction>());
        }
        if (status.getCountryCollection() == null) {
            status.setCountryCollection(new ArrayList<Country>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Merchant> attachedMerchantCollection = new ArrayList<Merchant>();
            for (Merchant merchantCollectionMerchantToAttach : status.getMerchantCollection()) {
                merchantCollectionMerchantToAttach = em.getReference(merchantCollectionMerchantToAttach.getClass(), merchantCollectionMerchantToAttach.getId());
                attachedMerchantCollection.add(merchantCollectionMerchantToAttach);
            }
            status.setMerchantCollection(attachedMerchantCollection);
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : status.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getId());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            status.setTransactionCollection(attachedTransactionCollection);
            Collection<Country> attachedCountryCollection = new ArrayList<Country>();
            for (Country countryCollectionCountryToAttach : status.getCountryCollection()) {
                countryCollectionCountryToAttach = em.getReference(countryCollectionCountryToAttach.getClass(), countryCollectionCountryToAttach.getId());
                attachedCountryCollection.add(countryCollectionCountryToAttach);
            }
            status.setCountryCollection(attachedCountryCollection);
            em.persist(status);
            for (Merchant merchantCollectionMerchant : status.getMerchantCollection()) {
                Status oldStatusOfMerchantCollectionMerchant = merchantCollectionMerchant.getStatus();
                merchantCollectionMerchant.setStatus(status);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
                if (oldStatusOfMerchantCollectionMerchant != null) {
                    oldStatusOfMerchantCollectionMerchant.getMerchantCollection().remove(merchantCollectionMerchant);
                    oldStatusOfMerchantCollectionMerchant = em.merge(oldStatusOfMerchantCollectionMerchant);
                }
            }
            for (Transaction transactionCollectionTransaction : status.getTransactionCollection()) {
                Status oldStatusOfTransactionCollectionTransaction = transactionCollectionTransaction.getStatus();
                transactionCollectionTransaction.setStatus(status);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldStatusOfTransactionCollectionTransaction != null) {
                    oldStatusOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldStatusOfTransactionCollectionTransaction = em.merge(oldStatusOfTransactionCollectionTransaction);
                }
            }
            for (Country countryCollectionCountry : status.getCountryCollection()) {
                Status oldStatusOfCountryCollectionCountry = countryCollectionCountry.getStatus();
                countryCollectionCountry.setStatus(status);
                countryCollectionCountry = em.merge(countryCollectionCountry);
                if (oldStatusOfCountryCollectionCountry != null) {
                    oldStatusOfCountryCollectionCountry.getCountryCollection().remove(countryCollectionCountry);
                    oldStatusOfCountryCollectionCountry = em.merge(oldStatusOfCountryCollectionCountry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Status status) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Status persistentStatus = em.find(Status.class, status.getId());
            Collection<Merchant> merchantCollectionOld = persistentStatus.getMerchantCollection();
            Collection<Merchant> merchantCollectionNew = status.getMerchantCollection();
            Collection<Transaction> transactionCollectionOld = persistentStatus.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = status.getTransactionCollection();
            Collection<Country> countryCollectionOld = persistentStatus.getCountryCollection();
            Collection<Country> countryCollectionNew = status.getCountryCollection();
            Collection<Merchant> attachedMerchantCollectionNew = new ArrayList<Merchant>();
            for (Merchant merchantCollectionNewMerchantToAttach : merchantCollectionNew) {
                merchantCollectionNewMerchantToAttach = em.getReference(merchantCollectionNewMerchantToAttach.getClass(), merchantCollectionNewMerchantToAttach.getId());
                attachedMerchantCollectionNew.add(merchantCollectionNewMerchantToAttach);
            }
            merchantCollectionNew = attachedMerchantCollectionNew;
            status.setMerchantCollection(merchantCollectionNew);
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getId());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            status.setTransactionCollection(transactionCollectionNew);
            Collection<Country> attachedCountryCollectionNew = new ArrayList<Country>();
            for (Country countryCollectionNewCountryToAttach : countryCollectionNew) {
                countryCollectionNewCountryToAttach = em.getReference(countryCollectionNewCountryToAttach.getClass(), countryCollectionNewCountryToAttach.getId());
                attachedCountryCollectionNew.add(countryCollectionNewCountryToAttach);
            }
            countryCollectionNew = attachedCountryCollectionNew;
            status.setCountryCollection(countryCollectionNew);
            status = em.merge(status);
            for (Merchant merchantCollectionOldMerchant : merchantCollectionOld) {
                if (!merchantCollectionNew.contains(merchantCollectionOldMerchant)) {
                    merchantCollectionOldMerchant.setStatus(null);
                    merchantCollectionOldMerchant = em.merge(merchantCollectionOldMerchant);
                }
            }
            for (Merchant merchantCollectionNewMerchant : merchantCollectionNew) {
                if (!merchantCollectionOld.contains(merchantCollectionNewMerchant)) {
                    Status oldStatusOfMerchantCollectionNewMerchant = merchantCollectionNewMerchant.getStatus();
                    merchantCollectionNewMerchant.setStatus(status);
                    merchantCollectionNewMerchant = em.merge(merchantCollectionNewMerchant);
                    if (oldStatusOfMerchantCollectionNewMerchant != null && !oldStatusOfMerchantCollectionNewMerchant.equals(status)) {
                        oldStatusOfMerchantCollectionNewMerchant.getMerchantCollection().remove(merchantCollectionNewMerchant);
                        oldStatusOfMerchantCollectionNewMerchant = em.merge(oldStatusOfMerchantCollectionNewMerchant);
                    }
                }
            }
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setStatus(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    Status oldStatusOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getStatus();
                    transactionCollectionNewTransaction.setStatus(status);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldStatusOfTransactionCollectionNewTransaction != null && !oldStatusOfTransactionCollectionNewTransaction.equals(status)) {
                        oldStatusOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldStatusOfTransactionCollectionNewTransaction = em.merge(oldStatusOfTransactionCollectionNewTransaction);
                    }
                }
            }
            for (Country countryCollectionOldCountry : countryCollectionOld) {
                if (!countryCollectionNew.contains(countryCollectionOldCountry)) {
                    countryCollectionOldCountry.setStatus(null);
                    countryCollectionOldCountry = em.merge(countryCollectionOldCountry);
                }
            }
            for (Country countryCollectionNewCountry : countryCollectionNew) {
                if (!countryCollectionOld.contains(countryCollectionNewCountry)) {
                    Status oldStatusOfCountryCollectionNewCountry = countryCollectionNewCountry.getStatus();
                    countryCollectionNewCountry.setStatus(status);
                    countryCollectionNewCountry = em.merge(countryCollectionNewCountry);
                    if (oldStatusOfCountryCollectionNewCountry != null && !oldStatusOfCountryCollectionNewCountry.equals(status)) {
                        oldStatusOfCountryCollectionNewCountry.getCountryCollection().remove(countryCollectionNewCountry);
                        oldStatusOfCountryCollectionNewCountry = em.merge(oldStatusOfCountryCollectionNewCountry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = status.getId();
                if (findStatus(id) == null) {
                    throw new NonexistentEntityException("The status with id " + id + " no longer exists.");
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
            Status status;
            try {
                status = em.getReference(Status.class, id);
                status.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The status with id " + id + " no longer exists.", enfe);
            }
            Collection<Merchant> merchantCollection = status.getMerchantCollection();
            for (Merchant merchantCollectionMerchant : merchantCollection) {
                merchantCollectionMerchant.setStatus(null);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
            }
            Collection<Transaction> transactionCollection = status.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setStatus(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            Collection<Country> countryCollection = status.getCountryCollection();
            for (Country countryCollectionCountry : countryCollection) {
                countryCollectionCountry.setStatus(null);
                countryCollectionCountry = em.merge(countryCollectionCountry);
            }
            em.remove(status);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Status> findStatusEntities() {
        return findStatusEntities(true, -1, -1);
    }

    public List<Status> findStatusEntities(int maxResults, int firstResult) {
        return findStatusEntities(false, maxResults, firstResult);
    }

    private List<Status> findStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Status.class));
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

    public Status findStatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Status.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Status> rt = cq.from(Status.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
