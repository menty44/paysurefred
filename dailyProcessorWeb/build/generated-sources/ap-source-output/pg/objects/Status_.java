package pg.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pg.objects.Country;
import pg.objects.Merchant;
import pg.objects.Purchase;
import pg.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-20T16:43:10")
@StaticMetamodel(Status.class)
public class Status_ { 

    public static volatile SingularAttribute<Status, Integer> id;
    public static volatile SingularAttribute<Status, Date> created;
    public static volatile SingularAttribute<Status, String> description;
    public static volatile CollectionAttribute<Status, Purchase> purchaseCollection;
    public static volatile CollectionAttribute<Status, Merchant> merchantCollection;
    public static volatile CollectionAttribute<Status, Country> countryCollection;
    public static volatile SingularAttribute<Status, Integer> code;
    public static volatile CollectionAttribute<Status, Transaction> transactionCollection;
    public static volatile SingularAttribute<Status, Date> modified;

}