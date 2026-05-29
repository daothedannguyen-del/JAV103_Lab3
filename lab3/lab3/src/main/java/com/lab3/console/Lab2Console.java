package com.lab3.console;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.lab3.dao.EmployeeDAO;
import com.lab3.model.Employee;
import com.lab3.utils.ValidationUtils;

public class Lab2Console {
    private static final EmployeeDAO dao = new EmployeeDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== QUAN LY NHAN VIEN =====");
            System.out.println("1. Them moi");
            System.out.println("2. Danh sach (tim kiem)");
            System.out.println("3. Chi tiet");
            System.out.println("4. Cap nhat");
            System.out.println("5. Xoa");
            System.out.println("6. Thoat");
            System.out.print("Lua chon: ");
            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1" -> create();
                    case "2" -> list();
                    case "3" -> detail();
                    case "4" -> update();
                    case "5" -> delete();
                    case "6" -> { return; }
                    default -> System.out.println("Lua chon khong hop le");
                }
            } catch (Exception e) {
                System.err.println("Loi: " + e.getMessage());
            }
        }
    }

    private static void create() throws Exception {
        Employee e = new Employee();
        System.out.print("Ma nhan vien (dang E001): ");
        e.setEmpCode(sc.nextLine());
        if (!ValidationUtils.isValidEmpCode(e.getEmpCode())) {
            System.out.println("Ma khong hop le. Phai bat dau bang E va 3 chu so.");
            return;
        }
        if (dao.findByCode(e.getEmpCode()) != null) {
            System.out.println("Ma da ton tai.");
            return;
        }
        System.out.print("Ho ten: ");
        e.setFullName(sc.nextLine());
        System.out.print("Email: ");
        e.setEmail(sc.nextLine());
        if (!ValidationUtils.isValidEmail(e.getEmail())) {
            System.out.println("Email khong hop le.");
            return;
        }
        System.out.print("So dien thoai (10-15 so): ");
        e.setPhone(sc.nextLine());
        if (!ValidationUtils.isValidPhone(e.getPhone())) {
            System.out.println("So dien thoai khong hop le.");
            return;
        }
        System.out.print("Gioi tinh (M/F/Other): ");
        e.setGender(sc.nextLine());
        System.out.print("Ngay sinh (yyyy-mm-dd): ");
        try {
            e.setBirthDate(LocalDate.parse(sc.nextLine()));
        } catch (DateTimeParseException ex) {
            System.out.println("Ngay sinh khong hop le. Dung dinh dang yyyy-mm-dd.");
            return;
        }
        System.out.print("Phong ban: ");
        e.setDepartment(sc.nextLine());
        System.out.print("Chuc vu: ");
        e.setPosition(sc.nextLine());
        System.out.print("Luong (so nguyen, vi du: 75000): ");
        while (true) {
            String salaryStr = sc.nextLine();
            try {
                long salaryLong = Long.parseLong(salaryStr);
                if (salaryLong > 0) {
                    e.setSalary(new BigDecimal(salaryLong));
                    break;
                } else {
                    System.out.println("Luong phai lon hon 0. Nhap lai: ");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Sai dinh dang. Hay nhap so nguyen (khong dau phay, khong dau cham). Nhap lai: ");
            }
        }
        if (dao.insert(e)) System.out.println("Them thanh cong.");
        else System.out.println("Them that bai.");
    }

    private static void list() throws Exception {
        System.out.print("Tim kiem (de trong de lay tat ca): ");
        String search = sc.nextLine();
        List<Employee> list = dao.list(search.isBlank() ? null : search);
        System.out.printf("%-8s %-20s %-25s %-15s %-12s\n", "Ma", "Ho ten", "Email", "Phong", "Luong");
        for (Employee e : list) {
            System.out.printf("%-8s %-20s %-25s %-15s %-12.0f\n",
                e.getEmpCode(), e.getFullName(), e.getEmail(), e.getDepartment(), e.getSalary());
        }
    }

    private static void detail() throws Exception {
        System.out.print("Ma nhan vien: ");
        String code = sc.nextLine();
        Employee e = dao.findByCode(code);
        if (e == null) System.out.println("Khong tim thay.");
        else System.out.println(e);
    }

    private static void update() throws Exception {
        System.out.print("Ma nhan vien: ");
        String code = sc.nextLine();
        Employee e = dao.findByCode(code);
        if (e == null) {
            System.out.println("Khong tim thay.");
            return;
        }
        System.out.print("Ho ten moi (" + e.getFullName() + "): ");
        String name = sc.nextLine(); if (!name.isBlank()) e.setFullName(name);
        System.out.print("Email moi (" + e.getEmail() + "): ");
        String email = sc.nextLine(); if (!email.isBlank()) e.setEmail(email);
        System.out.print("So dien thoai moi (" + e.getPhone() + "): ");
        String phone = sc.nextLine(); if (!phone.isBlank()) e.setPhone(phone);
        System.out.print("Gioi tinh moi (" + e.getGender() + "): ");
        String gender = sc.nextLine(); if (!gender.isBlank()) e.setGender(gender);
        System.out.print("Ngay sinh moi (yyyy-mm-dd) (" + e.getBirthDate() + "): ");
        String bd = sc.nextLine(); if (!bd.isBlank()) {
            try {
                e.setBirthDate(LocalDate.parse(bd));
            } catch (DateTimeParseException ex) {
                System.out.println("Ngay sinh khong hop le, giu nguyen cu.");
            }
        }
        System.out.print("Phong ban moi (" + e.getDepartment() + "): ");
        String dept = sc.nextLine(); if (!dept.isBlank()) e.setDepartment(dept);
        System.out.print("Chuc vu moi (" + e.getPosition() + "): ");
        String pos = sc.nextLine(); if (!pos.isBlank()) e.setPosition(pos);
        System.out.print("Luong moi (so nguyen) (" + e.getSalary().longValue() + "): ");
        String sal = sc.nextLine(); if (!sal.isBlank()) {
            try {
                long newSalary = Long.parseLong(sal);
                if (newSalary > 0)
                    e.setSalary(new BigDecimal(newSalary));
                else
                    System.out.println("Luong phai lon hon 0, giu nguyen cu.");
            } catch (NumberFormatException ex) {
                System.out.println("Sai dinh dang so, giu nguyen luong cu.");
            }
        }
        if (dao.update(e)) System.out.println("Cap nhat thanh cong.");
        else System.out.println("Cap nhat that bai.");
    }

    private static void delete() throws Exception {
        System.out.print("Ma nhan vien: ");
        String code = sc.nextLine();
        System.out.print("Xac nhan xoa (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y") && dao.delete(code))
            System.out.println("Da xoa.");
        else System.out.println("Xoa bi huy hoac that bai.");
    }
}