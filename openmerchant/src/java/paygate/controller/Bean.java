/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import paygate.objects.*;
import service.Onlinepay_Service;

/**
 *
 * @author Joseph
 */
@ManagedBean(name = "bean")
@SessionScoped
public class Bean implements Serializable {
    @EJB
    private ProcessForm processForm;                  
    @EJB
    private MerchantFacade merchantFacade;
    private String mname;
    private List<Merchant> merchants;
    private Merchant merchant;
    @PersistenceContext(unitName = "openmerchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    public boolean isUsd = false;
    private boolean curr;
    private boolean countrystatus = Boolean.FALSE;
    private String ctry;
    private String cat;
    private String mlogo;
    //private String currency = "";
    //private Boolean condition = false;
    
    private String name;
    private String email;    
    private String amount;
    private String description;
    private String currency;
    private String poption;
    

    /**
     * Creates a new instance of Bean
     */
    public Bean() {
        //System.out.println("Country From Constructor :"+ctry);
        //System.out.println("Category From Constructor :"+cat);
    }

    public ProcessForm getProcessForm() {
        return processForm;
    }    

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        System.out.println("Category Set to : "+cat);
        this.cat = cat;
    }

    public boolean isCountrystatus() {
        return countrystatus;
    }

    public void setCountrystatus(boolean countrystatus) {
        this.countrystatus = countrystatus;
    }
        
    public String getMlogo() {
        return mlogo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPoption() {
        return poption;
    }

    public void setPoption(String poption) {
        this.poption = poption;
    }
        
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    

    public void setMlogo(String mlogo) {
        this.mlogo = mlogo;
    }
            
    public String getCtry() {
        return ctry;
    }

    public void setCtry(String ctry) {
        System.out.println("Country Set to :"+ctry.compareTo("Kenya"));       
        //System.out.println(ctry.compareTo("kenya"));
        if(ctry.compareTo("Tanzania")==0){
            this.countrystatus = Boolean.TRUE;
        }
        this.ctry = ctry;
    }
    
    public MerchantFacade getMerchantFacade() {
        return merchantFacade;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        merchant = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mname).getSingleResult();
        mlogo = merchant.getName().toLowerCase().replaceAll("\\s", "");
        this.mname = mname;
    }

    public void merchantMnameChanged(ValueChangeEvent e) throws IOException {

        mname = e.getNewValue().toString();
        String page = "./index.jsp?mname=" + mname;
        merchant = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mname).getSingleResult();
        curr = merchant.isUsd();
        FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        System.out.println(mname);
    }

    public void handleChange() throws IOException {
        System.out.println("New value: "+mname);
        //String page = "./index.jsp?mname=" + mname;        
        merchant = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mname).getSingleResult();
        mlogo = merchant.getName().toLowerCase().replaceAll("\\s", "");
        String page = "index.xhtml?country="+merchant.getCountryid().getName()+"&cat="+merchant.getCategoryid().getId()+"&mname="+merchant.getName()+"&faces-redirect=true";
        FacesContext.getCurrentInstance().getExternalContext().redirect(page);
    }
    
    public void pChanged(ValueChangeEvent e){
        ctry = e.getNewValue().toString();
        if(ctry.compareTo("Tanzania")==0){
            this.countrystatus = Boolean.TRUE;
        }
        System.out.println("Something Changed :"+ctry+" Category :"+cat);
    }
    
    public void ppChanged(){
        //ctry = e.getNewValue().toString();
        if(ctry.compareTo("Tanzania")==0){
            this.countrystatus = Boolean.TRUE;
        }
        System.out.println("Something Changed :"+ctry+" Category :"+cat);
    }

    public List<Merchant> getMerchants() {
        //merchants = getMerchantFacade().findAll();
        Status status = (Status) em.createNamedQuery("Status.findByCode").setParameter("code", 101).getSingleResult();
        Country country = (Country) em.createNamedQuery("Country.findByName").setParameter("name", ctry.toLowerCase()).getSingleResult();
        Category c = (Category) em.createNamedQuery("Category.findById").setParameter("id", Integer.parseInt(cat)).getSingleResult();
        System.out.println("Country After Query :"+country.getName());
        System.out.println("Category After Query :"+c.getDescription());
        merchants = em.createNamedQuery("Merchant.findAllSort").setParameter("cid", country).setParameter("ctid", c).setParameter("code", status).getResultList();    
        System.out.println("Details : "+ctry+cat);
        
        //Country country = (Country) em.createNamedQuery("Country.findByName").setParameter("name",ctry.toLowerCase()).getSingleResult();
        //Category category = (Category) em.createNamedQuery("Category.findById").setParameter("id", Integer.parseInt(cat)).getSingleResult();
        //System.out.println("Country="+country+"Category="+category);
        //merchants = em.createNamedQuery("Merchant.findBySorted").setParameter("countryid", country).setParameter("categoryid", category).getResultList();
        //merchants = em.createNamedQuery("Merchant.findAll").setParameter("code", status).getResultList();
        System.out.println("Number of Merchants :"+merchants.size());
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
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

    /*
     * public String getCurrency() { return currency; }
     *
     * public void setCurrency(String currency) { //getCondition();
     * this.currency = currency;        
    }
     */

    /*
     * public Boolean getCondition() { if(currency.compareTo("KES")==0 ||
     * currency.compareTo("USD")==0){ setCondition(true); } return condition; }
     *
     * public void setCondition(Boolean condition) { this.condition = condition;
    }
     */
    public boolean isIsUsd() {
        isUsd = true;
        return isUsd;
    }

    public void setIsUsd(boolean isUsd) {
        this.isUsd = isUsd;
    }

    public boolean isCurr() {
        return curr;
    }

    public void setCurr(boolean curr) {
        this.curr = curr;
    }
    
    public void paymentForm(ActionEvent event){
        
        System.out.println("Listener Called");
        //String url = formData(mname, name, email, Integer.parseInt(amount), description, 2, currency);
        //System.out.println("URL Fetched is : "+url);
        
    }
    
    public String payment() throws IOException{        
        
        //System.out.println("Greeting : "+hello("joseph tosh"));
        //System.out.println("URL is :"+formData(mname, name, email, Integer.parseInt(amount), description, 2, "KES"));
        String url = processForm.sendForm(mname, name, email, Integer.parseInt(amount)*100, description, Integer.parseInt(poption), currency);
        //System.out.println("Returned URL is : "+processForm.sendForm("Paysure Limited", name, email, Integer.parseInt(amount), description, 2, "KES"));
        System.out.println("Returned URL is : "+url);
        System.out.println("Hello Mr. : "+processForm.sayHello(name));
        System.out.println("Name is : "+name);
        System.out.println("Email is : "+email);
        System.out.println("Amount is : "+amount);
        System.out.println("Description is : "+description);
        System.out.println("Currency is : "+currency);
        
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.sendRedirect(url);
        
        //String url = formData(mname, name, email, Integer.parseInt(amount)*100, description, 2, "KES");
        
        //System.out.println("URL Returned is : "+url);
        //FacesContext.getCurrentInstance().getExternalContext().redirect("www.google.com");
        return null;
    }       
    
}
