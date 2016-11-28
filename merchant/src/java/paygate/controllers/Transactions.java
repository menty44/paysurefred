        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import paygate.objects.Siteuser;
import paygate.objects.Transaction;

/**
 *
 * @author gachanja
 */
@PersistenceContext(name = "persistence/LogicalName", unitName = "merchantPU")
@ManagedBean
@SessionScoped
public class Transactions implements Serializable {
    private static final long serialVersionUID = 2L;
    public Date startDate;
    public Date endDate;
    public List<Transaction> transactions;
    private ListDataModel transactionsModel;
    public Integer noOfRows = 10;
    public Integer firstRowIndex = 0;
    @Resource
    private javax.transaction.UserTransaction utx;
    public Integer merchantid = 23;
    private Transaction transaction;
    private String lastname="me";

    public Integer getMerchantid() {
        merchantid = new Users().getMerchant().getId();
        return merchantid;
    }

    public void setMerchantid(Integer merchantid) {
        this.merchantid = merchantid;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getLastname() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession(false);
        lastname = (String) session.getAttribute("myname");
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public DataModel getTransactionsModel() {
        if (transactions == null) {
            transactionsModel = new ListDataModel();
        }
        transactionsModel.setWrappedData(getTransactions());
        return transactionsModel;
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
        if (firstRowIndex >= transactionsModel.getRowCount()) {
            firstRowIndex = transactionsModel.getRowCount() - noOfRows;
            if (firstRowIndex < 0) {
                firstRowIndex = 0;

            }
        }
        return "success";
    }

    public String scrollLast() {
        firstRowIndex = transactionsModel.getRowCount() - noOfRows;
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

        return firstRowIndex.compareTo((transactionsModel.getRowCount() - noOfRows)) > 0;

    }

    public boolean isScrollPreviousDisabled() {
        return firstRowIndex == 0;
    }

    public List<Transaction> getTransactions() {

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
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Date getEndDate() {
        if (endDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, +3);
            endDate = cal.getTime();
        }
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        if (startDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -3);
            startDate = cal.getTime();
        }
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Transactions() {
    }
    
    public String showUser(){
        String fc=FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return fc;
    }
    
}
