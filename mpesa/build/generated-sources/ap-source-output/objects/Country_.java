package objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Merchant;
import objects.Status;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-06T12:33:54")
@StaticMetamodel(Country.class)
public class Country_ { 

    public static volatile SingularAttribute<Country, Integer> id;
    public static volatile SingularAttribute<Country, Date> created;
    public static volatile SingularAttribute<Country, String> name;
    public static volatile SingularAttribute<Country, Status> statusid;
    public static volatile SingularAttribute<Country, String> code;
    public static volatile ListAttribute<Country, Merchant> merchantList;
    public static volatile SingularAttribute<Country, Date> modified;

}