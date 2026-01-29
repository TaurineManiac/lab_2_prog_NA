package org.example.service;

import org.example.model.Employee;
import lombok.extern.log4j.Log4j2;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) {
        employees.add(e);
        log.info("Employee added: {} (ID: {})", e.getName(), e.getId());
    }

    public boolean removeEmployee(String id) {
        boolean removed = employees.removeIf(e -> e.getId().equals(id));
        if (removed) log.info("Employee with ID: {} removed", id);
        return removed;
    }

    public List<Employee> getAll() { return employees; }

    public void printStatistics() {
        if (employees.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        Map<String, Long> countByRole = employees.stream()
                .collect(Collectors.groupingBy(Employee::getRole, Collectors.counting()));

        System.out.println("\n--- IT Company Statistics ---");
        countByRole.forEach((role, count) -> System.out.printf("Role: %s | Count: %d\n", role, count));
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(employees);
            log.info("Database saved to {}", filename);
        } catch (IOException e) {
            log.error("Save failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            employees = (List<Employee>) ois.readObject();
            log.info("Database loaded from {}", filename);
        } catch (Exception e) {
            log.error("Load failed", e);
        }
    }
}