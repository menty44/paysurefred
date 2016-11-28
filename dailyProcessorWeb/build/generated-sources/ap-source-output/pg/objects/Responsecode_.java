package pg.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pg.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-20T16:43:10")
@StaticMetamodel(Responsecode.class)
public class Responsecode_ { 

    public static volatile SingularAttribute<Responsecode, Integer> id;
    public static volatile SingularAttribute<Responsecode, Integer> cardtype;
    public static volatile SingularAttribute<Responsecode, Date> created;
    public static volatile SingularAttribute<Responsecode, String> description;
    public static volatile SingularAttribute<Responsecode, String> code;
    public static volatile CollectionAttribute<Responsecode, Transaction> transactionCollection;
    public static volatile SingularAttribute<Responsecode, Date> modified;

}