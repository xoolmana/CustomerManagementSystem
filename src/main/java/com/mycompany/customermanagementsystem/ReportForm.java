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
        
        add(new JScrollPane(reportTable), BorderLayout.CENTER);
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