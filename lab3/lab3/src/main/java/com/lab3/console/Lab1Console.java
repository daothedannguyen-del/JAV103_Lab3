package com.lab3.console;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lab3.utils.DBConnection;

public class Lab1Console {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("=== All Employees ===");
            ResultSet rs = stmt.executeQuery("SELECT emp_code, full_name, salary, department FROM employees");
            System.out.printf("%-8s %-20s %-12s %-10s\n", "ID", "Name", "Salary", "Dept");
            while (rs.next()) {
                System.out.printf("%-8s %-20s %-12.2f %-10s\n",
                        rs.getString("emp_code"),
                        rs.getString("full_name"),
                        rs.getDouble("salary"),
                        rs.getString("department"));
            }

            System.out.println("\n=== Departments with at least 2 employees ===");
            rs = stmt.executeQuery("SELECT department, COUNT(*) as cnt FROM employees GROUP BY department HAVING COUNT(*) >= 2");
            while (rs.next()) {
                System.out.println(rs.getString("department") + " -> " + rs.getInt("cnt"));
            }

            System.out.println("\n=== Department Statistics ===");
            rs = stmt.executeQuery(
                "SELECT d.dept_name, COUNT(e.emp_code) as total " +
                "FROM departments d LEFT JOIN employees e ON d.dept_name = e.department " +
                "GROUP BY d.dept_name");
            System.out.printf("%-15s %s\n", "Department", "Employee Count");
            while (rs.next()) {
                System.out.printf("%-15s %d\n", rs.getString("dept_name"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}