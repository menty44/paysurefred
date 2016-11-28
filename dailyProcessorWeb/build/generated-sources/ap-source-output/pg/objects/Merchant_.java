package pg.objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pg.objects.Cart;
import pg.objects.Category;
import pg.objects.Country;
import pg.objects.Purchase;
import pg.objects.Rate;
import pg.objects.Siteuser;
import pg.objects.Status;
import pg.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-20T16:43:10")
@StaticMetamodel(Merchant.class)
public class Merchant_ { 

    public static volatile SingularAttribute<Merchant, String> phone;
    public static volatile SingularAttribute<Merchant, String> bankaccount;
    public static volatile CollectionAttribute<Merchant, Purchase> purchaseCollection;
    public static volatile SingularAttribute<Merchant, String> street;
    public static volatile SingularAttribute<Merchant, String> state;
    public static volatile SingularAttribute<Merchant, String> branchcode;
    public static volatile SingularAttribute<Merchant, String> terminalid;
    public static volatile SingularAttribute<Merchant, String> ipaddress;
    public static volatile SingularAttribute<Merchant, String> password;
    public static volatile SingularAttribute<Merchant, Country> countryid;
    public static volatile SingularAttribute<Merchant, String> city;
    public static volatile SingularAttribute<Merchant, Integer> id;
    public static volatile SingularAttribute<Merchant, String> username;
    public static volatile SingularAttribute<Merchant, String> token;
    public static volatile SingularAttribute<Merchant, String> bankcode;
    public static volatile SingularAttribute<Merchant, Date> created;
    public static volatile SingularAttribute<Merchant, Cart> carttype;
    public static volatile CollectionAttribute<Merchant, Siteuser> siteuserCollection;
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
    public static volatile SingularAttribute<Merchant, String> liveid;
    public static volatile CollectionAttribute<Merchant, Transaction> transactionCollection;
    public static volatile SingularAttribute<Merchant, String> mobile;

}