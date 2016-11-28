package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Purchase;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-28T11:17:43")
@StaticMetamodel(Cardtype.class)
public class Cardtype_ { 

    public static volatile SingularAttribute<Cardtype, Integer> id;
    public static volatile ListAttribute<Cardtype, Purchase> purchaseList;
    public static volatile SingularAttribute<Cardtype, String> description;

}