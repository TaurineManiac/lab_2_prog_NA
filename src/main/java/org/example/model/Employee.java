package org.example.model;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Employee implements Payable, ProjectAssignable, java.io.Serializable {
    private String id;
    private String name;
    private int experience;
    private String currentProject;
    protected double salary;

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