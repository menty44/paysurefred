/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pg.objects;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import pg.objects.exceptions.NonexistentEntityException;

/**
 *
 * @author gachanja
 */
public class CountryJpaController {

    public CountryJpaController() {
        emf = Persistence.createEntityManagerFactory("dailyTransactionProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Country country) {
        if (country.getMerchantCollection() == null) {
            country.setMerchantCollection(new ArrayList<Merchant>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Status status = country.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getId());
                country.setStatus(status);
            }
            Collection<Merchant> attachedMerchantCollection = new ArrayList<Merchant>();
            for (Merchant merchantCollectionMerchantToAttach : country.getMerchantCollection()) {
                merchantCollectionMerchantToAttach = em.getReference(merchantCollectionMerchantToAttach.getClass(), merchantCollectionMerchantToAttach.getId());
                attachedMerchantCollection.add(merchantCollectionMerchantToAttach);
            }
            country.setMerchantCollection(attachedMerchantCollection);
            em.persist(country);
            if (status != null) {
                status.getCountryCollection().add(country);
                status = em.merge(status);
            }
            for (Merchant merchantCollectionMerchant : country.getMerchantCollection()) {
                Country oldCountryOfMerchantCollectionMerchant = merchantCollectionMerchant.getCountry();
                merchantCollectionMerchant.setCountry(country);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
                if (oldCountryOfMerchantCollectionMerchant != null) {
                    oldCountryOfMerchantCollectionMerchant.getMerchantCollection().remove(merchantCollectionMerchant);
                    oldCountryOfMerchantCollectionMerchant = em.merge(oldCountryOfMerchantCollectionMerchant);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Country country) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Country persistentCountry = em.find(Country.class, country.getId());
            Status statusOld = persistentCountry.getStatus();
            Status statusNew = country.getStatus();
            Collection<Merchant> merchantCollectionOld = persistentCountry.getMerchantCollection();
            Collection<Merchant> merchantCollectionNew = country.getMerchantCollection();
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getId());
                country.setStatus(statusNew);
            }
            Collection<Merchant> attachedMerchantCollectionNew = new ArrayList<Merchant>();
            for (Merchant merchantCollectionNewMerchantToAttach : merchantCollectionNew) {
                merchantCollectionNewMerchantToAttach = em.getReference(merchantCollectionNewMerchantToAttach.getClass(), merchantCollectionNewMerchantToAttach.getId());
                attachedMerchantCollectionNew.add(merchantCollectionNewMerchantToAttach);
            }
            merchantCollectionNew = attachedMerchantCollectionNew;
            country.setMerchantCollection(merchantCollectionNew);
            country = em.merge(country);
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getCountryCollection().remove(country);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getCountryCollection().add(country);
                statusNew = em.merge(statusNew);
            }
            for (Merchant merchantCollectionOldMerchant : merchantCollectionOld) {
                if (!merchantCollectionNew.contains(merchantCollectionOldMerchant)) {
                    merchantCollectionOldMerchant.setCountry(null);
                    merchantCollectionOldMerchant = em.merge(merchantCollectionOldMerchant);
                }
            }
            for (Merchant merchantCollectionNewMerchant : merchantCollectionNew) {
                if (!merchantCollectionOld.contains(merchantCollectionNewMerchant)) {
                    Country oldCountryOfMerchantCollectionNewMerchant = merchantCollectionNewMerchant.getCountry();
                    merchantCollectionNewMerchant.setCountry(country);
                    merchantCollectionNewMerchant = em.merge(merchantCollectionNewMerchant);
                    if (oldCountryOfMerchantCollectionNewMerchant != null && !oldCountryOfMerchantCollectionNewMerchant.equals(country)) {
                        oldCountryOfMerchantCollectionNewMerchant.getMerchantCollection().remove(merchantCollectionNewMerchant);
                        oldCountryOfMerchantCollectionNewMerchant = em.merge(oldCountryOfMerchantCollectionNewMerchant);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Country country;
            try {
                country = em.getReference(Country.class, id);
                country.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The country with id " + id + " no longer exists.", enfe);
            }
            Status status = country.getStatus();
            if (status != null) {
                status.getCountryCollection().remove(country);
                status = em.merge(status);
            }
            Collection<Merchant> merchantCollection = country.getMerchantCollection();
            for (Merchant merchantCollectionMerchant : merchantCollection) {
                merchantCollectionMerchant.setCountry(null);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
            }
            em.remove(country);
            em.getTransaction().commit();
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
