package paygate.objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Merchant;
import paygate.objects.Responsecode;
import paygate.objects.Status;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-08-01T16:04:17")
@StaticMetamodel(Transaction.class)
public class Transaction_ { 

    public static volatile SingularAttribute<Transaction, String> cardtype;
    public static volatile SingularAttribute<Transaction, String> delivered;
    public static volatile SingularAttribute<Transaction, Responsecode> responsecodeid;
    public static volatile SingularAttribute<Transaction, Status> statusid;
    public static volatile SingularAttribute<Transaction, String> debitcredit;
    public static volatile SingularAttribute<Transaction, String> clientname;
    public static volatile SingularAttribute<Transaction, Merchant> merchantid;
    public static volatile SingularAttribute<Transaction, Date> modified;
    public static volatile SingularAttribute<Transaction, Integer> id;
    public static volatile SingularAttribute<Transaction, BigInteger> amount;
    public static volatile SingularAttribute<Transaction, String> refno;
    public static volatile SingularAttribute<Transaction, String> email;
    public static volatile SingularAttribute<Transaction, Date> created;
    public static volatile SingularAttribute<Transaction, String> description;
    public static volatile SingularAttribute<Transaction, BigInteger> commission;
    public static volatile SingularAttribute<Transaction, String> merchantname;

}