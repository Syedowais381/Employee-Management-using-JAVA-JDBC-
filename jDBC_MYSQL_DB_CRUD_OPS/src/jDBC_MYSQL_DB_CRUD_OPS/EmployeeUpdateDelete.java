package jDBC_MYSQL_DB_CRUD_OPS;



import java.sql.*;
import java.util.Scanner;

public class EmployeeUpdateDelete {



    public static void showMenu(Scanner sc){
        
            while (true) {
                System.out.println("\n===== Employee Portal =====");
                System.out.println("1. Update Employee Details");
                System.out.println("2. Delete Employee");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        updateEmployee(sc);
                        break;
                    case 2:
                        deleteEmployee(sc);
                        break;
                    case 3:
                        System.out.println("Exiting... Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            }
        
    }

    // Method to update employee details
    private static void updateEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to update: ");
        int empId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter New Department: ");
        String dept = sc.nextLine();

        System.out.print("Enter New Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter New Email: ");
        String email = sc.nextLine();

        System.out.print("Enter New Phone: ");
        String phone = sc.nextLine();

        String sql = "UPDATE employeedetails SET Name=?, Age=?, Gender=?, Department=?, Salary=?, Email=?, Phone=? WHERE EmpID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, dept);
            ps.setDouble(5, salary);
            ps.setString(6, email);
            ps.setString(7, phone);
            ps.setInt(8, empId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Employee details updated successfully.");
            } else {
                System.out.println("❌ No employee found with ID " + empId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete employee
    private static void deleteEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to delete: ");
        int empId = sc.nextInt();

        String sql = "DELETE FROM employeedetails WHERE EmpID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Employee deleted successfully.");
            } else {
                System.out.println("❌ No employee found with ID " + empId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
