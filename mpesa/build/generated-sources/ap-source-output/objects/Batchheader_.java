package objects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Batchitem;
import objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-11-06T12:33:54")
@StaticMetamodel(Batchheader.class)
public class Batchheader_ { 

    public static volatile SingularAttribute<Batchheader, Integer> id;
    public static volatile SingularAttribute<Batchheader, Integer> requests;
    public static volatile SingularAttribute<Batchheader, String> status;
    public static volatile SingularAttribute<Batchheader, String> batchname;
    public static volatile SingularAttribute<Batchheader, String> description;
    public static volatile ListAttribute<Batchheader, Batchitem> batchitemList;
    public static volatile SingularAttribute<Batchheader, Date> date;
    public static volatile SingularAttribute<Batchheader, String> currency;
    public static volatile SingularAttribute<Batchheader, Merchant> merchantid;

}