package paygate.objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Batchheader;
import paygate.objects.Cart;
import paygate.objects.Category;
import paygate.objects.Country;
import paygate.objects.Purchase;
import paygate.objects.Rate;
import paygate.objects.Siteuser;
import paygate.objects.Status;
import paygate.objects.Token;
import paygate.objects.Transaction;
import paygate.objects.Transactionmpesa;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-16T12:02:07")
@StaticMetamodel(Merchant.class)
public class Merchant_ { 

    public static volatile SingularAttribute<Merchant, String> phone;
    public static volatile SingularAttribute<Merchant, String> bankaccount;
    public static volatile SingularAttribute<Merchant, String> enterpriseidusd;
    public static volatile SingularAttribute<Merchant, Boolean> edi;
    public static volatile SingularAttribute<Merchant, String> street;
    public static volatile SingularAttribute<Merchant, String> enterpriseid;
    public static volatile SingularAttribute<Merchant, String> state;
    public static volatile SingularAttribute<Merchant, Boolean> usd;
    public static volatile SingularAttribute<Merchant, String> branchcode;
    public static volatile SingularAttribute<Merchant, String> terminalid;
    public static volatile SingularAttribute<Merchant, String> ipaddress;
    public static volatile SingularAttribute<Merchant, String> mpesaid;
    public static volatile SingularAttribute<Merchant, String> edipass;
    public static volatile SingularAttribute<Merchant, String> password;
    public static volatile SingularAttribute<Merchant, Country> countryid;
    public static volatile SingularAttribute<Merchant, String> city;
    public static volatile SingularAttribute<Merchant, Integer> id;
    public static volatile ListAttribute<Merchant, Purchase> purchaseList;
    public static volatile SingularAttribute<Merchant, String> username;
    public static volatile SingularAttribute<Merchant, String> token;
    public static volatile SingularAttribute<Merchant, Date> created;
    public static volatile SingularAttribute<Merchant, String> bankcode;
    public static volatile SingularAttribute<Merchant, Cart> carttype;
    public static volatile ListAttribute<Merchant, Siteuser> siteuserList;
    public static volatile SingularAttribute<Merchant, String> name;
    public static volatile SingularAttribute<Merchant, BigInteger> eft;
    public static volatile SingularAttribute<Merchant, String> ediurl;
    public static volatile SingularAttribute<Merchant, String> key;
    public static volatile SingularAttribute<Merchant, String> liveidusd;
    public static volatile SingularAttribute<Merchant, String> merchantidentifier;
    public static volatile ListAttribute<Merchant, Transactionmpesa> transactionmpesaList;
    public static volatile SingularAttribute<Merchant, String> website;
    public static volatile SingularAttribute<Merchant, String> returnurl;
    public static volatile SingularAttribute<Merchant, Status> statusid;
    public static volatile SingularAttribute<Merchant, String> postcode;
    public static volatile SingularAttribute<Merchant, String> url;
    public static volatile SingularAttribute<Merchant, Date> modified;
    public static volatile SingularAttribute<Merchant, String> contactperson;
    public static volatile SingularAttribute<Merchant, String> ediusr;
    public static volatile SingularAttribute<Merchant, Category> categoryid;
    public static volatile ListAttribute<Merchant, Batchheader> batchheaderList;
    public static volatile SingularAttribute<Merchant, String> email;
    public static volatile ListAttribute<Merchant, Token> tokenList;
    public static volatile SingularAttribute<Merchant, Rate> rateid;
    public static volatile ListAttribute<Merchant, Transaction> transactionList;
    public static volatile SingularAttribute<Merchant, String> liveid;
    public static volatile SingularAttribute<Merchant, String> mobile;

}