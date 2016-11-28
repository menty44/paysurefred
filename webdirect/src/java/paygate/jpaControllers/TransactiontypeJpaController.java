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
import paygate.objects.Transactiontype;

/**
 *
 * @author paysure
 */
public class TransactiontypeJpaController implements Serializable {

    public TransactiontypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transactiontype transactiontype) throws RollbackFailureException, Exception {
        if (transactiontype.getPurchaseList() == null) {
            transactiontype.setPurchaseList(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : transactiontype.getPurchaseList()) {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getId());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            transactiontype.setPurchaseList(attachedPurchaseList);
            em.persist(transactiontype);
            for (Purchase purchaseListPurchase : transactiontype.getPurchaseList()) {
                Transactiontype oldTransactiontypeOfPurchaseListPurchase = purchaseListPurchase.getTransactiontype();
                purchaseListPurchase.setTransactiontype(transactiontype);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldTransactiontypeOfPurchaseListPurchase != null) {
                    oldTransactiontypeOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldTransactiontypeOfPurchaseListPurchase = em.merge(oldTransactiontypeOfPurchaseListPurchase);
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

    public void edit(Transactiontype transactiontype) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transactiontype persistentTransactiontype = em.find(Transactiontype.class, transactiontype.getId());
            List<Purchase> purchaseListOld = persistentTransactiontype.getPurchaseList();
            List<Purchase> purchaseListNew = transactiontype.getPurchaseList();
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew) {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getId());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            transactiontype.setPurchaseList(purchaseListNew);
            transactiontype = em.merge(transactiontype);
            for (Purchase purchaseListOldPurchase : purchaseListOld) {
                if (!purchaseListNew.contains(purchaseListOldPurchase)) {
                    purchaseListOldPurchase.setTransactiontype(null);
                    purchaseListOldPurchase = em.merge(purchaseListOldPurchase);
                }
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew) {
                if (!purchaseListOld.contains(purchaseListNewPurchase)) {
                    Transactiontype oldTransactiontypeOfPurchaseListNewPurchase = purchaseListNewPurchase.getTransactiontype();
                    purchaseListNewPurchase.setTransactiontype(transactiontype);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldTransactiontypeOfPurchaseListNewPurchase != null && !oldTransactiontypeOfPurchaseListNewPurchase.equals(transactiontype)) {
                        oldTransactiontypeOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldTransactiontypeOfPurchaseListNewPurchase = em.merge(oldTransactiontypeOfPurchaseListNewPurchase);
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
                Integer id = transactiontype.getId();
                if (findTransactiontype(id) == null) {
                    throw new NonexistentEntityException("The transactiontype with id " + id + " no longer exists.");
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
            Transactiontype transactiontype;
            try {
                transactiontype = em.getReference(Transactiontype.class, id);
                transactiontype.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactiontype with id " + id + " no longer exists.", enfe);
            }
            List<Purchase> purchaseList = transactiontype.getPurchaseList();
            for (Purchase purchaseListPurchase : purchaseList) {
                purchaseListPurchase.setTransactiontype(null);
                purchaseListPurchase = em.merge(purchaseListPurchase);
            }
            em.remove(transactiontype);
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

    public List<Transactiontype> findTransactiontypeEntities() {
        return findTransactiontypeEntities(true, -1, -1);
    }

    public List<Transactiontype> findTransactiontypeEntities(int maxResults, int firstResult) {
        return findTransactiontypeEntities(false, maxResults, firstResult);
    }

    private List<Transactiontype> findTransactiontypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transactiontype.class));
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

    public Transactiontype findTransactiontype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transactiontype.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactiontypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transactiontype> rt = cq.from(Transactiontype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
