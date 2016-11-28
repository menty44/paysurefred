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
import paygate.objects.Merchant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Category;

/**
 *
 * @author paysure
 */
public class CategoryJpaController implements Serializable {

    public CategoryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Category category) throws RollbackFailureException, Exception {
        if (category.getMerchantList() == null) {
            category.setMerchantList(new ArrayList<Merchant>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Merchant> attachedMerchantList = new ArrayList<Merchant>();
            for (Merchant merchantListMerchantToAttach : category.getMerchantList()) {
                merchantListMerchantToAttach = em.getReference(merchantListMerchantToAttach.getClass(), merchantListMerchantToAttach.getId());
                attachedMerchantList.add(merchantListMerchantToAttach);
            }
            category.setMerchantList(attachedMerchantList);
            em.persist(category);
            for (Merchant merchantListMerchant : category.getMerchantList()) {
                Category oldCategoryidOfMerchantListMerchant = merchantListMerchant.getCategoryid();
                merchantListMerchant.setCategoryid(category);
                merchantListMerchant = em.merge(merchantListMerchant);
                if (oldCategoryidOfMerchantListMerchant != null) {
                    oldCategoryidOfMerchantListMerchant.getMerchantList().remove(merchantListMerchant);
                    oldCategoryidOfMerchantListMerchant = em.merge(oldCategoryidOfMerchantListMerchant);
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

    public void edit(Category category) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Category persistentCategory = em.find(Category.class, category.getId());
            List<Merchant> merchantListOld = persistentCategory.getMerchantList();
            List<Merchant> merchantListNew = category.getMerchantList();
            List<Merchant> attachedMerchantListNew = new ArrayList<Merchant>();
            for (Merchant merchantListNewMerchantToAttach : merchantListNew) {
                merchantListNewMerchantToAttach = em.getReference(merchantListNewMerchantToAttach.getClass(), merchantListNewMerchantToAttach.getId());
                attachedMerchantListNew.add(merchantListNewMerchantToAttach);
            }
            merchantListNew = attachedMerchantListNew;
            category.setMerchantList(merchantListNew);
            category = em.merge(category);
            for (Merchant merchantListOldMerchant : merchantListOld) {
                if (!merchantListNew.contains(merchantListOldMerchant)) {
                    merchantListOldMerchant.setCategoryid(null);
                    merchantListOldMerchant = em.merge(merchantListOldMerchant);
                }
            }
            for (Merchant merchantListNewMerchant : merchantListNew) {
                if (!merchantListOld.contains(merchantListNewMerchant)) {
                    Category oldCategoryidOfMerchantListNewMerchant = merchantListNewMerchant.getCategoryid();
                    merchantListNewMerchant.setCategoryid(category);
                    merchantListNewMerchant = em.merge(merchantListNewMerchant);
                    if (oldCategoryidOfMerchantListNewMerchant != null && !oldCategoryidOfMerchantListNewMerchant.equals(category)) {
                        oldCategoryidOfMerchantListNewMerchant.getMerchantList().remove(merchantListNewMerchant);
                        oldCategoryidOfMerchantListNewMerchant = em.merge(oldCategoryidOfMerchantListNewMerchant);
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
                Integer id = category.getId();
                if (findCategory(id) == null) {
                    throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
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
            Category category;
            try {
                category = em.getReference(Category.class, id);
                category.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
            }
            List<Merchant> merchantList = category.getMerchantList();
            for (Merchant merchantListMerchant : merchantList) {
                merchantListMerchant.setCategoryid(null);
                merchantListMerchant = em.merge(merchantListMerchant);
            }
            em.remove(category);
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

    public List<Category> findCategoryEntities() {
        return findCategoryEntities(true, -1, -1);
    }

    public List<Category> findCategoryEntities(int maxResults, int firstResult) {
        return findCategoryEntities(false, maxResults, firstResult);
    }

    private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Category.class));
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

    public Category findCategory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Category> rt = cq.from(Category.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
