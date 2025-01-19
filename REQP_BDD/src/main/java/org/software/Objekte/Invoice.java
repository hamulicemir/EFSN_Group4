package org.software.Objekte;

public class Invoice {
    private int invoiceNumber;
    private Order order;

    public Invoice(int invoiceNumber, Order order){
        this.invoiceNumber = invoiceNumber;
        this.order = order;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public String generateInvoice(){
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice Number: ").append(invoiceNumber).append("\n");
        sb.append("Order: ").append(order.toString()).append("\n");
        return sb.toString();
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber=" + invoiceNumber +
                ", order=" + order +
                '}';
    }
}
