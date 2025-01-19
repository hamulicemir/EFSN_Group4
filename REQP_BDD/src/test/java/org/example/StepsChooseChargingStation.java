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

    @Given("I want to view all charging stations.")
    public void iWantToViewInformationAboutAChargingStation() {
        for (Map.Entry<Integer, ChargingStation> entry : chargingStations.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

    @When("I select a charging station for information")
    public void selectChargingStationForInformation(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int stationId = Integer.parseInt(row.get("station_id"));
                if (chargingStations.containsKey(stationId)) {
                    selectedChargingStation = chargingStations.get(stationId);
                    System.out.println("Selected station: " + selectedChargingStation.toString());
                } else {
                    System.out.println("Station ID " + stationId + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid station ID format: " + row.get("station_id"));
            }
        }
    }

    @Then("the system should provide general information")
    public void theSystemShouldProvideGeneralInformation() {
        if (selectedChargingStation != null) {
            System.out.println(selectedChargingStation.toString());
        } else {
            System.out.println("No charging station selected.");
        }
    }

    @Given("I want to see the status of the charging station")
    public void iWantToSeeTheStatusOfTheChargingStation() {
        System.out.println("Preparing to check the status of charging stations.");
    }

    @When("I select a charging station for status")
    public void selectChargingStationForStatus(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int stationId = Integer.parseInt(row.get("station_id"));
                if (chargingStations.containsKey(stationId)) {
                    selectedChargingStation = chargingStations.get(stationId);
                    System.out.println("Selected station ID for status: " + selectedChargingStation.getStationID());
                } else {
                    System.out.println("Station ID " + stationId + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid station ID format: " + row.get("station_id"));
            }
        }
    }

    @Then("the system should show me if the charging station is occupied")
    public void theSystemShouldShowMeIfTheChargingStationIsOccupied() {
        if (selectedChargingStation != null) {
            STATUS status = selectedChargingStation.getStatus();
            if(status == STATUS.IN_BETRIEB_BESETZT){
                System.out.println("Charging station is occupied.");
            }
            else{
                System.out.println("Charging station is not occupied.");
            }
            System.out.println("Charging station ID " + selectedChargingStation.getStationID() + " is " + status + ".");
        } else {
            System.out.println("No charging station selected to check status.");
        }
    }

    //  ERROR CASES //


        @Given("I want to see the status of a non-existing charging station")
        public void iWantToSeeTheStatusOfANonExistingChargingStation() {
            selectedChargingStation = null;
        }

        @When("I select a non-existing charging station for status")
        public void iSelectAChargingStationForStatus(Map<String, String> stationData) {
            for (Map.Entry<String, String> entry : stationData.entrySet()) {
                int stationId;
                try {
                    stationId = Integer.parseInt(entry.getValue());
                    selectedChargingStation = chargingStations.get(stationId);
                    if (selectedChargingStation == null) {
                        System.out.println("Charging station does not exist.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid station ID format.");
                }
            }
        }

        @Then("the system should display an error message indicating the station does not exist")
        public void theSystemShouldDisplayAnErrorMessageIndicatingTheStationDoesNotExist() {
            System.out.println("Charging station does not exist.");
        }

        @Given("I want to view information about a charging station with an invalid station id")
        public void iWantToViewInformationAboutAChargingStationWithAnInvalidStationId() {
            selectedChargingStation = null;
        }

        @When("I select a charging station with an invalid station id for information")
        public void iSelectAChargingStationForInformation(Map<String, String> stationData) {
            for (Map.Entry<String, String> entry : stationData.entrySet()) {
                try {
                    int stationId = Integer.parseInt(entry.getValue());
                    selectedChargingStation = chargingStations.get(stationId);
                    if (selectedChargingStation == null) {
                        System.out.println("Charging station does not exist.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid station ID format.");
                }
            }
        }

        @Then("the system should display an error message indicating the station ID is invalid")
        public void theSystemShouldDisplayAnErrorMessageIndicatingTheStationIDIsInvalid() {
            System.out.println("Invalid station ID format");
        }

    }