package org.example;

public class Customer {
    private int customerID;
    private String customerEmail;
    private String customerName;
    private double customerBalance;
    private String customerPassword;

    public Customer(int customerID, String customerEmail,String customerName,double customerBalance){
        this.customerID = customerID;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerBalance = customerBalance;
    }

    public boolean register(String customerEmail, String customerName, String customerPassword){
        if (customerEmail == null || customerName == null || customerPassword == null) {
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
        if (amount <=0){
            return false;
        }
        this.customerBalance += amount;
        return true;
    }

    public String getCustomerName() {
        return customerName;
    }

    /*
        private int customerID;
    private String customerEmail;
    private String customerName;
    private double customerBalance;
    private String customerPassword;
     */

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
