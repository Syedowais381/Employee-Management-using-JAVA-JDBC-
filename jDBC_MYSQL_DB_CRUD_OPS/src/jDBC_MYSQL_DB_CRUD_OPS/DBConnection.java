package jDBC_MYSQL_DB_CRUD_OPS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/alidb"; // replace with your DB name
    private static final String USER = "root";       // replace with your MySQL username
    private static final String PASSWORD = "rat123A@";   // replace with your MySQL password

    // Static block ensures driver is loaded only once
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found. Add the connector JAR to classpath.");
            e.printStackTrace();
        }
    }

    // Method to get connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Optional: Method to close connection safely
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
