package entity.domain;

import entity.domain.Dependant;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-27T13:22:50")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> Telephone;
    public static volatile SingularAttribute<Employee, Date> IDExpiry;
    public static volatile SingularAttribute<Employee, String> Employer;
    public static volatile SingularAttribute<Employee, String> qid;
    public static volatile SingularAttribute<Employee, String> Nationality;
    public static volatile SingularAttribute<Employee, String> Name;
    public static volatile SingularAttribute<Employee, String> MaritalStatus;
    public static volatile SingularAttribute<Employee, String> Occupation;
    public static volatile ListAttribute<Employee, Dependant> dependants;
    public static volatile SingularAttribute<Employee, Date> DOB;
    public static volatile SingularAttribute<Employee, String> PassportNo;
    public static volatile SingularAttribute<Employee, Date> PassportExpiry;
    public static volatile SingularAttribute<Employee, String> ResidencyType;

}