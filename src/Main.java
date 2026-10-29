import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerDAO cdao = new CustomerDAO();
        TransactionDAO tdao = new TransactionDAO();

        // ---------- LOGIN / SIGN-IN ----------
        System.out.println("===== LOGIN =====");
        System.out.print("Enter user id: ");
        String uid = sc.nextLine();
        System.out.print("Enter password: ");
        String pwd = sc.nextLine();

        try {
            LoginService.login(uid, pwd);
            System.out.println("Login successful. Welcome to Bank Transactions!");
        } catch (InvalidUserException e) {
            System.out.println("Error: " + e.getMessage()); // invaliduser
            return;
        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
            return;
        }

        // ---------- MENU ----------
        int choice = 0;
        do {
            System.out.println("\n1. Create Account");
            System.out.println("2. View Accounts");
            System.out.println("3. Search Account");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Delete Account");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1: // Create Account (setter/getter input + print)
                        Customer c = new Customer();
                        System.out.print("Enter acno: ");
                        c.setAcno(Integer.parseInt(sc.nextLine()));
                        System.out.print("Enter cname: ");
                        c.setCname(sc.nextLine());
                        System.out.print("Enter email: ");
                        c.setEmail(sc.nextLine());
                        System.out.print("Enter accounttype (savings/current): ");
                        c.setAccounttype(sc.nextLine());
                        System.out.print("Enter bal: ");
                        c.setBal(Double.parseDouble(sc.nextLine()));
                        cdao.insertCustomer(c);
                        System.out.println("Account created:");
                        c.printDetails();
                        break;

                    case 2: // View Accounts -> ArrayList
                        ArrayList<Customer> list = cdao.Viewcustomerdetails();
                        if (list.isEmpty()) {
                            System.out.println("No customers found.");
                        } else {
                            for (Customer cu : list) {
                                cu.printDetails();
                            }
                        }
                        break;

                    case 3: // Search Account -> acno, cname, bal
                        System.out.print("Enter acno: ");
                        int sacno = Integer.parseInt(sc.nextLine());
                        Customer found = cdao.searchCustomer(sacno);
                        System.out.println("acno: " + found.getAcno()
                                + " | cname: " + found.getCname()
                                + " | bal: " + found.getBal());
                        break;

                    case 4: // Deposit
                        System.out.print("Enter acno: ");
                        int dacno = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter tamt: ");
                        double damt = Double.parseDouble(sc.nextLine());
                        tdao.performTransaction(dacno, 'd', damt);
                        break;

                    case 5: // Withdraw
                        System.out.print("Enter acno: ");
                        int wacno = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter tamt: ");
                        double wamt = Double.parseDouble(sc.nextLine());
                        tdao.performTransaction(wacno, 'w', wamt);
                        break;

                    case 6: // Delete Account
                        System.out.print("Enter acno to delete: ");
                        int delno = Integer.parseInt(sc.nextLine());
                        cdao.deleteCustomer(delno);
                        System.out.println("Account deleted.");
                        break;

                    case 7:
                        System.out.println("Thank you. Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (InvalidAccount | InsufficientFunds | InvalidAmt
                     | InvalidTransactionException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            } catch (java.sql.SQLException e) {
                System.out.println("DB Error: " + e.getMessage());
            }

        } while (choice != 7);

        sc.close();
    }
}