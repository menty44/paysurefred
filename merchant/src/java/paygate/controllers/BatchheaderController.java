package paygate.controllers;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import org.primefaces.event.CellEditEvent;
//import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import paygate.controllers.util.JsfUtil;
import paygate.controllers.util.PaginationHelper;
import paygate.objects.*;
import paygate.utils.Email;

@ManagedBean(name = "batchheaderController")
@SessionScoped
public class BatchheaderController implements Serializable {

    @EJB
    private BatchitemFacade batchitemFacade;
    private Batchheader current;
    public List<Batchheader> headers;
    private DataModel items = null;
    @EJB
    private paygate.controllers.BatchheaderFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private UploadedFile uploadedFile;
    private String username;
    private int merchantid;
    private int index;
    private int numberofemails;
    //private Integer progress=0;
    private float progress = 0;
    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private int idd;
    private Batchheader header;
    private int headerid;
    @ManagedProperty(value = "#{showItems}")
    private ShowItems showItems;
    //Custom model properties starts here
    private List<Batchitem> batchitems;
    private Batchitem currentt;
    private ListDataModel batchitemsModel;
    private Integer noOfRows = 10;
    private Integer firstRowIndex = 0;
    private int key;
    private Integer merchantidd = 23;
    private int bid;
    private String bname;
    private String bhname;
    private String bdesc;
    private String curr;
    private UploadedFile file;
    private String status;
    private List<Batchheader> hedit;

    /*
     * public Integer getProgress() { return progress; }
     *
     * public void setProgress(Integer progress) { this.progress = progress; }
     */
    
