package org.example.service;

import org.example.model.*;
import lombok.extern.log4j.Log4j2;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) {
        employees.add(e);
        log.info("Employee added: {} (Role: {})", e.getName(), e.getRole());
    }

    public List<Employee> getAll() { return employees; }

    public List<Employee> searchByName(String name) {
        return employees.stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<Employee> searchBySkills(String skill) {
        return employees.stream()
                .filter(e -> e instanceof Developer)
                .filter(e -> ((Developer) e).getTechStack().toLowerCase().contains(skill.toLowerCase()))
                .toList();
    }

    public List<Employee> filterByRole(String role) {
        return employees.stream()
                .filter(e -> e.getRole().equalsIgnoreCase(role))
                .toList();
    }

    public List<Employee> filterByExperience(int min, int max) {
        return employees.stream()
                .filter(e -> e.getExperience() >= min && e.getExperience() <= max)
                .toList();
    }

    public List<Employee> filterByProject(String project) {
        return employees.stream()
                .filter(e -> e.getCurrentProject().equalsIgnoreCase(project))
                .toList();
    }

    public void sortByName() { employees.sort(Comparator.comparing(Employee::getName)); }
    public void sortByRole() { employees.sort(Comparator.comparing(Employee::getRole)); }

    public void printStatistics() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- IT Company Statistics ---");
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(Employee::getRole));

        grouped.forEach((role, list) -> {
            double avgExp = list.stream().mapToInt(Employee::getExperience).average().orElse(0);
            System.out.printf("Role: %-10s | Count: %d | Avg Exp: %.1f years\n",
                    role, list.size(), avgExp);
        });
    }

    public boolean removeEmployee(String id) { return employees.removeIf(e -> e.getId().equals(id)); }

    public boolean editProject(String id, String newProject) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> { e.assignProject(newProject); return true; })
                .orElse(false);
    }

    public void saveToFile(String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(employees);
            log.info("Data saved to {}", path);
        } catch (IOException e) {
            log.error("Save error", e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            employees = (List<Employee>) ois.readObject();
            log.info("Data loaded from {}", path);
        } catch (Exception e) {
            log.error("Load error", e);
        }
    }
}