package paygate.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import paygate.objects.util.JsfUtil;
import paygate.objects.util.PaginationHelper;

@ManagedBean(name = "merchantController")
@SessionScoped
public class MerchantController implements Serializable {

    EntityManagerFactory emf;
    EntityManager em;
    private Merchant current;
    private DataModel items = null;
    @EJB
    private paygate.objects.MerchantFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public MerchantController() {
    }

    public Merchant getSelected() {
        if (current == null) {
            current = new Merchant();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MerchantFacade getFacade() {
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
        current = (Merchant) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Merchant();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            if (current.getPassword().length() > 0) {
                byte[] data = current.getPassword().getBytes();
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    String result = new BigInteger(1, md.digest(data)).toString(16);
                    current.setPassword(result);
                } catch (NoSuchAlgorithmException e) {
                }
            }
            Date date = new Date();
            current.setMerchantidentifier("904904904904904");
            current.setCreated(date);
            current.setModified(date);
            current.setToken("Paysure@Ltd");
            current.setKey("key");
            
            System.out.println("Name is :"+current.getName());
            System.out.println("City is :"+current.getCity());
            System.out.println("Street is :"+current.getStreet());
            System.out.println("State is :"+current.getState());
            System.out.println("Username is :"+current.getUsername());
            System.out.println("Password is :"+current.getPassword());
            System.out.println("Merchant Identifier is :"+current.getMerchantidentifier());
            System.out.println("Created Date is :"+current.getCreated());
            System.out.println("Token is :"+current.getToken());
            System.out.println("Key is :"+current.getKey());
            System.out.println("Postcode is :"+current.getPostcode());
            System.out.println("Url is :"+current.getUrl());
            System.out.println("Return Url is :"+current.getReturnurl());
            System.out.println("Rate is :"+current.getRateid().getMerchantrate());
            
            System.out.println("Usd Condition is :"+current.getUsd());
            
            
            
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MerchantCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Merchant) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MerchantUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Merchant) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MerchantDeleted"));
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

    @FacesConverter(forClass = Merchant.class)
    public static class MerchantControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MerchantController controller = (MerchantController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "merchantController");
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
            if (object instanceof Merchant) {
                Merchant o = (Merchant) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + MerchantController.class.getName());
            }
        }
    }

    public List<Merchant> getThem() {
        emf = Persistence.createEntityManagerFactory("adminPU");
        em = emf.createEntityManager();
        List<Merchant> l = (List<Merchant>) em.createQuery("select m from Merchant m order by m.id asc").getResultList();
        return l;
    }
}
