package jDBC_MYSQL_DB_CRUD_OPS;

import java.sql.*;
import java.util.Scanner;

public class Login {

    // returns "ADMIN", "EMPLOYEE", or null if login fails
    public static LoginResult login(Scanner sc) {
        System.out.println("\n=== Login ===");
        System.out.println("1. Admin Login");
        System.out.println("2. Employee Login");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
        case 1 -> {
            if (adminLogin(sc)) {
                return new LoginResult("ADMIN", 0);
            }
        }
        case 2 -> {
            int empId = employeeLogin(sc);
            if (empId != -1) {
                return new LoginResult("EMPLOYEE", empId);
            }
        }
        default -> System.out.println("❌ Invalid choice.");
    }
    return null;
}
    // ---------- Admin Login ----------
    private static boolean adminLogin(Scanner sc) {
        System.out.print("Enter Admin Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine();

        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ Admin login successful. Welcome " + username + "!");
                    return true;
                } else {
                    System.out.println("❌ Invalid Admin credentials.");
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error during Admin login: " + e.getMessage());
        }
        return false;
    }

    // ---------- Employee Login ----------
    private static int employeeLogin(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String query = "SELECT * FROM employeedetails WHERE EmpID = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, empId);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("Name");
                    System.out.println("✅ Employee login successful. Welcome " + name + "!");
                    return empId;
                } else {
                    System.out.println("❌ Invalid Employee credentials.");
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error during Employee login: " + e.getMessage());
        }
        return -1;
    }
}
