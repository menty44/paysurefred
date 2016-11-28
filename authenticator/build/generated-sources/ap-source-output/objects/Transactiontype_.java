package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Purchase;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-17T16:19:37")
@StaticMetamodel(Transactiontype.class)
public class Transactiontype_ { 

    public static volatile SingularAttribute<Transactiontype, Integer> id;
    public static volatile SingularAttribute<Transactiontype, String> description;
    public static volatile CollectionAttribute<Transactiontype, Purchase> purchaseCollection;
    public static volatile SingularAttribute<Transactiontype, String> value;

}