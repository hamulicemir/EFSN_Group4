package org.software.Objekte;

import org.software.Enums.CHARGING_TYPE;
import org.software.Enums.STATUS;

import java.util.LinkedList;
import java.util.List;

public class ChargingStation {

    private int stationID;
    private String location;
    private double pricePerMinute;
    private double pricePerKWh;
    private STATUS status;
    private CHARGING_TYPE chargingType;
    private List<ChargingPoints> pointsList = new LinkedList<>();

    public ChargingStation (int stationID, String location, double pricePerMinute, double pricePerKWh, STATUS status, CHARGING_TYPE chargingType){
        this.stationID = stationID;
        this.location = location;
        this.pricePerMinute = pricePerMinute;
        this.pricePerKWh = pricePerKWh;
        this.status = status;
        this.chargingType = chargingType;
    }

    public ChargingStation() {

    }

    public List<ChargingPoints> getPointsList() {
        return pointsList;
    }

    public void addCharingPoint(ChargingPoints chargingPoint) {
       pointsList.add(chargingPoint);
    }

    public int getStationID(){
        return this.stationID;
    }

    public STATUS getStatus() {
        return this.status;
    }

    public CHARGING_TYPE getChargingType() {
        return this.chargingType;
    }

    public String getLocation (){
        return this.location;
    }

    public void updateChargingPrice (double pricePerKWh){
        this.pricePerKWh = pricePerKWh;
    }

    public boolean removeChargingPoint (ChargingPoints chargingPoint) {
        if (pointsList.contains(chargingPoint)) {
            pointsList.remove(chargingPoint);
            System.out.println("Charging point removed successfully.");
            return true;
        } else {
            System.out.println("Charging point not found.");
            return false;
        }
    }

    public double getPricePerKWh(){
        return this.pricePerKWh;
    }

    public double getPricePerMinute(){
        return this.pricePerMinute;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Station ID: ").append(stationID).append("\n");
        sb.append("Location: ").append(location).append("\n");
        sb.append("Price per minute: ").append(pricePerMinute).append("\n");
        sb.append("Price per kWh: ").append(pricePerKWh).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Charging Type: ").append(chargingType).append("\n");
        return sb.toString();
    }
}
