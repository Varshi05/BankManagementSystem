import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL  = "jdbc:mysql://localhost:3307/bankdb";
    private static final String USER = "root";        // <-- change
    private static final String PASS = "#Varshi@72204%"; // <-- change

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}