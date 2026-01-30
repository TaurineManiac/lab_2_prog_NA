package org.example.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Developer extends Employee {
    private String techStack;

    public Developer(String id, String name, int exp, String proj, String stack) {
        super(id, name, exp, proj, 0);
        this.techStack = stack;
        calculateSalary();
    }

    @Override
    public String getRole() { return "Developer"; }

    @Override
    public void calculateSalary() {

        this.salary = 2000 + (getExperience() * 150);
    }
}