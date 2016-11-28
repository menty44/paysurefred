/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;
import paygate.objects.Batchheader;
import paygate.objects.Merchant;
import paygate.objects.Siteuser;

/**
 *
 * @author Joseph
 */
//@PersistenceContext(name = "persistence/LogicalName", unitName = "merchantPU")
@Named(value = "showHeaders")
@SessionScoped
public class ShowHeaders implements Serializable {
    @EJB
    private BatchheaderFacade batchheaderFacade;
    private Batchheader batchheader;
    private List<Batchheader> batchheaders;
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private Merchant mt;
    private int size;
    private Siteuser siteuser;
    private String session="brrrrrrrrrrr";
    private List<Batchheader> headers;
    private int idd;
    private int id2;

    
    

    /**
     * Creates a new instance of ShowHeaders
     */
    public ShowHeaders() {
    }

    public BatchheaderFacade getBatchheaderFacade() {
        return batchheaderFacade;
    }

    public void setBatchheaderFacade(BatchheaderFacade batchheaderFacade) {
        this.batchheaderFacade = batchheaderFacade;
    }

    public Batchheader getBatchheader() {
        return batchheader;
    }

    public void setBatchheader(Batchheader batchheader) {
        this.batchheader = batchheader;
    }

    public List<Batchheader> getBatchheaders() {
        //batchheaders = getBatchheaderFacade().findAll();
        Siteuser su = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
        Merchant m= su.getMerchant();        
        batchheaders = em.createNamedQuery("Batchheader.findByMerchantid").setParameter("merchantid", m).getResultList();
        
        return  batchheaders;
        
        /*try {
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            utx.begin();
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            
            String username =  FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
            mt = siteuser.getMerchant();
            batchheaders = em.createNamedQuery("Batchheader.findByMerchantid").setParameter("merchantid", mt).getResultList();
            //size = batchheaders.size();
            utx.commit();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }*/        
        
        //return  batchheaders;        
    }

    public Merchant getMt() {
        return mt;
    }

    public void setMt(Merchant mt) {
        this.mt = mt;
    }

    public int getSize() {   
        //size = batchheaders.size();
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Siteuser getSiteuser() {
        siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
        return siteuser;
    }

    public void setSiteuser(Siteuser siteuser) {
        this.siteuser = siteuser;
    }

    public List<Batchheader> getHeaders() {
        headers = em.createNamedQuery("Batchheader.findAll").getResultList();
        return headers;
    }

    public void setHeaders(List<Batchheader> headers) {
        this.headers = headers;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }
            
    public String showId(){
        System.out.println("IDD Is : "+idd);
        batchheader = em.find(Batchheader.class, idd);
        this.id2 = idd;
        System.out.println("Batch Header Details : "+batchheader.getBatchname());
        
        
        
        return null;
    }
        
    public String getSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) context.getExternalContext().getSession(false);
        session = hs.toString();
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
                
    public void onRowSelect(SelectEvent event) {  
        FacesMessage msg = new FacesMessage("BatchHeader Selected: ", ((Batchheader) event.getObject()).getBatchname());    
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    
    
    
    
}
