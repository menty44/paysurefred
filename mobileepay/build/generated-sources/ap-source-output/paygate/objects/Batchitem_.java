package paygate.objects;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Batchheader;
import paygate.objects.EmailDelivery;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-16T12:02:07")
@StaticMetamodel(Batchitem.class)
public class Batchitem_ { 

    public static volatile SingularAttribute<Batchitem, Integer> id;
    public static volatile SingularAttribute<Batchitem, BigInteger> amount;
    public static volatile SingularAttribute<Batchitem, EmailDelivery> deliverystatus;
    public static volatile SingularAttribute<Batchitem, String> email;
    public static volatile SingularAttribute<Batchitem, String> description;
    public static volatile SingularAttribute<Batchitem, String> clientName;
    public static volatile SingularAttribute<Batchitem, Batchheader> batchheaderId;
    public static volatile SingularAttribute<Batchitem, String> currency;

}