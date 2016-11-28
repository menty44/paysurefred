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
import paygate.objects.Responsecode;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Cardtype;

/**
 *
 * @author paysure
 */
public class CardtypeJpaController implements Serializable {

    public CardtypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cardtype cardtype) throws RollbackFailureException, Exception {
        if (cardtype.getResponsecodeList() == null) {
            cardtype.setResponsecodeList(new ArrayList<Responsecode>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Responsecode> attachedResponsecodeList = new ArrayList<Responsecode>();
            for (Responsecode responsecodeListResponsecodeToAttach : cardtype.getResponsecodeList()) {
                responsecodeListResponsecodeToAttach = em.getReference(responsecodeListResponsecodeToAttach.getClass(), responsecodeListResponsecodeToAttach.getId());
                attachedResponsecodeList.add(responsecodeListResponsecodeToAttach);
            }
            cardtype.setResponsecodeList(attachedResponsecodeList);
            em.persist(cardtype);
            for (Responsecode responsecodeListResponsecode : cardtype.getResponsecodeList()) {
                Cardtype oldCardtypeOfResponsecodeListResponsecode = responsecodeListResponsecode.getCardtype();
                responsecodeListResponsecode.setCardtype(cardtype);
                responsecodeListResponsecode = em.merge(responsecodeListResponsecode);
                if (oldCardtypeOfResponsecodeListResponsecode != null) {
                    oldCardtypeOfResponsecodeListResponsecode.getResponsecodeList().remove(responsecodeListResponsecode);
                    oldCardtypeOfResponsecodeListResponsecode = em.merge(oldCardtypeOfResponsecodeListResponsecode);
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

    public void edit(Cardtype cardtype) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cardtype persistentCardtype = em.find(Cardtype.class, cardtype.getId());
            List<Responsecode> responsecodeListOld = persistentCardtype.getResponsecodeList();
            List<Responsecode> responsecodeListNew = cardtype.getResponsecodeList();
            List<Responsecode> attachedResponsecodeListNew = new ArrayList<Responsecode>();
            for (Responsecode responsecodeListNewResponsecodeToAttach : responsecodeListNew) {
                responsecodeListNewResponsecodeToAttach = em.getReference(responsecodeListNewResponsecodeToAttach.getClass(), responsecodeListNewResponsecodeToAttach.getId());
                attachedResponsecodeListNew.add(responsecodeListNewResponsecodeToAttach);
            }
            responsecodeListNew = attachedResponsecodeListNew;
            cardtype.setResponsecodeList(responsecodeListNew);
            cardtype = em.merge(cardtype);
            for (Responsecode responsecodeListOldResponsecode : responsecodeListOld) {
                if (!responsecodeListNew.contains(responsecodeListOldResponsecode)) {
                    responsecodeListOldResponsecode.setCardtype(null);
                    responsecodeListOldResponsecode = em.merge(responsecodeListOldResponsecode);
                }
            }
            for (Responsecode responsecodeListNewResponsecode : responsecodeListNew) {
                if (!responsecodeListOld.contains(responsecodeListNewResponsecode)) {
                    Cardtype oldCardtypeOfResponsecodeListNewResponsecode = responsecodeListNewResponsecode.getCardtype();
                    responsecodeListNewResponsecode.setCardtype(cardtype);
                    responsecodeListNewResponsecode = em.merge(responsecodeListNewResponsecode);
                    if (oldCardtypeOfResponsecodeListNewResponsecode != null && !oldCardtypeOfResponsecodeListNewResponsecode.equals(cardtype)) {
                        oldCardtypeOfResponsecodeListNewResponsecode.getResponsecodeList().remove(responsecodeListNewResponsecode);
                        oldCardtypeOfResponsecodeListNewResponsecode = em.merge(oldCardtypeOfResponsecodeListNewResponsecode);
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
                Integer id = cardtype.getId();
                if (findCardtype(id) == null) {
                    throw new NonexistentEntityException("The cardtype with id " + id + " no longer exists.");
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
            Cardtype cardtype;
            try {
                cardtype = em.getReference(Cardtype.class, id);
                cardtype.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cardtype with id " + id + " no longer exists.", enfe);
            }
            List<Responsecode> responsecodeList = cardtype.getResponsecodeList();
            for (Responsecode responsecodeListResponsecode : responsecodeList) {
                responsecodeListResponsecode.setCardtype(null);
                responsecodeListResponsecode = em.merge(responsecodeListResponsecode);
            }
            em.remove(cardtype);
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

    public List<Cardtype> findCardtypeEntities() {
        return findCardtypeEntities(true, -1, -1);
    }

    public List<Cardtype> findCardtypeEntities(int maxResults, int firstResult) {
        return findCardtypeEntities(false, maxResults, firstResult);
    }

    private List<Cardtype> findCardtypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cardtype.class));
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

    public Cardtype findCardtype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cardtype.class, id);
        } finally {
            em.close();
        }
    }

    public int getCardtypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cardtype> rt = cq.from(Cardtype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
