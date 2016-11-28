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
import pg.objects.exceptions.IllegalOrphanException;
import pg.objects.exceptions.NonexistentEntityException;
import pg.objects.exceptions.RollbackFailureException;

/**
 *
 * @author Joseph
 */
public class BatchheaderJpaController implements Serializable {

    public BatchheaderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Batchheader batchheader) throws RollbackFailureException, Exception {
        if (batchheader.getBatchitemCollection() == null) {
            batchheader.setBatchitemCollection(new ArrayList<Batchitem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Batchitem> attachedBatchitemCollection = new ArrayList<Batchitem>();
            for (Batchitem batchitemCollectionBatchitemToAttach : batchheader.getBatchitemCollection()) {
                batchitemCollectionBatchitemToAttach = em.getReference(batchitemCollectionBatchitemToAttach.getClass(), batchitemCollectionBatchitemToAttach.getId());
                attachedBatchitemCollection.add(batchitemCollectionBatchitemToAttach);
            }
            batchheader.setBatchitemCollection(attachedBatchitemCollection);
            em.persist(batchheader);
            for (Batchitem batchitemCollectionBatchitem : batchheader.getBatchitemCollection()) {
                Batchheader oldBatchheaderIdOfBatchitemCollectionBatchitem = batchitemCollectionBatchitem.getBatchheaderId();
                batchitemCollectionBatchitem.setBatchheaderId(batchheader);
                batchitemCollectionBatchitem = em.merge(batchitemCollectionBatchitem);
                if (oldBatchheaderIdOfBatchitemCollectionBatchitem != null) {
                    oldBatchheaderIdOfBatchitemCollectionBatchitem.getBatchitemCollection().remove(batchitemCollectionBatchitem);
                    oldBatchheaderIdOfBatchitemCollectionBatchitem = em.merge(oldBatchheaderIdOfBatchitemCollectionBatchitem);
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

    public void edit(Batchheader batchheader) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Batchheader persistentBatchheader = em.find(Batchheader.class, batchheader.getId());
            Collection<Batchitem> batchitemCollectionOld = persistentBatchheader.getBatchitemCollection();
            Collection<Batchitem> batchitemCollectionNew = batchheader.getBatchitemCollection();
            List<String> illegalOrphanMessages = null;
            for (Batchitem batchitemCollectionOldBatchitem : batchitemCollectionOld) {
                if (!batchitemCollectionNew.contains(batchitemCollectionOldBatchitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Batchitem " + batchitemCollectionOldBatchitem + " since its batchheaderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Batchitem> attachedBatchitemCollectionNew = new ArrayList<Batchitem>();
            for (Batchitem batchitemCollectionNewBatchitemToAttach : batchitemCollectionNew) {
                batchitemCollectionNewBatchitemToAttach = em.getReference(batchitemCollectionNewBatchitemToAttach.getClass(), batchitemCollectionNewBatchitemToAttach.getId());
                attachedBatchitemCollectionNew.add(batchitemCollectionNewBatchitemToAttach);
            }
            batchitemCollectionNew = attachedBatchitemCollectionNew;
            batchheader.setBatchitemCollection(batchitemCollectionNew);
            batchheader = em.merge(batchheader);
            for (Batchitem batchitemCollectionNewBatchitem : batchitemCollectionNew) {
                if (!batchitemCollectionOld.contains(batchitemCollectionNewBatchitem)) {
                    Batchheader oldBatchheaderIdOfBatchitemCollectionNewBatchitem = batchitemCollectionNewBatchitem.getBatchheaderId();
                    batchitemCollectionNewBatchitem.setBatchheaderId(batchheader);
                    batchitemCollectionNewBatchitem = em.merge(batchitemCollectionNewBatchitem);
                    if (oldBatchheaderIdOfBatchitemCollectionNewBatchitem != null && !oldBatchheaderIdOfBatchitemCollectionNewBatchitem.equals(batchheader)) {
                        oldBatchheaderIdOfBatchitemCollectionNewBatchitem.getBatchitemCollection().remove(batchitemCollectionNewBatchitem);
                        oldBatchheaderIdOfBatchitemCollectionNewBatchitem = em.merge(oldBatchheaderIdOfBatchitemCollectionNewBatchitem);
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
                Integer id = batchheader.getId();
                if (findBatchheader(id) == null) {
                    throw new NonexistentEntityException("The batchheader with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Batchheader batchheader;
            try {
                batchheader = em.getReference(Batchheader.class, id);
                batchheader.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The batchheader with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Batchitem> batchitemCollectionOrphanCheck = batchheader.getBatchitemCollection();
            for (Batchitem batchitemCollectionOrphanCheckBatchitem : batchitemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Batchheader (" + batchheader + ") cannot be destroyed since the Batchitem " + batchitemCollectionOrphanCheckBatchitem + " in its batchitemCollection field has a non-nullable batchheaderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(batchheader);
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

    public List<Batchheader> findBatchheaderEntities() {
        return findBatchheaderEntities(true, -1, -1);
    }

    public List<Batchheader> findBatchheaderEntities(int maxResults, int firstResult) {
        return findBatchheaderEntities(false, maxResults, firstResult);
    }

    private List<Batchheader> findBatchheaderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Batchheader.class));
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

    public Batchheader findBatchheader(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Batchheader.class, id);
        } finally {
            em.close();
        }
    }

    public int getBatchheaderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Batchheader> rt = cq.from(Batchheader.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
