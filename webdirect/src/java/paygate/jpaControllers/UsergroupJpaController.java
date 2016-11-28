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
import paygate.objects.Siteuser;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Usergroup;

/**
 *
 * @author paysure
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
        if (usergroup.getSiteuserList() == null) {
            usergroup.setSiteuserList(new ArrayList<Siteuser>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Siteuser> attachedSiteuserList = new ArrayList<Siteuser>();
            for (Siteuser siteuserListSiteuserToAttach : usergroup.getSiteuserList()) {
                siteuserListSiteuserToAttach = em.getReference(siteuserListSiteuserToAttach.getClass(), siteuserListSiteuserToAttach.getId());
                attachedSiteuserList.add(siteuserListSiteuserToAttach);
            }
            usergroup.setSiteuserList(attachedSiteuserList);
            em.persist(usergroup);
            for (Siteuser siteuserListSiteuser : usergroup.getSiteuserList()) {
                Usergroup oldUsergroupidOfSiteuserListSiteuser = siteuserListSiteuser.getUsergroupid();
                siteuserListSiteuser.setUsergroupid(usergroup);
                siteuserListSiteuser = em.merge(siteuserListSiteuser);
                if (oldUsergroupidOfSiteuserListSiteuser != null) {
                    oldUsergroupidOfSiteuserListSiteuser.getSiteuserList().remove(siteuserListSiteuser);
                    oldUsergroupidOfSiteuserListSiteuser = em.merge(oldUsergroupidOfSiteuserListSiteuser);
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
            List<Siteuser> siteuserListOld = persistentUsergroup.getSiteuserList();
            List<Siteuser> siteuserListNew = usergroup.getSiteuserList();
            List<Siteuser> attachedSiteuserListNew = new ArrayList<Siteuser>();
            for (Siteuser siteuserListNewSiteuserToAttach : siteuserListNew) {
                siteuserListNewSiteuserToAttach = em.getReference(siteuserListNewSiteuserToAttach.getClass(), siteuserListNewSiteuserToAttach.getId());
                attachedSiteuserListNew.add(siteuserListNewSiteuserToAttach);
            }
            siteuserListNew = attachedSiteuserListNew;
            usergroup.setSiteuserList(siteuserListNew);
            usergroup = em.merge(usergroup);
            for (Siteuser siteuserListOldSiteuser : siteuserListOld) {
                if (!siteuserListNew.contains(siteuserListOldSiteuser)) {
                    siteuserListOldSiteuser.setUsergroupid(null);
                    siteuserListOldSiteuser = em.merge(siteuserListOldSiteuser);
                }
            }
            for (Siteuser siteuserListNewSiteuser : siteuserListNew) {
                if (!siteuserListOld.contains(siteuserListNewSiteuser)) {
                    Usergroup oldUsergroupidOfSiteuserListNewSiteuser = siteuserListNewSiteuser.getUsergroupid();
                    siteuserListNewSiteuser.setUsergroupid(usergroup);
                    siteuserListNewSiteuser = em.merge(siteuserListNewSiteuser);
                    if (oldUsergroupidOfSiteuserListNewSiteuser != null && !oldUsergroupidOfSiteuserListNewSiteuser.equals(usergroup)) {
                        oldUsergroupidOfSiteuserListNewSiteuser.getSiteuserList().remove(siteuserListNewSiteuser);
                        oldUsergroupidOfSiteuserListNewSiteuser = em.merge(oldUsergroupidOfSiteuserListNewSiteuser);
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
            List<Siteuser> siteuserList = usergroup.getSiteuserList();
            for (Siteuser siteuserListSiteuser : siteuserList) {
                siteuserListSiteuser.setUsergroupid(null);
                siteuserListSiteuser = em.merge(siteuserListSiteuser);
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
