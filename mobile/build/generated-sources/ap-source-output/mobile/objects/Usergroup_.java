package mobile.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mobile.objects.Siteuser;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-12T09:15:43")
@StaticMetamodel(Usergroup.class)
public class Usergroup_ { 

    public static volatile SingularAttribute<Usergroup, Integer> id;
    public static volatile SingularAttribute<Usergroup, Date> created;
    public static volatile SingularAttribute<Usergroup, String> description;
    public static volatile SingularAttribute<Usergroup, String> name;
    public static volatile ListAttribute<Usergroup, Siteuser> siteuserList;
    public static volatile SingularAttribute<Usergroup, Date> modified;

}