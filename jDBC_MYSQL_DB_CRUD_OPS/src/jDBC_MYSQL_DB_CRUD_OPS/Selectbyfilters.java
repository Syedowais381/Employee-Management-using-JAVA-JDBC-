package jDBC_MYSQL_DB_CRUD_OPS;

import java.sql.*;
import java.util.Scanner;

public class Selectbyfilters {

    public static void search(Scanner sc) {




            StringBuilder sql = new StringBuilder("SELECT * FROM employeedetails WHERE 1=1");

            // --- User Inputs ---
            System.out.print("Search by EmpID (leave blank if not needed): ");
            String empInput = sc.nextLine().trim();
            Integer EmpID = null;
            if (!empInput.isEmpty()) {
                try {
                    EmpID = Integer.parseInt(empInput);
                    if (EmpID <= 0) {
                        System.out.println("⚠️ Invalid EmpID, ignoring filter.");
                        EmpID = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Invalid EmpID, ignoring filter.");
                }
            }

            System.out.print("Search by Name (partial allowed, leave blank if not needed): ");
            String Name = sc.nextLine().trim();

            System.out.print("Search by Department (partial allowed, leave blank if not needed): ");
            String Department = sc.nextLine().trim();

            // --- Build SQL ---
            if (EmpID != null) {
                sql.append(" AND EmpID = ?");
            }
            if (!Name.isEmpty()) {
                sql.append(" AND LOWER(Name) LIKE ?");
            }
            if (!Department.isEmpty()) {
                sql.append(" AND LOWER(Department) LIKE ?");
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(sql.toString())) {

                int index = 1;
                if (EmpID != null) {
                    pstmt.setInt(index++, EmpID);
                }
                if (!Name.isEmpty()) {
                    pstmt.setString(index++, "%" + Name.toLowerCase() + "%");
                }
                if (!Department.isEmpty()) {
                    pstmt.setString(index++, "%" + Department.toLowerCase() + "%");
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    boolean found = false;

                    System.out.println("\n================= Search Results =================");
                    System.out.printf("%-5s %-15s %-5s %-8s %-15s %-10s %-25s %-15s%n",
                            "ID", "Name", "Age", "Gender", "Department", "Salary", "Email", "Phone");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");

                    while (rs.next()) {
                        found = true;
                        System.out.printf("%-5d %-15s %-5d %-8s %-15s %-10s %-25s %-15s%n",
                                rs.getInt("EmpID"),
                                rs.getString("Name"),
                                rs.getInt("Age"),
                                rs.getString("Gender"),
                                rs.getString("Department"),
                                rs.getBigDecimal("Salary"),
                                rs.getString("Email"),
                                rs.getString("Phone")
                        );
                    }

                    if (!found) {
                        System.out.println("❌ No matching employee found.");
                    }
                    System.out.println("=================================================\n");
                }

            } catch (SQLException e) {
                System.out.println("❌ Database error: " + e.getMessage());
                e.printStackTrace();
            }

        } 
    }









































































































































































































































//package jDBC_MYSQL_DB_CRUD_OPS;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.PreparedStatement;
//import java.util.Scanner;
//
//public class Selectbyfilters {
//
//    public static void main(String[] args) throws Exception {
//
//        final String url = "jdbc:mysql://localhost:3306/alidb";
//        final String username = "root";
//        final String pass = "rat123A@";
//
//        StringBuilder sql = new StringBuilder("SELECT * FROM employeedetails WHERE 1=1");
//
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("Search by EmpID (leave blank if not needed):");
//        String empInput = sc.nextLine().trim();
//        int EmpID = -1;
//        if (!empInput.isEmpty()) {
//            try {
//                EmpID = Integer.parseInt(empInput);
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid EmpID, ignoring filter.");
//            }
//        }
//
//        
//        
//        System.out.println("Search by Name (leave blank if not needed):");
//        String Name = sc.nextLine().trim();
//
//        System.out.println("Search by Department (leave blank if not needed):");
//        String Department = sc.nextLine().trim();
//
//        // Build SQL dynamically
//        if (EmpID > 0) {
//            sql.append(" AND EmpID = ?");
//        }
//        if (!Name.isEmpty()) {
//            sql.append(" AND Name = ?");
//        }
//        if (!Department.isEmpty()) {
//            sql.append(" AND Department = ?");
//        }
//
//        Connection conn = DriverManager.getConnection(url, username, pass);
//        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
//
//        int index = 1;
//        if (EmpID > 0) {
//            pstmt.setInt(index++, EmpID);
//        }
//        if (!Name.isEmpty()) {
//            pstmt.setString(index++, Name);
//        }
//        if (!Department.isEmpty()) {
//            pstmt.setString(index++, Department);
//        }
//
//        ResultSet rs = pstmt.executeQuery();
//        boolean found = false;
//        while (rs.next()) {
//        	found = true;
//            System.out.println(
//                rs.getInt("EmpID") + "----" +
//                rs.getString("Name") + "----" +
//                rs.getInt("Age") + "----" +
//                rs.getString("Gender") + "----" +
//                rs.getString("Department") + "----" +
//                rs.getBigDecimal("Salary") + "----" +
//                rs.getString("Email") + "----" +
//                rs.getString("Phone")
//            );
//        }
//        if (!found) {
//            System.out.println("❌ No matching employee found.");
//        }
//
//        conn.close();
//    }
//}
