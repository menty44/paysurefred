package mobile.objects;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mobile.objects.Batchheader;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-12T09:15:43")
@StaticMetamodel(Batchitem.class)
public class Batchitem_ { 

    public static volatile SingularAttribute<Batchitem, Integer> id;
    public static volatile SingularAttribute<Batchitem, BigInteger> amount;
    public static volatile SingularAttribute<Batchitem, String> email;
    public static volatile SingularAttribute<Batchitem, String> description;
    public static volatile SingularAttribute<Batchitem, String> clientName;
    public static volatile SingularAttribute<Batchitem, Batchheader> batchheaderId;

}