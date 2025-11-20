package memo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/memo_jsp";
    private static final String USER = "root";
    private static final String PASS = "";

    // Note: method no longer throws checked Exception — throws unchecked on failure so callers don't need to declare/catch
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            // Wrap into unchecked exception so DAOs don't need to declare checked exceptions.
            throw new RuntimeException("Failed to get DB connection", e);
        }
    }
}
