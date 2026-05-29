package com.lab3.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,15}$");
    private static final Pattern EMP_CODE_PATTERN = Pattern.compile("^E[0-9]{3}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone == null || PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isPositiveSalary(double salary) {
        return salary > 0;
    }

    public static boolean isValidEmpCode(String code) {
        return code != null && EMP_CODE_PATTERN.matcher(code).matches();
    }
}