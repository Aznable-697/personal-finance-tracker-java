/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mattw.finance.dao;

import com.mattw.finance.db.DatabaseConnection;
import com.mattw.finance.model.Bill;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *BillDao (Data Access Object)
 * ----------------------------
 * ONLY responsible for SQL + database operations.
 *
 * The CLI should NOT contain SQL.
 * The Service should NOT contain SQL.
 * Only this DAO should talk to the database.
 *
 * Note:
 * - This is a common professional pattern.
 * - Handles all bill-related database operations.
 * @author Matt W.
 */
public class BillDao {

    // SQL constants (keeps queries easy to find/change)
    private static final String INSERT_BILL_SQL =
            "INSERT INTO bills (name, amount_due, due_date) VALUES (?, ?, ?)";

    private static final String SELECT_ALL_BILLS_SQL =
            "SELECT id, name, amount_due, due_date FROM bills ORDER BY due_date ASC";

    // NEW: SQL statement to delete a bill by its database ID
    private static final String DELETE_BILL_BY_ID_SQL =
            "DELETE FROM bills WHERE id = ?";
    
    /**
     * Inserts a new Bill into the database.
     *
     * @param bill The bill to insert
     * @return true if insert succeeded, false otherwise
     */
    public boolean addBill(Bill bill) {

        // try-with-resources automatically closes Connection + PreparedStatement
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_BILL_SQL)) {

            // Parameter 1 = name
            stmt.setString(1, bill.getName());

            // Parameter 2 = amount_due (BigDecimal maps nicely to DECIMAL)
            stmt.setBigDecimal(2, bill.getAmountDue());

            // Parameter 3 = due_date (LocalDate -> java.sql.Date)
            stmt.setDate(3, Date.valueOf(bill.getDueDate()));

            // Execute insert; returns number of affected rows
            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            // In production we'd log this.
            System.out.println("DAO Error: Could not insert bill.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reads ALL bills from the database.
     *
     * @return List of Bill objects (could be empty if none exist)
     */
    public List<Bill> getAllBills() {

        List<Bill> bills = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_BILLS_SQL);
             ResultSet rs = stmt.executeQuery()) {

            // Loop each row in the ResultSet and convert it to a Bill object
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");

                // amount_due is DECIMAL in the DB, so use BigDecimal in Java
                BigDecimal amountDue = rs.getBigDecimal("amount_due");

                // due_date is DATE in the DB, so read as java.sql.Date then convert to LocalDate
                Date sqlDate = rs.getDate("due_date");
                LocalDate dueDate = sqlDate.toLocalDate();

                bills.add(new Bill(id, name, amountDue, dueDate));
            }

        } catch (SQLException e) {
            System.out.println("DAO Error: Could not retrieve bills.");
            e.printStackTrace();
        }

        return bills;
    }
    /**
     * Deletes a bill using its ID.
     *
     * @param billId the database ID of the bill
     * @return true if a row was deleted, false if no matching bill was found
     */
    public boolean deleteBillById(int billId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BILL_BY_ID_SQL)) {

            // Set the ? in "WHERE id = ?"
            stmt.setInt(1, billId);

            // executeUpdate returns number of affected rows
            int rowsDeleted = stmt.executeUpdate();

            // If rowsDeleted > 0, the delete worked
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("DAO Error: Could not delete bill.");
            e.printStackTrace();
            return false;
        }
    }
}
