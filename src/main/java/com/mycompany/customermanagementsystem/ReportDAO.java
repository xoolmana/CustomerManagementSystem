/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customermanagementsystem;

/**
 *
 * @author PTHOMPSO
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    public List<Report> getInfo() {
        List<Report> reports = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sales_by_film_category");
            while (rs.next()) {
                Report report = new Report();
                report.setCategory(rs.getString("category"));
                report.setTotalSales(rs.getFloat("total_sales"));
                reports.add(report);
            }
        } catch (SQLException e) {
            System.out.println("Error in connection");
        }
        return reports;
    }
}
