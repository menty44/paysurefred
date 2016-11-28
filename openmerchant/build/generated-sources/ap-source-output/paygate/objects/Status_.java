package paygate.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Country;
import paygate.objects.Merchant;
import paygate.objects.Purchase;
import paygate.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-27T11:35:01")
@StaticMetamodel(Status.class)
public class Status_ { 

    public static volatile SingularAttribute<Status, Integer> id;
    public static volatile ListAttribute<Status, Purchase> purchaseList;
    public static volatile SingularAttribute<Status, Date> created;
    public static volatile ListAttribute<Status, Country> countryList;
    public static volatile SingularAttribute<Status, String> description;
    public static volatile SingularAttribute<Status, Integer> code;
    public static volatile ListAttribute<Status, Transaction> transactionList;
    public static volatile ListAttribute<Status, Merchant> merchantList;
    public static volatile SingularAttribute<Status, Date> modified;

}