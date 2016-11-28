package pg.objects;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-18T12:20:08")
@StaticMetamodel(Transactionfile.class)
public class Transactionfile_ { 

    public static volatile SingularAttribute<Transactionfile, Integer> id;
    public static volatile SingularAttribute<Transactionfile, Integer> transactions;
    public static volatile SingularAttribute<Transactionfile, BigInteger> paysure;
    public static volatile SingularAttribute<Transactionfile, BigInteger> merchant;
    public static volatile SingularAttribute<Transactionfile, Date> created;
    public static volatile SingularAttribute<Transactionfile, BigInteger> kenswitch;
    public static volatile SingularAttribute<Transactionfile, String> filename;
    public static volatile SingularAttribute<Transactionfile, BigInteger> chase;
    public static volatile SingularAttribute<Transactionfile, Date> filedate;
    public static volatile SingularAttribute<Transactionfile, Date> modified;

}