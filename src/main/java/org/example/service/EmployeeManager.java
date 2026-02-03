package org.example.service;

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
        salaryMap.put(parseInt(e.getId()), e.getSalary());
        projectMap.put(parseInt(e.getId()), e.getCurrentProject());
        log.info("Employee added: {} (Role: {})", e.getName(), e.getRole());
    }

    public List<Employee> getAll() { return new ArrayList<>(employees); }


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

    public String getStatisticsAsString() {
        if (employees.isEmpty()) return "No employees found.";

        StringBuilder sb = new StringBuilder("\n--- IT Company Statistics ---\n");
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(Employee::getRole));

        grouped.forEach((role, list) -> {
            double avgExp = list.stream().mapToInt(Employee::getExperience).average().orElse(0);
            sb.append(String.format("Role: %-10s | Count: %d | Avg Exp: %.1f years\n",
                    role, list.size(), avgExp));
        });
        return sb.toString();
    }

    public String getTopThreePerSalaryAsString() {
        if (employees.isEmpty()) return "No employees found.";

        StringBuilder sb = new StringBuilder("\n--- Top 3 Salary ---\n");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3)
                .forEach(e -> sb.append(String.format("Name: %-15s | Salary: %.2f$\n", e.getName(), e.getSalary())));
        return sb.toString();
    }

    public String getTopThreePerExperienceAsString() {
        if (employees.isEmpty()) return "No employees found.";

        StringBuilder sb = new StringBuilder("\n--- Top 3 Experience ---\n");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getExperience).reversed())
                .limit(3)
                .forEach(e -> sb.append(String.format("Name: %-15s | Experience: %d years\n", e.getName(), e.getExperience())));
        return sb.toString();
    }

    public String getSalaryByIdAsString(int id) {
        Double salary = salaryMap.get(id);
        return (salary == null) ? "Employee not found." : "Salary: " + salary + "$";
    }

    public String getTransfersAsString() {
        if (transfersBetweenProjects.isEmpty()) return "No transfers yet.";
        return "\n--- Project Transfer History ---\n" + String.join("\n", transfersBetweenProjects);
    }

    public boolean removeEmployee(String id) {
        boolean removed = employees.removeIf(e -> e.getId().equals(id));
        if (removed) {
            salaryMap.remove(parseInt(id));
            projectMap.remove(parseInt(id));
        }
        return removed;
    }

    public boolean editProject(String id, String newProject) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    transfersBetweenProjects.add(e.getId() + ": " + e.getCurrentProject() + " -> " + newProject);
                    projectMap.put(parseInt(e.getId()), newProject);
                    e.assignProject(newProject);
                    return true;
                })
                .orElse(false);
    }

    public void saveToFile(String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(projectMap);
            oos.writeObject(salaryMap);
            oos.writeObject(employees);
            oos.writeObject(transfersBetweenProjects);
            log.info("Data saved to {}", path);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String path) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            projectMap = (HashMap<Integer, String>) ois.readObject();
            salaryMap = (HashMap<Integer, Double>) ois.readObject();
            employees = (List<Employee>) ois.readObject();
            transfersBetweenProjects = (LinkedList<String>) ois.readObject();
            log.info("Data loaded from {}", path);
        }
    }
}