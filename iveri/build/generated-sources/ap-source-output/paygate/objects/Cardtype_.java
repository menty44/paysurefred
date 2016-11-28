package paygate.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import paygate.objects.Responsecode;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-01-09T12:39:55")
@StaticMetamodel(Cardtype.class)
public class Cardtype_ { 

    public static volatile SingularAttribute<Cardtype, Integer> id;
    public static volatile CollectionAttribute<Cardtype, Responsecode> responsecodeCollection;
    public static volatile SingularAttribute<Cardtype, String> description;

}