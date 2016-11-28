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
import paygate.objects.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.*;

/**
 *
 * @author paysure
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
        if (status.getTransactionList() == null) {
            status.setTransactionList(new ArrayList<Transaction>());
        }
        if (status.getMerchantList() == null) {
            status.setMerchantList(new ArrayList<Merchant>());
        }
        if (status.getPurchaseList() == null) {
            status.setPurchaseList(new ArrayList<Purchase>());
        }
        if (status.getCountryList() == null) {
            status.setCountryList(new ArrayList<Country>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
            for (Transaction transactionListTransactionToAttach : status.getTransactionList()) {
                transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(), transactionListTransactionToAttach.getId());
                attachedTransactionList.add(transactionListTransactionToAttach);
            }
            status.setTransactionList(attachedTransactionList);
            List<Merchant> attachedMerchantList = new ArrayList<Merchant>();
            for (Merchant merchantListMerchantToAttach : status.getMerchantList()) {
                merchantListMerchantToAttach = em.getReference(merchantListMerchantToAttach.getClass(), merchantListMerchantToAttach.getId());
                attachedMerchantList.add(merchantListMerchantToAttach);
            }
            status.setMerchantList(attachedMerchantList);
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : status.getPurchaseList()) {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getId());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            status.setPurchaseList(attachedPurchaseList);
            List<Country> attachedCountryList = new ArrayList<Country>();
            for (Country countryListCountryToAttach : status.getCountryList()) {
                countryListCountryToAttach = em.getReference(countryListCountryToAttach.getClass(), countryListCountryToAttach.getId());
                attachedCountryList.add(countryListCountryToAttach);
            }
            status.setCountryList(attachedCountryList);
            em.persist(status);
            for (Transaction transactionListTransaction : status.getTransactionList()) {
                Status oldStatusidOfTransactionListTransaction = transactionListTransaction.getStatusid();
                transactionListTransaction.setStatusid(status);
                transactionListTransaction = em.merge(transactionListTransaction);
                if (oldStatusidOfTransactionListTransaction != null) {
                    oldStatusidOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
                    oldStatusidOfTransactionListTransaction = em.merge(oldStatusidOfTransactionListTransaction);
                }
            }
            for (Merchant merchantListMerchant : status.getMerchantList()) {
                Status oldStatusidOfMerchantListMerchant = merchantListMerchant.getStatusid();
                merchantListMerchant.setStatusid(status);
                merchantListMerchant = em.merge(merchantListMerchant);
                if (oldStatusidOfMerchantListMerchant != null) {
                    oldStatusidOfMerchantListMerchant.getMerchantList().remove(merchantListMerchant);
                    oldStatusidOfMerchantListMerchant = em.merge(oldStatusidOfMerchantListMerchant);
                }
            }
            for (Purchase purchaseListPurchase : status.getPurchaseList()) {
                Status oldStatusidOfPurchaseListPurchase = purchaseListPurchase.getStatusid();
                purchaseListPurchase.setStatusid(status);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldStatusidOfPurchaseListPurchase != null) {
                    oldStatusidOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldStatusidOfPurchaseListPurchase = em.merge(oldStatusidOfPurchaseListPurchase);
                }
            }
            for (Country countryListCountry : status.getCountryList()) {
                Status oldStatusidOfCountryListCountry = countryListCountry.getStatusid();
                countryListCountry.setStatusid(status);
                countryListCountry = em.merge(countryListCountry);
                if (oldStatusidOfCountryListCountry != null) {
                    oldStatusidOfCountryListCountry.getCountryList().remove(countryListCountry);
                    oldStatusidOfCountryListCountry = em.merge(oldStatusidOfCountryListCountry);
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
            List<Transaction> transactionListOld = persistentStatus.getTransactionList();
            List<Transaction> transactionListNew = status.getTransactionList();
            List<Merchant> merchantListOld = persistentStatus.getMerchantList();
            List<Merchant> merchantListNew = status.getMerchantList();
            List<Purchase> purchaseListOld = persistentStatus.getPurchaseList();
            List<Purchase> purchaseListNew = status.getPurchaseList();
            List<Country> countryListOld = persistentStatus.getCountryList();
            List<Country> countryListNew = status.getCountryList();
            List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
            for (Transaction transactionListNewTransactionToAttach : transactionListNew) {
                transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(), transactionListNewTransactionToAttach.getId());
                attachedTransactionListNew.add(transactionListNewTransactionToAttach);
            }
            transactionListNew = attachedTransactionListNew;
            status.setTransactionList(transactionListNew);
            List<Merchant> attachedMerchantListNew = new ArrayList<Merchant>();
            for (Merchant merchantListNewMerchantToAttach : merchantListNew) {
                merchantListNewMerchantToAttach = em.getReference(merchantListNewMerchantToAttach.getClass(), merchantListNewMerchantToAttach.getId());
                attachedMerchantListNew.add(merchantListNewMerchantToAttach);
            }
            merchantListNew = attachedMerchantListNew;
            status.setMerchantList(merchantListNew);
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew) {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getId());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            status.setPurchaseList(purchaseListNew);
            List<Country> attachedCountryListNew = new ArrayList<Country>();
            for (Country countryListNewCountryToAttach : countryListNew) {
                countryListNewCountryToAttach = em.getReference(countryListNewCountryToAttach.getClass(), countryListNewCountryToAttach.getId());
                attachedCountryListNew.add(countryListNewCountryToAttach);
            }
            countryListNew = attachedCountryListNew;
            status.setCountryList(countryListNew);
            status = em.merge(status);
            for (Transaction transactionListOldTransaction : transactionListOld) {
                if (!transactionListNew.contains(transactionListOldTransaction)) {
                    transactionListOldTransaction.setStatusid(null);
                    transactionListOldTransaction = em.merge(transactionListOldTransaction);
                }
            }
            for (Transaction transactionListNewTransaction : transactionListNew) {
                if (!transactionListOld.contains(transactionListNewTransaction)) {
                    Status oldStatusidOfTransactionListNewTransaction = transactionListNewTransaction.getStatusid();
                    transactionListNewTransaction.setStatusid(status);
                    transactionListNewTransaction = em.merge(transactionListNewTransaction);
                    if (oldStatusidOfTransactionListNewTransaction != null && !oldStatusidOfTransactionListNewTransaction.equals(status)) {
                        oldStatusidOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
                        oldStatusidOfTransactionListNewTransaction = em.merge(oldStatusidOfTransactionListNewTransaction);
                    }
                }
            }
            for (Merchant merchantListOldMerchant : merchantListOld) {
                if (!merchantListNew.contains(merchantListOldMerchant)) {
                    merchantListOldMerchant.setStatusid(null);
                    merchantListOldMerchant = em.merge(merchantListOldMerchant);
                }
            }
            for (Merchant merchantListNewMerchant : merchantListNew) {
                if (!merchantListOld.contains(merchantListNewMerchant)) {
                    Status oldStatusidOfMerchantListNewMerchant = merchantListNewMerchant.getStatusid();
                    merchantListNewMerchant.setStatusid(status);
                    merchantListNewMerchant = em.merge(merchantListNewMerchant);
                    if (oldStatusidOfMerchantListNewMerchant != null && !oldStatusidOfMerchantListNewMerchant.equals(status)) {
                        oldStatusidOfMerchantListNewMerchant.getMerchantList().remove(merchantListNewMerchant);
                        oldStatusidOfMerchantListNewMerchant = em.merge(oldStatusidOfMerchantListNewMerchant);
                    }
                }
            }
            for (Purchase purchaseListOldPurchase : purchaseListOld) {
                if (!purchaseListNew.contains(purchaseListOldPurchase)) {
                    purchaseListOldPurchase.setStatusid(null);
                    purchaseListOldPurchase = em.merge(purchaseListOldPurchase);
                }
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew) {
                if (!purchaseListOld.contains(purchaseListNewPurchase)) {
                    Status oldStatusidOfPurchaseListNewPurchase = purchaseListNewPurchase.getStatusid();
                    purchaseListNewPurchase.setStatusid(status);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldStatusidOfPurchaseListNewPurchase != null && !oldStatusidOfPurchaseListNewPurchase.equals(status)) {
                        oldStatusidOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldStatusidOfPurchaseListNewPurchase = em.merge(oldStatusidOfPurchaseListNewPurchase);
                    }
                }
            }
            for (Country countryListOldCountry : countryListOld) {
                if (!countryListNew.contains(countryListOldCountry)) {
                    countryListOldCountry.setStatusid(null);
                    countryListOldCountry = em.merge(countryListOldCountry);
                }
            }
            for (Country countryListNewCountry : countryListNew) {
                if (!countryListOld.contains(countryListNewCountry)) {
                    Status oldStatusidOfCountryListNewCountry = countryListNewCountry.getStatusid();
                    countryListNewCountry.setStatusid(status);
                    countryListNewCountry = em.merge(countryListNewCountry);
                    if (oldStatusidOfCountryListNewCountry != null && !oldStatusidOfCountryListNewCountry.equals(status)) {
                        oldStatusidOfCountryListNewCountry.getCountryList().remove(countryListNewCountry);
                        oldStatusidOfCountryListNewCountry = em.merge(oldStatusidOfCountryListNewCountry);
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
            List<Transaction> transactionList = status.getTransactionList();
            for (Transaction transactionListTransaction : transactionList) {
                transactionListTransaction.setStatusid(null);
                transactionListTransaction = em.merge(transactionListTransaction);
            }
            List<Merchant> merchantList = status.getMerchantList();
            for (Merchant merchantListMerchant : merchantList) {
                merchantListMerchant.setStatusid(null);
                merchantListMerchant = em.merge(merchantListMerchant);
            }
            List<Purchase> purchaseList = status.getPurchaseList();
            for (Purchase purchaseListPurchase : purchaseList) {
                purchaseListPurchase.setStatusid(null);
                purchaseListPurchase = em.merge(purchaseListPurchase);
            }
            List<Country> countryList = status.getCountryList();
            for (Country countryListCountry : countryList) {
                countryListCountry.setStatusid(null);
                countryListCountry = em.merge(countryListCountry);
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
