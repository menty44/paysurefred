package objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Merchant;
import objects.Responsecode;
import objects.Status;
import objects.Transactionsource;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-06T12:33:54")
@StaticMetamodel(Transaction.class)
public class Transaction_ { 

    public static volatile SingularAttribute<Transaction, String> cardtype;
    public static volatile SingularAttribute<Transaction, Responsecode> responsecodeid;
    public static volatile SingularAttribute<Transaction, String> delivered;
    public static volatile SingularAttribute<Transaction, Status> statusid;
    public static volatile SingularAttribute<Transaction, String> debitcredit;
    public static volatile SingularAttribute<Transaction, Merchant> merchantid;
    public static volatile SingularAttribute<Transaction, String> currency;
    public static volatile SingularAttribute<Transaction, String> clientname;
    public static volatile SingularAttribute<Transaction, Boolean> threshold;
    public static volatile SingularAttribute<Transaction, Date> modified;
    public static volatile SingularAttribute<Transaction, Integer> id;
    public static volatile SingularAttribute<Transaction, BigInteger> amount;
    public static volatile SingularAttribute<Transaction, Boolean> processed;
    public static volatile SingularAttribute<Transaction, String> refno;
    public static volatile SingularAttribute<Transaction, Date> created;
    public static volatile SingularAttribute<Transaction, String> email;
    public static volatile SingularAttribute<Transaction, String> description;
    public static volatile SingularAttribute<Transaction, BigInteger> commission;
    public static volatile SingularAttribute<Transaction, String> merchantname;
    public static volatile SingularAttribute<Transaction, Transactionsource> transactionsourceid;

}