package org.example;

import org.example.model.*;
import org.example.service.EmployeeManager;
import org.example.util.InputUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    private static final String ADMIN_PASS = "admin123";
    private static boolean isAdmin = false;
    private static final EmployeeManager manager = new EmployeeManager();

    public static void main(String[] args) {
        log.info("IT Management System started.");
        while (true) {
            printMenu();
            int choice = InputUtils.readInt("Select option: ", 0, 10);

            switch (choice) {
                case 1 -> addFlow();
                case 2 -> manager.getAll().forEach(System.out::println);
                case 3 -> deleteFlow();
                case 4 -> searchFlow();
                case 5 -> manager.printStatistics();
                case 6 -> manager.saveToFile("data.bin");
                case 7 -> manager.loadFromFile("data.bin");
                case 8 -> login();
                case 0 -> { log.info("Exiting."); System.exit(0); }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== IT COMPANY SYSTEM ===" + (isAdmin ? " [ADMIN]" : ""));
        System.out.println("1. Add | 2. View All | 3. Delete (Admin) | 4. Search");
        System.out.println("5. Stats | 6. Save | 7. Load | 8. Admin Login | 0. Exit");
    }

    private static void login() {
        String pass = InputUtils.readString("Enter Admin Password: ");
        if (pass.equals(ADMIN_PASS)) {
            isAdmin = true;
            System.out.println("Admin access granted.");
        } else {
            System.out.println("Access denied.");
        }
    }

    private static void addFlow() {
        int type = InputUtils.readInt("Type (1.Dev, 2.Tester, 3.Manager): ", 1, 3);
        String id = InputUtils.readString("ID: ");
        String name = InputUtils.readString("Name: ");
        int exp = InputUtils.readInt("Experience (years): ", 0, 50);
        String project = InputUtils.readString("Project: ");

        if (type == 1) manager.addEmployee(new Developer(id, name, exp, project, InputUtils.readString("Tech Stack: ")));
        else if (type == 2) manager.addEmployee(new Tester(id, name, exp, project, InputUtils.readString("Test Type: ")));
        else manager.addEmployee(new Manager(id, name, exp, project, InputUtils.readInt("Team Size: ", 1, 100)));
    }

    private static void deleteFlow() {
        if (!isAdmin) { System.out.println("Admin rights required!"); return; }
        String id = InputUtils.readString("ID to delete: ");
        if (manager.removeEmployee(id)) System.out.println("Deleted.");
        else System.out.println("Not found.");
    }

    private static void searchFlow() {
        String name = InputUtils.readString("Enter name to search: ");
        manager.getAll().stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .forEach(System.out::println);
    }
}