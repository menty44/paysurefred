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
        if (gender.getPurchaseCollection() == null) {
            gender.setPurchaseCollection(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Purchase> attachedPurchaseCollection = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionPurchaseToAttach : gender.getPurchaseCollection()) {
                purchaseCollectionPurchaseToAttach = em.getReference(purchaseCollectionPurchaseToAttach.getClass(), purchaseCollectionPurchaseToAttach.getId());
                attachedPurchaseCollection.add(purchaseCollectionPurchaseToAttach);
            }
            gender.setPurchaseCollection(attachedPurchaseCollection);
            em.persist(gender);
            for (Purchase purchaseCollectionPurchase : gender.getPurchaseCollection()) {
                Gender oldGenderidOfPurchaseCollectionPurchase = purchaseCollectionPurchase.getGenderid();
                purchaseCollectionPurchase.setGenderid(gender);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
                if (oldGenderidOfPurchaseCollectionPurchase != null) {
                    oldGenderidOfPurchaseCollectionPurchase.getPurchaseCollection().remove(purchaseCollectionPurchase);
                    oldGenderidOfPurchaseCollectionPurchase = em.merge(oldGenderidOfPurchaseCollectionPurchase);
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
            Collection<Purchase> purchaseCollectionOld = persistentGender.getPurchaseCollection();
            Collection<Purchase> purchaseCollectionNew = gender.getPurchaseCollection();
            Collection<Purchase> attachedPurchaseCollectionNew = new ArrayList<Purchase>();
            for (Purchase purchaseCollectionNewPurchaseToAttach : purchaseCollectionNew) {
                purchaseCollectionNewPurchaseToAttach = em.getReference(purchaseCollectionNewPurchaseToAttach.getClass(), purchaseCollectionNewPurchaseToAttach.getId());
                attachedPurchaseCollectionNew.add(purchaseCollectionNewPurchaseToAttach);
            }
            purchaseCollectionNew = attachedPurchaseCollectionNew;
            gender.setPurchaseCollection(purchaseCollectionNew);
            gender = em.merge(gender);
            for (Purchase purchaseCollectionOldPurchase : purchaseCollectionOld) {
                if (!purchaseCollectionNew.contains(purchaseCollectionOldPurchase)) {
                    purchaseCollectionOldPurchase.setGenderid(null);
                    purchaseCollectionOldPurchase = em.merge(purchaseCollectionOldPurchase);
                }
            }
            for (Purchase purchaseCollectionNewPurchase : purchaseCollectionNew) {
                if (!purchaseCollectionOld.contains(purchaseCollectionNewPurchase)) {
                    Gender oldGenderidOfPurchaseCollectionNewPurchase = purchaseCollectionNewPurchase.getGenderid();
                    purchaseCollectionNewPurchase.setGenderid(gender);
                    purchaseCollectionNewPurchase = em.merge(purchaseCollectionNewPurchase);
                    if (oldGenderidOfPurchaseCollectionNewPurchase != null && !oldGenderidOfPurchaseCollectionNewPurchase.equals(gender)) {
                        oldGenderidOfPurchaseCollectionNewPurchase.getPurchaseCollection().remove(purchaseCollectionNewPurchase);
                        oldGenderidOfPurchaseCollectionNewPurchase = em.merge(oldGenderidOfPurchaseCollectionNewPurchase);
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
            Collection<Purchase> purchaseCollection = gender.getPurchaseCollection();
            for (Purchase purchaseCollectionPurchase : purchaseCollection) {
                purchaseCollectionPurchase.setGenderid(null);
                purchaseCollectionPurchase = em.merge(purchaseCollectionPurchase);
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
