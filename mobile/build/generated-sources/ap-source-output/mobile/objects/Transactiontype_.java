package mobile.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mobile.objects.Purchase;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-12T09:15:43")
@StaticMetamodel(Transactiontype.class)
public class Transactiontype_ { 

    public static volatile SingularAttribute<Transactiontype, Integer> id;
    public static volatile ListAttribute<Transactiontype, Purchase> purchaseList;
    public static volatile SingularAttribute<Transactiontype, String> description;
    public static volatile SingularAttribute<Transactiontype, String> value;

}