package objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-06T12:33:54")
@StaticMetamodel(Rate.class)
public class Rate_ { 

    public static volatile SingularAttribute<Rate, Integer> id;
    public static volatile SingularAttribute<Rate, BigInteger> paysurerate;
    public static volatile SingularAttribute<Rate, BigInteger> merchantrate;
    public static volatile SingularAttribute<Rate, Date> created;
    public static volatile ListAttribute<Rate, Merchant> merchantList;
    public static volatile SingularAttribute<Rate, Date> modified;

}