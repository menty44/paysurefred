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
import paygate.objects.Purchase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Gender;

/**
 *
 * @author paysure
 */
public class GenderJpaController implements Serializable {

    public GenderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gender gender) throws RollbackFailureException, Exception {
        if (gender.getPurchaseList() == null) {
            gender.setPurchaseList(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : gender.getPurchaseList()) {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getId());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            gender.setPurchaseList(attachedPurchaseList);
            em.persist(gender);
            for (Purchase purchaseListPurchase : gender.getPurchaseList()) {
                Gender oldGenderidOfPurchaseListPurchase = purchaseListPurchase.getGenderid();
                purchaseListPurchase.setGenderid(gender);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldGenderidOfPurchaseListPurchase != null) {
                    oldGenderidOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldGenderidOfPurchaseListPurchase = em.merge(oldGenderidOfPurchaseListPurchase);
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

    public void edit(Gender gender) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Gender persistentGender = em.find(Gender.class, gender.getId());
            List<Purchase> purchaseListOld = persistentGender.getPurchaseList();
            List<Purchase> purchaseListNew = gender.getPurchaseList();
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew) {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getId());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            gender.setPurchaseList(purchaseListNew);
            gender = em.merge(gender);
            for (Purchase purchaseListOldPurchase : purchaseListOld) {
                if (!purchaseListNew.contains(purchaseListOldPurchase)) {
                    purchaseListOldPurchase.setGenderid(null);
                    purchaseListOldPurchase = em.merge(purchaseListOldPurchase);
                }
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew) {
                if (!purchaseListOld.contains(purchaseListNewPurchase)) {
                    Gender oldGenderidOfPurchaseListNewPurchase = purchaseListNewPurchase.getGenderid();
                    purchaseListNewPurchase.setGenderid(gender);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldGenderidOfPurchaseListNewPurchase != null && !oldGenderidOfPurchaseListNewPurchase.equals(gender)) {
                        oldGenderidOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldGenderidOfPurchaseListNewPurchase = em.merge(oldGenderidOfPurchaseListNewPurchase);
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
                Integer id = gender.getId();
                if (findGender(id) == null) {
                    throw new NonexistentEntityException("The gender with id " + id + " no longer exists.");
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
            Gender gender;
            try {
                gender = em.getReference(Gender.class, id);
                gender.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gender with id " + id + " no longer exists.", enfe);
            }
            List<Purchase> purchaseList = gender.getPurchaseList();
            for (Purchase purchaseListPurchase : purchaseList) {
                purchaseListPurchase.setGenderid(null);
                purchaseListPurchase = em.merge(purchaseListPurchase);
            }
            em.remove(gender);
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

    public List<Gender> findGenderEntities() {
        return findGenderEntities(true, -1, -1);
    }

    public List<Gender> findGenderEntities(int maxResults, int firstResult) {
        return findGenderEntities(false, maxResults, firstResult);
    }

    private List<Gender> findGenderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gender.class));
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

    public Gender findGender(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gender.class, id);
        } finally {
            em.close();
        }
    }

    public int getGenderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gender> rt = cq.from(Gender.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
