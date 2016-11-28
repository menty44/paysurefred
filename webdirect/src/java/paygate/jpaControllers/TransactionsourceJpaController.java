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
import paygate.objects.Purchase;
import paygate.objects.Transactionsource;

/**
 *
 * @author paysure
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
        if (transactionsource.getTransactionList() == null) {
            transactionsource.setTransactionList(new ArrayList<Transaction>());
        }
        if (transactionsource.getPurchaseList() == null) {
            transactionsource.setPurchaseList(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
            for (Transaction transactionListTransactionToAttach : transactionsource.getTransactionList()) {
                transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(), transactionListTransactionToAttach.getId());
                attachedTransactionList.add(transactionListTransactionToAttach);
            }
            transactionsource.setTransactionList(attachedTransactionList);
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : transactionsource.getPurchaseList()) {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getId());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            transactionsource.setPurchaseList(attachedPurchaseList);
            em.persist(transactionsource);
            for (Transaction transactionListTransaction : transactionsource.getTransactionList()) {
                Transactionsource oldTransactionsourceidOfTransactionListTransaction = transactionListTransaction.getTransactionsourceid();
                transactionListTransaction.setTransactionsourceid(transactionsource);
                transactionListTransaction = em.merge(transactionListTransaction);
                if (oldTransactionsourceidOfTransactionListTransaction != null) {
                    oldTransactionsourceidOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
                    oldTransactionsourceidOfTransactionListTransaction = em.merge(oldTransactionsourceidOfTransactionListTransaction);
                }
            }
            for (Purchase purchaseListPurchase : transactionsource.getPurchaseList()) {
                Transactionsource oldTransactionsourceidOfPurchaseListPurchase = purchaseListPurchase.getTransactionsourceid();
                purchaseListPurchase.setTransactionsourceid(transactionsource);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldTransactionsourceidOfPurchaseListPurchase != null) {
                    oldTransactionsourceidOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldTransactionsourceidOfPurchaseListPurchase = em.merge(oldTransactionsourceidOfPurchaseListPurchase);
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
            List<Transaction> transactionListOld = persistentTransactionsource.getTransactionList();
            List<Transaction> transactionListNew = transactionsource.getTransactionList();
            List<Purchase> purchaseListOld = persistentTransactionsource.getPurchaseList();
            List<Purchase> purchaseListNew = transactionsource.getPurchaseList();
            List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
            for (Transaction transactionListNewTransactionToAttach : transactionListNew) {
                transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(), transactionListNewTransactionToAttach.getId());
                attachedTransactionListNew.add(transactionListNewTransactionToAttach);
            }
            transactionListNew = attachedTransactionListNew;
            transactionsource.setTransactionList(transactionListNew);
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew) {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getId());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            transactionsource.setPurchaseList(purchaseListNew);
            transactionsource = em.merge(transactionsource);
            for (Transaction transactionListOldTransaction : transactionListOld) {
                if (!transactionListNew.contains(transactionListOldTransaction)) {
                    transactionListOldTransaction.setTransactionsourceid(null);
                    transactionListOldTransaction = em.merge(transactionListOldTransaction);
                }
            }
            for (Transaction transactionListNewTransaction : transactionListNew) {
                if (!transactionListOld.contains(transactionListNewTransaction)) {
                    Transactionsource oldTransactionsourceidOfTransactionListNewTransaction = transactionListNewTransaction.getTransactionsourceid();
                    transactionListNewTransaction.setTransactionsourceid(transactionsource);
                    transactionListNewTransaction = em.merge(transactionListNewTransaction);
                    if (oldTransactionsourceidOfTransactionListNewTransaction != null && !oldTransactionsourceidOfTransactionListNewTransaction.equals(transactionsource)) {
                        oldTransactionsourceidOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
                        oldTransactionsourceidOfTransactionListNewTransaction = em.merge(oldTransactionsourceidOfTransactionListNewTransaction);
                    }
                }
            }
            for (Purchase purchaseListOldPurchase : purchaseListOld) {
                if (!purchaseListNew.contains(purchaseListOldPurchase)) {
                    purchaseListOldPurchase.setTransactionsourceid(null);
                    purchaseListOldPurchase = em.merge(purchaseListOldPurchase);
                }
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew) {
                if (!purchaseListOld.contains(purchaseListNewPurchase)) {
                    Transactionsource oldTransactionsourceidOfPurchaseListNewPurchase = purchaseListNewPurchase.getTransactionsourceid();
                    purchaseListNewPurchase.setTransactionsourceid(transactionsource);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldTransactionsourceidOfPurchaseListNewPurchase != null && !oldTransactionsourceidOfPurchaseListNewPurchase.equals(transactionsource)) {
                        oldTransactionsourceidOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldTransactionsourceidOfPurchaseListNewPurchase = em.merge(oldTransactionsourceidOfPurchaseListNewPurchase);
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
            List<Transaction> transactionList = transactionsource.getTransactionList();
            for (Transaction transactionListTransaction : transactionList) {
                transactionListTransaction.setTransactionsourceid(null);
                transactionListTransaction = em.merge(transactionListTransaction);
            }
            List<Purchase> purchaseList = transactionsource.getPurchaseList();
            for (Purchase purchaseListPurchase : purchaseList) {
                purchaseListPurchase.setTransactionsourceid(null);
                purchaseListPurchase = em.merge(purchaseListPurchase);
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
