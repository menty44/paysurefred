package paygate.objects;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Purchase;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-28T10:56:28")
@StaticMetamodel(Data.class)
public class Data_ { 

    public static volatile SingularAttribute<Data, Integer> id;
    public static volatile SingularAttribute<Data, BigInteger> itemprice;
    public static volatile SingularAttribute<Data, Purchase> purchaseid;
    public static volatile SingularAttribute<Data, String> itemname;
    public static volatile SingularAttribute<Data, Integer> quantity;
    public static volatile SingularAttribute<Data, BigInteger> subtotal;

}