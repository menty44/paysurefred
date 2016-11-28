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
public class UsergroupJpaController implements Serializable {

    public UsergroupJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usergroup usergroup) throws RollbackFailureException, Exception {
        if (usergroup.getSiteuserCollection() == null) {
            usergroup.setSiteuserCollection(new ArrayList<Siteuser>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Siteuser> attachedSiteuserCollection = new ArrayList<Siteuser>();
            for (Siteuser siteuserCollectionSiteuserToAttach : usergroup.getSiteuserCollection()) {
                siteuserCollectionSiteuserToAttach = em.getReference(siteuserCollectionSiteuserToAttach.getClass(), siteuserCollectionSiteuserToAttach.getId());
                attachedSiteuserCollection.add(siteuserCollectionSiteuserToAttach);
            }
            usergroup.setSiteuserCollection(attachedSiteuserCollection);
            em.persist(usergroup);
            for (Siteuser siteuserCollectionSiteuser : usergroup.getSiteuserCollection()) {
                Usergroup oldUsergroupidOfSiteuserCollectionSiteuser = siteuserCollectionSiteuser.getUsergroupid();
                siteuserCollectionSiteuser.setUsergroupid(usergroup);
                siteuserCollectionSiteuser = em.merge(siteuserCollectionSiteuser);
                if (oldUsergroupidOfSiteuserCollectionSiteuser != null) {
                    oldUsergroupidOfSiteuserCollectionSiteuser.getSiteuserCollection().remove(siteuserCollectionSiteuser);
                    oldUsergroupidOfSiteuserCollectionSiteuser = em.merge(oldUsergroupidOfSiteuserCollectionSiteuser);
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

    public void edit(Usergroup usergroup) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usergroup persistentUsergroup = em.find(Usergroup.class, usergroup.getId());
            Collection<Siteuser> siteuserCollectionOld = persistentUsergroup.getSiteuserCollection();
            Collection<Siteuser> siteuserCollectionNew = usergroup.getSiteuserCollection();
            Collection<Siteuser> attachedSiteuserCollectionNew = new ArrayList<Siteuser>();
            for (Siteuser siteuserCollectionNewSiteuserToAttach : siteuserCollectionNew) {
                siteuserCollectionNewSiteuserToAttach = em.getReference(siteuserCollectionNewSiteuserToAttach.getClass(), siteuserCollectionNewSiteuserToAttach.getId());
                attachedSiteuserCollectionNew.add(siteuserCollectionNewSiteuserToAttach);
            }
            siteuserCollectionNew = attachedSiteuserCollectionNew;
            usergroup.setSiteuserCollection(siteuserCollectionNew);
            usergroup = em.merge(usergroup);
            for (Siteuser siteuserCollectionOldSiteuser : siteuserCollectionOld) {
                if (!siteuserCollectionNew.contains(siteuserCollectionOldSiteuser)) {
                    siteuserCollectionOldSiteuser.setUsergroupid(null);
                    siteuserCollectionOldSiteuser = em.merge(siteuserCollectionOldSiteuser);
                }
            }
            for (Siteuser siteuserCollectionNewSiteuser : siteuserCollectionNew) {
                if (!siteuserCollectionOld.contains(siteuserCollectionNewSiteuser)) {
                    Usergroup oldUsergroupidOfSiteuserCollectionNewSiteuser = siteuserCollectionNewSiteuser.getUsergroupid();
                    siteuserCollectionNewSiteuser.setUsergroupid(usergroup);
                    siteuserCollectionNewSiteuser = em.merge(siteuserCollectionNewSiteuser);
                    if (oldUsergroupidOfSiteuserCollectionNewSiteuser != null && !oldUsergroupidOfSiteuserCollectionNewSiteuser.equals(usergroup)) {
                        oldUsergroupidOfSiteuserCollectionNewSiteuser.getSiteuserCollection().remove(siteuserCollectionNewSiteuser);
                        oldUsergroupidOfSiteuserCollectionNewSiteuser = em.merge(oldUsergroupidOfSiteuserCollectionNewSiteuser);
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
                Integer id = usergroup.getId();
                if (findUsergroup(id) == null) {
                    throw new NonexistentEntityException("The usergroup with id " + id + " no longer exists.");
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
            Usergroup usergroup;
            try {
                usergroup = em.getReference(Usergroup.class, id);
                usergroup.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usergroup with id " + id + " no longer exists.", enfe);
            }
            Collection<Siteuser> siteuserCollection = usergroup.getSiteuserCollection();
            for (Siteuser siteuserCollectionSiteuser : siteuserCollection) {
                siteuserCollectionSiteuser.setUsergroupid(null);
                siteuserCollectionSiteuser = em.merge(siteuserCollectionSiteuser);
            }
            em.remove(usergroup);
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

    public List<Usergroup> findUsergroupEntities() {
        return findUsergroupEntities(true, -1, -1);
    }

    public List<Usergroup> findUsergroupEntities(int maxResults, int firstResult) {
        return findUsergroupEntities(false, maxResults, firstResult);
    }

    private List<Usergroup> findUsergroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usergroup.class));
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

    public Usergroup findUsergroup(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usergroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsergroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usergroup> rt = cq.from(Usergroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
