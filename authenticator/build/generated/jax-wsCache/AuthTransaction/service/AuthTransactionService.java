
package service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6b20 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AuthTransactionService", targetNamespace = "http://authtransaction.webservice.kenswitch.org/", wsdlLocation = "http://41.215.139.59:8080/KenswitchWebServiceAuthTransaction/AuthTransaction?wsdl")
public class AuthTransactionService
    extends Service
{

    private final static URL AUTHTRANSACTIONSERVICE_WSDL_LOCATION;
    private final static WebServiceException AUTHTRANSACTIONSERVICE_EXCEPTION;
    private final static QName AUTHTRANSACTIONSERVICE_QNAME = new QName("http://authtransaction.webservice.kenswitch.org/", "AuthTransactionService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://41.215.139.59:8080/KenswitchWebServiceAuthTransaction/AuthTransaction?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUTHTRANSACTIONSERVICE_WSDL_LOCATION = url;
        AUTHTRANSACTIONSERVICE_EXCEPTION = e;
    }

    public AuthTransactionService() {
        super(__getWsdlLocation(), AUTHTRANSACTIONSERVICE_QNAME);
    }

    public AuthTransactionService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTHTRANSACTIONSERVICE_QNAME, features);
    }

    public AuthTransactionService(URL wsdlLocation) {
        super(wsdlLocation, AUTHTRANSACTIONSERVICE_QNAME);
    }

    public AuthTransactionService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUTHTRANSACTIONSERVICE_QNAME, features);
    }

    public AuthTransactionService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuthTransactionService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AuthTransaction
     */
    @WebEndpoint(name = "AuthTransactionPort")
    public AuthTransaction getAuthTransactionPort() {
        return super.getPort(new QName("http://authtransaction.webservice.kenswitch.org/", "AuthTransactionPort"), AuthTransaction.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuthTransaction
     */
    @WebEndpoint(name = "AuthTransactionPort")
    public AuthTransaction getAuthTransactionPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://authtransaction.webservice.kenswitch.org/", "AuthTransactionPort"), AuthTransaction.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTHTRANSACTIONSERVICE_EXCEPTION!= null) {
            throw AUTHTRANSACTIONSERVICE_EXCEPTION;
        }
        return AUTHTRANSACTIONSERVICE_WSDL_LOCATION;
    }

}
