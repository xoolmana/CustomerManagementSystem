/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customermanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sakila";
    private static String USER = "";
    private static String PASSWORD = "";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // [ USED TO UPDATE PRIVATE VARIABLES FROM INPUT USER/PASS ON LOGIN PAGE ]
    public static void UpdateUserPass(String newUser, String newPass) {
        USER = newUser;
        PASSWORD = newPass;
    }
}
