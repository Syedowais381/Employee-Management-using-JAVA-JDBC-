package jDBC_MYSQL_DB_CRUD_OPS;

import java.sql.*;
import java.util.Scanner;

public class BonusManagement {

    public static void giveBonus(Scanner sc) {
        System.out.println("\n=== Bonus Distribution ===");
        System.out.print("Enter Department (or type ALL for all employees): ");
        String department = sc.nextLine();

        System.out.print("Enter Bonus Type (1 = Fixed Amount, 2 = Percentage): ");
        int type = sc.nextInt();
        sc.nextLine(); // consume newline

        double bonusValue = 0;
        if (type == 1) {
            System.out.print("Enter Fixed Bonus Amount: ");
            bonusValue = sc.nextDouble();
        } else {
            System.out.print("Enter Bonus Percentage: ");
            bonusValue = sc.nextDouble();
        }
        sc.nextLine();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/alidb", "root", "rat123A@")) {

            // ✅ First select all eligible employees
            String selectSql = department.equalsIgnoreCase("ALL")
                    ? "SELECT EmpID, Salary FROM employeedetails"
                    : "SELECT EmpID, Salary FROM employeedetails WHERE Department = ?";

            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            if (!department.equalsIgnoreCase("ALL")) {
                selectStmt.setString(1, department);
            }

            ResultSet rs = selectStmt.executeQuery();

            // ✅ Insert bonus records instead of updating salary
            String insertSql = "INSERT INTO bonusdetails (EmpID, bonusAmount, bonusDate) VALUES (?, ?, NOW())";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);

            int count = 0;
            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                double salary = rs.getDouble("Salary");

                double finalBonus = (type == 1) ? bonusValue : (salary * bonusValue / 100);

                insertStmt.setInt(1, empId);
                insertStmt.setDouble(2, finalBonus);
                insertStmt.executeUpdate();
                count++;
            }

            System.out.println("✅ Bonus recorded for " + count + " employees (not added to Salary).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
