package paygate.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Cardtype;
import paygate.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-27T11:35:01")
@StaticMetamodel(Responsecode.class)
public class Responsecode_ { 

    public static volatile SingularAttribute<Responsecode, Integer> id;
    public static volatile SingularAttribute<Responsecode, Cardtype> cardtype;
    public static volatile SingularAttribute<Responsecode, Date> created;
    public static volatile SingularAttribute<Responsecode, String> description;
    public static volatile SingularAttribute<Responsecode, String> code;
    public static volatile ListAttribute<Responsecode, Transaction> transactionList;
    public static volatile SingularAttribute<Responsecode, Date> modified;

}