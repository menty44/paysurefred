package objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-06T12:33:54")
@StaticMetamodel(Config.class)
public class Config_ { 

    public static volatile SingularAttribute<Config, Integer> id;
    public static volatile SingularAttribute<Config, Date> created;
    public static volatile SingularAttribute<Config, String> description;
    public static volatile SingularAttribute<Config, String> value;
    public static volatile SingularAttribute<Config, String> config;
    public static volatile SingularAttribute<Config, Date> modified;

}