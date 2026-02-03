package org.example.controller;

import org.example.service.EmployeeManager;
import org.example.util.InputUtils;

public class MenuController {
//
//    private final EmployeeManager manager;
//
//    public MenuController(EmployeeManager manager) {
//        this.manager = manager;
//    }
//
//    public void showSearchMenu() {
//        System.out.println("\n--- SEARCH MENU ---");
//        System.out.println("1. Search by Name");
//        System.out.println("2. Search by Tech Stack (Developers only)");
//        System.out.println("3. Search by Salary by id");
//        System.out.println("4. Search by Project by id");
//        System.out.println("0. Back");
//
//        int choice = InputUtils.readInt("> ", 0, 4);
//        if (choice == 1) {
//            String name = InputUtils.readString("Enter name: ");
//            manager.searchByName(name).forEach(System.out::println);
//        } else if (choice == 2) {
//            String skill = InputUtils.readString("Enter tech skill: ");
//            manager.searchBySkills(skill).forEach(System.out::println);
//        }
//        else if (choice == 3) {
//            int id = InputUtils.readOneInt("Enter ID: ");
//            manager.printSalaryPerId(id);
//        }
//        else if (choice == 4) {
//            int id = InputUtils.readOneInt("Enter ID: ");
//            manager.printProjectPerId(id);
//        }
//    }
//
//    public void showSortMenu() {
//        System.out.println("\n--- SORTING MENU ---");
//        System.out.println("1. Sort by Name (A-Z)");
//        System.out.println("2. Sort by Role");
//        System.out.println("0. Back");
//
//        int choice = InputUtils.readInt("> ", 0, 2);
//        if (choice == 1) manager.sortByName();
//        else if (choice == 2) manager.sortByRole();
//
//        manager.getAll().forEach(System.out::println);
//    }
//
//    public void showFilterMenu() {
//        System.out.println("\n--- FILTER MENU ---");
//        System.out.println("1. Filter by Role");
//        System.out.println("2. Filter by Experience (Range)");
//        System.out.println("3. Filter by Project");
//        System.out.println("0. Back");
//
//        int choice = InputUtils.readInt("> ", 0, 3);
//        switch (choice) {
//            case 1 -> {
//                String role = InputUtils.readString("Enter role: ");
//                manager.filterByRole(role).forEach(System.out::println);
//            }
//            case 2 -> {
//                int min = InputUtils.readInt("Min experience (years): ", 0, 50);
//                int max = InputUtils.readInt("Max experience (years): ", 0, 50);
//                manager.filterByExperience(min, max).forEach(System.out::println);
//            }
//            case 3 -> {
//                String proj = InputUtils.readString("Enter project name: ");
//                manager.filterByProject(proj).forEach(System.out::println);
//            }
//        }
//    }
//
//    public void showStatisticMenu() {
//        System.out.println("\n--- STATISTICS MENU ---");
//        System.out.println("1. General Role Statistics (Count & Avg Exp)");
//        System.out.println("2. Top 3 Employees by Salary");
//        System.out.println("3. Top 3 Employees by Experience");
//        System.out.println("0. Back");
//
//        int choice = InputUtils.readInt("> ", 0, 3);
//        switch (choice) {
//            case 1 -> manager.printStatistics();
//            case 2 -> manager.printTopThreePerSalary();
//            case 3 -> manager.printTopThreePerExperience();
//            case 0 -> { /* Просто возврат в главное меню */ }
//        }
//    }
}