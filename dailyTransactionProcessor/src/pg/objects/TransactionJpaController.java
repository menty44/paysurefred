/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pg.objects;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pg.objects.exceptions.NonexistentEntityException;

/**
 *
 * @author gachanja
 */
public class TransactionJpaController {

    public TransactionJpaController() {
        emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaction transaction) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Status status = transaction.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getId());
                transaction.setStatus(status);
            }
            Responsecode responsecode = transaction.getResponsecode();
            if (responsecode != null) {
                responsecode = em.getReference(responsecode.getClass(), responsecode.getId());
                transaction.setResponsecode(responsecode);
            }
            Merchant merchant = transaction.getMerchant();
            if (merchant != null) {
                merchant = em.getReference(merchant.getClass(), merchant.getId());
                transaction.setMerchant(merchant);
            }
            em.persist(transaction);
            if (status != null) {
                status.getTransactionCollection().add(transaction);
                status = em.merge(status);
            }
            if (responsecode != null) {
                responsecode.getTransactionCollection().add(transaction);
                responsecode = em.merge(responsecode);
            }
            if (merchant != null) {
                merchant.getTransactionCollection().add(transaction);
                merchant = em.merge(merchant);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaction transaction) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaction persistentTransaction = em.find(Transaction.class, transaction.getId());
            Status statusOld = persistentTransaction.getStatus();
            Status statusNew = transaction.getStatus();
            Responsecode responsecodeOld = persistentTransaction.getResponsecode();
            Responsecode responsecodeNew = transaction.getResponsecode();
            Merchant merchantOld = persistentTransaction.getMerchant();
            Merchant merchantNew = transaction.getMerchant();
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getId());
                transaction.setStatus(statusNew);
            }
            if (responsecodeNew != null) {
                responsecodeNew = em.getReference(responsecodeNew.getClass(), responsecodeNew.getId());
                transaction.setResponsecode(responsecodeNew);
            }
            if (merchantNew != null) {
                merchantNew = em.getReference(merchantNew.getClass(), merchantNew.getId());
                transaction.setMerchant(merchantNew);
            }
            transaction = em.merge(transaction);
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getTransactionCollection().remove(transaction);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getTransactionCollection().add(transaction);
                statusNew = em.merge(statusNew);
            }
            if (responsecodeOld != null && !responsecodeOld.equals(responsecodeNew)) {
                responsecodeOld.getTransactionCollection().remove(transaction);
                responsecodeOld = em.merge(responsecodeOld);
            }
            if (responsecodeNew != null && !responsecodeNew.equals(responsecodeOld)) {
                responsecodeNew.getTransactionCollection().add(transaction);
                responsecodeNew = em.merge(responsecodeNew);
            }
            if (merchantOld != null && !merchantOld.equals(merchantNew)) {
                merchantOld.getTransactionCollection().remove(transaction);
                merchantOld = em.merge(merchantOld);
            }
            if (merchantNew != null && !merchantNew.equals(merchantOld)) {
                merchantNew.getTransactionCollection().add(transaction);
                merchantNew = em.merge(merchantNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaction transaction;
            try {
                transaction = em.getReference(Transaction.class, id);
                transaction.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.", enfe);
            }
            Status status = transaction.getStatus();
            if (status != null) {
                status.getTransactionCollection().remove(transaction);
                status = em.merge(status);
            }
            Responsecode responsecode = transaction.getResponsecode();
            if (responsecode != null) {
                responsecode.getTransactionCollection().remove(transaction);
                responsecode = em.merge(responsecode);
            }
            Merchant merchant = transaction.getMerchant();
            if (merchant != null) {
                merchant.getTransactionCollection().remove(transaction);
                merchant = em.merge(merchant);
            }
            em.remove(transaction);
            em.getTransaction().commit();
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
