package org.example;

import java.util.Date;

public class Order {
    private int orderID;
    private Customer customer;
    private ChargingStation chargingStation;
    private ChargingPoints chargingPoint;
    private int invoiceNumber;
    private Date startTime;
    private Date endTime;
    private double total;

    public Order(int orderID, Customer customer, ChargingStation chargingStation, ChargingPoints chargingPoint, Date startTime, Date endTime, int invoiceNumber) {
        this.orderID = orderID;
        this.customer = customer;
        this.chargingStation = chargingStation;
        this.chargingPoint = chargingPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.invoiceNumber = invoiceNumber;
    }

    public double calculateTotalCost(CHARGING_TYPE chargingType, double chargingTimeMinutes, double kWhConsumed){
        double typeCost = 0;
        if (chargingType == CHARGING_TYPE.AC)
            typeCost += 5;
        else if (chargingType == CHARGING_TYPE.DC) {
            typeCost += 2;
        }
        double timeCost = chargingTimeMinutes * 0.1;
        double consumptionCost = kWhConsumed * 0.5;

        double totalCost = typeCost + timeCost + consumptionCost;
        this.total = totalCost;

        return Math.round(totalCost);
    }

    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public String getLocation(){
        return chargingStation.getLocation();
    }
    public CHARGING_TYPE getChargingType(){
        return chargingPoint.getChargingType();
    }
    public STATUS getStatus() {
        return chargingPoint.getStatus();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("orderID: ").append(orderID).append("\n");
        sb.append("Customer: ").append(customer.toString()).append("\n");
        sb.append("Charing Station: ").append(chargingStation.toString()).append("\n");
        sb.append("Charging Point: ").append(chargingPoint.toString()).append("\n");
        sb.append("Start Time: ").append(startTime).append("\n");
        sb.append("End Time: ").append(endTime).append("\n");
        sb.append("Invoice Number: ").append(invoiceNumber).append("\n");
        return sb.toString();
    }

}
