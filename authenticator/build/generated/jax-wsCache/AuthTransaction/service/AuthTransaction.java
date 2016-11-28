
package service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6b20 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AuthTransaction", targetNamespace = "http://authtransaction.webservice.kenswitch.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AuthTransaction {


    /**
     * 
     * @param parameter
     * @return
     *     returns service.Response
     */
    @WebMethod(operationName = "AuthenticateTransaction")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "AuthenticateTransaction", targetNamespace = "http://authtransaction.webservice.kenswitch.org/", className = "service.AuthenticateTransaction")
    @ResponseWrapper(localName = "AuthenticateTransactionResponse", targetNamespace = "http://authtransaction.webservice.kenswitch.org/", className = "service.AuthenticateTransactionResponse")
    @Action(input = "http://authtransaction.webservice.kenswitch.org/AuthTransaction/AuthenticateTransactionRequest", output = "http://authtransaction.webservice.kenswitch.org/AuthTransaction/AuthenticateTransactionResponse")
    public Response authenticateTransaction(
        @WebParam(name = "parameter", targetNamespace = "")
        Request parameter);

}