/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import org.primefaces.event.SelectEvent;
import paygate.objects.Purchase;
import paygate.objects.Siteuser;

/**
 *
 * @author paysure
 */
@Named(value = "showPurchases")
@SessionScoped
public class ShowPurchases implements Serializable {

    private List<Purchase> purchases;
    private Purchase current;
    private ListDataModel PurchasesModel;
    private Integer noOfRows = 10;
    private Integer firstRowIndex = 0;        
    private Integer merchantid = 23;
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;    
    private Purchase purchase;
    private Date date;   // Date for the excel purchases file exported by a merchant from the list of purchases
    public ShowPurchases() {
    }

    public Purchase getCurrent() {
        return current;
    }

    public void setCurrent(Purchase current) {
        this.current = current;
    }

    public Date getDate() {
        date = new Date();
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
        
    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }                   
        
    public Integer getMerchantid() {
        merchantid = new Users().getMerchant().getId();
        return merchantid;
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
    
    public DataModel getPurchasesModel() {
        if (purchases == null) {
            PurchasesModel = new ListDataModel();
        }
        try {
            PurchasesModel.setWrappedData(getPurchases());
        } catch (NotSupportedException ex) {
            Logger.getLogger(ShowItems.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(ShowItems.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return PurchasesModel;
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
        if (firstRowIndex >= PurchasesModel.getRowCount()) {
            firstRowIndex = PurchasesModel.getRowCount() - noOfRows;
            if (firstRowIndex < 0) {
                firstRowIndex = 0;

            }
        }
        return "success";
    }
    
    public String scrollLast() {
        firstRowIndex = PurchasesModel.getRowCount() - noOfRows;
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

        return firstRowIndex.compareTo((PurchasesModel.getRowCount() - noOfRows)) > 0;

    }

    public boolean isScrollPreviousDisabled() {
        return firstRowIndex == 0;
    }

    public List<Purchase> getPurchases() throws NotSupportedException, SystemException {
        
        String user = showUser();
        //utx.begin();
        Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", user).getSingleResult();
        List<Purchase> purchases = em.createQuery("select p from Purchase p where p.merchantid.id= :value order by p.id desc").setParameter("value", siteuser.getMerchant().getId()).getResultList();
        
        
        
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
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
    
    public void show(ActionEvent event){        
        int rowIndex = getPurchasesModel().getRowIndex();
        System.out.println("Row Index is=>"+rowIndex);       
    }
    
    public void onRowSelect(SelectEvent event){
        FacesMessage msg = new FacesMessage("Purchase Selected",((Purchase) event.getObject()).getReferenceno());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }    
    
}
