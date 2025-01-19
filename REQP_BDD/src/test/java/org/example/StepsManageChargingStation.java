package org.example;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.software.Enums.CHARGING_TYPE;
import org.software.Objekte.ChargingPoints;
import org.software.Objekte.ChargingStation;
import org.software.Enums.STATUS;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("org.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")


public class StepsManageChargingStation {

    private List<ChargingStation> chargingStations;
    private ChargingStation newStation;

    @Given("I want to add a charging station")
    public void iWantToAddAChargingStation() {
        // Initialisiere die Liste der Ladestationen
        chargingStations = new LinkedList<>();
    }

    @When("I add a new charging station with details")
    public void iAddANewChargingStationWithDetails(DataTable dataTable) {
        List<Map<String, String>> stationDetails = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> details : stationDetails) {
            ChargingStation station = new ChargingStation(
                Integer.parseInt(details.get("id")),
                details.get("location"),
                Double.parseDouble(details.get("pricePerMinute")),
                Double.parseDouble(details.get("pricePerKWh")),
                STATUS.valueOf(details.get("status")),
                CHARGING_TYPE.valueOf(details.get("chargingType"))
            );
            chargingStations.add(station);
            newStation = station; // Assign the newly created station to newStation
        }
    }

    @Then("I should be able to input the Charging Station details")
    public void iShouldBeAbleToInputTheChargingStationDetails() {
        // Überprüfe, ob die neue Station existiert und Details korrekt eingegeben wurden
        assert newStation != null;
        System.out.println("Charging Station Added: " + newStation);
    }

    @And("the new charging station should be listed")
    public void theNewChargingStationShouldBeListed() {
        // Überprüfe, ob die Station in der Liste enthalten ist
        assert chargingStations.contains(newStation);
        System.out.println("Charging Stations List: " + chargingStations);
    }

    @Given("I want to remove a charging station")
    public void iWantToRemoveAChargingStation() {
        chargingStations = new LinkedList<>();
        chargingStations.add(new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.AC));
        chargingStations.add(new ChargingStation(102, "City Center", 0.3, 0.6, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC));
    }

    @When("I remove a charging station with id")
    public void iRemoveAChargingStation(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));
            chargingStations.removeIf(station -> station.getStationID() == id);
            System.out.println("Removing station with id: " + id);
        }
    }

    @Then("charging Station should be removed from the listing")
    public void chargingStationShouldBeRemovedFromTheListing() {
        // Ensure the list size is as expected
        assertEquals(1, chargingStations.size(), "The size of the list is incorrect after removal.");

        // Optionally check that the removed ID is not in the list
        assertTrue(chargingStations.stream().noneMatch(station -> station.getStationID() == 101),
                "Station with ID 101 should be removed but is still in the list.");

        System.out.println("Remaining Charging Stations: " + chargingStations);
    }


}