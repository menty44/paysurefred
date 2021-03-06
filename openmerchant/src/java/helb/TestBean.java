/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import paygate.controller.ProcessForm;
import paygate.objects.Helb;
import paygate.objects.Purchase;

/**
 *
 * @author Joseph
 */
@ManagedBean
@SessionScoped
public class TestBean implements Serializable{
    @EJB
    private ProcessForm processForm;       
    private List<Student> students;
    private long idnumber;
    private Purchase purchase;
    @PersistenceContext(unitName = "openmerchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private boolean exist;
    private String condition = "";
    private Helb helb;
    private String amount;
    private String description;
    private String sendUrl;
    private String poption;
        
    public TestBean() {
        
    }
    
    @PostConstruct
    public void init(){
        students = new ArrayList<Student>();
        students.add(new Student(1,"Susan","Njeri",21783279));
        students.add(new Student(2,"Nick","Omondi",22783279));
        students.add(new Student(3,"Ken","Keriko",23783279));
        students.add(new Student(4,"Joseph","Njeru",24783279));
        students.add(new Student(5,"Samuel","Ratemo",25783279));
        students.add(new Student(6,"Faith Soy","Chebet",26783279));
        students.add(new Student(7,"Joseph Muga","Omera",27783279));
        students.add(new Student(8,"Diana","Rose",28783279));
        students.add(new Student(9,"George","Okeyo",29783279));
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }
                
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public long getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(long idnumber) {
        this.idnumber = idnumber;
    }

    public ProcessForm getProcessForm() {
        return processForm;
    }
        
    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Helb getHelb() {
        return helb;
    }

    public void setHelb(Helb helb) {
        this.helb = helb;
    }
                        
    public void work(ActionEvent event) throws IOException{             
        try{
            helb = (Helb) em.createNamedQuery("Helb.findByIdnum").setParameter("idnum", Long.toString(idnumber)).getSingleResult();
            //Purchase p = (Purchase) em.createNamedQuery("Purchase.findById").setParameter("id", idnumber).getSingleResult();
            //purchase = p;
            System.out.println("found");
            condition = "yes";
            FacesContext context = FacesContext.getCurrentInstance();           
            context.addMessage(null, new FacesMessage("OK", "Student Exists/Found"));
            //exist = true;
            System.out.println("FeedBakc From WebService : "+ processForm.sayHello("Joseph Gitonga Njeru"));
            //int amt = Integer.parseInt(amount);
            //sendUrl = processForm.sendForm("Paysure Limited", helb.getName(), helb.getEmail(), 10000 , description, 2, "KES");
            System.out.println("Returned URL Link : "+sendUrl);
            //HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
            //response.sendRedirect(url);
            //payment();
        }catch(NoResultException e){
            System.out.println("not found");
            condition = "no";
            //condition = "yes";
            FacesContext context = FacesContext.getCurrentInstance();           
            context.addMessage(null, new FacesMessage("Error", "Student NotFound..Contact HELB"));
            //exist = false;
            //e.printStackTrace();
        }
        
        System.out.println("Condition is : "+condition);
        
    }
    
    public String workTwo(){
        
        try{
            helb = (Helb) em.createNamedQuery("Helb.findByIdnum").setParameter("idnum", Long.toString(idnumber)).getSingleResult();
            //Purchase p = (Purchase) em.createNamedQuery("Purchase.findById").setParameter("id", idnumber).getSingleResult();
            //purchase = p;
            System.out.println("found");
            condition = "yes";
            FacesContext context = FacesContext.getCurrentInstance();           
            context.addMessage(null, new FacesMessage("OK", "Student Exists/Found"));
            //exist = true;
            System.out.println("FeedBack From WebService : "+ processForm.sayHello("Joseph Gitonga Njeru"));
            //int amt = Integer.parseInt(amount);
            //sendUrl = processForm.sendForm("Paysure Limited", helb.getName(), helb.getEmail(), 10000 , description, 2, "KES");
            System.out.println("Returned URL Link : "+sendUrl);
            //HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
            //response.sendRedirect(url);
            //payment();
        }catch(NoResultException e){
            System.out.println("not found");
            condition = "no";
            //condition = "yes";
            FacesContext context = FacesContext.getCurrentInstance();           
            context.addMessage(null, new FacesMessage("Error", "Student NotFound..Contact HELB"));
            //exist = false;
            //e.printStackTrace();
        }
        
        System.out.println("Condition is : "+condition);
        return "Confirm.xhtml?faces-redirect=true";
    }
    
    public String payment() throws IOException{        
        System.out.println("URL to Redirect to is : "+sendUrl);
        String name = "Paysure Limited";
        String nname = helb.getName();
        String email = helb.getEmail();
        int amtt = 10000;
        String description = "Test Payment One HELB";
        String currency = "KES";
         
        System.out.println("Merchant Name : "+name);
        System.out.println("Name : "+nname);
        System.out.println("Email : "+email);
        System.out.println("Amount : "+amtt);
        System.out.println("Description : "+description);
        //System.out.println("Currency : "+currency);       
        String url = processForm.sendForm(name, nname, email, amtt, description, Integer.parseInt(poption) , currency);
        System.out.println("URL to Redirect to is : "+url);
        
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.sendRedirect(url);
        System.out.println(processForm.sayHello("jose tosh"));        
        //System.out.println(processForm.sendForm(name, nname, email, amtt, description, 2, currency));
        System.out.println("Currency : "+currency);
        //return "Pay.xhtml";
        return null;
    }
    
//    public void addMessage() {  
//        String summary = value2 ? "Checked" : "Unchecked";  
//  
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
//    }  

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
