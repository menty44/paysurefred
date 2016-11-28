package service;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import paygate.objects.Transaction.*;

@WebService(serviceName = "Processor")
@Stateless()
public class Processor {

    @PersistenceContext private EntityManager em;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTransactionCodeId")
    public int getTransactionCodeId(@WebParam(name = "id") int id) throws SystemException {
        
        return em.find(Transaction.class, id).getStatus();
    }
    
    
}
