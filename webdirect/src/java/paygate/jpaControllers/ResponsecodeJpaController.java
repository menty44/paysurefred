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
import paygate.objects.Cardtype;
import paygate.objects.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import paygate.jpaControllers.exceptions.NonexistentEntityException;
import paygate.jpaControllers.exceptions.RollbackFailureException;
import paygate.objects.Responsecode;

/**
 *
 * @author paysure
 */
public class ResponsecodeJpaController implements Serializable {

    public ResponsecodeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Responsecode responsecode) throws RollbackFailureException, Exception {
        if (responsecode.getTransactionList() == null) {
            responsecode.setTransactionList(new ArrayList<Transaction>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cardtype cardtype = responsecode.getCardtype();
            if (cardtype != null) {
                cardtype = em.getReference(cardtype.getClass(), cardtype.getId());
                responsecode.setCardtype(cardtype);
            }
            List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
            for (Transaction transactionListTransactionToAttach : responsecode.getTransactionList()) {
                transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(), transactionListTransactionToAttach.getId());
                attachedTransactionList.add(transactionListTransactionToAttach);
            }
            responsecode.setTransactionList(attachedTransactionList);
            em.persist(responsecode);
            if (cardtype != null) {
                cardtype.getResponsecodeList().add(responsecode);
                cardtype = em.merge(cardtype);
            }
            for (Transaction transactionListTransaction : responsecode.getTransactionList()) {
                Responsecode oldResponsecodeidOfTransactionListTransaction = transactionListTransaction.getResponsecodeid();
                transactionListTransaction.setResponsecodeid(responsecode);
                transactionListTransaction = em.merge(transactionListTransaction);
                if (oldResponsecodeidOfTransactionListTransaction != null) {
                    oldResponsecodeidOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
                    oldResponsecodeidOfTransactionListTransaction = em.merge(oldResponsecodeidOfTransactionListTransaction);
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

    public void edit(Responsecode responsecode) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Responsecode persistentResponsecode = em.find(Responsecode.class, responsecode.getId());
            Cardtype cardtypeOld = persistentResponsecode.getCardtype();
            Cardtype cardtypeNew = responsecode.getCardtype();
            List<Transaction> transactionListOld = persistentResponsecode.getTransactionList();
            List<Transaction> transactionListNew = responsecode.getTransactionList();
            if (cardtypeNew != null) {
                cardtypeNew = em.getReference(cardtypeNew.getClass(), cardtypeNew.getId());
                responsecode.setCardtype(cardtypeNew);
            }
            List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
            for (Transaction transactionListNewTransactionToAttach : transactionListNew) {
                transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(), transactionListNewTransactionToAttach.getId());
                attachedTransactionListNew.add(transactionListNewTransactionToAttach);
            }
            transactionListNew = attachedTransactionListNew;
            responsecode.setTransactionList(transactionListNew);
            responsecode = em.merge(responsecode);
            if (cardtypeOld != null && !cardtypeOld.equals(cardtypeNew)) {
                cardtypeOld.getResponsecodeList().remove(responsecode);
                cardtypeOld = em.merge(cardtypeOld);
            }
            if (cardtypeNew != null && !cardtypeNew.equals(cardtypeOld)) {
                cardtypeNew.getResponsecodeList().add(responsecode);
                cardtypeNew = em.merge(cardtypeNew);
            }
            for (Transaction transactionListOldTransaction : transactionListOld) {
                if (!transactionListNew.contains(transactionListOldTransaction)) {
                    transactionListOldTransaction.setResponsecodeid(null);
                    transactionListOldTransaction = em.merge(transactionListOldTransaction);
                }
            }
            for (Transaction transactionListNewTransaction : transactionListNew) {
                if (!transactionListOld.contains(transactionListNewTransaction)) {
                    Responsecode oldResponsecodeidOfTransactionListNewTransaction = transactionListNewTransaction.getResponsecodeid();
                    transactionListNewTransaction.setResponsecodeid(responsecode);
                    transactionListNewTransaction = em.merge(transactionListNewTransaction);
                    if (oldResponsecodeidOfTransactionListNewTransaction != null && !oldResponsecodeidOfTransactionListNewTransaction.equals(responsecode)) {
                        oldResponsecodeidOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
                        oldResponsecodeidOfTransactionListNewTransaction = em.merge(oldResponsecodeidOfTransactionListNewTransaction);
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
                Integer id = responsecode.getId();
                if (findResponsecode(id) == null) {
                    throw new NonexistentEntityException("The responsecode with id " + id + " no longer exists.");
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
            Responsecode responsecode;
            try {
                responsecode = em.getReference(Responsecode.class, id);
                responsecode.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsecode with id " + id + " no longer exists.", enfe);
            }
            Cardtype cardtype = responsecode.getCardtype();
            if (cardtype != null) {
                cardtype.getResponsecodeList().remove(responsecode);
                cardtype = em.merge(cardtype);
            }
            List<Transaction> transactionList = responsecode.getTransactionList();
            for (Transaction transactionListTransaction : transactionList) {
                transactionListTransaction.setResponsecodeid(null);
                transactionListTransaction = em.merge(transactionListTransaction);
            }
            em.remove(responsecode);
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

    public List<Responsecode> findResponsecodeEntities() {
        return findResponsecodeEntities(true, -1, -1);
    }

    public List<Responsecode> findResponsecodeEntities(int maxResults, int firstResult) {
        return findResponsecodeEntities(false, maxResults, firstResult);
    }

    private List<Responsecode> findResponsecodeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Responsecode.class));
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

    public Responsecode findResponsecode(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Responsecode.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsecodeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Responsecode> rt = cq.from(Responsecode.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
