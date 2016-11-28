package mobile.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mobile.objects.Purchase;
import mobile.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-12T09:15:43")
@StaticMetamodel(Transactionsource.class)
public class Transactionsource_ { 

    public static volatile SingularAttribute<Transactionsource, Integer> id;
    public static volatile ListAttribute<Transactionsource, Purchase> purchaseList;
    public static volatile SingularAttribute<Transactionsource, String> name;
    public static volatile ListAttribute<Transactionsource, Transaction> transactionList;

}