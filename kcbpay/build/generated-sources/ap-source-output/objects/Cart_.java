package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-28T11:17:43")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Integer> id;
    public static volatile SingularAttribute<Cart, String> name;
    public static volatile ListAttribute<Cart, Merchant> merchantList;

}