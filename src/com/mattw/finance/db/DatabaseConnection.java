/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mattw.finance.db;

import com.mattw.finance.config.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  * DatabaseConnection
 * ------------------
 * Centralized place to create a JDBC connection to MySQL.
 *
 * Why this class exists:
 * - Keeps connection details in ONE place (easy to change later)
 * - Prevents duplicate connection code across the app
 *
 * Note:
 * - Shows separation of concerns and basic layering
 */
public class DatabaseConnection {
    
      // Load values from config.properties
    private static final String URL = ConfigLoader.get("db.url");
    private static final String USER = ConfigLoader.get("db.user");
    private static final String PASSWORD = ConfigLoader.get("db.password");
    
    /**
     * Opens and returns a Connection to the database.
     *
     * @return Connection if successful, otherwise throws RuntimeException
     */
    public static Connection getConnection() {
        try {
            // DriverManager handles selecting the MySQL driver from your connector JAR.
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // In a real application we'd use a Logger.
            // For now, we throw a RuntimeException so callers KNOW it failed.
            System.out.println("The Database Connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
