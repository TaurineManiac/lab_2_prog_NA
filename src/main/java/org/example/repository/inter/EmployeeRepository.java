package org.example.repository.inter;

import org.example.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    void save(List<Employee> employees);
    List<Employee> getAll();
    Employee getById(int id);
    void deleteById(int id);
}
