package org.example.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Manager extends Employee {
    private int teamSize;

    public Manager(String id, String name, int experience, String project, int teamSize) {
        super(id, name, experience, project);
        this.teamSize = teamSize;
    }

    @Override
    public String getRole() { return "Manager"; }
}