package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true

)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Developer.class, name = "Developer"),
        @JsonSubTypes.Type(value = Tester.class, name = "Tester"),
        @JsonSubTypes.Type(value = Manager.class, name = "Manager")
})
@Entity                           // 1. Tells Hibernate this class maps to a DB table
@Table(name = "employees")        // 2. Specifies the table name in PostgreSQL
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 3. Puts all subclasses in ONE table
@DiscriminatorColumn(name = "emp_type")
public abstract class Employee implements Payable, ProjectAssignable, java.io.Serializable {
    @Id
    private String id;
    private String name;
    private int experience;
    private String currentProject;
    protected double salary;

    @JsonIgnore
    public abstract String getRole();

    @Override
    public void assignProject(String projectName) {
        this.currentProject = projectName;
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | Name: %s | Exp: %d yrs | Project: %s | Salary: %.2f$",
                getRole(), getId(), getName(), getExperience(), getCurrentProject(), salary);
    }
}