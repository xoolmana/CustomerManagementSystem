package com.mycompany.customermanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReportForm extends JFrame {
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private ReportDAO reportDAO = new ReportDAO();

    public ReportForm() {
        setTitle("Sales by film category");
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
        tableModel = new DefaultTableModel(new String[]{"Category", "Total Sales"}, 0);
        reportTable = new JTable(tableModel);
        loadReports();
        
        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // [ DARK/LIGHT MODE ]
        boolean darkMode = CustomerManagementSystem.darkMode;
        Color darkColor = MainMenu.darkColor;
        Color lightColor = MainMenu.lightColor;
        
        // [ TABLE ]
        if (darkMode) {
            reportTable.setBackground(darkColor);
            reportTable.setForeground(lightColor.darker()); // [ TEXT ]
            reportTable.setGridColor(lightColor.darker());   // [ GRID ]
            reportTable.setSelectionBackground(darkColor.darker()); // [ SELECTION BG ]
            reportTable.setSelectionForeground(lightColor);     // [ SELECTION TEXT ]
            // [ HEADER ]
            reportTable.getTableHeader().setBackground(darkColor);
            reportTable.getTableHeader().setForeground(lightColor);
        } else {
            reportTable.setBackground(lightColor);
            reportTable.setForeground(darkColor.darker()); // [ TEXT ]
            reportTable.setGridColor(darkColor.darker());   // [ GRID ]
            reportTable.setSelectionBackground(lightColor.darker()); // [ SELECTION BG ]
            reportTable.setSelectionForeground(darkColor);     // [ SELECTION TEXT ]
            // [ HEADER ]
            reportTable.getTableHeader().setBackground(lightColor);
            reportTable.getTableHeader().setForeground(darkColor.darker());
        }
        
        setBackground(darkMode ? darkColor : lightColor);
        getContentPane().setBackground(darkMode ? darkColor : lightColor);
        scrollPane.setBackground(darkMode ? darkColor : lightColor);
        scrollPane.getViewport().setBackground(darkMode ? darkColor : lightColor);

        setVisible(true);
    }
    
    
    private void loadReports() {
        tableModel.setRowCount(0);
        List<Report> reports = reportDAO.getInfo();
        for (Report report : reports) {
            tableModel.addRow(new Object[]{report.getCategory(), report.getTotalSales()});
        }
    }
    
}