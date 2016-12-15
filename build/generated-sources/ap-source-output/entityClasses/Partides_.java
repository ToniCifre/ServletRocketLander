package entityClasses;

import entityClasses.Usuari;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-15T16:45:42")
@StaticMetamodel(Partides.class)
public class Partides_ { 

    public static volatile SingularAttribute<Partides, Date> fecha;
    public static volatile SingularAttribute<Partides, Usuari> uId;
    public static volatile SingularAttribute<Partides, Float> punts;
    public static volatile SingularAttribute<Partides, Integer> coun;

}