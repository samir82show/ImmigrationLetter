package entity.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Employee implements Serializable {

    @Id
    @NotNull
    private String qid;

    @Basic
    private String Name;

    @Basic
    private String Nationality;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date DOB;

    @Basic
    private String MaritalStatus;

    @Basic
    private String Telephone;

    @Basic
    private String PassportNo;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date PassportExpiry;

    @Basic
    private String ResidencyType;

    @Basic
    private String Employer;

    @Basic
    private String Occupation;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date IDExpiry;

    @OneToMany(mappedBy = "employee")
    private List<Dependant> dependants;

    public Employee() {
    }

    public String getQid() {
        return this.qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNationality() {
        return this.Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public Date getDOB() {
        return this.DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getMaritalStatus() {
        return this.MaritalStatus;
    }

    public void setMaritalStatus(String MaritalStatus) {
        this.MaritalStatus = MaritalStatus;
    }

    public String getTelephone() {
        return this.Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getPassportNo() {
        return this.PassportNo;
    }

    public void setPassportNo(String PassportNo) {
        this.PassportNo = PassportNo;
    }

    public Date getPassportExpiry() {
        return this.PassportExpiry;
    }

    public void setPassportExpiry(Date PassportExpiry) {
        this.PassportExpiry = PassportExpiry;
    }

    public String getResidencyType() {
        return this.ResidencyType;
    }

    public void setResidencyType(String ResidencyType) {
        this.ResidencyType = ResidencyType;
    }

    public String getEmployer() {
        return this.Employer;
    }

    public void setEmployer(String Employer) {
        this.Employer = Employer;
    }

    public String getOccupation() {
        return this.Occupation;
    }

    public void setOccupation(String Occupation) {
        this.Occupation = Occupation;
    }

    public Date getIDExpiry() {
        return this.IDExpiry;
    }

    public void setIDExpiry(Date IDExpiry) {
        this.IDExpiry = IDExpiry;
    }

    public List<Dependant> getDependants() {
        if (dependants == null) {
            dependants = new ArrayList<>();
        }
        return this.dependants;
    }

    public void setDependants(List<Dependant> dependants) {
        this.dependants = dependants;
    }

    public void addDependant(Dependant dependant) {
        getDependants().add(dependant);
        dependant.setEmployee(this);
    }

    public void removeDependant(Dependant dependant) {
        getDependants().remove(dependant);
        dependant.setEmployee(null);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.qid);
        hash = 97 * hash + Objects.hashCode(this.Name);
        hash = 97 * hash + Objects.hashCode(this.Nationality);
        hash = 97 * hash + Objects.hashCode(this.DOB);
        hash = 97 * hash + Objects.hashCode(this.MaritalStatus);
        hash = 97 * hash + Objects.hashCode(this.Telephone);
        hash = 97 * hash + Objects.hashCode(this.PassportNo);
        hash = 97 * hash + Objects.hashCode(this.PassportExpiry);
        hash = 97 * hash + Objects.hashCode(this.ResidencyType);
        hash = 97 * hash + Objects.hashCode(this.Employer);
        hash = 97 * hash + Objects.hashCode(this.Occupation);
        hash = 97 * hash + Objects.hashCode(this.IDExpiry);
        hash = 97 * hash + Objects.hashCode(this.dependants);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.qid, other.qid)) {
            return false;
        }
        if (!Objects.equals(this.Name, other.Name)) {
            return false;
        }
        if (!Objects.equals(this.Nationality, other.Nationality)) {
            return false;
        }
        if (!Objects.equals(this.MaritalStatus, other.MaritalStatus)) {
            return false;
        }
        if (!Objects.equals(this.Telephone, other.Telephone)) {
            return false;
        }
        if (!Objects.equals(this.PassportNo, other.PassportNo)) {
            return false;
        }
        if (!Objects.equals(this.ResidencyType, other.ResidencyType)) {
            return false;
        }
        if (!Objects.equals(this.Employer, other.Employer)) {
            return false;
        }
        if (!Objects.equals(this.Occupation, other.Occupation)) {
            return false;
        }
        if (!Objects.equals(this.DOB, other.DOB)) {
            return false;
        }
        if (!Objects.equals(this.PassportExpiry, other.PassportExpiry)) {
            return false;
        }
        if (!Objects.equals(this.IDExpiry, other.IDExpiry)) {
            return false;
        }
        if (!Objects.equals(this.dependants, other.dependants)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return qid + "-" + Name;
    }

}
