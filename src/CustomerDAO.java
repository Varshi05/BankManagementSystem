import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO {

    // INSERT -> Create Account
    public void insertCustomer(Customer c) throws SQLException {
        String sql = "INSERT INTO customers(acno,cname,email,accounttype,bal) VALUES(?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getAcno());
            ps.setString(2, c.getCname());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAccounttype());
            ps.setDouble(5, c.getBal());
            ps.executeUpdate();
        }
    }

    // SELECT (all) -> View Accounts : Viewcustomerdetails [ArrayList]
    public ArrayList<Customer> Viewcustomerdetails() throws SQLException {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setAcno(rs.getInt("acno"));
                c.setCname(rs.getString("cname"));
                c.setEmail(rs.getString("email"));
                c.setAccounttype(rs.getString("accounttype"));
                c.setBal(rs.getDouble("bal"));
                list.add(c);
            }
        }
        return list;
    }

    // SELECT (one) -> Search Account; raises InvalidAccount if acno not found
    public Customer searchCustomer(int acno) throws SQLException, InvalidAccount {
        String sql = "SELECT * FROM customers WHERE acno=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, acno);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new InvalidAccount("invalid account number");
                }
                Customer c = new Customer();
                c.setAcno(rs.getInt("acno"));
                c.setCname(rs.getString("cname"));
                c.setEmail(rs.getString("email"));
                c.setAccounttype(rs.getString("accounttype"));
                c.setBal(rs.getDouble("bal"));
                return c;
            }
        }
    }

    // UPDATE -> update customer email (extra, covers "update" in JDBC list)
    public void updateEmail(int acno, String email) throws SQLException, InvalidAccount {
        String sql = "UPDATE customers SET email=? WHERE acno=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, acno);
            if (ps.executeUpdate() == 0) {
                throw new InvalidAccount("invalid account number");
            }
        }
    }

    // DELETE -> Delete Account
    public void deleteCustomer(int acno) throws SQLException, InvalidAccount {
        String sql = "DELETE FROM customers WHERE acno=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, acno);
            if (ps.executeUpdate() == 0) {
                throw new InvalidAccount("invalid account number");
            }
        }
    }
}