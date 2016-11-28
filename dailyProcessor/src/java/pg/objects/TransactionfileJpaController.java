/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.objects;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import pg.objects.exceptions.NonexistentEntityException;
import pg.objects.exceptions.RollbackFailureException;

/**
 *
 * @author Joseph
 */
public class TransactionfileJpaController implements Serializable {

    public TransactionfileJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public TransactionfileJpaController() {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transactionfile transactionfile) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(transactionfile);
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

    public void edit(Transactionfile transactionfile) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            transactionfile = em.merge(transactionfile);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transactionfile.getId();
                if (findTransactionfile(id) == null) {
                    throw new NonexistentEntityException("The transactionfile with id " + id + " no longer exists.");
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
            Transactionfile transactionfile;
            try {
                transactionfile = em.getReference(Transactionfile.class, id);
                transactionfile.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionfile with id " + id + " no longer exists.", enfe);
            }
            em.remove(transactionfile);
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

    public List<Transactionfile> findTransactionfileEntities() {
        return findTransactionfileEntities(true, -1, -1);
    }

    public List<Transactionfile> findTransactionfileEntities(int maxResults, int firstResult) {
        return findTransactionfileEntities(false, maxResults, firstResult);
    }

    private List<Transactionfile> findTransactionfileEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transactionfile.class));
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

    public Transactionfile findTransactionfile(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transactionfile.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionfileCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transactionfile> rt = cq.from(Transactionfile.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
