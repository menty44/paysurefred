package objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Country;
import objects.Gender;
import objects.Merchant;
import objects.Responsecode;
import objects.Status;
import objects.Transactionsource;
import objects.Transactiontype;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-17T16:19:37")
@StaticMetamodel(Token.class)
public class Token_ { 

    public static volatile SingularAttribute<Token, String> transactionindex;
    public static volatile SingularAttribute<Token, Transactiontype> transactiontype;
    public static volatile SingularAttribute<Token, Date> paysuredate;
    public static volatile SingularAttribute<Token, Responsecode> responsecode;
    public static volatile SingularAttribute<Token, String> recievinginstitution;
    public static volatile SingularAttribute<Token, String> expirydate;
    public static volatile SingularAttribute<Token, Integer> securitycode;
    public static volatile SingularAttribute<Token, String> currency;
    public static volatile SingularAttribute<Token, String> clientname;
    public static volatile SingularAttribute<Token, Integer> id;
    public static volatile SingularAttribute<Token, Integer> amount;
    public static volatile SingularAttribute<Token, String> tokeno;
    public static volatile SingularAttribute<Token, String> token;
    public static volatile SingularAttribute<Token, String> description;
    public static volatile SingularAttribute<Token, String> referenceno;
    public static volatile SingularAttribute<Token, Integer> age;
    public static volatile SingularAttribute<Token, Country> countryofresidence;
    public static volatile SingularAttribute<Token, String> instruction;
    public static volatile SingularAttribute<Token, Integer> shippingcost;
    public static volatile SingularAttribute<Token, String> nameoncard;
    public static volatile SingularAttribute<Token, String> systemtraceno;
    public static volatile SingularAttribute<Token, Date> tokendate;
    public static volatile SingularAttribute<Token, Status> statusid;
    public static volatile SingularAttribute<Token, String> transmission;
    public static volatile SingularAttribute<Token, Merchant> merchantid;
    public static volatile SingularAttribute<Token, Gender> genderid;
    public static volatile SingularAttribute<Token, String> email;
    public static volatile SingularAttribute<Token, String> secret;
    public static volatile SingularAttribute<Token, Integer> visits;
    public static volatile SingularAttribute<Token, Transactionsource> transactionsourceid;

}