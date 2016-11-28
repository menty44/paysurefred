package paygate.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-08-01T16:04:17")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Integer> id;
    public static volatile SingularAttribute<Cart, String> name;
    public static volatile CollectionAttribute<Cart, Merchant> merchantCollection;

}