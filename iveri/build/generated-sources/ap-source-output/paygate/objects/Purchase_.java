package paygate.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Data;
import paygate.objects.Gender;
import paygate.objects.Merchant;
import paygate.objects.Status;
import paygate.objects.Transactionsource;
import paygate.objects.Transactiontype;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-01-09T12:39:55")
@StaticMetamodel(Purchase.class)
public class Purchase_ { 

    public static volatile SingularAttribute<Purchase, Transactiontype> transactiontype;
    public static volatile SingularAttribute<Purchase, String> recievinginstitution;
    public static volatile SingularAttribute<Purchase, String> systemtraceno;
    public static volatile SingularAttribute<Purchase, Date> paysuredate;
    public static volatile SingularAttribute<Purchase, Status> statusid;
    public static volatile SingularAttribute<Purchase, String> transmission;
    public static volatile SingularAttribute<Purchase, String> currency;
    public static volatile SingularAttribute<Purchase, Merchant> merchantid;
    public static volatile SingularAttribute<Purchase, String> clientname;
    public static volatile SingularAttribute<Purchase, Integer> id;
    public static volatile SingularAttribute<Purchase, Integer> amount;
    public static volatile SingularAttribute<Purchase, Gender> genderid;
    public static volatile SingularAttribute<Purchase, Date> purchasedate;
    public static volatile SingularAttribute<Purchase, String> token;
    public static volatile SingularAttribute<Purchase, String> email;
    public static volatile SingularAttribute<Purchase, String> description;
    public static volatile CollectionAttribute<Purchase, Data> dataCollection;
    public static volatile SingularAttribute<Purchase, String> referenceno;
    public static volatile SingularAttribute<Purchase, Integer> age;
    public static volatile SingularAttribute<Purchase, String> secret;
    public static volatile SingularAttribute<Purchase, Integer> visits;
    public static volatile SingularAttribute<Purchase, Integer> shippingcost;
    public static volatile SingularAttribute<Purchase, Transactionsource> transactionsourceid;

}