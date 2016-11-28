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
        if (merchant.getTransactionCollection() == null) {
            merchant.setTransactionCollection(new ArrayList<Transaction>());
        }
        if (merchant.getSiteuserCollection() == null) {
            merchant.setSiteuserCollection(new ArrayList<Siteuser>());
        }
        if (merchant.getPurchaseCollection() == null) {
            merchant.setPurchaseCollection(new ArrayList<Purchase>());
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
            Cart carttype = merchant.getCarttype();
            if (carttype != null) {
                carttype = em.getReference(carttype.getClass(), carttype.getId());
                merchant.setCarttype(carttype);
            }
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : merchant.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getId());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            merchant.setTransactionCollection(attachedTransactionCollection);
            Collection<Siteuser> attachedSiteuserCollection = new ArrayList<Siteuser>();
            for (Siteuser siteuserCollectionSiteuserToAttach : merchant.getSiteuserCollection()) {
                siteuserCollectionSiteuserToAttach = em.getReference(siteuserCollectionSiteuserToAttach.getClass(), siteuserCollectionSiteuserToAttach.getId());
                attachedSiteuserCollection.add(siteuserCollectionSiteuserToAttach);
            }
            merchant.setSiteuserCollection(attachedSiteuserCollection);
            Collection<Purchase> attachedPurchaseCollection = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionPurchaseToAttach : merchant.getPurchaseCollection()) {
                purchaseCollectionPurchaseToAttach = em.getReference(purchaseCollectionPurchaseToAttach.getClass(), purchaseCollectionPurchaseToAttach.getId());
                attachedPurchaseCollection.add(purchaseCollectionPurchaseToAttach);
            }
            merchant.setPurchaseCollection(attachedPurchaseCollection);
            em.persist(merchant);
            if (statusid != null) {
                statusid.getMerchantCollection().add(merchant);
                statusid = em.merge(statusid);
            }
            if (rateid != null) {
                rateid.getMerchantCollection().add(merchant);
                rateid = em.merge(rateid);
            }
            if (countryid != null) {
                countryid.getMerchantCollection().add(merchant);
                countryid = em.merge(countryid);
            }
            if (categoryid != null) {
                categoryid.getMerchantCollection().add(merchant);
                categoryid = em.merge(categoryid);
            }
            if (carttype != null) {
                carttype.getMerchantCollection().add(merchant);
                carttype = em.merge(carttype);
            }
            for (Transaction transactionCollectionTransaction : merchant.getTransactionCollection()) {
                Merchant oldMerchantidOfTransactionCollectionTransaction = transactionCollectionTransaction.getMerchantid();
                transactionCollectionTransaction.setMerchantid(merchant);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldMerchantidOfTransactionCollectionTransaction != null) {
                    oldMerchantidOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldMerchantidOfTransactionCollectionTransaction = em.merge(oldMerchantidOfTransactionCollectionTransaction);
                }
            }
            for (Siteuser siteuserCollectionSiteuser : merchant.getSiteuserCollection()) {
                Merchant oldMerchantidOfSiteuserCollectionSiteuser = siteuserCollectionSiteuser.getMerchantid();
                siteuserCollectionSiteuser.setMerchantid(merchant);
                siteuserCollectionSiteuser = em.merge(siteuserCollectionSiteuser);
                if (oldMerchantidOfSiteuserCollectionSiteuser != null) {
                    oldMerchantidOfSiteuserCollectionSiteuser.getSiteuserCollection().remove(siteuserCollectionSiteuser);
                    oldMerchantidOfSiteuserCollectionSiteuser = em.merge(oldMerchantidOfSiteuserCollectionSiteuser);
                }
            }
            for (Purchase purchaseCollectionPurchase : merchant.getPurchaseCollection()) {
                Merchant oldMerchantidOfPurchaseCollectionPurchase = purchaseCollectionPurchase.getMerchantid();
                purchaseCollectionPurchase.setMerchantid(merchant);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
                if (oldMerchantidOfPurchaseCollectionPurchase != null) {
                    oldMerchantidOfPurchaseCollectionPurchase.getPurchaseCollection().remove(purchaseCollectionPurchase);
                    oldMerchantidOfPurchaseCollectionPurchase = em.merge(oldMerchantidOfPurchaseCollectionPurchase);
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
            Cart carttypeOld = persistentMerchant.getCarttype();
            Cart carttypeNew = merchant.getCarttype();
            Collection<Transaction> transactionCollectionOld = persistentMerchant.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = merchant.getTransactionCollection();
            Collection<Siteuser> siteuserCollectionOld = persistentMerchant.getSiteuserCollection();
            Collection<Siteuser> siteuserCollectionNew = merchant.getSiteuserCollection();
            Collection<Purchase> purchaseCollectionOld = persistentMerchant.getPurchaseCollection();
            Collection<Purchase> purchaseCollectionNew = merchant.getPurchaseCollection();
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
            if (carttypeNew != null) {
                carttypeNew = em.getReference(carttypeNew.getClass(), carttypeNew.getId());
                merchant.setCarttype(carttypeNew);
            }
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getId());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            merchant.setTransactionCollection(transactionCollectionNew);
            Collection<Siteuser> attachedSiteuserCollectionNew = new ArrayList<Siteuser>();
            for (Siteuser siteuserCollectionNewSiteuserToAttach : siteuserCollectionNew) {
                siteuserCollectionNewSiteuserToAttach = em.getReference(siteuserCollectionNewSiteuserToAttach.getClass(), siteuserCollectionNewSiteuserToAttach.getId());
                attachedSiteuserCollectionNew.add(siteuserCollectionNewSiteuserToAttach);
            }
            siteuserCollectionNew = attachedSiteuserCollectionNew;
            merchant.setSiteuserCollection(siteuserCollectionNew);
            Collection<Purchase> attachedPurchaseCollectionNew = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionNewPurchaseToAttach : purchaseCollectionNew) {
                purchaseCollectionNewPurchaseToAttach = em.getReference(purchaseCollectionNewPurchaseToAttach.getClass(), purchaseCollectionNewPurchaseToAttach.getId());
                attachedPurchaseCollectionNew.add(purchaseCollectionNewPurchaseToAttach);
            }
            purchaseCollectionNew = attachedPurchaseCollectionNew;
            merchant.setPurchaseCollection(purchaseCollectionNew);
            merchant = em.merge(merchant);
            if (statusidOld != null && !statusidOld.equals(statusidNew)) {
                statusidOld.getMerchantCollection().remove(merchant);
                statusidOld = em.merge(statusidOld);
            }
            if (statusidNew != null && !statusidNew.equals(statusidOld)) {
                statusidNew.getMerchantCollection().add(merchant);
                statusidNew = em.merge(statusidNew);
            }
            if (rateidOld != null && !rateidOld.equals(rateidNew)) {
                rateidOld.getMerchantCollection().remove(merchant);
                rateidOld = em.merge(rateidOld);
            }
            if (rateidNew != null && !rateidNew.equals(rateidOld)) {
                rateidNew.getMerchantCollection().add(merchant);
                rateidNew = em.merge(rateidNew);
            }
            if (countryidOld != null && !countryidOld.equals(countryidNew)) {
                countryidOld.getMerchantCollection().remove(merchant);
                countryidOld = em.merge(countryidOld);
            }
            if (countryidNew != null && !countryidNew.equals(countryidOld)) {
                countryidNew.getMerchantCollection().add(merchant);
                countryidNew = em.merge(countryidNew);
            }
            if (categoryidOld != null && !categoryidOld.equals(categoryidNew)) {
                categoryidOld.getMerchantCollection().remove(merchant);
                categoryidOld = em.merge(categoryidOld);
            }
            if (categoryidNew != null && !categoryidNew.equals(categoryidOld)) {
                categoryidNew.getMerchantCollection().add(merchant);
                categoryidNew = em.merge(categoryidNew);
            }
            if (carttypeOld != null && !carttypeOld.equals(carttypeNew)) {
                carttypeOld.getMerchantCollection().remove(merchant);
                carttypeOld = em.merge(carttypeOld);
            }
            if (carttypeNew != null && !carttypeNew.equals(carttypeOld)) {
                carttypeNew.getMerchantCollection().add(merchant);
                carttypeNew = em.merge(carttypeNew);
            }
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setMerchantid(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    Merchant oldMerchantidOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getMerchantid();
                    transactionCollectionNewTransaction.setMerchantid(merchant);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldMerchantidOfTransactionCollectionNewTransaction != null && !oldMerchantidOfTransactionCollectionNewTransaction.equals(merchant)) {
                        oldMerchantidOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldMerchantidOfTransactionCollectionNewTransaction = em.merge(oldMerchantidOfTransactionCollectionNewTransaction);
                    }
                }
            }
            for (Siteuser siteuserCollectionOldSiteuser : siteuserCollectionOld) {
                if (!siteuserCollectionNew.contains(siteuserCollectionOldSiteuser)) {
                    siteuserCollectionOldSiteuser.setMerchantid(null);
                    siteuserCollectionOldSiteuser = em.merge(siteuserCollectionOldSiteuser);
                }
            }
            for (Siteuser siteuserCollectionNewSiteuser : siteuserCollectionNew) {
                if (!siteuserCollectionOld.contains(siteuserCollectionNewSiteuser)) {
                    Merchant oldMerchantidOfSiteuserCollectionNewSiteuser = siteuserCollectionNewSiteuser.getMerchantid();
                    siteuserCollectionNewSiteuser.setMerchantid(merchant);
                    siteuserCollectionNewSiteuser = em.merge(siteuserCollectionNewSiteuser);
                    if (oldMerchantidOfSiteuserCollectionNewSiteuser != null && !oldMerchantidOfSiteuserCollectionNewSiteuser.equals(merchant)) {
                        oldMerchantidOfSiteuserCollectionNewSiteuser.getSiteuserCollection().remove(siteuserCollectionNewSiteuser);
                        oldMerchantidOfSiteuserCollectionNewSiteuser = em.merge(oldMerchantidOfSiteuserCollectionNewSiteuser);
                    }
                }
            }
            for (Purchase purchaseCollectionOldPurchase : purchaseCollectionOld) {
                if (!purchaseCollectionNew.contains(purchaseCollectionOldPurchase)) {
                    purchaseCollectionOldPurchase.setMerchantid(null);
                    purchaseCollectionOldPurchase = em.merge(purchaseCollectionOldPurchase);
                }
            }
            for (Purchase purchaseCollectionNewPurchase : purchaseCollectionNew) {
                if (!purchaseCollectionOld.contains(purchaseCollectionNewPurchase)) {
                    Merchant oldMerchantidOfPurchaseCollectionNewPurchase = purchaseCollectionNewPurchase.getMerchantid();
                    purchaseCollectionNewPurchase.setMerchantid(merchant);
                    purchaseCollectionNewPurchase = em.merge(purchaseCollectionNewPurchase);
                    if (oldMerchantidOfPurchaseCollectionNewPurchase != null && !oldMerchantidOfPurchaseCollectionNewPurchase.equals(merchant)) {
                        oldMerchantidOfPurchaseCollectionNewPurchase.getPurchaseCollection().remove(purchaseCollectionNewPurchase);
                        oldMerchantidOfPurchaseCollectionNewPurchase = em.merge(oldMerchantidOfPurchaseCollectionNewPurchase);
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
                statusid.getMerchantCollection().remove(merchant);
                statusid = em.merge(statusid);
            }
            Rate rateid = merchant.getRateid();
            if (rateid != null) {
                rateid.getMerchantCollection().remove(merchant);
                rateid = em.merge(rateid);
            }
            Country countryid = merchant.getCountryid();
            if (countryid != null) {
                countryid.getMerchantCollection().remove(merchant);
                countryid = em.merge(countryid);
            }
            Category categoryid = merchant.getCategoryid();
            if (categoryid != null) {
                categoryid.getMerchantCollection().remove(merchant);
                categoryid = em.merge(categoryid);
            }
            Cart carttype = merchant.getCarttype();
            if (carttype != null) {
                carttype.getMerchantCollection().remove(merchant);
                carttype = em.merge(carttype);
            }
            Collection<Transaction> transactionCollection = merchant.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setMerchantid(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            Collection<Siteuser> siteuserCollection = merchant.getSiteuserCollection();
            for (Siteuser siteuserCollectionSiteuser : siteuserCollection) {
                siteuserCollectionSiteuser.setMerchantid(null);
                siteuserCollectionSiteuser = em.merge(siteuserCollectionSiteuser);
            }
            Collection<Purchase> purchaseCollection = merchant.getPurchaseCollection();
            for (Purchase purchaseCollectionPurchase : purchaseCollection) {
                purchaseCollectionPurchase.setMerchantid(null);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
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
