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
import paygate.objects.Batchheader;
import paygate.objects.Batchitem;

/**
 *
 * @author paysure
 */
public class BatchitemJpaController implements Serializable {

    public BatchitemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Batchitem batchitem) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Batchheader batchheaderId = batchitem.getBatchheaderId();
            if (batchheaderId != null) {
                batchheaderId = em.getReference(batchheaderId.getClass(), batchheaderId.getId());
                batchitem.setBatchheaderId(batchheaderId);
            }
            em.persist(batchitem);
            if (batchheaderId != null) {
                batchheaderId.getBatchitemList().add(batchitem);
                batchheaderId = em.merge(batchheaderId);
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

    public void edit(Batchitem batchitem) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Batchitem persistentBatchitem = em.find(Batchitem.class, batchitem.getId());
            Batchheader batchheaderIdOld = persistentBatchitem.getBatchheaderId();
            Batchheader batchheaderIdNew = batchitem.getBatchheaderId();
            if (batchheaderIdNew != null) {
                batchheaderIdNew = em.getReference(batchheaderIdNew.getClass(), batchheaderIdNew.getId());
                batchitem.setBatchheaderId(batchheaderIdNew);
            }
            batchitem = em.merge(batchitem);
            if (batchheaderIdOld != null && !batchheaderIdOld.equals(batchheaderIdNew)) {
                batchheaderIdOld.getBatchitemList().remove(batchitem);
                batchheaderIdOld = em.merge(batchheaderIdOld);
            }
            if (batchheaderIdNew != null && !batchheaderIdNew.equals(batchheaderIdOld)) {
                batchheaderIdNew.getBatchitemList().add(batchitem);
                batchheaderIdNew = em.merge(batchheaderIdNew);
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
                Integer id = batchitem.getId();
                if (findBatchitem(id) == null) {
                    throw new NonexistentEntityException("The batchitem with id " + id + " no longer exists.");
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
            Batchitem batchitem;
            try {
                batchitem = em.getReference(Batchitem.class, id);
                batchitem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The batchitem with id " + id + " no longer exists.", enfe);
            }
            Batchheader batchheaderId = batchitem.getBatchheaderId();
            if (batchheaderId != null) {
                batchheaderId.getBatchitemList().remove(batchitem);
                batchheaderId = em.merge(batchheaderId);
            }
            em.remove(batchitem);
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

    public List<Batchitem> findBatchitemEntities() {
        return findBatchitemEntities(true, -1, -1);
    }

    public List<Batchitem> findBatchitemEntities(int maxResults, int firstResult) {
        return findBatchitemEntities(false, maxResults, firstResult);
    }

    private List<Batchitem> findBatchitemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Batchitem.class));
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

    public Batchitem findBatchitem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Batchitem.class, id);
        } finally {
            em.close();
        }
    }

    public int getBatchitemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Batchitem> rt = cq.from(Batchitem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
