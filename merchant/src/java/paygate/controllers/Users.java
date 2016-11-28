/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import paygate.objects.Merchant;
import paygate.objects.Siteuser;

/**
 *
 * @author gachanja
 */
@PersistenceContext(name = "persistence/LogicalName", unitName = "merchantPU")
public class Users implements Serializable {

    private static final long serialVersionUID = 3L;
    public Siteuser siteuser;
    public String username;
    public boolean isLoggedIn;
    public boolean isUsd;
    @Resource
    private javax.transaction.UserTransaction utx;
    public Merchant merchant;
    private String sessionId;

    /**
     * Get the value of merchant
     *
     * @return the value of merchant
     */
    public Merchant getMerchant() {
        merchant = siteuser.getMerchant();
        return merchant;
    }

    /**
     * Set the value of merchant
     *
     * @param merchant new value of merchant
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getSessionId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        sessionId = session.getId();
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Users() {
    }

    public boolean isIsLoggedIn() {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getRemoteUser().isEmpty()) {
                isLoggedIn = false;
            } else {
                isLoggedIn = true;
            }
        } catch (NullPointerException npe) {
        }
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isIsUsd() throws NamingException {
        if (this.isLoggedIn == true) {
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            Query query = em.createNamedQuery("Siteuser.findByUsername");
            siteuser = (Siteuser) query.setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
            isUsd = siteuser.getMerchant().isUsd();
        } else {
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            Query query = em.createNamedQuery("Siteuser.findByUsername");
            siteuser = (Siteuser) query.setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
            isUsd = siteuser.getMerchant().isUsd();
        }
        return isUsd;
    }

    public void setIsUsd(boolean isUsd) {
        this.isUsd = isUsd;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
//        this.username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();

        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;

    }

    /**
     * Get the value of siteuser
     *
     * @return the value of siteuser
     */
    public Siteuser getSiteuser() {
        if (this.isLoggedIn == true) {
            try {
                Context ctx = (Context) new InitialContext().lookup("java:comp/env");
                EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
                Query query = em.createNamedQuery("Siteuser.findByUsername");
                siteuser = (Siteuser) query.setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();

            } catch (NamingException ex) {
                Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            siteuser = new Siteuser();
        }
        return siteuser;
    }

    /**
     * Set the value of siteuser
     *
     * @param siteuser new value of siteuser
     */
    public void setSiteuser(Siteuser siteuser) {
        this.siteuser = siteuser;
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        final HttpServletRequest request = (HttpServletRequest) ec.getRequest();             
        request.getSession(false).invalidate();
        this.setIsLoggedIn(false);
        return "logout";
    }
}
