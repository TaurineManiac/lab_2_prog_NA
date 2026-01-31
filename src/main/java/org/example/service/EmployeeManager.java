package org.example.service;

import org.apache.logging.log4j.core.util.JsonUtils;
import org.example.model.*;
import lombok.extern.log4j.Log4j2;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Log4j2
public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();
    private HashMap<Integer, Double> salaryMap = new HashMap<>();
    private HashMap<Integer, String> projectMap = new HashMap<>();
    private List<String> transfersBetweenProjects = new LinkedList<>();

    public void addEmployee(Employee e) {
        employees.add(e);
        salaryMap.put(parseInt(e.getId()),e.getSalary());
        projectMap.put(parseInt(e.getId()), e.getCurrentProject());
        log.info("Employee added: {} (Role: {})", e.getName(), e.getRole());
    }

    public void addTransferBetweenProjects(Employee e,String project) {
        transfersBetweenProjects.add(e.getId() + ": " + e.getCurrentProject() + " -> " + project);
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

    public void printTopThreePerSalary() {
        if (employees.isEmpty()) {
            log.warn("No employees found.");
        }
            employees.stream()
                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
                    .limit(3)
                    .forEach(e -> System.out.printf("Name: %-15s | Salary: %.2f$\n", e.getName(), e.getSalary()));
    }

    public void printTopThreePerExperience() {
        if (employees.isEmpty()) {
            log.warn("No employees found.");
        }
        employees.stream()
                .sorted(Comparator.comparing(Employee::getExperience).reversed())
                .limit(3)
                .forEach(e -> System.out.printf("Name: %-15s | Experience: %d$\n", e.getName(), e.getExperience()));
    }

    public void printSalaryPerId(int id) {
        if (salaryMap.isEmpty()) {
            log.warn("No employees found.");
        }
        Double salary = salaryMap.get(id);
        if(salary == null) {
            log.warn("No employees found.");
        }
        else {
            System.out.println(salary);
        }
    }

    public void printTransfers() {
        System.out.println("\n--- Project Transfer History ---");
        if (transfersBetweenProjects.isEmpty()) System.out.println("No transfers yet.");
        transfersBetweenProjects.forEach(System.out::println);
    }

    public void printProjectPerId(int id) {
        if (projectMap.isEmpty()) {
            log.warn("No employees found.");
        }
        String projectName = projectMap.get(id);
        if(projectName == null) {
            log.warn("No employees found.");
        }
        else {
            System.out.println(projectName);
        }
    }

    public boolean removeEmployee(String id) { return employees.removeIf(e -> e.getId().equals(id)); }

    public boolean editProject(String id, String newProject) {

        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    addTransferBetweenProjects(e,newProject);
                    projectMap.put(parseInt(e.getId()),newProject);
                    e.assignProject(newProject);
                    return true; })
                .orElse(false);
    }

    public void saveToFile(String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(projectMap);
            oos.writeObject(salaryMap);
            oos.writeObject(employees);
            oos.writeObject(transfersBetweenProjects);
            log.info("Data saved to {}", path);
        } catch (IOException e) {
            log.error("Save error", e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            projectMap = (HashMap<Integer, String>) ois.readObject();
            salaryMap = (HashMap<Integer, Double>) ois.readObject();
            employees = (List<Employee>) ois.readObject();
            transfersBetweenProjects = (LinkedList<String>) ois.readObject();
            log.info("Data loaded from {}", path);
        } catch (Exception e) {
            log.error("Load error", e);
        }
    }
}