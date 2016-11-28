package paygate.objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-16T12:02:07")
@StaticMetamodel(Transactionmpesa.class)
public class Transactionmpesa_ { 

    public static volatile SingularAttribute<Transactionmpesa, String> routemethodname;
    public static volatile SingularAttribute<Transactionmpesa, String> text;
    public static volatile SingularAttribute<Transactionmpesa, Integer> routemethodid;
    public static volatile SingularAttribute<Transactionmpesa, String> mpesaacc;
    public static volatile SingularAttribute<Transactionmpesa, BigInteger> mpesamsisdn;
    public static volatile SingularAttribute<Transactionmpesa, String> orig;
    public static volatile SingularAttribute<Transactionmpesa, Date> tstamp;
    public static volatile SingularAttribute<Transactionmpesa, BigInteger> ipn;
    public static volatile SingularAttribute<Transactionmpesa, Merchant> merchantid;
    public static volatile SingularAttribute<Transactionmpesa, String> pass;
    public static volatile SingularAttribute<Transactionmpesa, Integer> id;
    public static volatile SingularAttribute<Transactionmpesa, Date> mpesatrxtime;
    public static volatile SingularAttribute<Transactionmpesa, String> username;
    public static volatile SingularAttribute<Transactionmpesa, BigInteger> dest;
    public static volatile SingularAttribute<Transactionmpesa, Integer> customerid;
    public static volatile SingularAttribute<Transactionmpesa, Date> mpesatrxdate;
    public static volatile SingularAttribute<Transactionmpesa, String> mpesacode;
    public static volatile SingularAttribute<Transactionmpesa, String> mpesasender;
    public static volatile SingularAttribute<Transactionmpesa, Double> mpesaamt;

}