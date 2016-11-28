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
public class StatusJpaController implements Serializable {

    public StatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Status status) throws RollbackFailureException, Exception {
        if (status.getTransactionCollection() == null) {
            status.setTransactionCollection(new ArrayList<Transaction>());
        }
        if (status.getMerchantCollection() == null) {
            status.setMerchantCollection(new ArrayList<Merchant>());
        }
        if (status.getPurchaseCollection() == null) {
            status.setPurchaseCollection(new ArrayList<Purchase>());
        }
        if (status.getCountryCollection() == null) {
            status.setCountryCollection(new ArrayList<Country>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : status.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getId());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            status.setTransactionCollection(attachedTransactionCollection);
            Collection<Merchant> attachedMerchantCollection = new ArrayList<Merchant>();
            for (Merchant merchantCollectionMerchantToAttach : status.getMerchantCollection()) {
                merchantCollectionMerchantToAttach = em.getReference(merchantCollectionMerchantToAttach.getClass(), merchantCollectionMerchantToAttach.getId());
                attachedMerchantCollection.add(merchantCollectionMerchantToAttach);
            }
            status.setMerchantCollection(attachedMerchantCollection);
            Collection<Purchase> attachedPurchaseCollection = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionPurchaseToAttach : status.getPurchaseCollection()) {
                purchaseCollectionPurchaseToAttach = em.getReference(purchaseCollectionPurchaseToAttach.getClass(), purchaseCollectionPurchaseToAttach.getId());
                attachedPurchaseCollection.add(purchaseCollectionPurchaseToAttach);
            }
            status.setPurchaseCollection(attachedPurchaseCollection);
            Collection<Country> attachedCountryCollection = new ArrayList<Country>();
            for (Country countryCollectionCountryToAttach : status.getCountryCollection()) {
                countryCollectionCountryToAttach = em.getReference(countryCollectionCountryToAttach.getClass(), countryCollectionCountryToAttach.getId());
                attachedCountryCollection.add(countryCollectionCountryToAttach);
            }
            status.setCountryCollection(attachedCountryCollection);
            em.persist(status);
            for (Transaction transactionCollectionTransaction : status.getTransactionCollection()) {
                Status oldStatusidOfTransactionCollectionTransaction = transactionCollectionTransaction.getStatusid();
                transactionCollectionTransaction.setStatusid(status);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldStatusidOfTransactionCollectionTransaction != null) {
                    oldStatusidOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldStatusidOfTransactionCollectionTransaction = em.merge(oldStatusidOfTransactionCollectionTransaction);
                }
            }
            for (Merchant merchantCollectionMerchant : status.getMerchantCollection()) {
                Status oldStatusidOfMerchantCollectionMerchant = merchantCollectionMerchant.getStatusid();
                merchantCollectionMerchant.setStatusid(status);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
                if (oldStatusidOfMerchantCollectionMerchant != null) {
                    oldStatusidOfMerchantCollectionMerchant.getMerchantCollection().remove(merchantCollectionMerchant);
                    oldStatusidOfMerchantCollectionMerchant = em.merge(oldStatusidOfMerchantCollectionMerchant);
                }
            }
            for (Purchase purchaseCollectionPurchase : status.getPurchaseCollection()) {
                Status oldStatusidOfPurchaseCollectionPurchase = purchaseCollectionPurchase.getStatusid();
                purchaseCollectionPurchase.setStatusid(status);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
                if (oldStatusidOfPurchaseCollectionPurchase != null) {
                    oldStatusidOfPurchaseCollectionPurchase.getPurchaseCollection().remove(purchaseCollectionPurchase);
                    oldStatusidOfPurchaseCollectionPurchase = em.merge(oldStatusidOfPurchaseCollectionPurchase);
                }
            }
            for (Country countryCollectionCountry : status.getCountryCollection()) {
                Status oldStatusidOfCountryCollectionCountry = countryCollectionCountry.getStatusid();
                countryCollectionCountry.setStatusid(status);
                countryCollectionCountry = em.merge(countryCollectionCountry);
                if (oldStatusidOfCountryCollectionCountry != null) {
                    oldStatusidOfCountryCollectionCountry.getCountryCollection().remove(countryCollectionCountry);
                    oldStatusidOfCountryCollectionCountry = em.merge(oldStatusidOfCountryCollectionCountry);
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

    public void edit(Status status) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status persistentStatus = em.find(Status.class, status.getId());
            Collection<Transaction> transactionCollectionOld = persistentStatus.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = status.getTransactionCollection();
            Collection<Merchant> merchantCollectionOld = persistentStatus.getMerchantCollection();
            Collection<Merchant> merchantCollectionNew = status.getMerchantCollection();
            Collection<Purchase> purchaseCollectionOld = persistentStatus.getPurchaseCollection();
            Collection<Purchase> purchaseCollectionNew = status.getPurchaseCollection();
            Collection<Country> countryCollectionOld = persistentStatus.getCountryCollection();
            Collection<Country> countryCollectionNew = status.getCountryCollection();
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getId());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            status.setTransactionCollection(transactionCollectionNew);
            Collection<Merchant> attachedMerchantCollectionNew = new ArrayList<Merchant>();
            for (Merchant merchantCollectionNewMerchantToAttach : merchantCollectionNew) {
                merchantCollectionNewMerchantToAttach = em.getReference(merchantCollectionNewMerchantToAttach.getClass(), merchantCollectionNewMerchantToAttach.getId());
                attachedMerchantCollectionNew.add(merchantCollectionNewMerchantToAttach);
            }
            merchantCollectionNew = attachedMerchantCollectionNew;
            status.setMerchantCollection(merchantCollectionNew);
            Collection<Purchase> attachedPurchaseCollectionNew = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionNewPurchaseToAttach : purchaseCollectionNew) {
                purchaseCollectionNewPurchaseToAttach = em.getReference(purchaseCollectionNewPurchaseToAttach.getClass(), purchaseCollectionNewPurchaseToAttach.getId());
                attachedPurchaseCollectionNew.add(purchaseCollectionNewPurchaseToAttach);
            }
            purchaseCollectionNew = attachedPurchaseCollectionNew;
            status.setPurchaseCollection(purchaseCollectionNew);
            Collection<Country> attachedCountryCollectionNew = new ArrayList<Country>();
            for (Country countryCollectionNewCountryToAttach : countryCollectionNew) {
                countryCollectionNewCountryToAttach = em.getReference(countryCollectionNewCountryToAttach.getClass(), countryCollectionNewCountryToAttach.getId());
                attachedCountryCollectionNew.add(countryCollectionNewCountryToAttach);
            }
            countryCollectionNew = attachedCountryCollectionNew;
            status.setCountryCollection(countryCollectionNew);
            status = em.merge(status);
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setStatusid(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    Status oldStatusidOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getStatusid();
                    transactionCollectionNewTransaction.setStatusid(status);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldStatusidOfTransactionCollectionNewTransaction != null && !oldStatusidOfTransactionCollectionNewTransaction.equals(status)) {
                        oldStatusidOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldStatusidOfTransactionCollectionNewTransaction = em.merge(oldStatusidOfTransactionCollectionNewTransaction);
                    }
                }
            }
            for (Merchant merchantCollectionOldMerchant : merchantCollectionOld) {
                if (!merchantCollectionNew.contains(merchantCollectionOldMerchant)) {
                    merchantCollectionOldMerchant.setStatusid(null);
                    merchantCollectionOldMerchant = em.merge(merchantCollectionOldMerchant);
                }
            }
            for (Merchant merchantCollectionNewMerchant : merchantCollectionNew) {
                if (!merchantCollectionOld.contains(merchantCollectionNewMerchant)) {
                    Status oldStatusidOfMerchantCollectionNewMerchant = merchantCollectionNewMerchant.getStatusid();
                    merchantCollectionNewMerchant.setStatusid(status);
                    merchantCollectionNewMerchant = em.merge(merchantCollectionNewMerchant);
                    if (oldStatusidOfMerchantCollectionNewMerchant != null && !oldStatusidOfMerchantCollectionNewMerchant.equals(status)) {
                        oldStatusidOfMerchantCollectionNewMerchant.getMerchantCollection().remove(merchantCollectionNewMerchant);
                        oldStatusidOfMerchantCollectionNewMerchant = em.merge(oldStatusidOfMerchantCollectionNewMerchant);
                    }
                }
            }
            for (Purchase purchaseCollectionOldPurchase : purchaseCollectionOld) {
                if (!purchaseCollectionNew.contains(purchaseCollectionOldPurchase)) {
                    purchaseCollectionOldPurchase.setStatusid(null);
                    purchaseCollectionOldPurchase = em.merge(purchaseCollectionOldPurchase);
                }
            }
            for (Purchase purchaseCollectionNewPurchase : purchaseCollectionNew) {
                if (!purchaseCollectionOld.contains(purchaseCollectionNewPurchase)) {
                    Status oldStatusidOfPurchaseCollectionNewPurchase = purchaseCollectionNewPurchase.getStatusid();
                    purchaseCollectionNewPurchase.setStatusid(status);
                    purchaseCollectionNewPurchase = em.merge(purchaseCollectionNewPurchase);
                    if (oldStatusidOfPurchaseCollectionNewPurchase != null && !oldStatusidOfPurchaseCollectionNewPurchase.equals(status)) {
                        oldStatusidOfPurchaseCollectionNewPurchase.getPurchaseCollection().remove(purchaseCollectionNewPurchase);
                        oldStatusidOfPurchaseCollectionNewPurchase = em.merge(oldStatusidOfPurchaseCollectionNewPurchase);
                    }
                }
            }
            for (Country countryCollectionOldCountry : countryCollectionOld) {
                if (!countryCollectionNew.contains(countryCollectionOldCountry)) {
                    countryCollectionOldCountry.setStatusid(null);
                    countryCollectionOldCountry = em.merge(countryCollectionOldCountry);
                }
            }
            for (Country countryCollectionNewCountry : countryCollectionNew) {
                if (!countryCollectionOld.contains(countryCollectionNewCountry)) {
                    Status oldStatusidOfCountryCollectionNewCountry = countryCollectionNewCountry.getStatusid();
                    countryCollectionNewCountry.setStatusid(status);
                    countryCollectionNewCountry = em.merge(countryCollectionNewCountry);
                    if (oldStatusidOfCountryCollectionNewCountry != null && !oldStatusidOfCountryCollectionNewCountry.equals(status)) {
                        oldStatusidOfCountryCollectionNewCountry.getCountryCollection().remove(countryCollectionNewCountry);
                        oldStatusidOfCountryCollectionNewCountry = em.merge(oldStatusidOfCountryCollectionNewCountry);
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

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status status;
            try {
                status = em.getReference(Status.class, id);
                status.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The status with id " + id + " no longer exists.", enfe);
            }
            Collection<Transaction> transactionCollection = status.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setStatusid(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            Collection<Merchant> merchantCollection = status.getMerchantCollection();
            for (Merchant merchantCollectionMerchant : merchantCollection) {
                merchantCollectionMerchant.setStatusid(null);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
            }
            Collection<Purchase> purchaseCollection = status.getPurchaseCollection();
            for (Purchase purchaseCollectionPurchase : purchaseCollection) {
                purchaseCollectionPurchase.setStatusid(null);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
            }
            Collection<Country> countryCollection = status.getCountryCollection();
            for (Country countryCollectionCountry : countryCollection) {
                countryCollectionCountry.setStatusid(null);
                countryCollectionCountry = em.merge(countryCollectionCountry);
            }
            em.remove(status);
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
