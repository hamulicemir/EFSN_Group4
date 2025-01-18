package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.software.Enums.CHARGING_TYPE;
import org.software.Objekte.ChargingPoints;
import org.software.Objekte.ChargingStation;
import org.software.Enums.STATUS;

import java.util.List;
import java.util.Map;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("org.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")

public class StepsChooseChargingStation {

    private ChargingStation chargingStation;

    @Given("I want to view information about a charging station")
    public void iWantToViewInformationAboutAChargingStation() {
        // Initialisiere eine Beispiel-Ladestation mit allen Daten
        chargingStation = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
    }

    @When("I select a charging station for information")
    public void selectChargingStationForInformation(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            String stationId = row.get("station_id");
            // Add logic to handle the selection of the charging station for information
        }
    }

    @Then("the system should provide general information")
    public void theSystemShouldProvideGeneralInformation() {
        // Set the station information before the assertion
        String stationInformation = chargingStation.toString();

        // Check if the information is correct
        assert stationInformation != null && !stationInformation.isEmpty() : "Station information is null or empty!";
        System.out.println("Station Information: " + stationInformation);
    }

    @Given("I want to see the status of the charging station")
    public void iWantToSeeTheStatusOfTheChargingStation() {
        // Initialisiere dieselbe Ladestation mit einem festen Status
        chargingStation = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
    }

    @When("I select a charging station for status")
    public void selectChargingStationForStatus(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            String stationId = row.get("station_id");
            // Add logic to handle the selection of the charging station for status
        }
    }

    @Then("the system should show me if the charging station is occupied")
    public void theSystemShouldShowMeIfTheChargingStationIsOccupied() {
        // Set the station status before the assertion
        STATUS stationStatus = chargingStation.getStatus();

        // Check if the status is correct
        assert stationStatus != null : "Station status is not defined!";
        assert stationStatus == STATUS.IN_BETRIEB_BESETZT || stationStatus == STATUS.IN_BETRIEB_FREI :
                "Station status must be IN_BETRIEB_BESETZT or IN_BETRIEB_FREI";
        System.out.println("Charging Station Status: " + stationStatus);
    }






    @Test
    public void testGetStationInfo() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        String info = station.toString();
        assertNotNull(info);
        assertTrue(info.contains("Downtown"));
    }

    @Test
    public void testGetStatus() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.AC);
        STATUS status = station.getStatus();
        assertEquals(STATUS.IN_BETRIEB_BESETZT, status);
    }

    @Test
    public void testUpdateChargingPrice() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        station.updateChargingPrice(0.6);
        assertEquals(0.6, station.getPricePerKWh());
        assertNotEquals(0.5, station.getPricePerKWh());
    }

    @Test
    public void testAddChargingPoint() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        ChargingPoints point = new ChargingPoints(1,"Wien", STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        station.addCharingPoint(point);
        assertTrue(station.getPointsList().contains(point));
    }

    @Test
    public void testRemoveChargingPoint() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        ChargingPoints point = new ChargingPoints(1,"Wien", STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        station.addCharingPoint(point);
        assertTrue(station.removeChargingPoint(point));
        assertFalse(station.getPointsList().contains(point));
    }

    @Test
    public void testGetLocation() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        assertEquals("Downtown", station.getLocation());
        assertNotNull(station.getLocation());
    }
}