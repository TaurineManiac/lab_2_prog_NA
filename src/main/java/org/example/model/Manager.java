package org.example.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("Manager")
public class Manager extends Employee {
    private int teamSize;

    public Manager(String id, String name, int exp, String proj, int teamSize) {
        super(id, name, exp, proj, 0);
        this.teamSize = teamSize;
        calculateSalary();
    }

    @Override
    public String getRole() { return "Manager"; }

    @Override
    public void calculateSalary() {

        this.salary = 3000 + (teamSize * 50);
    }
}