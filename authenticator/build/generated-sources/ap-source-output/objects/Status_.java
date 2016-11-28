package objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Country;
import objects.Merchant;
import objects.Purchase;
import objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-17T16:19:37")
@StaticMetamodel(Status.class)
public class Status_ { 

    public static volatile SingularAttribute<Status, Integer> id;
    public static volatile SingularAttribute<Status, Date> created;
    public static volatile CollectionAttribute<Status, Purchase> purchaseCollection;
    public static volatile SingularAttribute<Status, String> description;
    public static volatile CollectionAttribute<Status, Merchant> merchantCollection;
    public static volatile CollectionAttribute<Status, Country> countryCollection;
    public static volatile SingularAttribute<Status, Integer> code;
    public static volatile CollectionAttribute<Status, Transaction> transactionCollection;
    public static volatile SingularAttribute<Status, Date> modified;

}