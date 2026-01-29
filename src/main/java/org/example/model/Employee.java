package org.example.model;

import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int experience;
    private String currentProject;

    public abstract String getRole();
}