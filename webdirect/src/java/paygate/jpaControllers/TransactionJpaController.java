/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.jpaControllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.*;

/**
 *
 * @author paysure
 */
public class TransactionJpaController implements Serializable {

    public TransactionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaction transaction) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transactionsource transactionsourceid = transaction.getTransactionsourceid();
            if (transactionsourceid != null) {
                transactionsourceid = em.getReference(transactionsourceid.getClass(), transactionsourceid.getId());
                transaction.setTransactionsourceid(transactionsourceid);
            }
            Status statusid = transaction.getStatusid();
            if (statusid != null) {
                statusid = em.getReference(statusid.getClass(), statusid.getId());
                transaction.setStatusid(statusid);
            }
            Responsecode responsecodeid = transaction.getResponsecodeid();
            if (responsecodeid != null) {
                responsecodeid = em.getReference(responsecodeid.getClass(), responsecodeid.getId());
                transaction.setResponsecodeid(responsecodeid);
            }
            Merchant merchantid = transaction.getMerchantid();
            if (merchantid != null) {
                merchantid = em.getReference(merchantid.getClass(), merchantid.getId());
                transaction.setMerchantid(merchantid);
            }
            em.persist(transaction);
            if (transactionsourceid != null) {
                transactionsourceid.getTransactionList().add(transaction);
                transactionsourceid = em.merge(transactionsourceid);
            }
            if (statusid != null) {
                statusid.getTransactionList().add(transaction);
                statusid = em.merge(statusid);
            }
            if (responsecodeid != null) {
                responsecodeid.getTransactionList().add(transaction);
                responsecodeid = em.merge(responsecodeid);
            }
            if (merchantid != null) {
                merchantid.getTransactionList().add(transaction);
                merchantid = em.merge(merchantid);
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

    public void edit(Transaction transaction) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction persistentTransaction = em.find(Transaction.class, transaction.getId());
            Transactionsource transactionsourceidOld = persistentTransaction.getTransactionsourceid();
            Transactionsource transactionsourceidNew = transaction.getTransactionsourceid();
            Status statusidOld = persistentTransaction.getStatusid();
            Status statusidNew = transaction.getStatusid();
            Responsecode responsecodeidOld = persistentTransaction.getResponsecodeid();
            Responsecode responsecodeidNew = transaction.getResponsecodeid();
            Merchant merchantidOld = persistentTransaction.getMerchantid();
            Merchant merchantidNew = transaction.getMerchantid();
            if (transactionsourceidNew != null) {
                transactionsourceidNew = em.getReference(transactionsourceidNew.getClass(), transactionsourceidNew.getId());
                transaction.setTransactionsourceid(transactionsourceidNew);
            }
            if (statusidNew != null) {
                statusidNew = em.getReference(statusidNew.getClass(), statusidNew.getId());
                transaction.setStatusid(statusidNew);
            }
            if (responsecodeidNew != null) {
                responsecodeidNew = em.getReference(responsecodeidNew.getClass(), responsecodeidNew.getId());
                transaction.setResponsecodeid(responsecodeidNew);
            }
            if (merchantidNew != null) {
                merchantidNew = em.getReference(merchantidNew.getClass(), merchantidNew.getId());
                transaction.setMerchantid(merchantidNew);
            }
            transaction = em.merge(transaction);
            if (transactionsourceidOld != null && !transactionsourceidOld.equals(transactionsourceidNew)) {
                transactionsourceidOld.getTransactionList().remove(transaction);
                transactionsourceidOld = em.merge(transactionsourceidOld);
            }
            if (transactionsourceidNew != null && !transactionsourceidNew.equals(transactionsourceidOld)) {
                transactionsourceidNew.getTransactionList().add(transaction);
                transactionsourceidNew = em.merge(transactionsourceidNew);
            }
            if (statusidOld != null && !statusidOld.equals(statusidNew)) {
                statusidOld.getTransactionList().remove(transaction);
                statusidOld = em.merge(statusidOld);
            }
            if (statusidNew != null && !statusidNew.equals(statusidOld)) {
                statusidNew.getTransactionList().add(transaction);
                statusidNew = em.merge(statusidNew);
            }
            if (responsecodeidOld != null && !responsecodeidOld.equals(responsecodeidNew)) {
                responsecodeidOld.getTransactionList().remove(transaction);
                responsecodeidOld = em.merge(responsecodeidOld);
            }
            if (responsecodeidNew != null && !responsecodeidNew.equals(responsecodeidOld)) {
                responsecodeidNew.getTransactionList().add(transaction);
                responsecodeidNew = em.merge(responsecodeidNew);
            }
            if (merchantidOld != null && !merchantidOld.equals(merchantidNew)) {
                merchantidOld.getTransactionList().remove(transaction);
                merchantidOld = em.merge(merchantidOld);
            }
            if (merchantidNew != null && !merchantidNew.equals(merchantidOld)) {
                merchantidNew.getTransactionList().add(transaction);
                merchantidNew = em.merge(merchantidNew);
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
                Integer id = transaction.getId();
                if (findTransaction(id) == null) {
                    throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.");
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
            Transaction transaction;
            try {
                transaction = em.getReference(Transaction.class, id);
                transaction.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.", enfe);
            }
            Transactionsource transactionsourceid = transaction.getTransactionsourceid();
            if (transactionsourceid != null) {
                transactionsourceid.getTransactionList().remove(transaction);
                transactionsourceid = em.merge(transactionsourceid);
            }
            Status statusid = transaction.getStatusid();
            if (statusid != null) {
                statusid.getTransactionList().remove(transaction);
                statusid = em.merge(statusid);
            }
            Responsecode responsecodeid = transaction.getResponsecodeid();
            if (responsecodeid != null) {
                responsecodeid.getTransactionList().remove(transaction);
                responsecodeid = em.merge(responsecodeid);
            }
            Merchant merchantid = transaction.getMerchantid();
            if (merchantid != null) {
                merchantid.getTransactionList().remove(transaction);
                merchantid = em.merge(merchantid);
            }
            em.remove(transaction);
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

    public List<Transaction> findTransactionEntities() {
        return findTransactionEntities(true, -1, -1);
    }

    public List<Transaction> findTransactionEntities(int maxResults, int firstResult) {
        return findTransactionEntities(false, maxResults, firstResult);
    }

    private List<Transaction> findTransactionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaction.class));
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

    public Transaction findTransaction(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaction.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaction> rt = cq.from(Transaction.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
