package paygate.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Purchase;
import paygate.objects.Token;
import paygate.objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-16T12:02:07")
@StaticMetamodel(Transactionsource.class)
public class Transactionsource_ { 

    public static volatile SingularAttribute<Transactionsource, Integer> id;
    public static volatile ListAttribute<Transactionsource, Purchase> purchaseList;
    public static volatile SingularAttribute<Transactionsource, String> name;
    public static volatile ListAttribute<Transactionsource, Token> tokenList;
    public static volatile ListAttribute<Transactionsource, Transaction> transactionList;

}