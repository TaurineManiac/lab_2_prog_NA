package org.example.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tester extends Employee {
    private String testType;

    public Tester(String id, String name, int experience, String project, String testType) {
        super(id, name, experience, project);
        this.testType = testType;
    }

    @Override
    public String getRole() { return "Tester"; }
}