    @PostConstruct
    public void init(){
        //this.headers = em.createNamedQuery("Batchheader.findAll").getResultList();
        //Merchant merchant = (Merchant) em.createNamedQuery("Merchant.findByUsername").setParameter("username", username).getSingleResult();
        //this.headers = (List<Batchheader>) em.createNamedQuery("Batchheader.findByMerchantid").setParameter("merchantid", merchant).getSingleResult();
        this.username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }
    
    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getStatus() {
        this.status = "true";
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
        
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public BatchitemFacade getBatchitemFacade() {
        return batchitemFacade;
    }

    //Custom model properties end
    //Custom model methods starts here
    public Integer getMerchantidd() {
        merchantid = new Users().getMerchant().getId();
        return merchantid;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<Batchheader> getHedit() {
        return hedit;
    }

    public void setHedit(List<Batchheader> hedit) {
        this.hedit = hedit;
    }

    public List<Batchheader> getHeaders() {
        Merchant merchant = (Merchant) em.createNamedQuery("Merchant.findByUsername").setParameter("username", username).getSingleResult();
        //headers = em.createNamedQuery("Batchheader.findAll").getResultList();
        headers = (List<Batchheader>) em.createNamedQuery("Batchheader.findByMerchantid").setParameter("merchantid", merchant).getResultList();
        return headers;
    }

    public void setHeaders(List<Batchheader> headers) {
        //List<Batchheader> hds = em.createNamedQuery("Batchheader.findAll").getResultList();
        this.headers = headers;
    }

    public void setMerchantidd(Integer merchantid) {
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

        System.out.println("Header ID:=>" + getBid());
        int value = getSelected().getId();
        System.out.println("Selected Value=>" + value);
        batchitems = getBatchitemFacade().findThem(value);
        System.out.println("BatchItemsNumber:=>" + batchitems.size());
        return batchitems;
    }

    public void setBatchitems(List<Batchitem> batchitems) {
        this.batchitems = batchitems;
    }

    /*
     * public ShowItems() { }
     */
    public String showUser() {
        String fc = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return fc;
    }

    //Custom model methods end here
    public ShowItems getShowItems() {
        return showItems;
    }

    public void setShowItems(ShowItems showItems) {
        this.showItems = showItems;
    }

    /*
     * public List<Batchitem> show() throws NotSupportedException,
     * SystemException, RollbackException, HeuristicMixedException,
     * HeuristicRollbackException{ return getShowItems().getBatchitems(); }
     */
    /*
     * private Integer progress; * public Integer getProgress() { if(progress ==
     * null) progress = 0; else { progress = progress + (int)(Math.random() *
     * 35); * if(progress > 100) progress = 100; } * return progress; } * public
     * void setProgress(Integer progress) { this.progress = progress; } * public
     * void onComplete() { FacesContext.getCurrentInstance().addMessage(null,
     * new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed",
     * "Progress Completed")); } * public void cancel() { progress = null; }
     */
    public int getNumberofemails() {
        return numberofemails;
    }

    public void setNumberofemails(int numberofemails) {
        this.numberofemails = numberofemails;
    }

    public int getMerchantid() {
        return merchantid;
    }

    public String getUsername() {
        username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    // Bulk Emailing Method
    public String savePurchaseFromItem() throws NamingException, NotSupportedException, SystemException {
        username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        //Batchitem batchItem = (Batchitem) em.createNamedQuery("Batchitem.findById").setParameter("id", 7).getSingleResult();
        //email = batchItem.getEmail();
        //int key=current.getId();  
        int key = getIndex();
        List lst = em.createNamedQuery("Batchitem.filtered").setParameter("key", key).getResultList();
        numberofemails = lst.size();
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
            paygate.objects.Status status = (paygate.objects.Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
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
            purchase.setCurrency(batchItem.getCurrency());
            BigInteger bi = batchItem.getAmount();
            String val = bi.toString();
            int amt = Integer.parseInt(val);
            purchase.setAmount(amt);

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
                progress = progress + 30;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        progress = 100;
        return "List";
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("File Successfully Uploaded");
    }

    public BatchheaderController() {
    }

    public String goToEmail() {

        return "Email?faces-redirect=true";
    }

    public void email() {
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Batchheader getSelected() {
        if (current == null) {
            current = new Batchheader();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BatchheaderFacade getFacade() {
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
        return "List?faces-redirect=true";
    }

    public String prepareView() {
        current = (Batchheader) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Batchheader();
        selectedItemIndex = -1;
        return "Create?faces-redirect=true";
    }

    public String create() {
        try {
            Date date = new Date();
            current.setDate(date);
            current.setStatus("open");
            current.setRequests(0);
            //current.setDescription("description");
            String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            Siteuser siteuser = (Siteuser) em.createNamedQuery("Siteuser.findByUsername").setParameter("username", user).getSingleResult();
            Merchant merchant = siteuser.getMerchant();
            current.setMerchantid(merchant);

            if (!merchant.isUsd()) {
                System.out.println("Merchant No USD");
                current.setCurrency("KES");
            } else {
                System.out.println("Merchant Has USD");
            }
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BatchheaderCreated"));
            //return prepareCreate();
            //return prepareList();
            //return "List.xhtml?faces-redirect=true";
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String dis() {
        //current = (Batchheader) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Pass";
    }

    public String upload() {
        current = (Batchheader) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Upload?faces-redirect=true";
    }

    public String upload2() {
        current = (Batchheader) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        //bid=selectedItemIndex;
        bid = current.getId();
        System.out.println("Value is:=>" + bid);
        return "input?faces-redirect=true";
    }

    /*
     * public String submit() throws IOException, NotSupportedException,
     * SystemException, RollbackException, HeuristicMixedException,
     * HeuristicRollbackException { Batchitem batchitem = new Batchitem(); int
     * count = 0; Map<String, String> params =
     * FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
     * index = Integer.parseInt(params.get("index")); InputStream is = new
     * ByteArrayInputStream(uploadedFile.getBytes()); BufferedReader br = new
     * BufferedReader(new InputStreamReader(is)); String line; while ((line =
     * br.readLine()) != null) { StringTokenizer st = new StringTokenizer(line,
     * ","); batchitem.setBatchheaderId(current);
     * batchitem.setCurrency(current.getCurrency());
     * batchitem.setClientName(st.nextToken());
     * batchitem.setEmail(st.nextToken()); //BigDecimal bd=new
     * BigDecimal(st.nextToken()); //BigInteger bi=bd.toBigInteger();
     * //batchitem.setAmount(bi); String val = st.nextToken(); if
     * (val.contains(".")) {
     * //batchitem.setAmount(Integer.valueOf(val.replaceAll("\\.", "")));
     * BigInteger bi = new BigInteger(val.replaceAll("\\.", ""));
     * batchitem.setAmount(bi); } else { BigInteger bi = new
     * BigInteger(val.replaceAll("\\.", "")).multiply(BigInteger.valueOf(100));
     * batchitem.setAmount(bi); }
     *
     * batchitem.setDescription(st.nextToken()); utx.begin();
     * em.persist(batchitem); utx.commit(); count++; } //updateRequest();
     * utx.begin(); current.setRequests(count); em.merge(current); utx.commit();
     * //prepareList(); return "List?faces-redirect=true"; //return "show"; }
     */
    public String view() {
        current = (Batchheader) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        System.out.println(current.getId());
        System.out.println(selectedItemIndex);
        return "show?faces-redirect=true";
    }

    public String prepareEdit() {
        current = (Batchheader) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit?faces-redirect=true";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BatchheaderUpdated"));
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Batchheader) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List?faces-redirect=true";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View?faces-redirect=true";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List?faces-redirect=true";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BatchheaderDeleted"));
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
        return "List?faces-redirect=true";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List?faces-redirect=true";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        //return JsfUtil.getSelectItems(ejbFacade.show(), true);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        //return JsfUtil.getSelectItems(ejbFacade.show(), true);
    }

    /*
     * public List<Batchheader> show(){ String username =
     * FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
     * Siteuser siteuser = (Siteuser)
     * em.createNamedQuery("Siteuser.findByUsername").setParameter("username",
     * FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
     * headers = (List<Batchheader>)
     * em.createNamedQuery("Batchheader.findall").setParameter("mid",
     * siteuser.getMerchant()); return headers; }
     */
    @FacesConverter(forClass = Batchheader.class)
    public static class BatchheaderControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchheaderController controller = (BatchheaderController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchheaderController");
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
            if (object instanceof Batchheader) {
                Batchheader o = (Batchheader) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + BatchheaderController.class.getName());
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

    /*
     * public String move(){ try { current=(Batchheader)
     * getItems().getRowData(); bid=current.getId();
     * bname=current.getBatchname(); showItems.getBatchitems(); } catch
     * (NotSupportedException ex) {
     * Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE,
     * null, ex); } catch (SystemException ex) {
     * Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE,
     * null, ex); } catch (RollbackException ex) {
     * Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE,
     * null, ex); } catch (HeuristicMixedException ex) {
     * Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE,
     * null, ex); } catch (HeuristicRollbackException ex) {
     * Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE,
     * null, ex); } return "input"; }
     */
    public Batchheader getHeader() {
        return header;
    }

    public void setHeader(Batchheader header) {
        this.header = header;
    }

    public int getHeaderid() {
        return headerid;
    }

    public void setHeaderid(int headerid) {
        this.headerid = headerid;
        header = em.find(Batchheader.class, this.headerid);
        hedit = em.createNamedQuery("Batchheader.findById").setParameter("id", this.headerid).getResultList();
        System.out.println("Header Id Set To : " + this.headerid);
        System.out.println("Header Is Set To : " + this.header);
    }

    public void markHeader(ActionEvent event) {
    }

    public org.primefaces.model.UploadedFile getFile() {
        return file;
    }

    public void setFile(org.primefaces.model.UploadedFile file) {
        this.file = file;
    }

    public void uplodefile(ActionEvent event) {
        System.out.println("File Is : " + file.getFileName());
        System.out.println("File Details : " + new String(file.getContents()));
    }

    public void handle2FileUpload(FileUploadEvent event) {

        UploadedFile file = (UploadedFile) event.getFile();
        //System.out.println("File Details Are : "+file.getName());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("File Details : " + event.getFile().getFileName());
    }

    public void primeFileUpload(FileUploadEvent event) throws IOException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        UploadedFile uf = event.getFile();
        System.out.println("Called....");
        System.out.println("File Details : " + new String(uf.getContents()));
        System.out.println("Header Id is : " + headerid);

        int count = 0;
        Batchitem batchitem = new Batchitem();
        InputStream is = new ByteArrayInputStream(uf.getContents());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            batchitem.setBatchheaderId(header);
            batchitem.setCurrency(header.getCurrency());
            batchitem.setClientName(st.nextToken());
            batchitem.setEmail(st.nextToken());
            //BigDecimal bd=new BigDecimal(st.nextToken());
            //BigInteger bi=bd.toBigInteger();
            //batchitem.setAmount(bi); 
            String val = st.nextToken();
            if (val.contains(".")) {
                //batchitem.setAmount(Integer.valueOf(val.replaceAll("\\.", "")));
                BigInteger bi = new BigInteger(val.replaceAll("\\.", ""));
                batchitem.setAmount(bi);
            } else {
                BigInteger bi = new BigInteger(val.replaceAll("\\.", "")).multiply(BigInteger.valueOf(100));
                batchitem.setAmount(bi);
            }

            batchitem.setDescription(st.nextToken());
            utx.begin();
            em.persist(batchitem);
            utx.commit();
            //System.out.println("Created Batch Item : "+batchitem.getClientName());            
            count++;
        }
        //updateRequest();
        utx.begin();
        header.setRequests(count);
        em.merge(header);
        utx.commit();
    }

    public void primeFileUpload2(FileUploadEvent event) {

        try {
            UploadedFile uf = event.getFile();
            System.out.println("Called....");
            System.out.println("File Details : " + new String(uf.getContents()));
            System.out.println("Header Id is : " + headerid);

            int count = 0;
            Batchitem batchitem = new Batchitem();
            InputStream is = new ByteArrayInputStream(uf.getContents());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                batchitem.setBatchheaderId(header);
                batchitem.setCurrency(header.getCurrency());
                batchitem.setClientName(st.nextToken());
                batchitem.setEmail(st.nextToken());
                //BigDecimal bd=new BigDecimal(st.nextToken());
                //BigInteger bi=bd.toBigInteger();
                //batchitem.setAmount(bi); 
                String val = st.nextToken();
                if (val.contains(".")) {
                    //batchitem.setAmount(Integer.valueOf(val.replaceAll("\\.", "")));
                    BigInteger bi = new BigInteger(val.replaceAll("\\.", ""));
                    batchitem.setAmount(bi);
                } else {
                    BigInteger bi = new BigInteger(val.replaceAll("\\.", "")).multiply(BigInteger.valueOf(100));
                    batchitem.setAmount(bi);
                }

                batchitem.setDescription(st.nextToken());
                utx.begin();
                em.persist(batchitem);
                utx.commit();
                //System.out.println("Created Batch Item : "+batchitem.getClientName());            
                count++;
            }

            utx.begin();
            header.setRequests(count);
            em.merge(header);
            em.flush();
            utx.commit();

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().toString(), e.getMessage().toString());
            FacesContext.getCurrentInstance().addMessage("somekey", facesMsg);
            //e.getMessage().toString();
        }





    }

    public String sendMails() {

        System.out.println("Id to Use Is : " + this.headerid);

        System.out.println("Header Has been Set to : " + this.header);

        List<Batchitem> list = em.createNamedQuery("Batchitem.filtered").setParameter("key", this.headerid).getResultList();

        System.out.println("Emails to be Sent : " + list.size() + " At Time : " + new Date());

        Iterator it = list.iterator();
        while (it.hasNext()) {
            Batchitem batchItem = (Batchitem) it.next();
            Merchant merchant = (Merchant) em.createNamedQuery("Merchant.findByUsername").setParameter("username", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getSingleResult();
            Purchase purchase = new Purchase();
            // Start building the purchase object from a batchitem record
            purchase.setMerchantid(merchant);
            purchase.setRecievinginstitution(merchant.getId().toString());
            Transactiontype transactiontype = new Transactiontype();
            transactiontype.setId(1);
            purchase.setTransactiontype(transactiontype);
            paygate.objects.Status status = (paygate.objects.Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
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
            purchase.setCurrency(batchItem.getCurrency());
            BigInteger bi = batchItem.getAmount();
            String val = bi.toString();
            int amt = Integer.parseInt(val);
            purchase.setAmount(amt);

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

            Email email = new Email();
            try {
                email.sendEmail(purchase);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Email Sent", "Email Sent Successfully to : " + purchase.getEmail()));
                //progress += 1;
            } catch (UnsupportedEncodingException ex) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Email Error", "Problem Sending Email to Address : " + purchase.getEmail()));
                Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            int num = list.size();
            //progress += 30;
            progress += 100 / num;
        }
        progress = 100;
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "All Emails Have Been Sent"));
        System.out.println("Emails Sent...Yipee....Time All Sent : " + new Date());
        try {
            utx.begin();
        } catch (NotSupportedException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        header.setStatus("closed");
        header.setEmailstatus(Boolean.TRUE);
        em.merge(header);
        em.flush();
        try {
            utx.commit();
        } catch (RollbackException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(BatchheaderController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        Merchant merchant = (Merchant) em.createNamedQuery("Merchant.findByUsername").setParameter("username", username).getSingleResult();
        //headers = em.createNamedQuery("Batchheader.findAll").getResultList();
        headers = (List<Batchheader>) em.createNamedQuery("Batchheader.findByMerchantid").setParameter("merchantid", merchant).getResultList();
        return "Header?faces-redirect=true";
        //return null;
    }

    public String getBdesc() {
        return bdesc;
    }

    public void setBdesc(String bdesc) {
        this.bdesc = bdesc;
    }

    public String getBhname() {
        return bhname;
    }

    public void setBhname(String bhname) {
        this.bhname = bhname;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String saveBatch() throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

        System.out.println("Has Been Called Well : " + bhname);

        Batchheader batchheader = new Batchheader();
        batchheader.setBatchname(bhname);
        batchheader.setDescription(bdesc);
        batchheader.setCurrency(curr);
        batchheader.setDate(new Date());
        batchheader.setRequests(0);
        batchheader.setStatus("open");
        //batchheader.setMerchantid(em.find(Merchant.class, merchantidd));
        Merchant merchant = (Merchant) em.createNamedQuery("Merchant.findByUsername").setParameter("username", username).getSingleResult();
        batchheader.setMerchantid(merchant);
        batchheader.setEmailstatus(Boolean.FALSE);

        utx.begin();
        em.persist(batchheader);
        utx.commit();

        return "Header?faces-redirect=true";
    }

    public List<Batchitem> getBatchHeaderItems() {
        int key = headerid;
        return em.createNamedQuery("Batchitem.filtered").setParameter("key", key).getResultList();
    }
    
    public String goBack(){
        return "Header.xhtml?faces-redirect=true";
    }
    
    public void cellEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
          
        if(newValue != null && !newValue.equals(oldValue)) {  
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);  
            System.out.println("Cell Changed : "+oldValue+ " New Value : "+newValue);            
            FacesContext.getCurrentInstance().addMessage(null, msg);  
        }          
    }  
    
    public String updateHeader(){
        
        System.out.println("New Header Name : "+header.getBatchname());
        System.out.println("New Header Description : "+header.getDescription());
        System.out.println("New Header Id : "+header.getId());        
        try{
            utx.begin();
            //em.persist(header);
            em.merge(header);
            em.flush();
            utx.commit();            
        }catch(Exception e){
            e.printStackTrace();
        }        
        return "Header.xhtml?faces-redirect=true";
    }
    
}
