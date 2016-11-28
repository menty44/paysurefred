package paygate.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.*;
import paygate.objects.Batchitem;
import paygate.objects.Siteuser;

//@PersistenceContext(name = "persistence/LogicalName", unitName = "merchantPU")
@ManagedBean
//@SessionScoped
@ApplicationScoped
public class ShowItems implements Serializable {
    
    private List<Batchitem> batchitems;
    private Batchitem current;
    private ListDataModel batchitemsModel;
    private Integer noOfRows = 10;
    private Integer firstRowIndex = 0;     
    private int key;
    private HtmlInputText inputText;    
    private Integer merchantid = 23;
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
//    @ManagedProperty(value="#{batchheaderController}")
//    private BatchheaderController bc;

//    public BatchheaderController getBc() {
//        return bc;
//    }
//
//    public void setBc(BatchheaderController bc) {
//        this.bc = bc;
//    }
    
    public HtmlInputText getInputText() {
        return inputText;
    }

    public void setInputText(HtmlInputText inputText) {
        this.inputText = inputText;
    }
        
    public Integer getMerchantid() {
        merchantid = new Users().getMerchant().getId();
        return merchantid;
    }

    public int getKey() {
        //String val= getInputText().getValue().toString();
        //key=Integer.parseInt(val);
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
    
    public void setMerchantid(Integer merchantid) {
        this.merchantid = merchantid;
    }
    
    public Integer getFirstRowIndex() {
        return firstRowIndex;
    }

    public Integer getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(Integer noOfRows) {
        this.noOfRows = noOfRows;
    }
    
    public DataModel getBatchitemsModel() {
        if (batchitems == null) {
            batchitemsModel = new ListDataModel();
        }
        try {
            batchitemsModel.setWrappedData(getBatchitems());
        } catch (NotSupportedException ex) {
            Logger.getLogger(ShowItems.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(ShowItems.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(ShowItems.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(ShowItems.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(ShowItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        return batchitemsModel;
    }

    public String scrollFirst() {
        firstRowIndex = 0;
        return "success";
    }

    public String scrollPrevious() {
        firstRowIndex -= noOfRows;
        if (firstRowIndex < 0) {
            firstRowIndex = 0;
        }
        return "success";
    }
    
    public String scrollNext() {
        firstRowIndex += noOfRows;
        if (firstRowIndex >= batchitemsModel.getRowCount()) {
            firstRowIndex = batchitemsModel.getRowCount() - noOfRows;
            if (firstRowIndex < 0) {
                firstRowIndex = 0;

            }
        }
        return "success";
    }
    
    public String scrollLast() {
        firstRowIndex = batchitemsModel.getRowCount() - noOfRows;
        if (firstRowIndex < 0) {
            firstRowIndex = 0;
        }
        return "success";
    }
    
    public boolean isScrollFirstDisabled() {
        return firstRowIndex == 0;
    }

    public boolean isScrollLastDisabled() {
//        return (firstRowIndex >= (transactionsModel.getRowCount() - noOfRows));
        return true;
    }

    public boolean isScrollNextDisabled() {
//        return firstRowIndex.compareTo((transactionsModel.getRowCount() - noOfRows)) == 0;

        return firstRowIndex.compareTo((batchitemsModel.getRowCount() - noOfRows)) > 0;

    }

    public boolean isScrollPreviousDisabled() {
        return firstRowIndex == 0;
    }
    
    

    public List<Batchitem> getBatchitems() throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        
        Map<String, String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        //key=Integer.parseInt(params.get("key"));   
        //int index=batchitemsModel.getRowIndex();
        //System.out.println(index);
        //System.out.println(params.get("Value is "+"key"));
        utx.begin();
        //key=bc.getBid();
        //Query query=em.createNamedQuery("Batchitem.filter");
        Query query= em.createNamedQuery("Batchitem.filtered");
        query.setParameter("key", key);
        batchitems = query.getResultList();
        utx.commit();                 
        return batchitems;
    }
    
    
    //private DataModel<Batchitem> bi = new ArrayDataModel<Batchitem>(batchitems);
 
    
    
    
    
    
    
    
    
    /*public List<Batchitem> getBatchitems() {
        
        try{
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            utx.begin();
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            
            String username =  FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
            
            Query query=em.createNamedQuery("Batchitem.filter");
            
            
            
            batchitems = query.getResultList();
            utx.commit();
            
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
        
        
        
        
        
        
        
        
        
        
        return batchitems;
    }*/
    
    
    /*public List<Transaction> getTransactions() {

        try {
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            utx.begin();
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            
            String username =  FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
            Query query = em.createNamedQuery("Transaction.findAll");
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            query.setParameter("merchant", siteuser.getMerchant()); 
            
            transactions = query.getResultList();
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
        return transactions;
    }*/
    
    
    
    
    public void setBatchitems(List<Batchitem> batchitems) {
        this.batchitems = batchitems;
    }
       
    public ShowItems() {
    }
    
    public String showUser(){
        String fc=FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return fc;
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
