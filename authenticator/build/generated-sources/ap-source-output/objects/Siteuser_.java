package objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Merchant;
import objects.Usergroup;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-17T16:19:37")
@StaticMetamodel(Siteuser.class)
public class Siteuser_ { 

    public static volatile SingularAttribute<Siteuser, Integer> id;
    public static volatile SingularAttribute<Siteuser, String> username;
    public static volatile SingularAttribute<Siteuser, Date> created;
    public static volatile SingularAttribute<Siteuser, String> lastname;
    public static volatile SingularAttribute<Siteuser, Usergroup> usergroupid;
    public static volatile SingularAttribute<Siteuser, String> firstname;
    public static volatile SingularAttribute<Siteuser, String> password;
    public static volatile SingularAttribute<Siteuser, Date> lastaccess;
    public static volatile SingularAttribute<Siteuser, Date> modified;
    public static volatile SingularAttribute<Siteuser, Merchant> merchantid;

}