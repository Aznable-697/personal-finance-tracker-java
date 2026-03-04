/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mattw.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *Bill (Model)
 * ------------
 * Represents one row in the bills table.
 *
 * Table:
 *  id INT AUTO_INCREMENT PRIMARY KEY
 *  name VARCHAR(255) NOT NULL
 *  amount_due DECIMAL(10,2) NOT NULL
 *  due_date DATE NOT NULL
 *
 * Why we use a model object:
 * - Easier to pass around one object instead of 3-4 variables
 * - Cleaner code for DAO / Service / CLI
 * - Looks more professional (OOP design)
 * @author Matt W.
 */
public class Bill {

    // Database primary key (auto-increment)
    private int id;

    // Bill name (example: Electric, Rent, Car Insurance)
    private String name;

    // Using BigDecimal because database uses DECIMAL(10,2)
    // BigDecimal avoids floating-point rounding issues.
    private BigDecimal amountDue;

    // Using LocalDate (better than String for dates)
    private LocalDate dueDate;

    /**
     * Constructor used when creating a NEW bill (no id yet).
     */
    public Bill(String name, BigDecimal amountDue, LocalDate dueDate) {
        this.name = name;
        this.amountDue = amountDue;
        this.dueDate = dueDate;
    }

    /**
     * Constructor used when reading a bill FROM the database (id included).
     */
    public Bill(int id, String name, BigDecimal amountDue, LocalDate dueDate) {
        this.id = id;
        this.name = name;
        this.amountDue = amountDue;
        this.dueDate = dueDate;
    }

    // ----- Getters / Setters -----

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Helpful for debugging / printing.
     */
    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountDue=" + amountDue +
                ", dueDate=" + dueDate +
                '}';
    }
}