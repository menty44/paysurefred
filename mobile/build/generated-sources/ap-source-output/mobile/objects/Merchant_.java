package mobile.objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mobile.objects.Cart;
import mobile.objects.Category;
import mobile.objects.Country;
import mobile.objects.Purchase;
import mobile.objects.Rate;
import mobile.objects.Siteuser;
import mobile.objects.Status;
import mobile.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-12T09:15:43")
@StaticMetamodel(Merchant.class)
public class Merchant_ { 

    public static volatile SingularAttribute<Merchant, String> phone;
    public static volatile SingularAttribute<Merchant, String> bankaccount;
    public static volatile SingularAttribute<Merchant, String> street;
    public static volatile SingularAttribute<Merchant, String> state;
    public static volatile SingularAttribute<Merchant, String> branchcode;
    public static volatile SingularAttribute<Merchant, String> terminalid;
    public static volatile SingularAttribute<Merchant, String> ipaddress;
    public static volatile SingularAttribute<Merchant, String> password;
    public static volatile SingularAttribute<Merchant, Country> countryid;
    public static volatile SingularAttribute<Merchant, String> city;
    public static volatile SingularAttribute<Merchant, Integer> id;
    public static volatile ListAttribute<Merchant, Purchase> purchaseList;
    public static volatile SingularAttribute<Merchant, String> username;
    public static volatile SingularAttribute<Merchant, String> token;
    public static volatile SingularAttribute<Merchant, String> bankcode;
    public static volatile SingularAttribute<Merchant, Date> created;
    public static volatile SingularAttribute<Merchant, Cart> carttype;
    public static volatile ListAttribute<Merchant, Siteuser> siteuserList;
    public static volatile SingularAttribute<Merchant, String> name;
    public static volatile SingularAttribute<Merchant, BigInteger> eft;
    public static volatile SingularAttribute<Merchant, String> key;
    public static volatile SingularAttribute<Merchant, String> merchantidentifier;
    public static volatile SingularAttribute<Merchant, Status> statusid;
    public static volatile SingularAttribute<Merchant, String> returnurl;
    public static volatile SingularAttribute<Merchant, String> postcode;
    public static volatile SingularAttribute<Merchant, String> url;
    public static volatile SingularAttribute<Merchant, Date> modified;
    public static volatile SingularAttribute<Merchant, String> contactperson;
    public static volatile SingularAttribute<Merchant, Category> categoryid;
    public static volatile SingularAttribute<Merchant, String> email;
    public static volatile SingularAttribute<Merchant, Rate> rateid;
    public static volatile ListAttribute<Merchant, Transaction> transactionList;
    public static volatile SingularAttribute<Merchant, String> liveid;
    public static volatile SingularAttribute<Merchant, String> mobile;

}