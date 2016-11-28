package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Purchase;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-17T16:19:37")
@StaticMetamodel(Data.class)
public class Data_ { 

    public static volatile SingularAttribute<Data, Integer> id;
    public static volatile SingularAttribute<Data, Integer> itemprice;
    public static volatile SingularAttribute<Data, Purchase> purchaseid;
    public static volatile SingularAttribute<Data, String> itemname;
    public static volatile SingularAttribute<Data, Integer> manufacturerid;
    public static volatile SingularAttribute<Data, String> manufacturername;
    public static volatile SingularAttribute<Data, Integer> itemid;

}