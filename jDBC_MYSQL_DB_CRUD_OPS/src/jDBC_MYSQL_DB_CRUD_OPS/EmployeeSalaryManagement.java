package jDBC_MYSQL_DB_CRUD_OPS;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeSalaryManagement {



	public static void salaryMenu(Scanner sc, Integer empId, String role) {
	    System.out.println("\n--- Salary Management ---");
	    System.out.println("1. Increment Salary (by %)");
	    System.out.println("2. Department-wise Average Salary");
	    System.out.println("3. Total Department Salary Expense");
	    if ("employee".equalsIgnoreCase(role)) {
	        System.out.println("4. View My Payslip");
	    } else {
	        System.out.println("4. Generate Payslip for Any Employee");
	    }
	    System.out.print("Enter your choice: ");
	    int choice = sc.nextInt();
	    sc.nextLine();

	    switch (choice) {
	        case 1 -> incrementSalary(sc);
	        case 2 -> averageSalaryByDepartment(sc);
	        case 3 -> totalExpenseByDepartment(sc);
	        case 4 -> {
	            if ("employee".equalsIgnoreCase(role) && empId != null) {
	                generatePayslip(empId);  // employee sees own pay slip
	            } else {
	                System.out.print("Enter Employee ID: ");
	                int id = sc.nextInt();
	                sc.nextLine();
	                generatePayslip(id);  // admin can generate for anyone
	            }
	        }
	        default -> System.out.println("❌ Invalid choice.");
	    }
	}


    // 1. Increment salary of all employees in a department
    private static void incrementSalary(Scanner sc) {
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Increment Percentage: ");
        double percent = sc.nextDouble();

        String query = "UPDATE employeedetails SET Salary = Salary + (Salary * ? / 100) WHERE Department = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDouble(1, percent);
            ps.setString(2, dept);

            int rows = ps.executeUpdate();
            System.out.println("✅ Salary increment applied to " + rows + " employees in " + dept + " department.");

        } catch (SQLException e) {
            System.err.println("❌ Error incrementing salary: " + e.getMessage());
        }
    }

    // 2. Department-wise average salary
    private static void averageSalaryByDepartment(Scanner sc) {
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        String query = "SELECT AVG(Salary) AS avgSalary FROM employeedetails WHERE Department = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, dept);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double avg = rs.getDouble("avgSalary");
                    System.out.printf("📊 Average Salary in %s department: %.2f%n", dept, avg);
                } else {
                    System.out.println("No data found for this department.");
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error calculating average salary: " + e.getMessage());
        }
    }

    // 3. Total salary expense of a department
    private static void totalExpenseByDepartment(Scanner sc) {
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        String query = "SELECT SUM(Salary) AS totalSalary FROM employeedetails WHERE Department = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, dept);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble("totalSalary");
                    System.out.printf("💰 Total Salary Expense in %s department: %.2f%n", dept, total);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error calculating salary expense: " + e.getMessage());
        }
    }

    // 4. Generate Pay slip for an employee (with deductions & allowances)
 // 4. Generate Pay slip for an employee (with deductions & allowances + bonus)
    
 // 4. Generate Pay slip for an employee (with deductions & allowances + bonus + date)
    public static void generatePayslip(int empId) {

        String empQuery = "SELECT Name, Department, Salary FROM employeedetails WHERE EmpID = ?";
        String bonusQuery = "SELECT IFNULL(SUM(bonusAmount), 0) AS totalBonus FROM bonusdetails WHERE empId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement empPs = con.prepareStatement(empQuery);
             PreparedStatement bonusPs = con.prepareStatement(bonusQuery)) {

            empPs.setInt(1, empId);
            try (ResultSet rs = empPs.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String dept = rs.getString("Department");
                    double baseSalary = rs.getDouble("Salary");

                    // --- Get total bonus ---
                    bonusPs.setInt(1, empId);
                    double totalBonus = 0;
                    try (ResultSet brs = bonusPs.executeQuery()) {
                        if (brs.next()) {
                            totalBonus = brs.getDouble("totalBonus");
                        }
                    }

                    // --- Salary breakdown ---
                    double hra = baseSalary * 0.20;
                    double da = baseSalary * 0.10;
                    double pf = baseSalary * 0.12;  // deduction
                    double tax = baseSalary * 0.10; // deduction
                    double netSalary = baseSalary + hra + da + totalBonus - pf - tax;

                    // --- Date formatting ---
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy"); 
                    String payslipMonth = today.format(formatter);

                    // --- Print Pay slip ---
                    System.out.println("\n--- Payslip ---");
                    System.out.println("Payslip Month: " + payslipMonth);
                    System.out.println("Generated on: " + today);
                    System.out.println("Employee: " + name);
                    System.out.println("Department: " + dept);
                    System.out.printf("Base Salary: %.2f%n", baseSalary);
                    System.out.printf("HRA: %.2f%n", hra);
                    System.out.printf("DA: %.2f%n", da);
                    System.out.printf("Total Bonus: %.2f%n", totalBonus);
                    System.out.printf("PF Deduction: -%.2f%n", pf);
                    System.out.printf("Tax Deduction: -%.2f%n", tax);
                    System.out.printf("Net Salary: %.2f%n", netSalary);
                    System.out.println("----------------------------");

                } else {
                    System.out.println("❌ Employee not found.");
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error generating payslip: " + e.getMessage());
        }
    }


}

