package paygate.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Siteuser;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-01-09T12:39:55")
@StaticMetamodel(Usergroup.class)
public class Usergroup_ { 

    public static volatile SingularAttribute<Usergroup, Integer> id;
    public static volatile SingularAttribute<Usergroup, Date> created;
    public static volatile SingularAttribute<Usergroup, String> description;
    public static volatile SingularAttribute<Usergroup, String> name;
    public static volatile CollectionAttribute<Usergroup, Siteuser> siteuserCollection;
    public static volatile SingularAttribute<Usergroup, Date> modified;

}