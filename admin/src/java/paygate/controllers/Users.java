/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import paygate.objects.Siteuser;

/**
 *
 * @author gachanja
 */
@PersistenceContext(name = "persistence/LogicalName", unitName = "adminPU")
public class Users  implements Serializable {

    private static final long serialVersionUID = 3L;
    public Siteuser siteuser;
    public String username;
    public boolean isLoggedIn;

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

    public Siteuser getSiteuser() {
        if (this.isLoggedIn == true) {
            try {
                Context ctx = (Context) new InitialContext().lookup("java:comp/env");
                EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
                Query query = (Query) em.createNamedQuery("Siteuser.findByUsername");
                siteuser = (Siteuser) query.setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();

            } catch (NamingException ex) {
                Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            siteuser = new Siteuser();
        }
        return siteuser;

    }

    public void setSiteuser(Siteuser siteuser) {
        this.siteuser = siteuser;
    }

    public String getUsername() {
        this.username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
