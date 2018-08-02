package entity.domain;

import entity.domain.Employee;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-27T13:22:50")
@StaticMetamodel(Dependant.class)
public class Dependant_ { 

    public static volatile SingularAttribute<Dependant, String> Occupation;
    public static volatile SingularAttribute<Dependant, String> MyHost;
    public static volatile SingularAttribute<Dependant, Date> IDExpiry;
    public static volatile SingularAttribute<Dependant, Date> DOB;
    public static volatile SingularAttribute<Dependant, String> PassportNo;
    public static volatile SingularAttribute<Dependant, Date> PassportExpiry;
    public static volatile SingularAttribute<Dependant, Employee> employee;
    public static volatile SingularAttribute<Dependant, String> qid;
    public static volatile SingularAttribute<Dependant, String> Nationality;
    public static volatile SingularAttribute<Dependant, String> ResidencyType;
    public static volatile SingularAttribute<Dependant, String> Name;

}