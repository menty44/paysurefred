package paygate.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Purchase;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-16T12:02:07")
@StaticMetamodel(Cardtype.class)
public class Cardtype_ { 

    public static volatile SingularAttribute<Cardtype, Integer> id;
    public static volatile ListAttribute<Cardtype, Purchase> purchaseList;
    public static volatile SingularAttribute<Cardtype, String> description;

}