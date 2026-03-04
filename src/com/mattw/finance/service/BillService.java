/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mattw.finance.service;

import com.mattw.finance.dao.BillDao;
import com.mattw.finance.model.Bill;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * BillService (Business Logic)
 * ----------------------------
 * Handles validation and "business rules".
 *
 * The CLI should:
 * - collect input
 * - call service methods
 *
 * The Service should:
 * - validate and parse input
 * - call DAO methods
 *
 * Note:
 * - Separating Service from DAO is a big professionalism boost.
 * @author Matt W.
 */
public class BillService {

    // The service "depends on" the DAO
    private final BillDao billDao;

    public BillService() {
        // Simple approach: create DAO here.
        // Later upgrade: dependency injection.
        this.billDao = new BillDao();
    }

    /**
     * Validates raw input and attempts to create + save a bill.
     *
     * @param nameRaw     bill name from user
     * @param amountRaw   amount from user (string form so we can validate)
     * @param dueDateRaw  date from user (YYYY-MM-DD)
     * @return true if saved successfully, false otherwise
     */
    public boolean addBillFromUserInput(String nameRaw, String amountRaw, String dueDateRaw) {

        // 1) Validate name
        String name = (nameRaw == null) ? "" : nameRaw.trim();
        if (name.isEmpty()) {
            System.out.println("Validation Error: Bill name cannot be empty.");
            return false;
        }

        // 2) Validate amount
        BigDecimal amountDue;
        try {
            amountDue = new BigDecimal(amountRaw.trim());

            // Example business rule: amount must be > 0
            if (amountDue.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Validation Error: Amount must be greater than 0.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Validation Error: Amount must be a valid number (example: 250.00).");
            return false;
        }

        // 3) Validate date
        LocalDate dueDate;
        try {
            // Expects YYYY-MM-DD
            dueDate = LocalDate.parse(dueDateRaw.trim());
        } catch (DateTimeParseException e) {
            System.out.println("Validation Error: Due date must be in format YYYY-MM-DD.");
            return false;
        }

        // 4) If all validation passes, create the Bill model object
        Bill bill = new Bill(name, amountDue, dueDate);

        // 5) Save to database via DAO
        return billDao.addBill(bill);
    }

    /**
     * Returns all bills from the database.
     */
    public List<Bill> getAllBills() {
        return billDao.getAllBills();
    }
}
