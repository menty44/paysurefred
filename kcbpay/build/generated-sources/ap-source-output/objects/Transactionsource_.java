package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Purchase;
import objects.Token;
import objects.Transaction;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-28T11:17:43")
@StaticMetamodel(Transactionsource.class)
public class Transactionsource_ { 

    public static volatile SingularAttribute<Transactionsource, Integer> id;
    public static volatile ListAttribute<Transactionsource, Purchase> purchaseList;
    public static volatile SingularAttribute<Transactionsource, String> name;
    public static volatile ListAttribute<Transactionsource, Token> tokenList;
    public static volatile ListAttribute<Transactionsource, Transaction> transactionList;

}