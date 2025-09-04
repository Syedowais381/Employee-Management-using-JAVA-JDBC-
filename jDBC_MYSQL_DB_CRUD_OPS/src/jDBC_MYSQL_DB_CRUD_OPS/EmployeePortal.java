package jDBC_MYSQL_DB_CRUD_OPS;

import java.util.Scanner;

public class EmployeePortal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n===== Welcome to Employee Portal =====");

        // Call Login first → returns LoginResult object
        LoginResult loginresult = Login.login(sc);

        if (loginresult == null) {
            System.out.println("❌ Login failed. Exiting...");
            return;
        }

        boolean exit = false;

        while (!exit) {
            if ("ADMIN".equalsIgnoreCase(loginresult.role)) {
                System.out.println("\n===== Admin Portal =====");
                System.out.println("1. Register New Employee");
                System.out.println("2. Update/Delete Employee");
                System.out.println("3. Search Employees (Filters)");
                System.out.println("4. Salary Management");
                System.out.println("5. Bonus Management");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> EmployeeRegistration.register(sc);
                    case 2 -> EmployeeUpdateDelete.showMenu(sc);
                    case 3 -> Selectbyfilters.search(sc);
                    case 4 -> EmployeeSalaryManagement.salaryMenu(sc, null, "ADMIN");
                    case 5 -> BonusManagement.giveBonus(sc);
                    case 6 -> exit = true;
                    default -> System.out.println("❌ Invalid choice! Try again.");
                }

            } else if ("EMPLOYEE".equalsIgnoreCase(loginresult.role)) {
                System.out.println("\n===== Employee Portal =====");
                System.out.println("1. View Payslip");
                
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> EmployeeSalaryManagement.generatePayslip(loginresult.empId);
                    case 2 -> exit = true;
                    default -> System.out.println("❌ Invalid choice! Try again.");
                }
            }
        }

        sc.close();
        System.out.println("✅ Exiting Employee Portal. Goodbye!");
    }
}


// jdsdfkfsknf
