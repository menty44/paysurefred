
package service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AuthenticateTransactionResponse_QNAME = new QName("http://authtransaction.webservice.kenswitch.org/", "AuthenticateTransactionResponse");
    private final static QName _AuthenticateTransaction_QNAME = new QName("http://authtransaction.webservice.kenswitch.org/", "AuthenticateTransaction");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AuthenticateTransactionResponse }
     * 
     */
    public AuthenticateTransactionResponse createAuthenticateTransactionResponse() {
        return new AuthenticateTransactionResponse();
    }

    /**
     * Create an instance of {@link AuthenticateTransaction }
     * 
     */
    public AuthenticateTransaction createAuthenticateTransaction() {
        return new AuthenticateTransaction();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticateTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://authtransaction.webservice.kenswitch.org/", name = "AuthenticateTransactionResponse")
    public JAXBElement<AuthenticateTransactionResponse> createAuthenticateTransactionResponse(AuthenticateTransactionResponse value) {
        return new JAXBElement<AuthenticateTransactionResponse>(_AuthenticateTransactionResponse_QNAME, AuthenticateTransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticateTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://authtransaction.webservice.kenswitch.org/", name = "AuthenticateTransaction")
    public JAXBElement<AuthenticateTransaction> createAuthenticateTransaction(AuthenticateTransaction value) {
        return new JAXBElement<AuthenticateTransaction>(_AuthenticateTransaction_QNAME, AuthenticateTransaction.class, null, value);
    }

}
