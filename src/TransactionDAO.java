import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {

    public void performTransaction(int acno, char ttype, double tamt)
            throws SQLException, InvalidAccount, InsufficientFunds,
                   InvalidAmt, InvalidTransactionException {

        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false); // start transaction

            // 1) check acno is valid and get bal
            double bal;
            String sel = "SELECT bal FROM customers WHERE acno=? FOR UPDATE";
            try (PreparedStatement ps = con.prepareStatement(sel)) {
                ps.setInt(1, acno);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new InvalidAccount("invalid account number");
                    }
                    bal = rs.getDouble("bal");
                }
            }

            // 2) validate ttype and amount
            ttype = Character.toLowerCase(ttype);
            if (ttype == 'w') {
                if (tamt < 100) {
                    throw new InvalidAmt("invalid withdraw");
                }
                if (tamt > bal) {
                    throw new InsufficientFunds("invalid withdraw - insufficient funds");
                }
                bal = bal - tamt;
            } else if (ttype == 'd') {
                if (tamt > 50000 || tamt <= 0) {
                    throw new InvalidAmt("invalid deposit");
                }
                bal = bal + tamt;
            } else {
                throw new InvalidTransactionException(); // 'invalid transaction'
            }

            // 3) update balance
            String upd = "UPDATE customers SET bal=? WHERE acno=?";
            try (PreparedStatement ps = con.prepareStatement(upd)) {
                ps.setDouble(1, bal);
                ps.setInt(2, acno);
                ps.executeUpdate();
            }

            // 4) insert into transactions table
            String ins = "INSERT INTO transactions(transaction_type,acno,tamt) VALUES(?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(ins)) {
                ps.setString(1, String.valueOf(ttype));
                ps.setInt(2, acno);
                ps.setDouble(3, tamt);
                ps.executeUpdate();
            }

            con.commit(); // success -> commit
            System.out.println("Transaction successful. New balance: " + bal);

        } catch (SQLException | InvalidAccount | InsufficientFunds
                 | InvalidAmt | InvalidTransactionException e) {
            con.rollback(); // any failure -> rollback
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }
}