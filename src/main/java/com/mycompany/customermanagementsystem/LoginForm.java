/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customermanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginForm() {
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());
                
                // [ SET GLOBAL VARIABLES TO ENTERED USER/PASS ]
                DBConnection.UpdateUserPass(user, pass);
                
                try {
                    // [ TRY TO CONNECT TO DB USING LOGIN VARIABLES ]
                    DBConnection.getConnection();
                    
                    // [ OPEN MAIN MENU IF SUCCESSFUL ]
                    new MainMenu().setVisible(true);
                    dispose();
                } catch (SQLException error) {
                    // [ IF PASSWORD/USER DOES NOT WORK - CANNOT CONNECT TO DB ]
                    System.out.println(error);
                    System.out.println("Invalid login.");
                    JOptionPane.showMessageDialog(null, "Invalid login!");
                }
                
            }
        });

        addComponents();
        setVisible(true);
    }
    
    private void addComponents() {
        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(51, 51, 51));
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new java.awt.Color(255,255,255));
        panel.add(userLabel);
        panel.add(userField);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new java.awt.Color(255,255,255));
        panel.add(passwordLabel);
        panel.add(passField);
        panel.add(loginButton);
        add(panel);
    }
}

