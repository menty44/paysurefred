package paygate.controllers;

import java.io.*;
import paygate.objects.Batchitem;
import paygate.controllers.util.JsfUtil;
import paygate.controllers.util.PaginationHelper;
import paygate.controllers.BatchitemFacade;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import paygate.objects.*;
import paygate.objects.Status;
import paygate.utils.Email;

@ManagedBean(name = "batchitemController")
@SessionScoped
public class BatchitemController implements Serializable {
    @EJB
    private BatchheaderFacade batchheaderFacade;
    @EJB
    private Bean bean;
        
    private Batchitem current;
    private DataModel items = null;
    @EJB
    private paygate.controllers.BatchitemFacade ejbFacade;    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    //private UploadedFile uploadedFile;
    private String username;
    private String email;
    private int merchantid;
    private int count;
    private Batchheader bh;
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
        
    public BatchheaderFacade getBatchheaderFacade() {
        return batchheaderFacade;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
        
    public Batchheader getBh() {
        return bh;
    }

    public void setBh(Batchheader bh) {
        this.bh = bh;
    }
        
    public int getMerchantid() {
        return merchantid;
    }

    public String getEmail() {
        return email;
    }
   
    public String getUsername() {
        return username;
    }

    public BatchitemController() {
    }

    public Batchitem getSelected() {
        if (current == null) {
            current = new Batchitem();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BatchitemFacade getFacade() {
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
        current = (Batchitem) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Batchitem();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
                       
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BatchitemCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void savePurchaseFromItem() throws NamingException, NotSupportedException, SystemException {
        username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        //Batchitem batchItem = (Batchitem) em.createNamedQuery("Batchitem.findById").setParameter("id", 7).getSingleResult();
        //email = batchItem.getEmail();

        List lst = em.createNamedQuery("Batchitem.findAll").getResultList();
        Iterator it = lst.iterator();
        while (it.hasNext()) {
            Batchitem batchItem = (Batchitem) it.next();

            Merchant merchant = (Merchant) em.createNamedQuery("Merchant.findByUsername").setParameter("username", username).getSingleResult();
            merchantid = merchant.getId();
            Purchase purchase = new Purchase();
            // Start building the purchase object from a batchitem record
            purchase.setMerchantid(merchant);
            purchase.setRecievinginstitution(merchant.getId().toString());
            Transactiontype transactiontype = new Transactiontype();
            transactiontype.setId(1);
            purchase.setTransactiontype(transactiontype);
            Status status = (Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
            purchase.setStatusid(status);
            purchase.setToken("paysure");
            purchase.setSecret("secret");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
            purchase.setTransmission(sdf.format(date));
            purchase.setPurchasedate(date);
            purchase.setPaysuredate(date);
            Transactionsource transactionsource = new Transactionsource();
            transactionsource.setId(2);
            purchase.setTransactionsourceid(transactionsource);

            //from item record
            purchase.setClientname(batchItem.getClientName());
            purchase.setEmail(batchItem.getEmail());
            purchase.setDescription(batchItem.getDescription());
            purchase.setAmount(345);

            List<Purchase> purchaseList = (List<Purchase>) em.createNamedQuery("Purchase.findLatest").setParameter("merchantid", merchant).setParameter("transactionsourceid", transactionsource).setMaxResults(1).getResultList();
            String referenceno = "";
            int systemtraceno;

            if (purchaseList.size() > 0) {
                Iterator i = purchaseList.iterator();
                Purchase p = (Purchase) i.next();
                systemtraceno = Integer.valueOf(p.getSystemtraceno());
                referenceno = String.valueOf(merchant.getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", String.valueOf(Integer.valueOf(p.getSystemtraceno()) + 1)).replaceAll(" ", "0");

                purchase.setSystemtraceno(String.format("%1$" + 6 + "s", String.valueOf(Integer.valueOf(p.getSystemtraceno()) + 1)).replaceAll(" ", "0"));
                purchase.setReferenceno(referenceno);

            } else {
                referenceno = String.valueOf(merchant.getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", "1").replaceAll(" ", "0");
                purchase.setReferenceno(referenceno);
                purchase.setSystemtraceno(String.format("%1$" + 6 + "s", "1").replaceAll(" ", "0"));

            }

            utx.begin();
            em.persist(purchase);
            try {
                utx.commit();
            } catch (RollbackException ex) {
                Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (HeuristicMixedException ex) {
                Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (HeuristicRollbackException ex) {
                Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Email email = new Email();
            try {
                email.sendEmail(purchase);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
         *
         *
         * Merchant merchant = (Merchant)
         * em.createNamedQuery("Merchant.findByUsername").setParameter("username",
         * username).getSingleResult(); merchantid = merchant.getId(); Purchase
         * purchase = new Purchase(); // Start building the purchase object from
         * a batchitem record purchase.setMerchantid(merchant);
         * purchase.setRecievinginstitution(merchant.getId().toString());
         * Transactiontype transactiontype = new Transactiontype();
         * transactiontype.setId(1);
         * purchase.setTransactiontype(transactiontype); Status status =
         * (Status) em.createNamedQuery("Status.findById").setParameter("id",
         * 11).getSingleResult(); purchase.setStatusid(status);
         * purchase.setToken("paysure"); purchase.setSecret("secret"); Date date
         * = new Date(); SimpleDateFormat sdf = new
         * SimpleDateFormat("MMddHHmmss");
         * purchase.setTransmission(sdf.format(date));
         * purchase.setPurchasedate(date); purchase.setPaysuredate(date);
         * Transactionsource transactionsource = new Transactionsource();
         * transactionsource.setId(2);
         * purchase.setTransactionsourceid(transactionsource);
         *
         * //from item record
         * purchase.setClientname(batchItem.getClientName());
         * purchase.setEmail(batchItem.getEmail());
         * purchase.setDescription(batchItem.getDescription());
         * purchase.setAmount(345); * List<Purchase> purchaseList =
         * (List<Purchase>)
         * em.createNamedQuery("Purchase.findLatest").setParameter("merchantid",
         * merchant).setParameter("transactionsourceid",
         * transactionsource).setMaxResults(1).getResultList(); String
         * referenceno = ""; int systemtraceno;
         *
         * if (purchaseList.size() > 0) { Iterator i = purchaseList.iterator();
         * Purchase p = (Purchase) i.next(); systemtraceno =
         * Integer.valueOf(p.getSystemtraceno()); referenceno =
         * String.valueOf(merchant.getId()) + "R"; referenceno = referenceno +
         * String.format("%1$" + (12 - referenceno.length()) + "s",
         * String.valueOf(Integer.valueOf(p.getSystemtraceno()) +
         * 1)).replaceAll(" ", "0");
         *
         * purchase.setSystemtraceno(String.format("%1$" + 6 + "s",
         * String.valueOf(Integer.valueOf(p.getSystemtraceno()) +
         * 1)).replaceAll(" ", "0")); purchase.setReferenceno(referenceno);
         *
         * } else { referenceno = String.valueOf(merchant.getId()) + "R";
         * referenceno = referenceno + String.format("%1$" + (12 -
         * referenceno.length()) + "s", "1").replaceAll(" ", "0");
         * purchase.setReferenceno(referenceno);
         * purchase.setSystemtraceno(String.format("%1$" + 6 + "s",
         * "1").replaceAll(" ", "0"));
         *
         * }
         *
         * utx.begin(); em.persist(purchase); try { utx.commit(); } catch
         * (RollbackException ex) {
         * Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE,
         * null, ex); } catch (HeuristicMixedException ex) {
         * Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE,
         * null, ex); } catch (HeuristicRollbackException ex) {
         * Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE,
         * null, ex); } catch (SecurityException ex) {
         * Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE,
         * null, ex); } catch (IllegalStateException ex) {
         * Logger.getLogger(BatchitemController.class.getName()).log(Level.SEVERE,
         * null, ex); }
         *
         * Email email=new Email(); email.sendEmail(purchase);
         *
         *
         */

        //return "Show";

    }

    public String prepareEdit() {
        current = (Batchitem) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BatchitemUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Batchitem) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BatchitemDeleted"));
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

    @FacesConverter(forClass = Batchitem.class)
    public static class BatchitemControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchitemController controller = (BatchitemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchitemController");
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
            if (object instanceof Batchitem) {
                Batchitem o = (Batchitem) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + BatchitemController.class.getName());
            }
        }
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
