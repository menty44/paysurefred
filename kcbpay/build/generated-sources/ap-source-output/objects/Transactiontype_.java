package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Purchase;
import objects.Token;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-28T11:17:43")
@StaticMetamodel(Transactiontype.class)
public class Transactiontype_ { 

    public static volatile SingularAttribute<Transactiontype, Integer> id;
    public static volatile ListAttribute<Transactiontype, Purchase> purchaseList;
    public static volatile SingularAttribute<Transactiontype, String> description;
    public static volatile SingularAttribute<Transactiontype, String> value;
    public static volatile ListAttribute<Transactiontype, Token> tokenList;

}