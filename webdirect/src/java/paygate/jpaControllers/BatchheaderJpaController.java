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
import paygate.objects.Batchitem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.IllegalOrphanException;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Batchheader;

/**
 *
 * @author paysure
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
        if (batchheader.getBatchitemList() == null) {
            batchheader.setBatchitemList(new ArrayList<Batchitem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Batchitem> attachedBatchitemList = new ArrayList<Batchitem>();
            for (Batchitem batchitemListBatchitemToAttach : batchheader.getBatchitemList()) {
                batchitemListBatchitemToAttach = em.getReference(batchitemListBatchitemToAttach.getClass(), batchitemListBatchitemToAttach.getId());
                attachedBatchitemList.add(batchitemListBatchitemToAttach);
            }
            batchheader.setBatchitemList(attachedBatchitemList);
            em.persist(batchheader);
            for (Batchitem batchitemListBatchitem : batchheader.getBatchitemList()) {
                Batchheader oldBatchheaderIdOfBatchitemListBatchitem = batchitemListBatchitem.getBatchheaderId();
                batchitemListBatchitem.setBatchheaderId(batchheader);
                batchitemListBatchitem = em.merge(batchitemListBatchitem);
                if (oldBatchheaderIdOfBatchitemListBatchitem != null) {
                    oldBatchheaderIdOfBatchitemListBatchitem.getBatchitemList().remove(batchitemListBatchitem);
                    oldBatchheaderIdOfBatchitemListBatchitem = em.merge(oldBatchheaderIdOfBatchitemListBatchitem);
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
            List<Batchitem> batchitemListOld = persistentBatchheader.getBatchitemList();
            List<Batchitem> batchitemListNew = batchheader.getBatchitemList();
            List<String> illegalOrphanMessages = null;
            for (Batchitem batchitemListOldBatchitem : batchitemListOld) {
                if (!batchitemListNew.contains(batchitemListOldBatchitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Batchitem " + batchitemListOldBatchitem + " since its batchheaderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Batchitem> attachedBatchitemListNew = new ArrayList<Batchitem>();
            for (Batchitem batchitemListNewBatchitemToAttach : batchitemListNew) {
                batchitemListNewBatchitemToAttach = em.getReference(batchitemListNewBatchitemToAttach.getClass(), batchitemListNewBatchitemToAttach.getId());
                attachedBatchitemListNew.add(batchitemListNewBatchitemToAttach);
            }
            batchitemListNew = attachedBatchitemListNew;
            batchheader.setBatchitemList(batchitemListNew);
            batchheader = em.merge(batchheader);
            for (Batchitem batchitemListNewBatchitem : batchitemListNew) {
                if (!batchitemListOld.contains(batchitemListNewBatchitem)) {
                    Batchheader oldBatchheaderIdOfBatchitemListNewBatchitem = batchitemListNewBatchitem.getBatchheaderId();
                    batchitemListNewBatchitem.setBatchheaderId(batchheader);
                    batchitemListNewBatchitem = em.merge(batchitemListNewBatchitem);
                    if (oldBatchheaderIdOfBatchitemListNewBatchitem != null && !oldBatchheaderIdOfBatchitemListNewBatchitem.equals(batchheader)) {
                        oldBatchheaderIdOfBatchitemListNewBatchitem.getBatchitemList().remove(batchitemListNewBatchitem);
                        oldBatchheaderIdOfBatchitemListNewBatchitem = em.merge(oldBatchheaderIdOfBatchitemListNewBatchitem);
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
            List<Batchitem> batchitemListOrphanCheck = batchheader.getBatchitemList();
            for (Batchitem batchitemListOrphanCheckBatchitem : batchitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Batchheader (" + batchheader + ") cannot be destroyed since the Batchitem " + batchitemListOrphanCheckBatchitem + " in its batchitemList field has a non-nullable batchheaderId field.");
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
