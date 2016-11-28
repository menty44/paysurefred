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
public class ResponsecodeJpaController implements Serializable {

    public ResponsecodeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Responsecode responsecode) throws RollbackFailureException, Exception {
        if (responsecode.getTransactionCollection() == null) {
            responsecode.setTransactionCollection(new ArrayList<Transaction>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : responsecode.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getId());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            responsecode.setTransactionCollection(attachedTransactionCollection);
            em.persist(responsecode);
            for (Transaction transactionCollectionTransaction : responsecode.getTransactionCollection()) {
                Responsecode oldResponsecodeidOfTransactionCollectionTransaction = transactionCollectionTransaction.getResponsecodeid();
                transactionCollectionTransaction.setResponsecodeid(responsecode);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldResponsecodeidOfTransactionCollectionTransaction != null) {
                    oldResponsecodeidOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldResponsecodeidOfTransactionCollectionTransaction = em.merge(oldResponsecodeidOfTransactionCollectionTransaction);
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

    public void edit(Responsecode responsecode) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
                    transactionCollectionOldTransaction.setResponsecodeid(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    Responsecode oldResponsecodeidOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getResponsecodeid();
                    transactionCollectionNewTransaction.setResponsecodeid(responsecode);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldResponsecodeidOfTransactionCollectionNewTransaction != null && !oldResponsecodeidOfTransactionCollectionNewTransaction.equals(responsecode)) {
                        oldResponsecodeidOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldResponsecodeidOfTransactionCollectionNewTransaction = em.merge(oldResponsecodeidOfTransactionCollectionNewTransaction);
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

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Responsecode responsecode;
            try {
                responsecode = em.getReference(Responsecode.class, id);
                responsecode.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsecode with id " + id + " no longer exists.", enfe);
            }
            Collection<Transaction> transactionCollection = responsecode.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setResponsecodeid(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            em.remove(responsecode);
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
