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
public class TransactiontypeJpaController implements Serializable {

    public TransactiontypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transactiontype transactiontype) throws RollbackFailureException, Exception {
        if (transactiontype.getPurchaseCollection() == null) {
            transactiontype.setPurchaseCollection(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Purchase> attachedPurchaseCollection = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionPurchaseToAttach : transactiontype.getPurchaseCollection()) {
                purchaseCollectionPurchaseToAttach = em.getReference(purchaseCollectionPurchaseToAttach.getClass(), purchaseCollectionPurchaseToAttach.getId());
                attachedPurchaseCollection.add(purchaseCollectionPurchaseToAttach);
            }
            transactiontype.setPurchaseCollection(attachedPurchaseCollection);
            em.persist(transactiontype);
            for (Purchase purchaseCollectionPurchase : transactiontype.getPurchaseCollection()) {
                Transactiontype oldTransactiontypeOfPurchaseCollectionPurchase = purchaseCollectionPurchase.getTransactiontype();
                purchaseCollectionPurchase.setTransactiontype(transactiontype);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
                if (oldTransactiontypeOfPurchaseCollectionPurchase != null) {
                    oldTransactiontypeOfPurchaseCollectionPurchase.getPurchaseCollection().remove(purchaseCollectionPurchase);
                    oldTransactiontypeOfPurchaseCollectionPurchase = em.merge(oldTransactiontypeOfPurchaseCollectionPurchase);
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

    public void edit(Transactiontype transactiontype) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transactiontype persistentTransactiontype = em.find(Transactiontype.class, transactiontype.getId());
            Collection<Purchase> purchaseCollectionOld = persistentTransactiontype.getPurchaseCollection();
            Collection<Purchase> purchaseCollectionNew = transactiontype.getPurchaseCollection();
            Collection<Purchase> attachedPurchaseCollectionNew = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionNewPurchaseToAttach : purchaseCollectionNew) {
                purchaseCollectionNewPurchaseToAttach = em.getReference(purchaseCollectionNewPurchaseToAttach.getClass(), purchaseCollectionNewPurchaseToAttach.getId());
                attachedPurchaseCollectionNew.add(purchaseCollectionNewPurchaseToAttach);
            }
            purchaseCollectionNew = attachedPurchaseCollectionNew;
            transactiontype.setPurchaseCollection(purchaseCollectionNew);
            transactiontype = em.merge(transactiontype);
            for (Purchase purchaseCollectionOldPurchase : purchaseCollectionOld) {
                if (!purchaseCollectionNew.contains(purchaseCollectionOldPurchase)) {
                    purchaseCollectionOldPurchase.setTransactiontype(null);
                    purchaseCollectionOldPurchase = em.merge(purchaseCollectionOldPurchase);
                }
            }
            for (Purchase purchaseCollectionNewPurchase : purchaseCollectionNew) {
                if (!purchaseCollectionOld.contains(purchaseCollectionNewPurchase)) {
                    Transactiontype oldTransactiontypeOfPurchaseCollectionNewPurchase = purchaseCollectionNewPurchase.getTransactiontype();
                    purchaseCollectionNewPurchase.setTransactiontype(transactiontype);
                    purchaseCollectionNewPurchase = em.merge(purchaseCollectionNewPurchase);
                    if (oldTransactiontypeOfPurchaseCollectionNewPurchase != null && !oldTransactiontypeOfPurchaseCollectionNewPurchase.equals(transactiontype)) {
                        oldTransactiontypeOfPurchaseCollectionNewPurchase.getPurchaseCollection().remove(purchaseCollectionNewPurchase);
                        oldTransactiontypeOfPurchaseCollectionNewPurchase = em.merge(oldTransactiontypeOfPurchaseCollectionNewPurchase);
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
                Integer id = transactiontype.getId();
                if (findTransactiontype(id) == null) {
                    throw new NonexistentEntityException("The transactiontype with id " + id + " no longer exists.");
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
            Transactiontype transactiontype;
            try {
                transactiontype = em.getReference(Transactiontype.class, id);
                transactiontype.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactiontype with id " + id + " no longer exists.", enfe);
            }
            Collection<Purchase> purchaseCollection = transactiontype.getPurchaseCollection();
            for (Purchase purchaseCollectionPurchase : purchaseCollection) {
                purchaseCollectionPurchase.setTransactiontype(null);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
            }
            em.remove(transactiontype);
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

    public List<Transactiontype> findTransactiontypeEntities() {
        return findTransactiontypeEntities(true, -1, -1);
    }

    public List<Transactiontype> findTransactiontypeEntities(int maxResults, int firstResult) {
        return findTransactiontypeEntities(false, maxResults, firstResult);
    }

    private List<Transactiontype> findTransactiontypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transactiontype.class));
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

    public Transactiontype findTransactiontype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transactiontype.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactiontypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transactiontype> rt = cq.from(Transactiontype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
