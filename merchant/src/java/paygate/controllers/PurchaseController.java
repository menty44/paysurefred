package paygate.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import paygate.controllers.util.JsfUtil;
import paygate.controllers.util.PaginationHelper;
import paygate.objects.*;
import paygate.utils.Email;

@ManagedBean(name = "purchaseController")
@SessionScoped
public class PurchaseController implements Serializable {

    private Purchase current;
    private DataModel items = null;
    @EJB
    private paygate.controllers.PurchaseFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String value;
    private String currency;

    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
                
    public PurchaseController() {
    }
    
    public Purchase getSelected() {
        if (current == null) {
            current = new Purchase();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PurchaseFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Purchase) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Purchase();
        selectedItemIndex = -1;
        return "Create";
    }
    
    public String createPurc(){
        
        System.out.println("Called..");
        return null;
    }

    public String create() {
        try {
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();

            current.setMerchantid(siteuser.getMerchant());
            current.setRecievinginstitution(siteuser.getMerchant().getId().toString());
            Transactiontype transactiontype = new Transactiontype();
            transactiontype.setId(1);
            current.setTransactiontype(transactiontype);
            //Status status = (Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
            //current.setStatusid(status);
            current.setToken("paysure");
            current.setSecret("secret");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
            current.setTransmission(sdf.format(date));
            current.setPurchasedate(date);
            current.setPaysuredate(date);
            
            if(value.contains(".")){
                current.setAmount(Integer.valueOf(value.replaceAll("\\.", "")));
            }else{
                current.setAmount(Integer.valueOf(value.replaceAll("\\.", ""))*100);
            }               
            /*if(currency.compareTo("")==0){
                currency="KES";
            } else{
                current.setCurrency(currency);
            }*/
            
            if(!siteuser.getMerchant().isUsd()){
                current.setCurrency("KES");
            }else{
                current.setCurrency(currency);
            }            
            
            Transactionsource transactionsource = new Transactionsource();
            transactionsource.setId(2);
            current.setTransactionsourceid(transactionsource);

            //List<Purchase> pp = (List<Purchase>) em.createNamedQuery("Purchase.list").setParameter("clientname", siteuser.getMerchant()).getResultList();

            List<Purchase> purchaseList = (List<Purchase>) em.createNamedQuery("Purchase.findLatest").setParameter("merchantid", siteuser.getMerchant()).setParameter("transactionsourceid", transactionsource).setMaxResults(1).getResultList();
            String referenceno = "";
            int systemtraceno;

            if (purchaseList.size() > 0) {
                Iterator i = purchaseList.iterator();
                Purchase purchase = (Purchase) i.next();
                systemtraceno = Integer.valueOf(purchase.getSystemtraceno());
                referenceno = String.valueOf(siteuser.getMerchant().getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", String.valueOf(Integer.valueOf(purchase.getSystemtraceno()) + 1)).replaceAll(" ", "0");

                current.setSystemtraceno(String.format("%1$" + 6 + "s", String.valueOf(Integer.valueOf(purchase.getSystemtraceno()) + 1)).replaceAll(" ", "0"));
                current.setReferenceno(referenceno);


            } else {
                referenceno = String.valueOf(siteuser.getMerchant().getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", "1").replaceAll(" ", "0");
                current.setReferenceno(referenceno);
                current.setSystemtraceno(String.format("%1$" + 6 + "s", "1").replaceAll(" ", "0"));

            }
            getFacade().create(current);
            Email email = new Email();
            email.sendEmail(current);
            
            System.out.println(current.getId());
            System.out.println(current.getClientname());
            System.out.println(current.getCurrency());
            //System.out.println(current.getId());
            //System.out.println(current.getId());
            
            
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PurchaseCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Purchase) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PurchaseUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Purchase) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PurchaseDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Purchase.class)
    public static class PurchaseControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PurchaseController controller = (PurchaseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "purchaseController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Purchase) {
                Purchase o = (Purchase) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PurchaseController.class.getName());
            }
        }
    }

    public List<Purchase> getpurchases() throws NamingException {
        Context ctx = (Context) new InitialContext().lookup("java:comp/env");
        EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");

        Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();

        List<Purchase> p = em.createQuery("select p from Purchase p where p.merchantid.id= :value").setParameter("value", siteuser.getMerchant().getId()).getResultList();
        return p;
    }    
 
}
