public class Customer {
    private int acno;
    private String cname;
    private String email;
    private String accounttype;
    private double bal;

    public Customer() {}

    public Customer(int acno, String cname, String email, String accounttype, double bal) {
        this.acno = acno;
        this.cname = cname;
        this.email = email;
        this.accounttype = accounttype;
        this.bal = bal;
    }

    public int getAcno() { return acno; }
    public void setAcno(int acno) { this.acno = acno; }

    public String getCname() { return cname; }
    public void setCname(String cname) { this.cname = cname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAccounttype() { return accounttype; }
    public void setAccounttype(String accounttype) { this.accounttype = accounttype; }

    public double getBal() { return bal; }
    public void setBal(double bal) { this.bal = bal; }

    public void printDetails() {
        System.out.println("Acno: " + getAcno() + " | Name: " + getCname()
                + " | Email: " + getEmail() + " | Type: " + getAccounttype()
                + " | Bal: " + getBal());
    }
}