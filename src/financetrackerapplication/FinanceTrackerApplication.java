/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package financetrackerapplication;

//Import files here
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Matt
 * @date January 23 2025 - Started working on project
 * @date February 12 2025 - Added MYSQL Database to program to store data
 */
public class FinanceTrackerApplication {
    
    private static List<String> transactions = new ArrayList<>();
    private static double balance = 0.0;
    
    public static void main(String[] args) {
        // TODO code application logic here
       Scanner scanner = new Scanner(System.in);
       
       while(true) {
          System.out.println("Personal Finance Tracker");
          System.out.println("1. Add Bill");
          System.out.println("2. View Bills");
          System.out.println("3. Exit");
          System.out.println("Please ENTER your Choice: ");
          int choice = scanner.nextInt();
          scanner.nextLine();
          
          switch (choice) {
           case 1:
               addBill(scanner);
               break;
           case 2:
               viewBills();
               break;
           case 3:
               System.out.println("Goodbye!");
               return;
           default:
               System.out.println("Invalid choice! Please try again.");
       }
     }
   }
    
    // Method to add Bill to the Database
    public static void addBill(Scanner scanner) {
        
        System.out.print("Please Enter the New Bills Name");
        String billName = scanner.nextLine();
        
        System.out.print("Please Enter the Amount Due: ");
        double amountDue = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Please Enter the Due Date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();
        
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO bills (name, amount_due, due_date) VALUES (?, ?, ?)")) {
            
            stmt.setString(1, billName);
            
            stmt.setDouble(2, amountDue);
            
            stmt.setString(3, dueDate);
            
           int rowInserted = stmt.executeUpdate();
           if (rowInserted > 0){
            System.out.println("Bill added successfully!");
           }
        } catch (SQLException e) {
            System.out.println("Error inserting bill.");
            e.printStackTrace();
        }
    }
    
    // Method to View the Stored Bills
    public static void viewBills() {
        String query = "SELECT * FROM bills";
        
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                java.sql.ResultSet rs = stmt.executeQuery()) {
                    
                    System.out.println("\nStored Bills:");
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id") +
                                ", Name: " + rs.getString("name") +
                                ", Amount Due: $" + rs.getDouble("amount_due") +
                                ", Due Date: " + rs.getDate("due_date"));
                    }
                } catch (SQLException e) {
                        System.out.println("Error retrieving bills.");
                        e.printStackTrace();
                        }
    }
    
// public static void addTransaction(String type, double amount) {
//     balance += amount;
//     transactions.add(type + ": $" + amount);
// }
// 
// public static void viewTransactions() {
//     if (transactions.isEmpty()) {
//           System.out.println("No transactions yet.");
//     } else {
//         System.out.println("Transaction History:");
//         for (String transaction : transactions) {
//             System.out.println(transaction);
//         }
//     }
// }
// 
// public static void viewBalance() {
//     System.out.println("Current balance: $" + balance);
// }
// 
// public static void saveData() {
//     try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt"))) {
//         writer.write("Balance: $" + balance + "\n");
//         for (String transaction : transactions) {
//             writer.write(transaction + "\n");
//         }
//     } catch (IOException e) {
//         System.out.println("Error saving data.");
//     }
// }
    
}
