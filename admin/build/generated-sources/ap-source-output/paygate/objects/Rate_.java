package paygate.objects;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-08-01T16:04:17")
@StaticMetamodel(Rate.class)
public class Rate_ { 

    public static volatile SingularAttribute<Rate, Integer> id;
    public static volatile SingularAttribute<Rate, BigDecimal> paysurerate;
    public static volatile SingularAttribute<Rate, BigInteger> merchantrate;
    public static volatile SingularAttribute<Rate, Date> created;
    public static volatile CollectionAttribute<Rate, Merchant> merchantCollection;
    public static volatile SingularAttribute<Rate, Date> modified;

}