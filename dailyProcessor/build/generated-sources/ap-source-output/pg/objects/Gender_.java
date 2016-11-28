package pg.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pg.objects.Purchase;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-18T12:20:08")
@StaticMetamodel(Gender.class)
public class Gender_ { 

    public static volatile SingularAttribute<Gender, Integer> id;
    public static volatile CollectionAttribute<Gender, Purchase> purchaseCollection;
    public static volatile SingularAttribute<Gender, String> value;

}