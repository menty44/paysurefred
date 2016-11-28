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
import paygate.objects.Transactiontype;
import paygate.objects.Transactionsource;
import paygate.objects.Status;
import paygate.objects.Merchant;
import paygate.objects.Gender;
import paygate.objects.Data;
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
        if (purchase.getDataList() == null) {
            purchase.setDataList(new ArrayList<Data>());
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
            List<Data> attachedDataList = new ArrayList<Data>();
            for (Data dataListDataToAttach : purchase.getDataList()) {
                dataListDataToAttach = em.getReference(dataListDataToAttach.getClass(), dataListDataToAttach.getId());
                attachedDataList.add(dataListDataToAttach);
            }
            purchase.setDataList(attachedDataList);
            em.persist(purchase);
            if (transactiontype != null) {
                transactiontype.getPurchaseList().add(purchase);
                transactiontype = em.merge(transactiontype);
            }
            if (transactionsourceid != null) {
                transactionsourceid.getPurchaseList().add(purchase);
                transactionsourceid = em.merge(transactionsourceid);
            }
            if (statusid != null) {
                statusid.getPurchaseList().add(purchase);
                statusid = em.merge(statusid);
            }
            if (merchantid != null) {
                merchantid.getPurchaseList().add(purchase);
                merchantid = em.merge(merchantid);
            }
            if (genderid != null) {
                genderid.getPurchaseList().add(purchase);
                genderid = em.merge(genderid);
            }
            for (Data dataListData : purchase.getDataList()) {
                Purchase oldPurchaseidOfDataListData = dataListData.getPurchaseid();
                dataListData.setPurchaseid(purchase);
                dataListData = em.merge(dataListData);
                if (oldPurchaseidOfDataListData != null) {
                    oldPurchaseidOfDataListData.getDataList().remove(dataListData);
                    oldPurchaseidOfDataListData = em.merge(oldPurchaseidOfDataListData);
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
            List<Data> dataListOld = persistentPurchase.getDataList();
            List<Data> dataListNew = purchase.getDataList();
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
            List<Data> attachedDataListNew = new ArrayList<Data>();
            for (Data dataListNewDataToAttach : dataListNew) {
                dataListNewDataToAttach = em.getReference(dataListNewDataToAttach.getClass(), dataListNewDataToAttach.getId());
                attachedDataListNew.add(dataListNewDataToAttach);
            }
            dataListNew = attachedDataListNew;
            purchase.setDataList(dataListNew);
            purchase = em.merge(purchase);
            if (transactiontypeOld != null && !transactiontypeOld.equals(transactiontypeNew)) {
                transactiontypeOld.getPurchaseList().remove(purchase);
                transactiontypeOld = em.merge(transactiontypeOld);
            }
            if (transactiontypeNew != null && !transactiontypeNew.equals(transactiontypeOld)) {
                transactiontypeNew.getPurchaseList().add(purchase);
                transactiontypeNew = em.merge(transactiontypeNew);
            }
            if (transactionsourceidOld != null && !transactionsourceidOld.equals(transactionsourceidNew)) {
                transactionsourceidOld.getPurchaseList().remove(purchase);
                transactionsourceidOld = em.merge(transactionsourceidOld);
            }
            if (transactionsourceidNew != null && !transactionsourceidNew.equals(transactionsourceidOld)) {
                transactionsourceidNew.getPurchaseList().add(purchase);
                transactionsourceidNew = em.merge(transactionsourceidNew);
            }
            if (statusidOld != null && !statusidOld.equals(statusidNew)) {
                statusidOld.getPurchaseList().remove(purchase);
                statusidOld = em.merge(statusidOld);
            }
            if (statusidNew != null && !statusidNew.equals(statusidOld)) {
                statusidNew.getPurchaseList().add(purchase);
                statusidNew = em.merge(statusidNew);
            }
            if (merchantidOld != null && !merchantidOld.equals(merchantidNew)) {
                merchantidOld.getPurchaseList().remove(purchase);
                merchantidOld = em.merge(merchantidOld);
            }
            if (merchantidNew != null && !merchantidNew.equals(merchantidOld)) {
                merchantidNew.getPurchaseList().add(purchase);
                merchantidNew = em.merge(merchantidNew);
            }
            if (genderidOld != null && !genderidOld.equals(genderidNew)) {
                genderidOld.getPurchaseList().remove(purchase);
                genderidOld = em.merge(genderidOld);
            }
            if (genderidNew != null && !genderidNew.equals(genderidOld)) {
                genderidNew.getPurchaseList().add(purchase);
                genderidNew = em.merge(genderidNew);
            }
            for (Data dataListOldData : dataListOld) {
                if (!dataListNew.contains(dataListOldData)) {
                    dataListOldData.setPurchaseid(null);
                    dataListOldData = em.merge(dataListOldData);
                }
            }
            for (Data dataListNewData : dataListNew) {
                if (!dataListOld.contains(dataListNewData)) {
                    Purchase oldPurchaseidOfDataListNewData = dataListNewData.getPurchaseid();
                    dataListNewData.setPurchaseid(purchase);
                    dataListNewData = em.merge(dataListNewData);
                    if (oldPurchaseidOfDataListNewData != null && !oldPurchaseidOfDataListNewData.equals(purchase)) {
                        oldPurchaseidOfDataListNewData.getDataList().remove(dataListNewData);
                        oldPurchaseidOfDataListNewData = em.merge(oldPurchaseidOfDataListNewData);
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
                transactiontype.getPurchaseList().remove(purchase);
                transactiontype = em.merge(transactiontype);
            }
            Transactionsource transactionsourceid = purchase.getTransactionsourceid();
            if (transactionsourceid != null) {
                transactionsourceid.getPurchaseList().remove(purchase);
                transactionsourceid = em.merge(transactionsourceid);
            }
            Status statusid = purchase.getStatusid();
            if (statusid != null) {
                statusid.getPurchaseList().remove(purchase);
                statusid = em.merge(statusid);
            }
            Merchant merchantid = purchase.getMerchantid();
            if (merchantid != null) {
                merchantid.getPurchaseList().remove(purchase);
                merchantid = em.merge(merchantid);
            }
            Gender genderid = purchase.getGenderid();
            if (genderid != null) {
                genderid.getPurchaseList().remove(purchase);
                genderid = em.merge(genderid);
            }
            List<Data> dataList = purchase.getDataList();
            for (Data dataListData : dataList) {
                dataListData.setPurchaseid(null);
                dataListData = em.merge(dataListData);
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
