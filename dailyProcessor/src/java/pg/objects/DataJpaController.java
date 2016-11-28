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
public class DataJpaController implements Serializable {

    public DataJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Data data) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Purchase purchaseid = data.getPurchaseid();
            if (purchaseid != null) {
                purchaseid = em.getReference(purchaseid.getClass(), purchaseid.getId());
                data.setPurchaseid(purchaseid);
            }
            em.persist(data);
            if (purchaseid != null) {
                purchaseid.getDataCollection().add(data);
                purchaseid = em.merge(purchaseid);
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

    public void edit(Data data) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Data persistentData = em.find(Data.class, data.getId());
            Purchase purchaseidOld = persistentData.getPurchaseid();
            Purchase purchaseidNew = data.getPurchaseid();
            if (purchaseidNew != null) {
                purchaseidNew = em.getReference(purchaseidNew.getClass(), purchaseidNew.getId());
                data.setPurchaseid(purchaseidNew);
            }
            data = em.merge(data);
            if (purchaseidOld != null && !purchaseidOld.equals(purchaseidNew)) {
                purchaseidOld.getDataCollection().remove(data);
                purchaseidOld = em.merge(purchaseidOld);
            }
            if (purchaseidNew != null && !purchaseidNew.equals(purchaseidOld)) {
                purchaseidNew.getDataCollection().add(data);
                purchaseidNew = em.merge(purchaseidNew);
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
                Integer id = data.getId();
                if (findData(id) == null) {
                    throw new NonexistentEntityException("The data with id " + id + " no longer exists.");
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
            Data data;
            try {
                data = em.getReference(Data.class, id);
                data.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The data with id " + id + " no longer exists.", enfe);
            }
            Purchase purchaseid = data.getPurchaseid();
            if (purchaseid != null) {
                purchaseid.getDataCollection().remove(data);
                purchaseid = em.merge(purchaseid);
            }
            em.remove(data);
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

    public List<Data> findDataEntities() {
        return findDataEntities(true, -1, -1);
    }

    public List<Data> findDataEntities(int maxResults, int firstResult) {
        return findDataEntities(false, maxResults, firstResult);
    }

    private List<Data> findDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Data.class));
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

    public Data findData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Data.class, id);
        } finally {
            em.close();
        }
    }

    public int getDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Data> rt = cq.from(Data.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
