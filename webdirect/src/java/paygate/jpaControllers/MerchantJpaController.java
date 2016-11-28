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
import paygate.objects.Status;
import paygate.objects.Rate;
import paygate.objects.Country;
import paygate.objects.Category;
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
public class MerchantJpaController implements Serializable {

    public MerchantJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Merchant merchant) throws RollbackFailureException, Exception {
        if (merchant.getTransactionList() == null) {
            merchant.setTransactionList(new ArrayList<Transaction>());
        }
        if (merchant.getSiteuserList() == null) {
            merchant.setSiteuserList(new ArrayList<Siteuser>());
        }
        if (merchant.getPurchaseList() == null) {
            merchant.setPurchaseList(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status statusid = merchant.getStatusid();
            if (statusid != null) {
                statusid = em.getReference(statusid.getClass(), statusid.getId());
                merchant.setStatusid(statusid);
            }
            Rate rateid = merchant.getRateid();
            if (rateid != null) {
                rateid = em.getReference(rateid.getClass(), rateid.getId());
                merchant.setRateid(rateid);
            }
            Country countryid = merchant.getCountryid();
            if (countryid != null) {
                countryid = em.getReference(countryid.getClass(), countryid.getId());
                merchant.setCountryid(countryid);
            }
            Category categoryid = merchant.getCategoryid();
            if (categoryid != null) {
                categoryid = em.getReference(categoryid.getClass(), categoryid.getId());
                merchant.setCategoryid(categoryid);
            }
            List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
            for (Transaction transactionListTransactionToAttach : merchant.getTransactionList()) {
                transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(), transactionListTransactionToAttach.getId());
                attachedTransactionList.add(transactionListTransactionToAttach);
            }
            merchant.setTransactionList(attachedTransactionList);
            List<Siteuser> attachedSiteuserList = new ArrayList<Siteuser>();
            for (Siteuser siteuserListSiteuserToAttach : merchant.getSiteuserList()) {
                siteuserListSiteuserToAttach = em.getReference(siteuserListSiteuserToAttach.getClass(), siteuserListSiteuserToAttach.getId());
                attachedSiteuserList.add(siteuserListSiteuserToAttach);
            }
            merchant.setSiteuserList(attachedSiteuserList);
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : merchant.getPurchaseList()) {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getId());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            merchant.setPurchaseList(attachedPurchaseList);
            em.persist(merchant);
            if (statusid != null) {
                statusid.getMerchantList().add(merchant);
                statusid = em.merge(statusid);
            }
            if (rateid != null) {
                rateid.getMerchantList().add(merchant);
                rateid = em.merge(rateid);
            }
            if (countryid != null) {
                countryid.getMerchantList().add(merchant);
                countryid = em.merge(countryid);
            }
            if (categoryid != null) {
                categoryid.getMerchantList().add(merchant);
                categoryid = em.merge(categoryid);
            }
            for (Transaction transactionListTransaction : merchant.getTransactionList()) {
                Merchant oldMerchantidOfTransactionListTransaction = transactionListTransaction.getMerchantid();
                transactionListTransaction.setMerchantid(merchant);
                transactionListTransaction = em.merge(transactionListTransaction);
                if (oldMerchantidOfTransactionListTransaction != null) {
                    oldMerchantidOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
                    oldMerchantidOfTransactionListTransaction = em.merge(oldMerchantidOfTransactionListTransaction);
                }
            }
            for (Siteuser siteuserListSiteuser : merchant.getSiteuserList()) {
                Merchant oldMerchantidOfSiteuserListSiteuser = siteuserListSiteuser.getMerchantid();
                siteuserListSiteuser.setMerchantid(merchant);
                siteuserListSiteuser = em.merge(siteuserListSiteuser);
                if (oldMerchantidOfSiteuserListSiteuser != null) {
                    oldMerchantidOfSiteuserListSiteuser.getSiteuserList().remove(siteuserListSiteuser);
                    oldMerchantidOfSiteuserListSiteuser = em.merge(oldMerchantidOfSiteuserListSiteuser);
                }
            }
            for (Purchase purchaseListPurchase : merchant.getPurchaseList()) {
                Merchant oldMerchantidOfPurchaseListPurchase = purchaseListPurchase.getMerchantid();
                purchaseListPurchase.setMerchantid(merchant);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldMerchantidOfPurchaseListPurchase != null) {
                    oldMerchantidOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldMerchantidOfPurchaseListPurchase = em.merge(oldMerchantidOfPurchaseListPurchase);
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

    public void edit(Merchant merchant) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Merchant persistentMerchant = em.find(Merchant.class, merchant.getId());
            Status statusidOld = persistentMerchant.getStatusid();
            Status statusidNew = merchant.getStatusid();
            Rate rateidOld = persistentMerchant.getRateid();
            Rate rateidNew = merchant.getRateid();
            Country countryidOld = persistentMerchant.getCountryid();
            Country countryidNew = merchant.getCountryid();
            Category categoryidOld = persistentMerchant.getCategoryid();
            Category categoryidNew = merchant.getCategoryid();
            List<Transaction> transactionListOld = persistentMerchant.getTransactionList();
            List<Transaction> transactionListNew = merchant.getTransactionList();
            List<Siteuser> siteuserListOld = persistentMerchant.getSiteuserList();
            List<Siteuser> siteuserListNew = merchant.getSiteuserList();
            List<Purchase> purchaseListOld = persistentMerchant.getPurchaseList();
            List<Purchase> purchaseListNew = merchant.getPurchaseList();
            if (statusidNew != null) {
                statusidNew = em.getReference(statusidNew.getClass(), statusidNew.getId());
                merchant.setStatusid(statusidNew);
            }
            if (rateidNew != null) {
                rateidNew = em.getReference(rateidNew.getClass(), rateidNew.getId());
                merchant.setRateid(rateidNew);
            }
            if (countryidNew != null) {
                countryidNew = em.getReference(countryidNew.getClass(), countryidNew.getId());
                merchant.setCountryid(countryidNew);
            }
            if (categoryidNew != null) {
                categoryidNew = em.getReference(categoryidNew.getClass(), categoryidNew.getId());
                merchant.setCategoryid(categoryidNew);
            }
            List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
            for (Transaction transactionListNewTransactionToAttach : transactionListNew) {
                transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(), transactionListNewTransactionToAttach.getId());
                attachedTransactionListNew.add(transactionListNewTransactionToAttach);
            }
            transactionListNew = attachedTransactionListNew;
            merchant.setTransactionList(transactionListNew);
            List<Siteuser> attachedSiteuserListNew = new ArrayList<Siteuser>();
            for (Siteuser siteuserListNewSiteuserToAttach : siteuserListNew) {
                siteuserListNewSiteuserToAttach = em.getReference(siteuserListNewSiteuserToAttach.getClass(), siteuserListNewSiteuserToAttach.getId());
                attachedSiteuserListNew.add(siteuserListNewSiteuserToAttach);
            }
            siteuserListNew = attachedSiteuserListNew;
            merchant.setSiteuserList(siteuserListNew);
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew) {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getId());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            merchant.setPurchaseList(purchaseListNew);
            merchant = em.merge(merchant);
            if (statusidOld != null && !statusidOld.equals(statusidNew)) {
                statusidOld.getMerchantList().remove(merchant);
                statusidOld = em.merge(statusidOld);
            }
            if (statusidNew != null && !statusidNew.equals(statusidOld)) {
                statusidNew.getMerchantList().add(merchant);
                statusidNew = em.merge(statusidNew);
            }
            if (rateidOld != null && !rateidOld.equals(rateidNew)) {
                rateidOld.getMerchantList().remove(merchant);
                rateidOld = em.merge(rateidOld);
            }
            if (rateidNew != null && !rateidNew.equals(rateidOld)) {
                rateidNew.getMerchantList().add(merchant);
                rateidNew = em.merge(rateidNew);
            }
            if (countryidOld != null && !countryidOld.equals(countryidNew)) {
                countryidOld.getMerchantList().remove(merchant);
                countryidOld = em.merge(countryidOld);
            }
            if (countryidNew != null && !countryidNew.equals(countryidOld)) {
                countryidNew.getMerchantList().add(merchant);
                countryidNew = em.merge(countryidNew);
            }
            if (categoryidOld != null && !categoryidOld.equals(categoryidNew)) {
                categoryidOld.getMerchantList().remove(merchant);
                categoryidOld = em.merge(categoryidOld);
            }
            if (categoryidNew != null && !categoryidNew.equals(categoryidOld)) {
                categoryidNew.getMerchantList().add(merchant);
                categoryidNew = em.merge(categoryidNew);
            }
            for (Transaction transactionListOldTransaction : transactionListOld) {
                if (!transactionListNew.contains(transactionListOldTransaction)) {
                    transactionListOldTransaction.setMerchantid(null);
                    transactionListOldTransaction = em.merge(transactionListOldTransaction);
                }
            }
            for (Transaction transactionListNewTransaction : transactionListNew) {
                if (!transactionListOld.contains(transactionListNewTransaction)) {
                    Merchant oldMerchantidOfTransactionListNewTransaction = transactionListNewTransaction.getMerchantid();
                    transactionListNewTransaction.setMerchantid(merchant);
                    transactionListNewTransaction = em.merge(transactionListNewTransaction);
                    if (oldMerchantidOfTransactionListNewTransaction != null && !oldMerchantidOfTransactionListNewTransaction.equals(merchant)) {
                        oldMerchantidOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
                        oldMerchantidOfTransactionListNewTransaction = em.merge(oldMerchantidOfTransactionListNewTransaction);
                    }
                }
            }
            for (Siteuser siteuserListOldSiteuser : siteuserListOld) {
                if (!siteuserListNew.contains(siteuserListOldSiteuser)) {
                    siteuserListOldSiteuser.setMerchantid(null);
                    siteuserListOldSiteuser = em.merge(siteuserListOldSiteuser);
                }
            }
            for (Siteuser siteuserListNewSiteuser : siteuserListNew) {
                if (!siteuserListOld.contains(siteuserListNewSiteuser)) {
                    Merchant oldMerchantidOfSiteuserListNewSiteuser = siteuserListNewSiteuser.getMerchantid();
                    siteuserListNewSiteuser.setMerchantid(merchant);
                    siteuserListNewSiteuser = em.merge(siteuserListNewSiteuser);
                    if (oldMerchantidOfSiteuserListNewSiteuser != null && !oldMerchantidOfSiteuserListNewSiteuser.equals(merchant)) {
                        oldMerchantidOfSiteuserListNewSiteuser.getSiteuserList().remove(siteuserListNewSiteuser);
                        oldMerchantidOfSiteuserListNewSiteuser = em.merge(oldMerchantidOfSiteuserListNewSiteuser);
                    }
                }
            }
            for (Purchase purchaseListOldPurchase : purchaseListOld) {
                if (!purchaseListNew.contains(purchaseListOldPurchase)) {
                    purchaseListOldPurchase.setMerchantid(null);
                    purchaseListOldPurchase = em.merge(purchaseListOldPurchase);
                }
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew) {
                if (!purchaseListOld.contains(purchaseListNewPurchase)) {
                    Merchant oldMerchantidOfPurchaseListNewPurchase = purchaseListNewPurchase.getMerchantid();
                    purchaseListNewPurchase.setMerchantid(merchant);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldMerchantidOfPurchaseListNewPurchase != null && !oldMerchantidOfPurchaseListNewPurchase.equals(merchant)) {
                        oldMerchantidOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldMerchantidOfPurchaseListNewPurchase = em.merge(oldMerchantidOfPurchaseListNewPurchase);
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

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Merchant merchant;
            try {
                merchant = em.getReference(Merchant.class, id);
                merchant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The merchant with id " + id + " no longer exists.", enfe);
            }
            Status statusid = merchant.getStatusid();
            if (statusid != null) {
                statusid.getMerchantList().remove(merchant);
                statusid = em.merge(statusid);
            }
            Rate rateid = merchant.getRateid();
            if (rateid != null) {
                rateid.getMerchantList().remove(merchant);
                rateid = em.merge(rateid);
            }
            Country countryid = merchant.getCountryid();
            if (countryid != null) {
                countryid.getMerchantList().remove(merchant);
                countryid = em.merge(countryid);
            }
            Category categoryid = merchant.getCategoryid();
            if (categoryid != null) {
                categoryid.getMerchantList().remove(merchant);
                categoryid = em.merge(categoryid);
            }
            List<Transaction> transactionList = merchant.getTransactionList();
            for (Transaction transactionListTransaction : transactionList) {
                transactionListTransaction.setMerchantid(null);
                transactionListTransaction = em.merge(transactionListTransaction);
            }
            List<Siteuser> siteuserList = merchant.getSiteuserList();
            for (Siteuser siteuserListSiteuser : siteuserList) {
                siteuserListSiteuser.setMerchantid(null);
                siteuserListSiteuser = em.merge(siteuserListSiteuser);
            }
            List<Purchase> purchaseList = merchant.getPurchaseList();
            for (Purchase purchaseListPurchase : purchaseList) {
                purchaseListPurchase.setMerchantid(null);
                purchaseListPurchase = em.merge(purchaseListPurchase);
            }
            em.remove(merchant);
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
