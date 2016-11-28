package service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.WebServiceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import paygate.objects.*;
import utils.Helper;

**
 *
 * @author Joseph
 */

@WebService(serviceName = "onlinepay")
public class Onlinepay {

    @EJB
    private Helper helper;
    @PersistenceContext(unitName = "webdirectPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    @Resource
    WebServiceContext context;

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        //redirect();
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    //Webservice method for receiving shopping cart details
    @WebMethod(operationName = "pickData")
    public String pickData(@WebParam(name = "data") String data, @WebParam(name = "dataitems") String dataitems) {

        String url = "";
        try {
            Purchase p = new Purchase();

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(data));
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("purchase");
            utx.begin();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String cardtype = getTagValue("cardtype", eElement);
                    String mname = getTagValue("mname", eElement);
                    Merchant m = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mname).getSingleResult();
                    p.setMerchantid(m);
                    p.setRecievinginstitution(m.getId().toString());
                    Transactiontype transactiontype = new Transactiontype();
                    transactiontype.setId(1);
                    p.setTransactiontype(transactiontype);
                    paygate.objects.Status status = (paygate.objects.Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
                    p.setStatusid(status);
                    p.setToken("paysure");
                    p.setSecret("secret");
                    p.setReferenceno(getTagValue("refno", eElement));
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
                    StringBuilder tran = new StringBuilder(sdf.format(date));
                    String transmission = tran.toString();
                    p.setTransmission(transmission);
                    p.setPurchasedate(date);
                    p.setPaysuredate(date);
                    Transactionsource transactionsource = new Transactionsource();
                    transactionsource.setId(1);
                    p.setTransactionsourceid(transactionsource);
                    //p.setSystemtraceno(getTagValue("systemtraceno", eElement));
                    List<Purchase> purchaseList = (List<Purchase>) em.createNamedQuery("Purchase.findLatest").setParameter("merchantid", m).setParameter("transactionsourceid", transactionsource).setMaxResults(1).getResultList();
                    if (purchaseList.size() > 0) {
                        Iterator itr = purchaseList.iterator();
                        Purchase purchase = (Purchase) itr.next();
                        p.setSystemtraceno(String.format("%1$" + 6 + "s", String.valueOf(Integer.valueOf(purchase.getSystemtraceno()) + 1)).replaceAll(" ", "0"));
                    } else {
                        p.setSystemtraceno(String.format("%1$" + 6 + "s", "1").replaceAll(" ", "0"));
                    }

                    p.setDescription(getTagValue("description", eElement));

                    String s = getTagValue("amount", eElement);
                    BigDecimal bd = new BigDecimal(s);
                    BigDecimal bd1 = bd.movePointRight(2);
                    Integer amount = Integer.valueOf(bd1.intValue());
                    p.setAmount(amount);
                    //p.setAmount(Integer.parseInt(getTagValue("amount", eElement)));
                    p.setClientname(getTagValue("buyer", eElement));
                    p.setEmail(getTagValue("email", eElement));
                    p.setCurrency(getTagValue("currency", eElement));

                    //look for a purchase first
                /*
                     * Purchase pfound=(Purchase)
                     * em.createNamedQuery("Purchase.findByReferenceno").setParameter("referenceno",
                     * getTagValue("refno", eElement)).getSingleResult();
                     * if(pfound.getReferenceno().compareTo(getTagValue("refno",
                     * eElement))==0){ em.remove(pfound); }
                     */

                    //List results= (List) em.createNamedQuery("Purchase.findByReferenceno").setParameter("referenceno", getTagValue("refno", eElement)).getSingleResult();

                    List results = helper.findP(getTagValue("refno", eElement));

                    if (results.isEmpty()) {
                        em.persist(p);
                    } else if (results.size() == 1) {
                        Purchase pn = (Purchase) results.get(0);

                        helper.removePurchase(pn);

                        String mnamen = getTagValue("mname", eElement);
                        Merchant mn = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mnamen).getSingleResult();
                        p.setMerchantid(mn);
                        p.setRecievinginstitution(mn.getId().toString());
                        Transactiontype transactiontypen = new Transactiontype();
                        transactiontypen.setId(1);
                        p.setTransactiontype(transactiontypen);
                        paygate.objects.Status statusn = (paygate.objects.Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
                        p.setStatusid(statusn);
                        p.setToken("paysure");
                        p.setSecret("secret");
                        p.setReferenceno(getTagValue("refno", eElement));
                        Date daten = new Date();
                        SimpleDateFormat sdfn = new SimpleDateFormat("MMddHHmmss");
                        StringBuilder trann = new StringBuilder(sdfn.format(daten));
                        String transmissionn = trann.toString();
                        p.setTransmission(transmissionn);
                        p.setPurchasedate(daten);
                        p.setPaysuredate(daten);

                        Transactionsource transactionsourcen = new Transactionsource();
                        transactionsource.setId(1);
                        p.setTransactionsourceid(transactionsource);
                        //p.setSystemtraceno(getTagValue("systemtraceno", eElement));
                        List<Purchase> purchaseList2 = (List<Purchase>) em.createNamedQuery("Purchase.findLatest").setParameter("merchantid", mn).setParameter("transactionsourceid", transactionsource).setMaxResults(1).getResultList();
                        if (purchaseList.size() > 0) {
                            Iterator itr = purchaseList2.iterator();
                            Purchase purchase = (Purchase) itr.next();
                            p.setSystemtraceno(String.format("%1$" + 6 + "s", String.valueOf(Integer.valueOf(purchase.getSystemtraceno()) + 1)).replaceAll(" ", "0"));
                        } else {
                            p.setSystemtraceno(String.format("%1$" + 6 + "s", "1").replaceAll(" ", "0"));
                        }
                        p.setDescription(getTagValue("description", eElement));
                        p.setAmount(Integer.parseInt(getTagValue("amount", eElement)));
                        p.setClientname(getTagValue("buyer", eElement));
                        p.setEmail(getTagValue("email", eElement));
                        p.setCurrency(getTagValue("currency", eElement));



                        em.persist(p);

                    }


                    //proceed to save a purchase
                    //em.persist(p);
                    utx.commit();

                    /*
                     * Purchase p2=new Purchase(); p2=em.find(Purchase.class,
                     * 260);
                     */

                    //DataItem di=new DataItem();
                    //di.saveToDb(dataitems, p);

                    url = link(cardtype, p);

                    System.out.println("-------------------------------");
                }
            }

        } catch (RollbackException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(url);
        return url;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "formData")
    public String formData(@WebParam(name = "mname") String mname, @WebParam(name = "name") String name, @WebParam(name = "email") String email, @WebParam(name = "amount") int amount, @WebParam(name = "description") String description, @WebParam(name = "payoption") int payoption, @WebParam(name = "currency") String currency) {

        String mnameform = mname;
        String nameform = name;
        String emailform = email;
        int formamount = amount;
        String formdescription = description;
        int formpayoption = payoption;
        String cardtype = "" + formpayoption;
        String curr = currency;
        String url = "";

        Purchase p2 = new Purchase();

        try {
            utx.begin();
            Merchant m = (Merchant) em.createNamedQuery("Merchant.findByName").setParameter("name", mnameform).getSingleResult();
            p2.setMerchantid(m);
            p2.setRecievinginstitution(m.getId().toString());
            Transactiontype transactiontype = new Transactiontype();
            transactiontype.setId(1);
            p2.setTransactiontype(transactiontype);
            paygate.objects.Status status = (paygate.objects.Status) em.createNamedQuery("Status.findById").setParameter("id", 11).getSingleResult();
            p2.setStatusid(status);
            p2.setToken("paysure");
            p2.setSecret("secret");
            //refno here
            //p2.setReferenceno(formdescription);
            Date date = new Date();
            SimpleDateFormat sdfn = new SimpleDateFormat("MMddHHmmss");
            StringBuilder trann = new StringBuilder(sdfn.format(date));
            String transmissionn = trann.toString();
            p2.setTransmission(transmissionn);
            p2.setPurchasedate(date);
            p2.setPaysuredate(date);
            Transactionsource transactionsource = new Transactionsource();
            transactionsource.setId(2);
            p2.setTransactionsourceid(transactionsource);
            //systraceno here
            //p2.setSystemtraceno("000272");
            p2.setDescription(formdescription);
            if (cardtype.compareTo("2") == 0) {
                p2.setAmount(formamount * 100);
            } else {
                p2.setAmount(formamount);
            }
            p2.setAmount(formamount);
            p2.setClientname(nameform);
            p2.setEmail(emailform);
            p2.setCurrency(curr);

            List<Purchase> purchaseList = (List<Purchase>) em.createNamedQuery("Purchase.findLatest").setParameter("merchantid", m).setParameter("transactionsourceid", transactionsource).setMaxResults(1).getResultList();
            String referenceno = "";
            int systemtraceno;
            if (purchaseList.size() > 0) {
                Iterator i = purchaseList.iterator();
                Purchase p = (Purchase) i.next();
                systemtraceno = Integer.valueOf(p.getSystemtraceno());
                referenceno = String.valueOf(m.getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", String.valueOf(Integer.valueOf(p.getSystemtraceno()) + 1)).replaceAll(" ", "0");
                p2.setSystemtraceno(String.format("%1$" + 6 + "s", String.valueOf(Integer.valueOf(p.getSystemtraceno()) + 1)).replaceAll(" ", "0"));
                p2.setReferenceno(referenceno);
            } else {
                referenceno = String.valueOf(m.getId()) + "R";
                referenceno = referenceno + String.format("%1$" + (12 - referenceno.length()) + "s", "1").replaceAll(" ", "0");
                p2.setReferenceno(referenceno);
                p2.setSystemtraceno(String.format("%1$" + 6 + "s", "1").replaceAll(" ", "0"));
            }
            em.persist(p2);
            utx.commit();
            url = link(cardtype, p2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public String link(String cardtype, Purchase p) {
        if (cardtype.compareTo("2") == 0) {
            Acquirer a = new Acquirer();
            a = p.getMerchantid().getAcquireridvisa();
            String link = "";
            if (a.getId().compareTo(1) == 0) {
                if (p.getCurrency().compareTo("USD") == 0) {
                    link = "https://epayments.paysure.co.ke/visa/visaepay.jsp?appid=" + p.getMerchantid().getLiveusd() + "&tamt=" + p.getAmount() + "&reference=" + p.getReferenceno() + "&email=" + p.getMerchantid().getEmail() + "&desc=" + p.getDescription() + "&mname=" + p.getMerchantid().getName();
                } else if (p.getCurrency().compareTo("KES") == 0) {
                    link = "https://epayments.paysure.co.ke/visa/visaepay.jsp?appid=" + p.getMerchantid().getLiveid() + "&tamt=" + p.getAmount() + "&reference=" + p.getReferenceno() + "&email=" + p.getMerchantid().getEmail() + "&desc=" + p.getDescription() + "&mname=" + p.getMerchantid().getName();
                } else {
                    link = "https://epayments.paysure.co.ke/visa/errorrdirect.jsp";
                }
            } else if (a.getId().compareTo(2) == 0) {
                link = "https://epayments.paysure.co.ke/kcbpay/receive.jsp?vpc_Version=1&vpc_Command=pay&vpc_AccessCode=" + p.getMerchantid().getVpcaccesscode() + "&vpc_MerchTxnRef=" + p.getReferenceno() + "&vpc_Merchant=" + p.getMerchantid().getVpcmerchantid() + "&vpc_OrderInfo=" + p.getReferenceno() + "&vpc_Amount=" + p.getAmount() + "&vpc_Locale=en&vpc_ReturnAuthResponseData=Y&vpc_ReturnURL=https://epayments.paysure.co.ke/kcbpay/Process&=virtualPaymentClientURL=https://migs-mtf.mastercard.com.au/vpcpay";

            }else{
                System.out.println("Yet To Be Implemented");
            }
            return link;
        } else if (cardtype.compareTo("3") == 0) {
            //return "appid=" + p.getMerchantid().getLiveid() + "&tamt=" + p.getAmount() + "&reference=" + p.getReferenceno() + "&email=" + p.getMerchantid().getEmail() + "&desc=" + p.getDescription() + "&mname=" + p.getMerchantid().getName();
            return "https://epayments.paysure.co.ke/mpesa/confirm.jsp?refno=" + p.getReferenceno() + "&tamt=" + p.getAmount() + "&mname=" + p.getMerchantid().getName();

        } else {
            int i = p.getAmount();
            float f = (float) i;
            f = f / 100;
            String lnk = "";
            Acquirer a = new Acquirer();
            a = p.getMerchantid().getAcquireridkenswitch();
            if (a.getId().compareTo(4) == 0) {
                lnk = "https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=00&tid92012=Paysure@Ltd&mid=" + p.getMerchantid().getMerchantidentifier() + "&mname=" + p.getMerchantid().getName() + "&tamt=" + f + "&recinst=&trace=" + p.getSystemtraceno() + "&reference=" + p.getReferenceno() + "&tdt=" + p.getTransmission() + "&ldate=" + p.getTransmission().substring(0, 4) + "&ltime=" + p.getTransmission().substring(4, p.getTransmission().length()) + "&anonymous=&email=" + p.getEmail();
            }            
            else if(a.getId().compareTo(5) == 0) {
                lnk = "https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=01&tid92012=Paysure@Ltd&mid=" + p.getMerchantid().getMerchantidentifier() + "&mname=" + p.getMerchantid().getName() + "&tamt=" + f + "&recinst=&trace=" + p.getSystemtraceno() + "&reference=" + p.getReferenceno() + "&tdt=" + p.getTransmission() + "&ldate=" + p.getTransmission().substring(0, 4) + "&ltime=" + p.getTransmission().substring(4, p.getTransmission().length()) + "&anonymous=&email=" + p.getEmail();
            }
            else if(a.getId().compareTo(6) == 0){
                lnk = "https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=38&tid92012=Paysure@Ltd&mid=" + p.getMerchantid().getMerchantidentifier() + "&mname=" + p.getMerchantid().getName() + "&tamt=" + f + "&recinst=&trace=" + p.getSystemtraceno() + "&reference=" + p.getReferenceno() + "&tdt=" + p.getTransmission() + "&ldate=" + p.getTransmission().substring(0, 4) + "&ltime=" + p.getTransmission().substring(4, p.getTransmission().length()) + "&anonymous=&email=" + p.getEmail();
            
        }
            //return "https://www.kenswitch.com/KenswitchPaymentGateway/KenswitchPaymentGateway.aspx?trant=00&tid92012=Paysure@Ltd&mid=" + p.getMerchantid().getMerchantidentifier() + "&mname=" + p.getMerchantid().getName() + "&tamt=" + f + "&recinst=&trace=" + p.getSystemtraceno() + "&reference=" + p.getReferenceno() + "&tdt=" + p.getTransmission() + "&ldate=" + p.getTransmission().substring(0, 4) + "&ltime=" + p.getTransmission().substring(4, p.getTransmission().length()) + "&anonymous=&email=" + p.getEmail();
            return lnk;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "test")
    public String test() {
        //TODO write your implementation code here:
        return "Your Ping Worked";
    }
    /**
     * Web service operation
     */
    /*
     * @WebMethod(operationName = "android") public String
     * android(@WebParam(name = "amount") String amount, @WebParam(name =
     * "cardno") String cardno, @WebParam(name = "expirydate") String
     * expirydate) throws ResultException, TransformerConfigurationException,
     * TransformerException { //TODO write your implementatioe here //: String
     * date = expirydate; StringBuilder sb = new StringBuilder();
     * sb.append(date.charAt(2)); sb.append(date.charAt(3)); sb.append("20");
     * sb.append(date.charAt(0)); sb.append(date.charAt(1)); String d =
     * sb.toString(); String mode = "Test";
     *
     * Enterprise e= new Enterprise("host",
     * "37B42B61-F2C9-4B1E-9587-BBDA2116ABFE",
     * ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
     * e.prepare("Transaction", "Debit", "3a687c08-c840-43d6-ae4f-71a3fd619b6e",
     * mode); e.setAttribute("MerchantReference",
     * String.valueOf(System.currentTimeMillis())); e.setTag("Amount", "1000");
     * // R1.23 e.setTag("Currency", "KES"); e.setTag("CCNumber",
     * "4242424242424242"); e.setTag("ExpiryDate", "122013");
     *
     * System.out.println(e.getLoggableRequest()); ResultStatus resultStatus =
     * e.execute();
     *
     *
     *
     * /*Enterprise e= new Enterprise("host",
     * "4327DA6D-FB2B-4C5E-A209-70BA4EB8FB50",
     * ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
     * e.prepare("Transaction", "Authorisation",
     * "3a687c08-c840-43d6-ae4f-71a3fd619b6e", mode);
     * e.setAttribute("MerchantReference",
     * String.valueOf(System.currentTimeMillis())); e.setTag("Amount", "1000");
     * // R1.23 e.setTag("Currency", "KES"); e.setTag("CCNumber",
     * "4242424242424242"); e.setTag("ExpiryDate", "122013"); e.setTag("Email",
     * "josetosh06@gmail.com"); System.out.println(e.getLoggableRequest());
     */
    /*
     * Enterprise e = new Enterprise("host",
     * "4327DA6D-FB2B-4C5E-A209-70BA4EB8FB50",
     * ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
     * e.prepare("Transaction", "Debit", "3a687c08-c840-43d6-ae4f-71a3fd619b6e",
     * mode); e.setAttribute("MerchantReference",
     * String.valueOf(System.currentTimeMillis())); e.setTag("Amount", amount);
     * // R1.23 e.setTag("CCNumber", cardno); e.setTag("ExpiryDate", d);
     * System.out.println(e.getLoggableRequest()); ResultStatus resultStatus =
     * e.execute(); System.out.println("Result : " + resultStatus.getValue());
     * String result = ""; if (resultStatus == ResultStatus.UNSUCCESSFUL) { //
     * Display the result(eg.DENIED or ApplicationID not configured // )
     * System.out.println("Result code : " + e.getResultCode());
     * System.out.println("Result source : " + e.getResultSource());
     * System.out.println("Result description : " + e.getResultDescription());
     * result = result + e.getResultCode() + " Failed!!!"; } else if
     * (resultStatus == ResultStatus.SUCCESSFUL || resultStatus ==
     * ResultStatus.SUCCESSFUL_WITH_WARNING) { System.out.println("Successful
     * Transaction Test "); System.out.println("RequestID: " +
     * e.getAttribute("RequestID")); System.out.println("Acquirer Reference : "
     * + e.getTag("AcquirerReference")); System.out.println("Acquirer Date : " +
     * e.getTag("AcquirerDate")); System.out.println("Acquirer Time : " +
     * e.getTag("AcquirerTime")); System.out.println("Authorisation Code : " +
     * e.getTag("AuthorisationCode")); System.out.println("Amount : " +
     * e.getTag("Amount")); System.out.println("Terminal : " +
     * e.getTag("Terminal")); System.out.println("Transaction Index : " +
     * e.getTag("TransactionIndex"));
     * System.out.println(System.getProperty("java.home")); result = result +
     * e.getResultCode() + " Successful Transaction Test \n" + "RequestID : " +
     * e.getAttribute("RequestID") + "\n Acquirer Reference : " +
     * e.getTag("AcquirerReference") + "\n Acquirer Date : " +
     * e.getTag("AcquirerDate") + "\n Acquirer Time : " +
     * e.getTag("AcquirerTime") + "\n Authorisation Code : " +
     * e.getTag("AuthorisationCode") + "\n Amount : " + e.getTag("Amount") +
     * "\nTerminal:" + e.getTag("Terminal") + "\n Transaction Index: " +
     * e.getTag("TransactionIndex"); } return result + "Date:" + d + "Card No:"
     * + cardno;
     */
    //return "working..."+amount+cardno+expirydate;
    //return e.getLoggableRequest();
    // return e.getLoggableResponse();
    //  }
    /**
     * Web service operation
     */
    /*
     * @WebMethod(operationName = "sampletest") public String sampletest()
     * throws ResultException, TransformerConfigurationException,
     * TransformerException { //TODO write your implementation code here: String
     * mode = "Test"; Enterprise e= new Enterprise("host",
     * "4327DA6D-FB2B-4C5E-A209-70BA4EB8FB50",
     * ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
     * e.prepare("Transaction", "Debit", "3a687c08-c840-43d6-ae4f-71a3fd619b6e",
     * mode); e.setAttribute("MerchantReference",
     * String.valueOf(System.currentTimeMillis())); e.setTag("Amount", "1000");
     * // R1.23 e.setTag("Currency", "KES"); e.setTag("CCNumber",
     * "4242424242424242"); e.setTag("ExpiryDate", "122013");
     *
     * System.out.println(e.getLoggableRequest()); //ResultStatus resultStatus =
     * e.execute();
     *
     *
     * return null; }
     */
}
/**
 * Web service operation
 */
/*
 * @WebMethod(operationName = "search") public String search(@WebParam(name =
 * "refno") String refno) { //TODO write your implementation code here: //String
 * referenceno=refno; //Purchase p=(Purchase)
 * em.createNativeQuery("Purchase.findByReferenceno").setParameter("referenceno",
 * referenceno).getSingleResult(); return refno; }
 */
/**
 * Web service operation
 */
//    @WebMethod(operationName = "redirect")
//    @Oneway
//    public void redirect() {
//        try {
//            ServletContext servletContext = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
//            HttpServletRequest request = (HttpServletRequest) context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
//            HttpServletResponse response = (HttpServletResponse) context.getMessageContext().get(MessageContext.SERVLET_RESPONSE);
//            servletContext.getRequestDispatcher("/Redirect").include(request, response);            
//            
//        } catch (ServletException ex) {
//            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Onlinepay.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       
//    }



/*JN, 6/10/2012....created the pickData webservice operation for receiving shopping cart details 
 * from a merchant's website. It takes two parameters which are XML format character string. The 
 * first parameter (data) is a summary of the order from the shopping cart while the second 
 * parameter (dataitems) is a summary collection of each individual item type of all the items in
 * the shopping cart. Depending on the card type the buyer click on his side to use for settling
 * his order amount,he will be redirected to the respective weppage form (Kenswitch or Visa) to 
 * proceed with payment. 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */