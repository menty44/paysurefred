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
import paygate.objects.Status;
import paygate.objects.Merchant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Country;

/**
 *
 * @author paysure
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
        if (country.getMerchantList() == null) {
            country.setMerchantList(new ArrayList<Merchant>());
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
            List<Merchant> attachedMerchantList = new ArrayList<Merchant>();
            for (Merchant merchantListMerchantToAttach : country.getMerchantList()) {
                merchantListMerchantToAttach = em.getReference(merchantListMerchantToAttach.getClass(), merchantListMerchantToAttach.getId());
                attachedMerchantList.add(merchantListMerchantToAttach);
            }
            country.setMerchantList(attachedMerchantList);
            em.persist(country);
            if (statusid != null) {
                statusid.getCountryList().add(country);
                statusid = em.merge(statusid);
            }
            for (Merchant merchantListMerchant : country.getMerchantList()) {
                Country oldCountryidOfMerchantListMerchant = merchantListMerchant.getCountryid();
                merchantListMerchant.setCountryid(country);
                merchantListMerchant = em.merge(merchantListMerchant);
                if (oldCountryidOfMerchantListMerchant != null) {
                    oldCountryidOfMerchantListMerchant.getMerchantList().remove(merchantListMerchant);
                    oldCountryidOfMerchantListMerchant = em.merge(oldCountryidOfMerchantListMerchant);
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
            List<Merchant> merchantListOld = persistentCountry.getMerchantList();
            List<Merchant> merchantListNew = country.getMerchantList();
            if (statusidNew != null) {
                statusidNew = em.getReference(statusidNew.getClass(), statusidNew.getId());
                country.setStatusid(statusidNew);
            }
            List<Merchant> attachedMerchantListNew = new ArrayList<Merchant>();
            for (Merchant merchantListNewMerchantToAttach : merchantListNew) {
                merchantListNewMerchantToAttach = em.getReference(merchantListNewMerchantToAttach.getClass(), merchantListNewMerchantToAttach.getId());
                attachedMerchantListNew.add(merchantListNewMerchantToAttach);
            }
            merchantListNew = attachedMerchantListNew;
            country.setMerchantList(merchantListNew);
            country = em.merge(country);
            if (statusidOld != null && !statusidOld.equals(statusidNew)) {
                statusidOld.getCountryList().remove(country);
                statusidOld = em.merge(statusidOld);
            }
            if (statusidNew != null && !statusidNew.equals(statusidOld)) {
                statusidNew.getCountryList().add(country);
                statusidNew = em.merge(statusidNew);
            }
            for (Merchant merchantListOldMerchant : merchantListOld) {
                if (!merchantListNew.contains(merchantListOldMerchant)) {
                    merchantListOldMerchant.setCountryid(null);
                    merchantListOldMerchant = em.merge(merchantListOldMerchant);
                }
            }
            for (Merchant merchantListNewMerchant : merchantListNew) {
                if (!merchantListOld.contains(merchantListNewMerchant)) {
                    Country oldCountryidOfMerchantListNewMerchant = merchantListNewMerchant.getCountryid();
                    merchantListNewMerchant.setCountryid(country);
                    merchantListNewMerchant = em.merge(merchantListNewMerchant);
                    if (oldCountryidOfMerchantListNewMerchant != null && !oldCountryidOfMerchantListNewMerchant.equals(country)) {
                        oldCountryidOfMerchantListNewMerchant.getMerchantList().remove(merchantListNewMerchant);
                        oldCountryidOfMerchantListNewMerchant = em.merge(oldCountryidOfMerchantListNewMerchant);
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
                statusid.getCountryList().remove(country);
                statusid = em.merge(statusid);
            }
            List<Merchant> merchantList = country.getMerchantList();
            for (Merchant merchantListMerchant : merchantList) {
                merchantListMerchant.setCountryid(null);
                merchantListMerchant = em.merge(merchantListMerchant);
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
