package paygate.objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Token;
import paygate.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-16T12:02:07")
@StaticMetamodel(Responsecode.class)
public class Responsecode_ { 

    public static volatile SingularAttribute<Responsecode, Integer> id;
    public static volatile SingularAttribute<Responsecode, Integer> cardtype;
    public static volatile SingularAttribute<Responsecode, Date> created;
    public static volatile SingularAttribute<Responsecode, String> description;
    public static volatile ListAttribute<Responsecode, Token> tokenList;
    public static volatile SingularAttribute<Responsecode, String> code;
    public static volatile ListAttribute<Responsecode, Transaction> transactionList;
    public static volatile SingularAttribute<Responsecode, Date> modified;

}