import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public static void login(String userid, String password)
            throws InvalidUserException, SQLException {
        String sql = "SELECT * FROM users WHERE userid=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userid);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new InvalidUserException(); // 'invaliduser'
                }
            }
        }
    }
}