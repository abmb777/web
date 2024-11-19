import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Connection URL, username, and password for MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/course_registration?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Method to get a connection to the MySQL database
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
