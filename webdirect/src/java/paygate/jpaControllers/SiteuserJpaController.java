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
import paygate.objects.Usergroup;
import paygate.objects.Merchant;
import paygate.objects.Siteuser;

/**
 *
 * @author paysure
 */
public class SiteuserJpaController implements Serializable {

    public SiteuserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Siteuser siteuser) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usergroup usergroupid = siteuser.getUsergroupid();
            if (usergroupid != null) {
                usergroupid = em.getReference(usergroupid.getClass(), usergroupid.getId());
                siteuser.setUsergroupid(usergroupid);
            }
            Merchant merchantid = siteuser.getMerchantid();
            if (merchantid != null) {
                merchantid = em.getReference(merchantid.getClass(), merchantid.getId());
                siteuser.setMerchantid(merchantid);
            }
            em.persist(siteuser);
            if (usergroupid != null) {
                usergroupid.getSiteuserList().add(siteuser);
                usergroupid = em.merge(usergroupid);
            }
            if (merchantid != null) {
                merchantid.getSiteuserList().add(siteuser);
                merchantid = em.merge(merchantid);
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

    public void edit(Siteuser siteuser) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Siteuser persistentSiteuser = em.find(Siteuser.class, siteuser.getId());
            Usergroup usergroupidOld = persistentSiteuser.getUsergroupid();
            Usergroup usergroupidNew = siteuser.getUsergroupid();
            Merchant merchantidOld = persistentSiteuser.getMerchantid();
            Merchant merchantidNew = siteuser.getMerchantid();
            if (usergroupidNew != null) {
                usergroupidNew = em.getReference(usergroupidNew.getClass(), usergroupidNew.getId());
                siteuser.setUsergroupid(usergroupidNew);
            }
            if (merchantidNew != null) {
                merchantidNew = em.getReference(merchantidNew.getClass(), merchantidNew.getId());
                siteuser.setMerchantid(merchantidNew);
            }
            siteuser = em.merge(siteuser);
            if (usergroupidOld != null && !usergroupidOld.equals(usergroupidNew)) {
                usergroupidOld.getSiteuserList().remove(siteuser);
                usergroupidOld = em.merge(usergroupidOld);
            }
            if (usergroupidNew != null && !usergroupidNew.equals(usergroupidOld)) {
                usergroupidNew.getSiteuserList().add(siteuser);
                usergroupidNew = em.merge(usergroupidNew);
            }
            if (merchantidOld != null && !merchantidOld.equals(merchantidNew)) {
                merchantidOld.getSiteuserList().remove(siteuser);
                merchantidOld = em.merge(merchantidOld);
            }
            if (merchantidNew != null && !merchantidNew.equals(merchantidOld)) {
                merchantidNew.getSiteuserList().add(siteuser);
                merchantidNew = em.merge(merchantidNew);
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
                Integer id = siteuser.getId();
                if (findSiteuser(id) == null) {
                    throw new NonexistentEntityException("The siteuser with id " + id + " no longer exists.");
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
            Siteuser siteuser;
            try {
                siteuser = em.getReference(Siteuser.class, id);
                siteuser.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The siteuser with id " + id + " no longer exists.", enfe);
            }
            Usergroup usergroupid = siteuser.getUsergroupid();
            if (usergroupid != null) {
                usergroupid.getSiteuserList().remove(siteuser);
                usergroupid = em.merge(usergroupid);
            }
            Merchant merchantid = siteuser.getMerchantid();
            if (merchantid != null) {
                merchantid.getSiteuserList().remove(siteuser);
                merchantid = em.merge(merchantid);
            }
            em.remove(siteuser);
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

    public List<Siteuser> findSiteuserEntities() {
        return findSiteuserEntities(true, -1, -1);
    }

    public List<Siteuser> findSiteuserEntities(int maxResults, int firstResult) {
        return findSiteuserEntities(false, maxResults, firstResult);
    }

    private List<Siteuser> findSiteuserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Siteuser.class));
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

    public Siteuser findSiteuser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Siteuser.class, id);
        } finally {
            em.close();
        }
    }

    public int getSiteuserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Siteuser> rt = cq.from(Siteuser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
