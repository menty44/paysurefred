package pg.objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pg.objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-18T12:20:08")
@StaticMetamodel(Category.class)
public class Category_ { 

    public static volatile SingularAttribute<Category, Integer> id;
    public static volatile SingularAttribute<Category, String> description;
    public static volatile SingularAttribute<Category, String> name;
    public static volatile CollectionAttribute<Category, Merchant> merchantCollection;

}