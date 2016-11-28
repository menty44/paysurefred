/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import paygate.objects.Token;

/**
 *
 * @author Joseph
 */
@Named(value = "tokenController")
@SessionScoped
public class TokenController implements Serializable {

    @EJB
    private TokenManagementBeanLocal tokenManagementBean;
    private List<Token> tokens;
    private int id;
    private String name;
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    /**
     * Creates a new instance of TokenController
     */
    public TokenController() {
    }

    @PostConstruct
    public void init() {
        tokens = tokenManagementBean.getAllTokens();
    }

    public void confirm(ActionEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error", "Please try again later.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void b4(ActionEvent event) {
        show();
    }

    public String show() {
        System.out.println("Name is : " + name);
        System.out.println("Called From Dialog : " + new Date());
        return "logout";
    }

    public void useid(ActionEvent event) {
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showMsg(ActionEvent event) {
        System.out.println("Show Message Called");
    }

    public String shift() {
        System.out.println("Shift method Called");
        System.out.println("Id Is : " + id);
        //Token token = em.find(Token.class, id);
        //System.out.println("Token T-Index : "+token.getTransactionindex()); 
        System.out.println("Tokens Before : " + tokens);
        tokenManagementBean.tokenDebit(id);
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Successful", "Debit Went Through"));
        tokens = tokenManagementBean.getAllTokens();
        System.out.println("Tokens After : " + tokens);
        //FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage("one", new FacesMessage("yipee", "Payment Went Well"));
        //return "Index";
        //context.addMessage("username", new FacesMessage("Invalid UserName and Password"));
        return null;
    }
    
    public String reverse(){
        tokenManagementBean.tokenReverse(id);
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Successful", "Reversal Went Through")); 
        tokens = tokenManagementBean.getAllTokens();
        System.out.println("Reversal Ok.");
        return null;
    }
    
    public void save(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Second Message", "Additional Info Here..."));  
    }  

    public String login() {

        if (id > 2) {

            return null;

        } else {

            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage("one", new FacesMessage("Invalid UserName and Password"));

            return null;

        }

    }

    public String removeTokens(int tokenid) {
        tokenManagementBean.deleteToken(tokenid);
        tokens = tokenManagementBean.getAllTokens();
        return null;
    }

    public String debitToken(int tokenid) {
        //tokenManagementBean.tokenDebit(tokenid);
        this.id = tokenid;
        return "Debit?faces-redirect=true";
    }

    public void DebitMessage(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "The Debit Went Through Well"));
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

class FacesUtil {

    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    // Getters -----------------------------------------------------------------------------------
    public static String getActionAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
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
