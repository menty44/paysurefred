package paygate.controller;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.ws.WebServiceRef;
import service.Onlinepay_Service;

/**
 *
 * @author Joseph
 */
@Stateless
@LocalBean
public class ProcessForm {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/epayments.paysure.co.ke/webdirect/onlinepay.wsdl")
    private Onlinepay_Service service;       

    /*private String formData(java.lang.String mname, java.lang.String name, java.lang.String email, int amount, java.lang.String description, int payoption, java.lang.String currency) {
        service.Onlinepay port = service.getOnlinepayPort();
        return port.formData(mname, name, email, amount, description, payoption, currency);
    }*/

    public String sendForm(String mname, String name, String email, int amount, String description, int payoption, String currency){        
        String url = formData(mname, name, email, amount, description, payoption, currency);        
        return url;
    }

    private String hello(java.lang.String name) {
        service.Onlinepay port = service.getOnlinepayPort();
        return port.hello(name);
    }
    
    public String sayHello(String name){
        return hello(name);
    }

    private String formData(java.lang.String mname, java.lang.String name, java.lang.String email, int amount, java.lang.String description, int payoption, java.lang.String currency) {
        service.Onlinepay port = service.getOnlinepayPort();
        return port.formData(mname, name, email, amount, description, payoption, currency);
    }
    
}
