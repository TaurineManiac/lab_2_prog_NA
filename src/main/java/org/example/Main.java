package org.example;

import org.example.controller.MenuController;
import org.example.service.EmployeeManager;
import org.example.util.InputUtils;
import org.example.model.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    private static final EmployeeManager manager = new EmployeeManager();
    private static final MenuController menuController = new MenuController(manager);
    private static final String ADMIN_PASS = "admin123";
    private static boolean isAdmin = false;

    public static void main(String[] args) {
        log.info("System started.");

        while (true) {
            printMainMenu();
            int choice = InputUtils.readInt("Select option: ", 0, 11);

            switch (choice) {
                case 1 -> addFlow();
                case 2 -> manager.getAll().forEach(System.out::println);
                case 3 -> menuController.showSearchMenu();
                case 4 -> menuController.showSortMenu();
                case 5 -> manager.printStatistics();
                case 6 -> handleAdminAction();
                case 7 -> manager.loadFromFile("data.bin");
                case 8 -> login();

                case 9 -> manager.saveToFile("data.bin");
                case 10 -> deleteFlow();
                case 11 -> menuController.showFilterMenu();
                case 0 -> {
                    log.info("Exiting...");
                    System.exit(0);
                }
            }
        }
    }


    private static void deleteFlow() {
        if (!isAdmin) {
            System.out.println("Access Denied! Admin rights required.");
            return;
        }
        String id = InputUtils.readString("Enter Employee ID to delete: ");
        if (manager.removeEmployee(id)) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private static void printMainMenu() {
        System.out.println("\n===== IT COMPANY MANAGEMENT =====");
        System.out.println("1. Add Employee      | 2. View All");
        System.out.println("3. Search            | 4. Sorting");
        System.out.println("5. Statistics        | 6. Edit Project (Admin)");
        System.out.println("7. Load Data         | 8. Login Admin");
        System.out.println("9. Save Data         | 10. Delete Employee (Admin)");
        System.out.println("11. Filter");
        System.out.println("0. Exit");
        if (isAdmin) System.out.println(">>> LOGGED AS ADMIN <<<");
    }

    private static void handleAdminAction() {
        if (!isAdmin) {
            System.out.println("Access Denied. Please login first (Option 8).");
            return;
        }
        String id = InputUtils.readString("Enter ID: ");
        String proj = InputUtils.readString("New Project: ");

        if (manager.editProject(id, proj)) {
            System.out.println("Updated successfully!");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void login() {
        String pass = InputUtils.readString("Admin Password: ");
        isAdmin = pass.equals(ADMIN_PASS);
        System.out.println(isAdmin ? "Login successful!" : "Wrong password.");
    }

    private static void addFlow() {
        System.out.println("\n--- Adding New Employee ---");
        int type = InputUtils.readInt("1. Developer | 2. Tester | 3. Manager: ", 1, 3);
        String id = InputUtils.readString("ID: ");
        String name = InputUtils.readString("Name: ");
        int exp = InputUtils.readInt("Experience (years): ", 0, 50);
        String proj = InputUtils.readString("Project: ");


        if (type == 1) {
            String stack = InputUtils.readString("Tech Stack: ");
            manager.addEmployee(new Developer(id, name, exp, proj, stack));
        } else if (type == 2) {
            String testType = InputUtils.readString("Test Type (Manual/Auto): ");
            manager.addEmployee(new Tester(id, name, exp, proj, testType));
        } else {
            int teamSize = InputUtils.readInt("Team Size: ", 1, 100);
            manager.addEmployee(new Manager(id, name, exp, proj, teamSize));
        }

        System.out.println("Employee added successfully!");
    }
}