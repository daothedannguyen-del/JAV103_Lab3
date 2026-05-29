package com.lab3.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private String empCode;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private LocalDate birthDate;
    private String department;
    private String position;
    private BigDecimal salary;

    public Employee() {}

    public Employee(String empCode, String fullName, String email, String phone,
                    String gender, LocalDate birthDate, String department,
                    String position, BigDecimal salary) {
        this.empCode = empCode;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    public String getEmpCode() { return empCode; }
    public void setEmpCode(String empCode) { this.empCode = empCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %s | %s | %s | %.2f",
                empCode, fullName, email, phone, gender, birthDate, department, position, salary);
    }
}