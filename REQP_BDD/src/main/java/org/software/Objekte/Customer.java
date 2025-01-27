package org.software.Objekte;

public class Customer {
    private int customerID;
    private String customerEmail;
    private String customerName;
    private double customerBalance;
    private String customerPassword;

    public Customer(int customerID, String customerEmail,String customerName,double customerBalance, String customerPassword){
        this.customerID = customerID;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerBalance = customerBalance;
        this.customerPassword= customerPassword;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getCustomerID() {
        return customerID;
    }

    public boolean register(String customerEmail, String customerName, String customerPassword){
        if (customerEmail == null || customerName == null || customerPassword == null) {
            return false;
        }
        if(customerEmail.equals(this.customerEmail)){
            System.out.println("This email is not available. Please enter a valid email address!");
            return false;
        }
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        return true;
    }

    public boolean login(String customerEmail, String customerPassword){
        if (this.customerEmail.equals(customerEmail) && this.customerPassword.equals(customerPassword)) {
            return true;
        }
        return false;
    }

    public double viewBalance() {
        return customerBalance;
    }

    public boolean topUpAccount (double amount){
        if (amount <= 0){
            return false;
        }
        this.customerBalance += amount;
        return true;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("customerID: ").append(customerID).append("\n");
        sb.append("Customer Email: ").append(customerEmail).append("\n");
        sb.append("Customer Name: ").append(customerName).append("\n");
        sb.append("Customer Balance: ").append(customerBalance).append("\n");
        return sb.toString();
    }
}
