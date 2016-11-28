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
import java.util.ArrayList;
import java.util.Collection;
import pg.objects.exceptions.NonexistentEntityException;

/**
 *
 * @author gachanja
 */
public class ResponsecodeJpaController {

    public ResponsecodeJpaController() {
        emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Responsecode responsecode) {
        if (responsecode.getTransactionCollection() == null) {
            responsecode.setTransactionCollection(new ArrayList<Transaction>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : responsecode.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getId());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            responsecode.setTransactionCollection(attachedTransactionCollection);
            em.persist(responsecode);
            for (Transaction transactionCollectionTransaction : responsecode.getTransactionCollection()) {
                Responsecode oldResponsecodeOfTransactionCollectionTransaction = transactionCollectionTransaction.getResponsecode();
                transactionCollectionTransaction.setResponsecode(responsecode);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldResponsecodeOfTransactionCollectionTransaction != null) {
                    oldResponsecodeOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldResponsecodeOfTransactionCollectionTransaction = em.merge(oldResponsecodeOfTransactionCollectionTransaction);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Responsecode responsecode) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Responsecode persistentResponsecode = em.find(Responsecode.class, responsecode.getId());
            Collection<Transaction> transactionCollectionOld = persistentResponsecode.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = responsecode.getTransactionCollection();
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getId());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            responsecode.setTransactionCollection(transactionCollectionNew);
            responsecode = em.merge(responsecode);
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setResponsecode(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    Responsecode oldResponsecodeOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getResponsecode();
                    transactionCollectionNewTransaction.setResponsecode(responsecode);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldResponsecodeOfTransactionCollectionNewTransaction != null && !oldResponsecodeOfTransactionCollectionNewTransaction.equals(responsecode)) {
                        oldResponsecodeOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldResponsecodeOfTransactionCollectionNewTransaction = em.merge(oldResponsecodeOfTransactionCollectionNewTransaction);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = responsecode.getId();
                if (findResponsecode(id) == null) {
                    throw new NonexistentEntityException("The responsecode with id " + id + " no longer exists.");
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
            Responsecode responsecode;
            try {
                responsecode = em.getReference(Responsecode.class, id);
                responsecode.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsecode with id " + id + " no longer exists.", enfe);
            }
            Collection<Transaction> transactionCollection = responsecode.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setResponsecode(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            em.remove(responsecode);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Responsecode> findResponsecodeEntities() {
        return findResponsecodeEntities(true, -1, -1);
    }

    public List<Responsecode> findResponsecodeEntities(int maxResults, int firstResult) {
        return findResponsecodeEntities(false, maxResults, firstResult);
    }

    private List<Responsecode> findResponsecodeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Responsecode.class));
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

    public Responsecode findResponsecode(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Responsecode.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsecodeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Responsecode> rt = cq.from(Responsecode.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
