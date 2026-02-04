package org.example.util;

import lombok.extern.log4j.Log4j2;
import java.util.Scanner;

@Log4j2
public class InputUtils {
//    private static final Scanner scanner = new Scanner(System.in);
//
//    public static String readString(String prompt) {
//        while (true) {
//            System.out.print(prompt);
//            String input = scanner.nextLine().trim();
//            if (!input.isEmpty()) return input;
//            System.out.println("Error: Input cannot be empty!");
//            log.warn("User provided empty string input.");
//        }
//    }
//
//    public static int readInt(String prompt, int min, int max) {
//        while (true) {
//            System.out.print(prompt);
//            try {
//                int value = Integer.parseInt(scanner.nextLine());
//                if (value >= min && value <= max) return value;
//                System.out.printf("Error: Please enter a number between %d and %d\n", min, max);
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Invalid number format!");
//                log.error("Invalid number input attempt.");
//            }
//        }
//    }
//
//    public static int readOneInt(String prompt) {
//        while (true) {
//            System.out.print(prompt);
//            try {
//                int value = Integer.parseInt(scanner.nextLine());
//                return value;
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Invalid number format!");
//                log.error("Invalid number input attempt.");
//            }
//        }
//    }
}