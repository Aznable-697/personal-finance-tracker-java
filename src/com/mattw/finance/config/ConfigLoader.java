/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mattw.finance.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * ConfigLoader
 *
 * Responsible for loading configuration values
 * from config.properties.
 *
 * This keeps sensitive values like database
 * credentials out of the source code.
 * @author Matt W.
 */
public class ConfigLoader {
   private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    /**
     * Returns a configuration value by key
     */
    public static String get(String key) {
        return properties.getProperty(key);
    } 
}
