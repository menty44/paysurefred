/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import paygate.objects.*;

/**
 *
 * @author gachanja
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        //cq.select(cq.from(entityClass));
        Root<Batchitem> batchitem = cq.from(Batchitem.class);
        cq.select(batchitem).where(getEntityManager().getCriteriaBuilder().equal(batchitem.get("batchheaderId").get("id"), 45));
        //cq.orderBy(cq.from(entityClass).get("id"))
        cq.orderBy(getEntityManager().getCriteriaBuilder().desc(cq.from(entityClass).get("id")));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findThem(int id){        
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        //cq.select(cq.from(entityClass));
        Root<Batchitem> batchitem = cq.from(Batchitem.class);
        cq.select(batchitem).where(getEntityManager().getCriteriaBuilder().equal(batchitem.get("batchheaderId").get("id"), id));
        //cq.orderBy(cq.from(entityClass).get("id"))
        cq.orderBy(getEntityManager().getCriteriaBuilder().desc(cq.from(entityClass).get("id")));
        return getEntityManager().createQuery(cq).getResultList();        
    }
    
    public List<Batchheader> show(){
        String username =  FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        Siteuser siteuser = (Siteuser) getEntityManager().createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
        return (List<Batchheader>) getEntityManager().createNamedQuery("Batchheader.findall").setParameter("mid", siteuser.getMerchant());        
    }

    public List<T> findRange(int[] range) {
        /*javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        //cq.select(cq.from(entityClass));
        Root<Batchheader> r = cq.from(Batchheader.class);                
        Siteuser siteuser = (Siteuser) getEntityManager().createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
        Merchant merchant = siteuser.getMerchant();
        int id = merchant.getId();
        //cq.where(getEntityManager().getCriteriaBuilder().equal(cq.from(entityClass).get("merchantid"), getEntityManager().getCriteriaBuilder().parameter(int.class, "merchantid")));        
        cq.where(getEntityManager().getCriteriaBuilder().equal(r.get("merchantid").get("id"),id));
        cq.orderBy(getEntityManager().getCriteriaBuilder().desc(cq.from(entityClass).get("id")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);        
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();*/
        
        Siteuser siteuser = (Siteuser) getEntityManager().createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
        Merchant merchant = siteuser.getMerchant();
        int id = merchant.getId();
        System.out.println(id);        
        
        CriteriaQuery<Batchheader> cqbh = getEntityManager().getCriteriaBuilder().createQuery(Batchheader.class);
        Root<Batchheader> batchheader = cqbh.from(Batchheader.class);
        cqbh.select(batchheader).where(getEntityManager().getCriteriaBuilder().equal(batchheader.get("merchantid").get("id"), id));
        cqbh.orderBy(getEntityManager().getCriteriaBuilder().desc(cqbh.from(entityClass).get("id")));
        javax.persistence.Query q = getEntityManager().createQuery(cqbh);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();                
    }
    
    public List<T> fetchThem(){
        
        CriteriaQuery<Batchitem> cqbi = getEntityManager().getCriteriaBuilder().createQuery(Batchitem.class);
        Root<Batchitem> batchitem = cqbi.from(Batchitem.class);
        cqbi.select(batchitem).where(getEntityManager().getCriteriaBuilder().equal(batchitem.get("batchheaderId").get("id"), 48));
        javax.persistence.Query q = getEntityManager().createQuery(cqbi);
        return q.getResultList();
        
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        //cq.orderBy(getEntityManager().getCriteriaBuilder().desc(cq.from(entityClass).get("id")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
