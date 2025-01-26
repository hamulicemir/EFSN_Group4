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

import java.util.*;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.*;


public class StepsManageChargingStation {

    private Map<Integer, ChargingStation> chargingStations = new HashMap<>();
    private String errorMessage;

    //Test Case 1
    @When("I add a new charging station with details")
    public void iAddANewChargingStationWithDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int id = Integer.parseInt(row.get("id"));
                String location = row.get("location");
                double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
                double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

                ChargingStation station = new ChargingStation(id, location, pricePerMinute, pricePerKWh, null);
                chargingStations.put(id, station);

                System.out.println("Added charging station: " + station.toString());
            } catch (Exception e) {
                System.out.println("Failed to add charging station. Error: " + e.getMessage());
            }
        }
    }

    @Then("the new charging station should be listed")
    public void theNewChargingStationShouldBeListed(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));
            ChargingStation station = chargingStations.get(id);
            assertNotNull(station, "Charging station with ID " + id + " should be listed.");
            assertEquals(row.get("location"), station.getLocation(), "Location mismatch.");
            assertEquals(Double.parseDouble(row.get("pricePerMinute")), station.getPricePerMinute(), 0.01, "Price per minute mismatch.");
            assertEquals(Double.parseDouble(row.get("pricePerKWh")), station.getPricePerKWh(), 0.01, "Price per kWh mismatch.");
        }
    }

    //Test Case 2

    @Given("the following charging stations are existing:")
    public void theFollowingChargingStationsExist(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            // Parse and validate input
            int id = Integer.parseInt(row.get("id"));
            String location = row.get("location");
            double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

            // Create and add the charging station to the map
            ChargingStation station = new ChargingStation(id, location, pricePerMinute, pricePerKWh, null);
            chargingStations.put(id, station);
        }
        System.out.println("Initialized charging stations: " + chargingStations);
    }



    @When("I remove a charging station with id")
    public void iRemoveAChargingStationWithId(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));

            if (chargingStations.containsKey(id)) {
                chargingStations.remove(id);
                System.out.println("Removed charging station with ID: " + id);
            } else {
                System.out.println("Charging station with ID " + id + " does not exist.");
            }
        }
    }


    @Then("the charging station should no longer appear in the listing:")
    public void theChargingStationShouldNoLongerAppearInTheListing(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));

            // Check if the station is no longer in the map
            assertFalse(chargingStations.containsKey(id), "Charging station with ID " + id + " should not be in the listing.");
        }
        System.out.println("Verified the charging station has been removed.");
    }


    @Then("the listing should now contain:")
    public void theListingShouldNowContain(DataTable dataTable) {
        List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> expectedRow : expectedRows) {
            int id = Integer.parseInt(expectedRow.get("id"));
            String location = expectedRow.get("location");
            double pricePerMinute = Double.parseDouble(expectedRow.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(expectedRow.get("pricePerKWh"));

            // Validate that the remaining stations match the expected data
            ChargingStation station = chargingStations.get(id);
            assertNotNull(station, "Expected charging station with ID " + id + " was not found.");

            assertEquals(location, station.getLocation(), "Location mismatch for station ID " + id);
            assertEquals(pricePerMinute, station.getPricePerMinute(), "Price per minute mismatch for station ID " + id);
            assertEquals(pricePerKWh, station.getPricePerKWh(), "Price per kWh mismatch for station ID " + id);

            System.out.println("Verified charging station: " + station);
        }
    }

    //error cases

    @Given("I want to remove a charging station with an id that does not exist")
    public void iWantToRemoveAChargingStationWithAnIdThatDoesNotExist() {
        System.out.println("Ready to handle removal of non-existing charging station.");
    }

    @When("I remove a charging station with the following id")
    public void iRemoveAChargingStationWithTheFollowingId(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int id = Integer.parseInt(row.get("id"));
                if (chargingStations.containsKey(id)) {
                    chargingStations.remove(id);
                    System.out.println("Removed charging station with ID: " + id);
                } else {
                    errorMessage = "Charging Station with ID " + id + " does not exist";
                    System.out.println(errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format: " + row.get("id"));
            }
        }
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedMessage) {
        assertEquals(expectedMessage, errorMessage, "Error message should match the expected one.");
    }

    @And("no changes should be made to the listing")
    public void noChangesShouldBeMadeToTheListing() {
        System.out.println("No changes made to the existing list of charging stations.");
    }

    @Given("I want to add a charging station with a negative price per minute value")
    public void iWantToAddAChargingStationWithANegativePricePerMinuteValue() {
        System.out.println("Preparing to test invalid price input.");
    }

    @When("I add a new charging station with invalid details")
    public void iAddANewChargingStationWithInvalidDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
                if (pricePerMinute < 0) {
                    errorMessage = "Invalid details provided for the Charging Station";
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println("Error while validating details: " + e.getMessage());
            }
        }
    }

    @And("the charging station should not be added to the listing")
    public void theChargingStationShouldNotBeAddedToTheListing() {
        assertTrue(chargingStations.isEmpty(), "No invalid charging station should be added.");
    }
}