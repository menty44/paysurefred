package objects;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objects.Merchant;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2014-02-17T16:19:37")
@StaticMetamodel(Category.class)
public class Category_ { 

    public static volatile SingularAttribute<Category, Integer> id;
    public static volatile SingularAttribute<Category, String> description;
    public static volatile SingularAttribute<Category, String> name;
    public static volatile CollectionAttribute<Category, Merchant> merchantCollection;

}