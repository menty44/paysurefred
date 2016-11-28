package paygate.objects;

import paygate.objects.Rate;
import paygate.objects.util.JsfUtil;
import paygate.objects.util.PaginationHelper;
import paygate.objects.RateFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
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

@ManagedBean(name = "rateController")
@SessionScoped
public class RateController implements Serializable {
    
    private Rate current;
    private DataModel items = null;
    @EJB
    private paygate.objects.RateFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String pattern;
    private String value;
    
    public RateController() {
    }
    
    public Rate getSelected() {
        if (current == null) {
            current = new Rate();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    private RateFacade getFacade() {
        return ejbFacade;
    }

    /*
     * private void populateRate(List<Rate> list, int size) { for(int i = 0 ; i
     * < size ; i++) list.add(new Rate(getId(), getMerchantrate(),
     * getPaysurerate(), getCreated(), getModified())); }
     */
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(1000) {
                
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
        current = (Rate) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public String prepareCreate() {
        current = new Rate();
        selectedItemIndex = -1;
        return "Create";
    }
    
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RateCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String prepareEdit() {
        current = (Rate) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RateUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String destroy() {
        current = (Rate) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RateDeleted"));
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
        /*
         * DecimalFormat decimalFormat = new DecimalFormat();
         * decimalFormat.setMinimumIntegerDigits(3);
         * System.err.println(decimalFormat.format(2));
         */
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
        List<Rate> rateList = ejbFacade.findAll();
        SelectItem[] rates = new SelectItem[rateList.size()];
        Iterator i = rateList.iterator();
        int x = 0;
        while (i.hasNext()) {
            Rate rate = (Rate) i.next();
            rates[x++] = new SelectItem(rate, rate.getId().toString());
        }
        return rates;
//        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    @FacesConverter(forClass = Rate.class)
    public static class RateControllerConverter implements Converter {
        
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RateController controller = (RateController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rateController");
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
            if (object instanceof Rate) {
                Rate o = (Rate) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RateController.class.getName());
            }
        }
    }
    
    public void GetTwoDecimal() {
        pattern = "###.0000";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        System.out.println(value + " " + pattern + " " + output);
        //BigDecimal payment = new BigDecimal("1115.37");
        //System.out.println(payment.toString());
        //double d = 2.34568;
        // DecimalFormat f = new DecimalFormat("##.00");  // this will helps you to always keeps in two decimal places
        // System.out.println(f.format(d)); 
    }
}
