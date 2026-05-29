package com.lab3.dao;

import com.lab3.model.Employee;
import com.lab3.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public boolean insert(Employee e) throws SQLException {
        String sql = "INSERT INTO employees(emp_code, full_name, email, phone, gender, birth_date, department, position, salary) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getEmpCode());
            ps.setString(2, e.getFullName());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getPhone());
            ps.setString(5, e.getGender());
            ps.setDate(6, Date.valueOf(e.getBirthDate()));
            ps.setString(7, e.getDepartment());
            ps.setString(8, e.getPosition());
            ps.setBigDecimal(9, e.getSalary());
            return ps.executeUpdate() == 1;
        }
    }

    public List<Employee> list(String search) throws SQLException {
        String sql = "SELECT * FROM employees";
        boolean useSearch = search != null && !search.isBlank();
        if (useSearch) sql += " WHERE emp_code LIKE ? OR full_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (useSearch) {
                String q = "%" + search + "%";
                ps.setString(1, q);
                ps.setString(2, q);
            }
            ResultSet rs = ps.executeQuery();
            List<Employee> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        }
    }

    public Employee findByCode(String code) throws SQLException {
        String sql = "SELECT * FROM employees WHERE emp_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    public boolean update(Employee e) throws SQLException {
        String sql = "UPDATE employees SET full_name=?, email=?, phone=?, gender=?, birth_date=?, department=?, position=?, salary=? WHERE emp_code=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getFullName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getGender());
            ps.setDate(5, Date.valueOf(e.getBirthDate()));
            ps.setString(6, e.getDepartment());
            ps.setString(7, e.getPosition());
            ps.setBigDecimal(8, e.getSalary());
            ps.setString(9, e.getEmpCode());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(String code) throws SQLException {
        String sql = "DELETE FROM employees WHERE emp_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            return ps.executeUpdate() == 1;
        }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmpCode(rs.getString("emp_code"));
        e.setFullName(rs.getString("full_name"));
        e.setEmail(rs.getString("email"));
        e.setPhone(rs.getString("phone"));
        e.setGender(rs.getString("gender"));
        e.setBirthDate(rs.getDate("birth_date").toLocalDate());
        e.setDepartment(rs.getString("department"));
        e.setPosition(rs.getString("position"));
        e.setSalary(rs.getBigDecimal("salary"));
        return e;
    }
}