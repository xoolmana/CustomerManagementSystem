package com.mycompany.customermanagementsystem;

import static com.mycompany.customermanagementsystem.MainMenu.darkColor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerManagementForm extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO = new CustomerDAO();

    public CustomerManagementForm() {
        setTitle("Customer Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener (new java.awt.event.WindowAdapter(){

            @Override
            
            // [ CHECK IF MANAGEMENT FORM IS CLOSED - OPEN BACK UP MAIN MENU ]
            public void windowClosing(java.awt.event.WindowEvent evt) {
              
              new MainMenu().setVisible(true);
              dispose();

            }
            
        });
        
        // Initialize table and model
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Email"}, 0);
        customerTable = new JTable(tableModel);
        loadCustomers();

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddCustomerDialog();
                loadCustomers();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editCustomer();
                loadCustomers();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
                loadCustomers();
            }
        });

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        
        
        // [ DARK/LIGHT MODE ]
        boolean darkMode = CustomerManagementSystem.darkMode;
        Color darkColor = MainMenu.darkColor;
        Color lightColor = MainMenu.lightColor;
        
        // [ TABLE ]
        if (darkMode) {
            customerTable.setBackground(darkColor);
            customerTable.setForeground(lightColor.darker()); // [ TEXT ]
            customerTable.setGridColor(lightColor.darker());   // [ GRID ]
            customerTable.setSelectionBackground(darkColor.darker()); // [ SELECTION BG ]
            customerTable.setSelectionForeground(lightColor);     // [ SELECTION TEXT ]
            // [ HEADER ]
            customerTable.getTableHeader().setBackground(darkColor);
            customerTable.getTableHeader().setForeground(lightColor);
        } else {
            customerTable.setBackground(lightColor);
            customerTable.setForeground(darkColor.darker()); // [ TEXT ]
            customerTable.setGridColor(darkColor.darker());   // [ GRID ]
            customerTable.setSelectionBackground(lightColor.darker()); // [ SELECTION BG ]
            customerTable.setSelectionForeground(darkColor);     // [ SELECTION TEXT ]
            // [ HEADER ]
            customerTable.getTableHeader().setBackground(lightColor);
            customerTable.getTableHeader().setForeground(darkColor.darker());
        }
        
        getContentPane().setBackground(darkMode ? darkColor : lightColor);
        panel.setBackground(darkMode ? darkColor : lightColor);
        scrollPane.setBackground(darkMode ? darkColor : lightColor);
        scrollPane.getViewport().setBackground(darkMode ? darkColor : lightColor);
        
        // [ BUTTONS ]
        addButton.setBackground(darkMode ? darkColor.darker() : lightColor.darker());
        addButton.setForeground(darkMode ? lightColor : darkColor);
        editButton.setBackground(darkMode ? darkColor.darker() : lightColor.darker());
        editButton.setForeground(darkMode ? lightColor : darkColor);
        deleteButton.setBackground(darkMode ? darkColor.darker() : lightColor.darker());
        deleteButton.setForeground(darkMode ? lightColor : darkColor);
        
        setVisible(true);
        
        
        
    }
    
    
    private void loadCustomers() {
        tableModel.setRowCount(0);
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail()});
        }
    }
    /*
    
    */

    private void showAddCustomerDialog() {
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField emailField = new JTextField(10);

        JPanel addPanel = new JPanel();
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel emailLabel = new JLabel("Email:");
        addPanel.add(firstNameLabel);
        addPanel.add(firstNameField);
        addPanel.add(lastNameLabel);
        addPanel.add(lastNameField);
        addPanel.add(emailLabel);
        addPanel.add(emailField);
        
        // [ DARK MODE ]
        boolean darkMode = CustomerManagementSystem.darkMode;
        Color darkColor = MainMenu.darkColor;
        Color lightColor = MainMenu.lightColor;
        
        //getContentPane().setBackground(darkMode ? darkColor : lightColor);
        //addPanel.setBackground(darkMode ? darkColor : lightColor);
        firstNameLabel.setForeground(darkMode ? lightColor : darkColor);
        lastNameLabel.setForeground(darkMode ? lightColor : darkColor);
        emailLabel.setForeground(darkMode ? lightColor : darkColor);
        
        
        int result = JOptionPane.showConfirmDialog(null, addPanel, "Add New Customer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()) {
                Customer customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmail(email);
                customerDAO.addCustomer(customer);
                JOptionPane.showMessageDialog(this, "Customer added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Retrieve selected customer's information
        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentFirstName = (String) tableModel.getValueAt(selectedRow, 1);
        String currentLastName = (String) tableModel.getValueAt(selectedRow, 2);
        String currentEmail = (String) tableModel.getValueAt(selectedRow, 3);

        // Create input fields pre-filled with current customer details
        JTextField firstNameField = new JTextField(currentFirstName, 10);
        JTextField lastNameField = new JTextField(currentLastName, 10);
        JTextField emailField = new JTextField(currentEmail, 10);

        JPanel editPanel = new JPanel();
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel emailLabel = new JLabel("Email:");
        editPanel.add(firstNameLabel);
        editPanel.add(firstNameField);
        editPanel.add(lastNameLabel);
        editPanel.add(lastNameField);
        editPanel.add(emailLabel);
        editPanel.add(emailField);
        
        // [ DARK MODE ]
        boolean darkMode = CustomerManagementSystem.darkMode;
        Color darkColor = MainMenu.darkColor;
        Color lightColor = MainMenu.lightColor;
        
        firstNameLabel.setForeground(darkMode ? lightColor : darkColor);
        lastNameLabel.setForeground(darkMode ? lightColor : darkColor);
        emailLabel.setForeground(darkMode ? lightColor : darkColor);
        
        int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Customer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newFirstName = firstNameField.getText();
            String newLastName = lastNameField.getText();
            String newEmail = emailField.getText();

            if (!newFirstName.isEmpty() && !newLastName.isEmpty() && !newEmail.isEmpty()) {
                // Update customer object and database
                Customer customer = new Customer();
                customer.setId(customerId);
                customer.setFirstName(newFirstName);
                customer.setLastName(newLastName);
                customer.setEmail(newEmail);

                customerDAO.updateCustomer(customer); // Calls update method in DAO
                JOptionPane.showMessageDialog(this, "Customer updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        System.out.println(selectedRow);
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            customerDAO.deleteCustomer(customerId);
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
        }
    }
    
}