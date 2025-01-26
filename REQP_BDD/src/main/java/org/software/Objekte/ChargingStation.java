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

    private List<ChargingPoints> pointsList = new LinkedList<>();

    public ChargingStation (int stationID, String location, double pricePerMinute, double pricePerKWh, CHARGING_TYPE chargingType){
        this.stationID = stationID;
        this.location = location;
        this.pricePerMinute = pricePerMinute;
        this.pricePerKWh = pricePerKWh;
    }
    public ChargingStation (int stationID, String location){
        this.stationID = stationID;
        this.location = location;
    }

    public ChargingStation() {
        this.stationID = 0;
        this.location = "";
        this.pricePerMinute = 0;
        this.pricePerKWh = 0;
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
    public boolean setPricePerMinute(double pricePerMinute){
        if(pricePerMinute > 0) {
            this.pricePerMinute = pricePerMinute;
            return true;
        }
        return false;
    }

    public boolean setPricePerKWh(double pricePerKWh) {
        if(pricePerKWh > 0){
            this.pricePerKWh = pricePerKWh;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Station ID: ").append(stationID).append("\n");
        sb.append("Location: ").append(location).append("\n");
        sb.append("Price per minute: ").append(pricePerMinute).append("\n");
        sb.append("Price per kWh: ").append(pricePerKWh).append("\n");
        return sb.toString();
    }
}
