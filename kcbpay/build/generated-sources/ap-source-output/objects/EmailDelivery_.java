package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Batchitem;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-28T11:17:43")
@StaticMetamodel(EmailDelivery.class)
public class EmailDelivery_ { 

    public static volatile SingularAttribute<EmailDelivery, Integer> id;
    public static volatile SingularAttribute<EmailDelivery, Integer> status;
    public static volatile SingularAttribute<EmailDelivery, String> description;
    public static volatile ListAttribute<EmailDelivery, Batchitem> batchitemList;

}