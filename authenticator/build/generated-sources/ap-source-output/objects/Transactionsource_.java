package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Purchase;
import objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-17T16:19:37")
@StaticMetamodel(Transactionsource.class)
public class Transactionsource_ { 

    public static volatile SingularAttribute<Transactionsource, Integer> id;
    public static volatile CollectionAttribute<Transactionsource, Purchase> purchaseCollection;
    public static volatile SingularAttribute<Transactionsource, String> name;
    public static volatile CollectionAttribute<Transactionsource, Transaction> transactionCollection;

}