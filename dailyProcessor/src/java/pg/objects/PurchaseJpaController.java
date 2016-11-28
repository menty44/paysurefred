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
public class PurchaseJpaController implements Serializable {

    public PurchaseJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Purchase purchase) throws RollbackFailureException, Exception {
        if (purchase.getDataCollection() == null) {
            purchase.setDataCollection(new ArrayList<Data>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transactiontype transactiontype = purchase.getTransactiontype();
            if (transactiontype != null) {
                transactiontype = em.getReference(transactiontype.getClass(), transactiontype.getId());
                purchase.setTransactiontype(transactiontype);
            }
            Transactionsource transactionsourceid = purchase.getTransactionsourceid();
            if (transactionsourceid != null) {
                transactionsourceid = em.getReference(transactionsourceid.getClass(), transactionsourceid.getId());
                purchase.setTransactionsourceid(transactionsourceid);
            }
            Status statusid = purchase.getStatusid();
            if (statusid != null) {
                statusid = em.getReference(statusid.getClass(), statusid.getId());
                purchase.setStatusid(statusid);
            }
            Merchant merchantid = purchase.getMerchantid();
            if (merchantid != null) {
                merchantid = em.getReference(merchantid.getClass(), merchantid.getId());
                purchase.setMerchantid(merchantid);
            }
            Gender genderid = purchase.getGenderid();
            if (genderid != null) {
                genderid = em.getReference(genderid.getClass(), genderid.getId());
                purchase.setGenderid(genderid);
            }
            Collection<Data> attachedDataCollection = new ArrayList<Data>();
            for (Data dataCollectionDataToAttach : purchase.getDataCollection()) {
                dataCollectionDataToAttach = em.getReference(dataCollectionDataToAttach.getClass(), dataCollectionDataToAttach.getId());
                attachedDataCollection.add(dataCollectionDataToAttach);
            }
            purchase.setDataCollection(attachedDataCollection);
            em.persist(purchase);
            if (transactiontype != null) {
                transactiontype.getPurchaseCollection().add(purchase);
                transactiontype = em.merge(transactiontype);
            }
            if (transactionsourceid != null) {
                transactionsourceid.getPurchaseCollection().add(purchase);
                transactionsourceid = em.merge(transactionsourceid);
            }
            if (statusid != null) {
                statusid.getPurchaseCollection().add(purchase);
                statusid = em.merge(statusid);
            }
            if (merchantid != null) {
                merchantid.getPurchaseCollection().add(purchase);
                merchantid = em.merge(merchantid);
            }
            if (genderid != null) {
                genderid.getPurchaseCollection().add(purchase);
                genderid = em.merge(genderid);
            }
            for (Data dataCollectionData : purchase.getDataCollection()) {
                Purchase oldPurchaseidOfDataCollectionData = dataCollectionData.getPurchaseid();
                dataCollectionData.setPurchaseid(purchase);
                dataCollectionData = em.merge(dataCollectionData);
                if (oldPurchaseidOfDataCollectionData != null) {
                    oldPurchaseidOfDataCollectionData.getDataCollection().remove(dataCollectionData);
                    oldPurchaseidOfDataCollectionData = em.merge(oldPurchaseidOfDataCollectionData);
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

    public void edit(Purchase purchase) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Purchase persistentPurchase = em.find(Purchase.class, purchase.getId());
            Transactiontype transactiontypeOld = persistentPurchase.getTransactiontype();
            Transactiontype transactiontypeNew = purchase.getTransactiontype();
            Transactionsource transactionsourceidOld = persistentPurchase.getTransactionsourceid();
            Transactionsource transactionsourceidNew = purchase.getTransactionsourceid();
            Status statusidOld = persistentPurchase.getStatusid();
            Status statusidNew = purchase.getStatusid();
            Merchant merchantidOld = persistentPurchase.getMerchantid();
            Merchant merchantidNew = purchase.getMerchantid();
            Gender genderidOld = persistentPurchase.getGenderid();
            Gender genderidNew = purchase.getGenderid();
            Collection<Data> dataCollectionOld = persistentPurchase.getDataCollection();
            Collection<Data> dataCollectionNew = purchase.getDataCollection();
            if (transactiontypeNew != null) {
                transactiontypeNew = em.getReference(transactiontypeNew.getClass(), transactiontypeNew.getId());
                purchase.setTransactiontype(transactiontypeNew);
            }
            if (transactionsourceidNew != null) {
                transactionsourceidNew = em.getReference(transactionsourceidNew.getClass(), transactionsourceidNew.getId());
                purchase.setTransactionsourceid(transactionsourceidNew);
            }
            if (statusidNew != null) {
                statusidNew = em.getReference(statusidNew.getClass(), statusidNew.getId());
                purchase.setStatusid(statusidNew);
            }
            if (merchantidNew != null) {
                merchantidNew = em.getReference(merchantidNew.getClass(), merchantidNew.getId());
                purchase.setMerchantid(merchantidNew);
            }
            if (genderidNew != null) {
                genderidNew = em.getReference(genderidNew.getClass(), genderidNew.getId());
                purchase.setGenderid(genderidNew);
            }
            Collection<Data> attachedDataCollectionNew = new ArrayList<Data>();
            for (Data dataCollectionNewDataToAttach : dataCollectionNew) {
                dataCollectionNewDataToAttach = em.getReference(dataCollectionNewDataToAttach.getClass(), dataCollectionNewDataToAttach.getId());
                attachedDataCollectionNew.add(dataCollectionNewDataToAttach);
            }
            dataCollectionNew = attachedDataCollectionNew;
            purchase.setDataCollection(dataCollectionNew);
            purchase = em.merge(purchase);
            if (transactiontypeOld != null && !transactiontypeOld.equals(transactiontypeNew)) {
                transactiontypeOld.getPurchaseCollection().remove(purchase);
                transactiontypeOld = em.merge(transactiontypeOld);
            }
            if (transactiontypeNew != null && !transactiontypeNew.equals(transactiontypeOld)) {
                transactiontypeNew.getPurchaseCollection().add(purchase);
                transactiontypeNew = em.merge(transactiontypeNew);
            }
            if (transactionsourceidOld != null && !transactionsourceidOld.equals(transactionsourceidNew)) {
                transactionsourceidOld.getPurchaseCollection().remove(purchase);
                transactionsourceidOld = em.merge(transactionsourceidOld);
            }
            if (transactionsourceidNew != null && !transactionsourceidNew.equals(transactionsourceidOld)) {
                transactionsourceidNew.getPurchaseCollection().add(purchase);
                transactionsourceidNew = em.merge(transactionsourceidNew);
            }
            if (statusidOld != null && !statusidOld.equals(statusidNew)) {
                statusidOld.getPurchaseCollection().remove(purchase);
                statusidOld = em.merge(statusidOld);
            }
            if (statusidNew != null && !statusidNew.equals(statusidOld)) {
                statusidNew.getPurchaseCollection().add(purchase);
                statusidNew = em.merge(statusidNew);
            }
            if (merchantidOld != null && !merchantidOld.equals(merchantidNew)) {
                merchantidOld.getPurchaseCollection().remove(purchase);
                merchantidOld = em.merge(merchantidOld);
            }
            if (merchantidNew != null && !merchantidNew.equals(merchantidOld)) {
                merchantidNew.getPurchaseCollection().add(purchase);
                merchantidNew = em.merge(merchantidNew);
            }
            if (genderidOld != null && !genderidOld.equals(genderidNew)) {
                genderidOld.getPurchaseCollection().remove(purchase);
                genderidOld = em.merge(genderidOld);
            }
            if (genderidNew != null && !genderidNew.equals(genderidOld)) {
                genderidNew.getPurchaseCollection().add(purchase);
                genderidNew = em.merge(genderidNew);
            }
            for (Data dataCollectionOldData : dataCollectionOld) {
                if (!dataCollectionNew.contains(dataCollectionOldData)) {
                    dataCollectionOldData.setPurchaseid(null);
                    dataCollectionOldData = em.merge(dataCollectionOldData);
                }
            }
            for (Data dataCollectionNewData : dataCollectionNew) {
                if (!dataCollectionOld.contains(dataCollectionNewData)) {
                    Purchase oldPurchaseidOfDataCollectionNewData = dataCollectionNewData.getPurchaseid();
                    dataCollectionNewData.setPurchaseid(purchase);
                    dataCollectionNewData = em.merge(dataCollectionNewData);
                    if (oldPurchaseidOfDataCollectionNewData != null && !oldPurchaseidOfDataCollectionNewData.equals(purchase)) {
                        oldPurchaseidOfDataCollectionNewData.getDataCollection().remove(dataCollectionNewData);
                        oldPurchaseidOfDataCollectionNewData = em.merge(oldPurchaseidOfDataCollectionNewData);
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
                Integer id = purchase.getId();
                if (findPurchase(id) == null) {
                    throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.");
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
            Purchase purchase;
            try {
                purchase = em.getReference(Purchase.class, id);
                purchase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.", enfe);
            }
            Transactiontype transactiontype = purchase.getTransactiontype();
            if (transactiontype != null) {
                transactiontype.getPurchaseCollection().remove(purchase);
                transactiontype = em.merge(transactiontype);
            }
            Transactionsource transactionsourceid = purchase.getTransactionsourceid();
            if (transactionsourceid != null) {
                transactionsourceid.getPurchaseCollection().remove(purchase);
                transactionsourceid = em.merge(transactionsourceid);
            }
            Status statusid = purchase.getStatusid();
            if (statusid != null) {
                statusid.getPurchaseCollection().remove(purchase);
                statusid = em.merge(statusid);
            }
            Merchant merchantid = purchase.getMerchantid();
            if (merchantid != null) {
                merchantid.getPurchaseCollection().remove(purchase);
                merchantid = em.merge(merchantid);
            }
            Gender genderid = purchase.getGenderid();
            if (genderid != null) {
                genderid.getPurchaseCollection().remove(purchase);
                genderid = em.merge(genderid);
            }
            Collection<Data> dataCollection = purchase.getDataCollection();
            for (Data dataCollectionData : dataCollection) {
                dataCollectionData.setPurchaseid(null);
                dataCollectionData = em.merge(dataCollectionData);
            }
            em.remove(purchase);
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

    public List<Purchase> findPurchaseEntities() {
        return findPurchaseEntities(true, -1, -1);
    }

    public List<Purchase> findPurchaseEntities(int maxResults, int firstResult) {
        return findPurchaseEntities(false, maxResults, firstResult);
    }

    private List<Purchase> findPurchaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Purchase.class));
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

    public Purchase findPurchase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Purchase.class, id);
        } finally {
            em.close();
        }
    }

    public int getPurchaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Purchase> rt = cq.from(Purchase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
