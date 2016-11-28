package paygate.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Purchase;
import paygate.objects.Token;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-16T12:02:07")
@StaticMetamodel(Gender.class)
public class Gender_ { 

    public static volatile SingularAttribute<Gender, Integer> id;
    public static volatile ListAttribute<Gender, Purchase> purchaseList;
    public static volatile SingularAttribute<Gender, String> value;
    public static volatile ListAttribute<Gender, Token> tokenList;

}