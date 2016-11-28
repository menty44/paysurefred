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
public class CountryJpaController implements Serializable {

    public CountryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Country country) throws RollbackFailureException, Exception {
        if (country.getMerchantCollection() == null) {
            country.setMerchantCollection(new ArrayList<Merchant>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status statusid = country.getStatusid();
            if (statusid != null) {
                statusid = em.getReference(statusid.getClass(), statusid.getId());
                country.setStatusid(statusid);
            }
            Collection<Merchant> attachedMerchantCollection = new ArrayList<Merchant>();
            for (Merchant merchantCollectionMerchantToAttach : country.getMerchantCollection()) {
                merchantCollectionMerchantToAttach = em.getReference(merchantCollectionMerchantToAttach.getClass(), merchantCollectionMerchantToAttach.getId());
                attachedMerchantCollection.add(merchantCollectionMerchantToAttach);
            }
            country.setMerchantCollection(attachedMerchantCollection);
            em.persist(country);
            if (statusid != null) {
                statusid.getCountryCollection().add(country);
                statusid = em.merge(statusid);
            }
            for (Merchant merchantCollectionMerchant : country.getMerchantCollection()) {
                Country oldCountryidOfMerchantCollectionMerchant = merchantCollectionMerchant.getCountryid();
                merchantCollectionMerchant.setCountryid(country);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
                if (oldCountryidOfMerchantCollectionMerchant != null) {
                    oldCountryidOfMerchantCollectionMerchant.getMerchantCollection().remove(merchantCollectionMerchant);
                    oldCountryidOfMerchantCollectionMerchant = em.merge(oldCountryidOfMerchantCollectionMerchant);
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

    public void edit(Country country) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Country persistentCountry = em.find(Country.class, country.getId());
            Status statusidOld = persistentCountry.getStatusid();
            Status statusidNew = country.getStatusid();
            Collection<Merchant> merchantCollectionOld = persistentCountry.getMerchantCollection();
            Collection<Merchant> merchantCollectionNew = country.getMerchantCollection();
            if (statusidNew != null) {
                statusidNew = em.getReference(statusidNew.getClass(), statusidNew.getId());
                country.setStatusid(statusidNew);
            }
            Collection<Merchant> attachedMerchantCollectionNew = new ArrayList<Merchant>();
            for (Merchant merchantCollectionNewMerchantToAttach : merchantCollectionNew) {
                merchantCollectionNewMerchantToAttach = em.getReference(merchantCollectionNewMerchantToAttach.getClass(), merchantCollectionNewMerchantToAttach.getId());
                attachedMerchantCollectionNew.add(merchantCollectionNewMerchantToAttach);
            }
            merchantCollectionNew = attachedMerchantCollectionNew;
            country.setMerchantCollection(merchantCollectionNew);
            country = em.merge(country);
            if (statusidOld != null && !statusidOld.equals(statusidNew)) {
                statusidOld.getCountryCollection().remove(country);
                statusidOld = em.merge(statusidOld);
            }
            if (statusidNew != null && !statusidNew.equals(statusidOld)) {
                statusidNew.getCountryCollection().add(country);
                statusidNew = em.merge(statusidNew);
            }
            for (Merchant merchantCollectionOldMerchant : merchantCollectionOld) {
                if (!merchantCollectionNew.contains(merchantCollectionOldMerchant)) {
                    merchantCollectionOldMerchant.setCountryid(null);
                    merchantCollectionOldMerchant = em.merge(merchantCollectionOldMerchant);
                }
            }
            for (Merchant merchantCollectionNewMerchant : merchantCollectionNew) {
                if (!merchantCollectionOld.contains(merchantCollectionNewMerchant)) {
                    Country oldCountryidOfMerchantCollectionNewMerchant = merchantCollectionNewMerchant.getCountryid();
                    merchantCollectionNewMerchant.setCountryid(country);
                    merchantCollectionNewMerchant = em.merge(merchantCollectionNewMerchant);
                    if (oldCountryidOfMerchantCollectionNewMerchant != null && !oldCountryidOfMerchantCollectionNewMerchant.equals(country)) {
                        oldCountryidOfMerchantCollectionNewMerchant.getMerchantCollection().remove(merchantCollectionNewMerchant);
                        oldCountryidOfMerchantCollectionNewMerchant = em.merge(oldCountryidOfMerchantCollectionNewMerchant);
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
                Integer id = country.getId();
                if (findCountry(id) == null) {
                    throw new NonexistentEntityException("The country with id " + id + " no longer exists.");
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
            Country country;
            try {
                country = em.getReference(Country.class, id);
                country.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The country with id " + id + " no longer exists.", enfe);
            }
            Status statusid = country.getStatusid();
            if (statusid != null) {
                statusid.getCountryCollection().remove(country);
                statusid = em.merge(statusid);
            }
            Collection<Merchant> merchantCollection = country.getMerchantCollection();
            for (Merchant merchantCollectionMerchant : merchantCollection) {
                merchantCollectionMerchant.setCountryid(null);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
            }
            em.remove(country);
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

    public List<Country> findCountryEntities() {
        return findCountryEntities(true, -1, -1);
    }

    public List<Country> findCountryEntities(int maxResults, int firstResult) {
        return findCountryEntities(false, maxResults, firstResult);
    }

    private List<Country> findCountryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Country.class));
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

    public Country findCountry(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Country.class, id);
        } finally {
            em.close();
        }
    }

    public int getCountryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Country> rt = cq.from(Country.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
