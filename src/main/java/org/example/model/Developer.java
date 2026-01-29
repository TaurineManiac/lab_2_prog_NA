package org.example.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Developer extends Employee {
    private String techStack;

    public Developer(String id, String name, int experience, String project, String techStack) {
        super(id, name, experience, project);
        this.techStack = techStack;
    }

    @Override
    public String getRole() { return "Developer"; }
}