package jDBC_MYSQL_DB_CRUD_OPS;

public class LoginResult {
    public String role;   // "ADMIN" or "EMPLOYEE"
    public int empId;     // only for employees, 0 for admin

    public LoginResult(String role, int empId) {
        this.role = role;
        this.empId = empId;
    }

    public String getRole() {
        return role;
    }

    public int getEmpId() {
        return empId;
    }
}
