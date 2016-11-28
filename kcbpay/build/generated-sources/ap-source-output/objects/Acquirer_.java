package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-28T11:17:43")
@StaticMetamodel(Acquirer.class)
public class Acquirer_ { 

    public static volatile SingularAttribute<Acquirer, Integer> id;
    public static volatile SingularAttribute<Acquirer, String> name;
    public static volatile ListAttribute<Acquirer, Merchant> merchantList;
    public static volatile SingularAttribute<Acquirer, String> url;

}