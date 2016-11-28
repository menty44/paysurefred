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
public class CartJpaController implements Serializable {

    public CartJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cart cart) throws RollbackFailureException, Exception {
        if (cart.getMerchantCollection() == null) {
            cart.setMerchantCollection(new ArrayList<Merchant>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Merchant> attachedMerchantCollection = new ArrayList<Merchant>();
            for (Merchant merchantCollectionMerchantToAttach : cart.getMerchantCollection()) {
                merchantCollectionMerchantToAttach = em.getReference(merchantCollectionMerchantToAttach.getClass(), merchantCollectionMerchantToAttach.getId());
                attachedMerchantCollection.add(merchantCollectionMerchantToAttach);
            }
            cart.setMerchantCollection(attachedMerchantCollection);
            em.persist(cart);
            for (Merchant merchantCollectionMerchant : cart.getMerchantCollection()) {
                Cart oldCarttypeOfMerchantCollectionMerchant = merchantCollectionMerchant.getCarttype();
                merchantCollectionMerchant.setCarttype(cart);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
                if (oldCarttypeOfMerchantCollectionMerchant != null) {
                    oldCarttypeOfMerchantCollectionMerchant.getMerchantCollection().remove(merchantCollectionMerchant);
                    oldCarttypeOfMerchantCollectionMerchant = em.merge(oldCarttypeOfMerchantCollectionMerchant);
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

    public void edit(Cart cart) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cart persistentCart = em.find(Cart.class, cart.getId());
            Collection<Merchant> merchantCollectionOld = persistentCart.getMerchantCollection();
            Collection<Merchant> merchantCollectionNew = cart.getMerchantCollection();
            Collection<Merchant> attachedMerchantCollectionNew = new ArrayList<Merchant>();
            for (Merchant merchantCollectionNewMerchantToAttach : merchantCollectionNew) {
                merchantCollectionNewMerchantToAttach = em.getReference(merchantCollectionNewMerchantToAttach.getClass(), merchantCollectionNewMerchantToAttach.getId());
                attachedMerchantCollectionNew.add(merchantCollectionNewMerchantToAttach);
            }
            merchantCollectionNew = attachedMerchantCollectionNew;
            cart.setMerchantCollection(merchantCollectionNew);
            cart = em.merge(cart);
            for (Merchant merchantCollectionOldMerchant : merchantCollectionOld) {
                if (!merchantCollectionNew.contains(merchantCollectionOldMerchant)) {
                    merchantCollectionOldMerchant.setCarttype(null);
                    merchantCollectionOldMerchant = em.merge(merchantCollectionOldMerchant);
                }
            }
            for (Merchant merchantCollectionNewMerchant : merchantCollectionNew) {
                if (!merchantCollectionOld.contains(merchantCollectionNewMerchant)) {
                    Cart oldCarttypeOfMerchantCollectionNewMerchant = merchantCollectionNewMerchant.getCarttype();
                    merchantCollectionNewMerchant.setCarttype(cart);
                    merchantCollectionNewMerchant = em.merge(merchantCollectionNewMerchant);
                    if (oldCarttypeOfMerchantCollectionNewMerchant != null && !oldCarttypeOfMerchantCollectionNewMerchant.equals(cart)) {
                        oldCarttypeOfMerchantCollectionNewMerchant.getMerchantCollection().remove(merchantCollectionNewMerchant);
                        oldCarttypeOfMerchantCollectionNewMerchant = em.merge(oldCarttypeOfMerchantCollectionNewMerchant);
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
                Integer id = cart.getId();
                if (findCart(id) == null) {
                    throw new NonexistentEntityException("The cart with id " + id + " no longer exists.");
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
            Cart cart;
            try {
                cart = em.getReference(Cart.class, id);
                cart.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cart with id " + id + " no longer exists.", enfe);
            }
            Collection<Merchant> merchantCollection = cart.getMerchantCollection();
            for (Merchant merchantCollectionMerchant : merchantCollection) {
                merchantCollectionMerchant.setCarttype(null);
                merchantCollectionMerchant = em.merge(merchantCollectionMerchant);
            }
            em.remove(cart);
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

    public List<Cart> findCartEntities() {
        return findCartEntities(true, -1, -1);
    }

    public List<Cart> findCartEntities(int maxResults, int firstResult) {
        return findCartEntities(false, maxResults, firstResult);
    }

    private List<Cart> findCartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cart.class));
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

    public Cart findCart(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cart.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cart> rt = cq.from(Cart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
