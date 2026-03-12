/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mattw.finance;

//Import files here
import com.mattw.finance.model.Bill;
import com.mattw.finance.service.BillService;

import java.util.List;
import java.util.Scanner;
/**
 ** FinanceTrackerApplication (CLI)
 * -------------------------------
 * This is the "front-end" for Version 1 (console app).
 *
 * Responsibilities:
 * - Display menu
 * - Collect user input
 * - Call service methods
 *
 * NOT responsible for:
 * - SQL
 * - database connections
 * - complex validation logic
 * @author Matt
 * @date January 23 2025 - Started working on project
 * @date February 12 2025 - Added MYSQL Database to program to store data
 * @date March 04 2026 - Massive update to application and added initial app to GitHub.
 */
public class FinanceTrackerApplication {
    
   // Service layer handles validation + calls DAO
    private static final BillService billService = new BillService();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            // Menu
            System.out.println("\nPersonal Finance Tracker");
            System.out.println("1. Add Bill");
            System.out.println("2. View Bills");
            System.out.println("3. Delete Bill");
            System.out.println("4. Exit");
            System.out.print("Please ENTER your Choice: ");

            // Read input safely as string first (prevents Scanner weirdness)
            String choiceRaw = scanner.nextLine().trim();

            switch (choiceRaw) {
                case "1":
                    addBillFlow(scanner);
                    break;

                case "2":
                    viewBillsFlow();
                    break;

                case "3":
                    deleteBillFlow(scanner);
                    break;
                 case "4":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Handles the user interaction for adding a bill.
     * (Collect input -> pass to service)
     */
    private static void addBillFlow(Scanner scanner) {

        System.out.print("Please Enter the New Bill's Name: ");
        String billName = scanner.nextLine();

        System.out.print("Please Enter the Amount Due (example: 250.00): ");
        String amountDue = scanner.nextLine();

        System.out.print("Please Enter the Due Date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();

        boolean success = billService.addBillFromUserInput(billName, amountDue, dueDate);

        if (success) {
            System.out.println("Bill added successfully!");
        } else {
            System.out.println("Bill was NOT added. Please correct the input and try again.");
        }
    }

    /**
     * Handles the user interaction for viewing bills.
     * (Call service -> print results)
     */
    private static void viewBillsFlow() {

        List<Bill> bills = billService.getAllBills();

        System.out.println("\nStored Bills:");

        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }

        for (Bill bill : bills) {
            // Simple formatting for clean output
            System.out.println(
                    "ID: " + bill.getId()
                            + ", Name: " + bill.getName()
                            + ", Amount Due: $" + bill.getAmountDue()
                            + ", Due Date: " + bill.getDueDate()
            );
        }
    }
    
    /**
     * Handles deleting a bill by its ID.
     */
    private static void deleteBillFlow(Scanner scanner) {

        // Helpful UX: show bills before asking for the ID
        viewBillsFlow();

        System.out.print("\nEnter the Bill ID to delete: ");
        String billIdRaw = scanner.nextLine();

        boolean success = billService.deleteBillById(billIdRaw);

        if (success) {
            System.out.println("Bill deleted successfully!");
        } else {
            System.out.println("No bill was deleted. Please verify the ID and try again.");
        }
    }
}
