package org.example.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tester extends Employee {
    private String testType;

    public Tester(String id, String name, int exp, String proj, String testType) {
        super(id, name, exp, proj, 0);
        this.testType = testType;
        calculateSalary();
    }

    @Override
    public String getRole() { return "Tester"; }

    @Override
    public void calculateSalary() {

        this.salary = 1200 + (getExperience() * 100);
    }
}