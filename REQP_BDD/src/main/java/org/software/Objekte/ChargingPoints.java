package org.software.Objekte;

import org.software.Enums.CHARGING_TYPE;
import org.software.Enums.STATUS;

public class ChargingPoints {

    private int pointID;
    private String location;
    private STATUS status;
    private CHARGING_TYPE chargingType;

    public ChargingPoints(int pointID, String location, STATUS status, CHARGING_TYPE chargingType) {
        this.pointID = pointID;
        this.location = location;
        this.status = status;
        this.chargingType = chargingType;
    }

    public int getPointID() {
        return pointID;
    }

    public String getLocation() {
        return location;
    }

    public void updateStatus(STATUS newStatus){
        this.status = newStatus;
    }

    public STATUS getStatus() {
        return this.status;
    }

    public CHARGING_TYPE getChargingType () {
        return this.chargingType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Point ID: ").append(pointID).append("\n");
        sb.append("Location: ").append(location).append("\n");
        sb.append("Status: ").append(getStatus()).append("\n");
        sb.append("Charging Type: ").append(getChargingType()).append("\n");
        return sb.toString();
    }
}