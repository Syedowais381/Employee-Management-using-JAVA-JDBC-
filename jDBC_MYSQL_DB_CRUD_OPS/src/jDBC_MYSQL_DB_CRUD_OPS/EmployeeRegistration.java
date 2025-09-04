package jDBC_MYSQL_DB_CRUD_OPS;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmployeeRegistration {
    

    private static final String INSERT_QUERY = "INSERT INTO employeedetails (EmpID, Name, Age, Gender, Department, Salary, Email, Phone, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static void register(Scanner sc) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {

            System.out.println("===== Employee Registration Portal =====");

            System.out.print("Enter Employee ID (integer): ");
            int empId = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            System.out.print("Enter Full Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Gender (Male/Female/Other): ");
            String gender = sc.nextLine().trim();

            System.out.print("Enter Department: ");
            String department = sc.nextLine().trim();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine().trim();

            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine().trim();
            
            System.out.print("Set Password for Employee: ");
            String password = sc.nextLine();


            // ================= Validation =================
            if (name.isEmpty() || department.isEmpty() || gender.isEmpty()
                    || email.isEmpty() || phone.isEmpty()|| password.isEmpty()) {
                System.out.println("❌ All fields are mandatory. Registration failed.");
                return;
            }

            if (age < 18 || age > 65) {
                System.out.println("❌ Invalid age! Age must be between 18 and 65.");
                return;
            }

            if (salary < 0) {
                System.out.println("❌ Salary cannot be negative.");
                return;
            }

            if (!isValidEmail(email)) {
                System.out.println("❌ Invalid email format.");
                return;
            }

            if (!isValidPhone(phone)) {
                System.out.println("❌ Invalid phone number format.");
                return;
            }

            // ================= Insert into DB =================
            ps.setInt(1, empId);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, gender);
            ps.setString(5, department);
            ps.setDouble(6, salary);
            ps.setString(7, email);
            ps.setString(8, phone);
            ps.setString(9, password);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Employee registered successfully!");
            } else {
                System.out.println("❌ Employee registration failed.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("⚠️ Employee with this ID already exists. Try another ID.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========== Utility Functions ==========
    private static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    private static boolean isValidPhone(String phone) {
        String regex = "^[0-9]{10}$"; // simple 10-digit check
        return Pattern.matches(regex, phone);
    }
}
