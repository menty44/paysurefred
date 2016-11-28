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
public class TransactionsourceJpaController implements Serializable {

    public TransactionsourceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transactionsource transactionsource) throws RollbackFailureException, Exception {
        if (transactionsource.getTransactionCollection() == null) {
            transactionsource.setTransactionCollection(new ArrayList<Transaction>());
        }
        if (transactionsource.getPurchaseCollection() == null) {
            transactionsource.setPurchaseCollection(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : transactionsource.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getId());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            transactionsource.setTransactionCollection(attachedTransactionCollection);
            Collection<Purchase> attachedPurchaseCollection = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionPurchaseToAttach : transactionsource.getPurchaseCollection()) {
                purchaseCollectionPurchaseToAttach = em.getReference(purchaseCollectionPurchaseToAttach.getClass(), purchaseCollectionPurchaseToAttach.getId());
                attachedPurchaseCollection.add(purchaseCollectionPurchaseToAttach);
            }
            transactionsource.setPurchaseCollection(attachedPurchaseCollection);
            em.persist(transactionsource);
            for (Transaction transactionCollectionTransaction : transactionsource.getTransactionCollection()) {
                Transactionsource oldTransactionsourceidOfTransactionCollectionTransaction = transactionCollectionTransaction.getTransactionsourceid();
                transactionCollectionTransaction.setTransactionsourceid(transactionsource);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldTransactionsourceidOfTransactionCollectionTransaction != null) {
                    oldTransactionsourceidOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldTransactionsourceidOfTransactionCollectionTransaction = em.merge(oldTransactionsourceidOfTransactionCollectionTransaction);
                }
            }
            for (Purchase purchaseCollectionPurchase : transactionsource.getPurchaseCollection()) {
                Transactionsource oldTransactionsourceidOfPurchaseCollectionPurchase = purchaseCollectionPurchase.getTransactionsourceid();
                purchaseCollectionPurchase.setTransactionsourceid(transactionsource);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
                if (oldTransactionsourceidOfPurchaseCollectionPurchase != null) {
                    oldTransactionsourceidOfPurchaseCollectionPurchase.getPurchaseCollection().remove(purchaseCollectionPurchase);
                    oldTransactionsourceidOfPurchaseCollectionPurchase = em.merge(oldTransactionsourceidOfPurchaseCollectionPurchase);
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

    public void edit(Transactionsource transactionsource) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transactionsource persistentTransactionsource = em.find(Transactionsource.class, transactionsource.getId());
            Collection<Transaction> transactionCollectionOld = persistentTransactionsource.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = transactionsource.getTransactionCollection();
            Collection<Purchase> purchaseCollectionOld = persistentTransactionsource.getPurchaseCollection();
            Collection<Purchase> purchaseCollectionNew = transactionsource.getPurchaseCollection();
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getId());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            transactionsource.setTransactionCollection(transactionCollectionNew);
            Collection<Purchase> attachedPurchaseCollectionNew = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionNewPurchaseToAttach : purchaseCollectionNew) {
                purchaseCollectionNewPurchaseToAttach = em.getReference(purchaseCollectionNewPurchaseToAttach.getClass(), purchaseCollectionNewPurchaseToAttach.getId());
                attachedPurchaseCollectionNew.add(purchaseCollectionNewPurchaseToAttach);
            }
            purchaseCollectionNew = attachedPurchaseCollectionNew;
            transactionsource.setPurchaseCollection(purchaseCollectionNew);
            transactionsource = em.merge(transactionsource);
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setTransactionsourceid(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    Transactionsource oldTransactionsourceidOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getTransactionsourceid();
                    transactionCollectionNewTransaction.setTransactionsourceid(transactionsource);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldTransactionsourceidOfTransactionCollectionNewTransaction != null && !oldTransactionsourceidOfTransactionCollectionNewTransaction.equals(transactionsource)) {
                        oldTransactionsourceidOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldTransactionsourceidOfTransactionCollectionNewTransaction = em.merge(oldTransactionsourceidOfTransactionCollectionNewTransaction);
                    }
                }
            }
            for (Purchase purchaseCollectionOldPurchase : purchaseCollectionOld) {
                if (!purchaseCollectionNew.contains(purchaseCollectionOldPurchase)) {
                    purchaseCollectionOldPurchase.setTransactionsourceid(null);
                    purchaseCollectionOldPurchase = em.merge(purchaseCollectionOldPurchase);
                }
            }
            for (Purchase purchaseCollectionNewPurchase : purchaseCollectionNew) {
                if (!purchaseCollectionOld.contains(purchaseCollectionNewPurchase)) {
                    Transactionsource oldTransactionsourceidOfPurchaseCollectionNewPurchase = purchaseCollectionNewPurchase.getTransactionsourceid();
                    purchaseCollectionNewPurchase.setTransactionsourceid(transactionsource);
                    purchaseCollectionNewPurchase = em.merge(purchaseCollectionNewPurchase);
                    if (oldTransactionsourceidOfPurchaseCollectionNewPurchase != null && !oldTransactionsourceidOfPurchaseCollectionNewPurchase.equals(transactionsource)) {
                        oldTransactionsourceidOfPurchaseCollectionNewPurchase.getPurchaseCollection().remove(purchaseCollectionNewPurchase);
                        oldTransactionsourceidOfPurchaseCollectionNewPurchase = em.merge(oldTransactionsourceidOfPurchaseCollectionNewPurchase);
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
                Integer id = transactionsource.getId();
                if (findTransactionsource(id) == null) {
                    throw new NonexistentEntityException("The transactionsource with id " + id + " no longer exists.");
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
            Transactionsource transactionsource;
            try {
                transactionsource = em.getReference(Transactionsource.class, id);
                transactionsource.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionsource with id " + id + " no longer exists.", enfe);
            }
            Collection<Transaction> transactionCollection = transactionsource.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setTransactionsourceid(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            Collection<Purchase> purchaseCollection = transactionsource.getPurchaseCollection();
            for (Purchase purchaseCollectionPurchase : purchaseCollection) {
                purchaseCollectionPurchase.setTransactionsourceid(null);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
            }
            em.remove(transactionsource);
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

    public List<Transactionsource> findTransactionsourceEntities() {
        return findTransactionsourceEntities(true, -1, -1);
    }

    public List<Transactionsource> findTransactionsourceEntities(int maxResults, int firstResult) {
        return findTransactionsourceEntities(false, maxResults, firstResult);
    }

    private List<Transactionsource> findTransactionsourceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transactionsource.class));
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

    public Transactionsource findTransactionsource(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transactionsource.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionsourceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transactionsource> rt = cq.from(Transactionsource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
