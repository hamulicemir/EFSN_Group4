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
import org.software.Software;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("org.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")

public class StepsChooseChargingStation {

    private Map<Integer, ChargingStation> chargingStations = new HashMap<>();
    private ChargingStation selectedChargingStation;
    ChargingStation chargingStation = new ChargingStation();

    @Given("I want to view information about a charging station with the station id {int}")
    public void iWantToViewInformationAboutAChargingStation(int id) {
        if (chargingStation.getStationID() == id) {
            System.out.println("Charging Station with ID " + id + " found.");
            chargingStation.toString();
        } else {
            System.out.println("Charging Station with ID " + id + " not found.");
        }
    }

    @When("I select a charging station for information")
    public void selectChargingStationForInformation(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            int stationId = Integer.parseInt(row.get("station_id"));
            selectedChargingStation = chargingStations.get(stationId);
            if (selectedChargingStation == null) {
                System.out.println("Charging Station with ID " + stationId + " not found.");
            }
        }
    }

    @Then("the system should provide general information")
    public void theSystemShouldProvideGeneralInformation() {
        assertNotNull(selectedChargingStation, "No charging station selected!");
        String stationInformation = selectedChargingStation.toString();
        assertNotNull(stationInformation, "Station information is null!");
        assertFalse(stationInformation.isEmpty(), "Station information is empty!");
        System.out.println("Station Information: " + stationInformation);
    }

    @Given("I want to see the status of the charging station")
    public void iWantToSeeTheStatusOfTheChargingStation() {
        // Initialize some charging stations for testing
        chargingStations.put(101, new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC));
        chargingStations.put(102, new ChargingStation(102, "Uptown", 0.3, 0.6, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.DC));
    }

    @When("I select a charging station for status")
    public void selectChargingStationForStatus(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            int stationId = Integer.parseInt(row.get("station_id"));
            selectedChargingStation = chargingStations.get(stationId);
            if (selectedChargingStation != null) {
                STATUS stationStatus = selectedChargingStation.getStatus();
                System.out.println("Charging Station Status: " + stationStatus);
            } else {
                System.out.println("Charging Station with ID " + stationId + " not found.");
            }
        }
    }

    @Then("the system should show me if the charging station is occupied")
    public void theSystemShouldShowMeIfTheChargingStationIsOccupied() {
        assertNotNull(selectedChargingStation, "No charging station selected!");
        STATUS stationStatus = selectedChargingStation.getStatus();
        assertNotNull(stationStatus, "Station status is not defined!");
        assertTrue(stationStatus == STATUS.IN_BETRIEB_BESETZT || stationStatus == STATUS.IN_BETRIEB_FREI,
                "Station status must be IN_BETRIEB_BESETZT or IN_BETRIEB_FREI");
        System.out.println("Charging Station Status: " + stationStatus);
    }

    //  ERROR CASES //


}