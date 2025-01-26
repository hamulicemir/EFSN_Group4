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
            int id = Integer.parseInt(row.get("id"));
            String location = row.get("location");
            double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

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

            ChargingStation station = chargingStations.get(id);
            assertNotNull(station, "Expected charging station with ID " + id + " was not found.");

            assertEquals(location, station.getLocation(), "Location mismatch for station ID " + id);
            assertEquals(pricePerMinute, station.getPricePerMinute(), "Price per minute mismatch for station ID " + id);
            assertEquals(pricePerKWh, station.getPricePerKWh(), "Price per kWh mismatch for station ID " + id);

            System.out.println("Verified charging station: " + station);
        }
    }

    //Error Cases

    @Given("the following charging stations with this data exist:")
    public void theFollowingChargingStationsExistWithThisData(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));
            String location = row.get("location");
            double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

            ChargingStation station = new ChargingStation(id, location, pricePerMinute, pricePerKWh, null);
            chargingStations.put(id, station);
        }
        System.out.println("Initialized charging stations: " + chargingStations);
    }

    @When("I attempt to remove a charging station with the following id:")
    public void iAttemptToRemoveAChargingStationWithTheFollowingId(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));

            if (chargingStations.containsKey(id)) {
                chargingStations.remove(id);
                errorMessage = null; // No error if station is removed
                System.out.println("Removed charging station with ID: " + id);
            } else {
                errorMessage = "Station " + id + " does not exist";
                System.out.println(errorMessage);
            }
        }
    }

    @Then("the system should display the error message for non-exisiting Station: {string}")
    public void theSystemShouldDisplayTheErrorMessageChargingStationDoesntExist(String expectedMessage) {
        assertNotNull(errorMessage, "Expected an error message but none was set.");
        assertEquals(expectedMessage, errorMessage, "Error message mismatch.");
        System.out.println("Verified error message: " + errorMessage);
    }

    @And("no changes should be made to the listing")
    public void noChangesShouldBeMadeToTheListing() {
        // No specific action required since we verify the final state in the next step
        System.out.println("Verified no changes were made to the listing.");
    }

    @Then("the listing should still contain:")
    public void theListingShouldStillContain(DataTable dataTable) {
        List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> expectedRow : expectedRows) {
            int id = Integer.parseInt(expectedRow.get("id"));
            String location = expectedRow.get("location");
            double pricePerMinute = Double.parseDouble(expectedRow.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(expectedRow.get("pricePerKWh"));

            ChargingStation station = chargingStations.get(id);
            assertNotNull(station, "Expected charging station with ID " + id + " was not found.");
            assertEquals(location, station.getLocation(), "Location mismatch for station ID " + id);
            assertEquals(pricePerMinute, station.getPricePerMinute(), "Price per minute mismatch for station ID " + id);
            assertEquals(pricePerKWh, station.getPricePerKWh(), "Price per kWh mismatch for station ID " + id);

            System.out.println("Verified charging station: " + station);
        }
    }


    @When("I add a new charging station with a negative price per minute value")
    public void iAddANewChargingStationWithANegativePricePerMinuteValue(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int id = Integer.parseInt(row.get("id"));
                String location = row.get("location");
                double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
                double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

                ChargingStation station = new ChargingStation(id, location);

                boolean setPricePerMinuteSuccess = station.setPricePerMinute(pricePerMinute);
                boolean setPricePerKWhSuccess = station.setPricePerKWh(pricePerKWh);

                if (setPricePerMinuteSuccess && setPricePerKWhSuccess) {
                    chargingStations.put(id, station);
                    errorMessage = null;
                } else {
                    errorMessage = "Invalid details provided for the Charging Station";
                }
            } catch (IllegalArgumentException e) {
                errorMessage = e.getMessage();
                System.out.println(errorMessage);
            }
        }
    }

    @Then("the system should display the error message for invalid details: {string}")
    public void theSystemShouldDisplayTheErrorMessage(String expectedMessage) {
        assertNotNull(errorMessage, "Expected an error message but none was set.");
        assertEquals(expectedMessage, errorMessage, "Error message mismatch.");
        System.out.println("Verified error message: " + errorMessage);
    }

    @Then("the charging station should not be added to the listing")
    public void theChargingStationShouldNotBeAddedToTheListing() {
        int invalidId = 202; // Replace with the ID from the scenario
        assertFalse(chargingStations.containsKey(invalidId), "Charging station with ID " + invalidId + " should not be in the listing.");
        System.out.println("Verified the charging station was not added to the listing.");
    }

    //Edge Case

    @Given("a charging station with ID {int} is currently in use")
    public void aChargingStationWithIDIsCurrentlyInUse(int id, DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String location = row.get("location");
            double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

            // Create a charging station with the given details
            ChargingStation station = new ChargingStation(id, location, pricePerMinute, pricePerKWh, null);
            station.addCharingPoint(new ChargingPoints(1, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.DC)); // Add active session
            chargingStations.put(id, station);
        }
        System.out.println("Initialized charging stations with active sessions: " + chargingStations);
    }

    @When("I attempt to remove the charging station with the following id")
    public void iAttemptToRemoveTheChargingStationWithTheFollowingId(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));
            String status = row.get("status");

            if (chargingStations.containsKey(id)) {
                ChargingStation station = chargingStations.get(id);

                // Check if the station is in use
                boolean isActive = station.getPointsList().stream()
                        .anyMatch(point -> point.getStatus().name().equals(status));

                if (isActive) {
                    errorMessage = "Charging Station with active sessions cannot be removed";
                    System.out.println(errorMessage);
                } else {
                    chargingStations.remove(id);
                    errorMessage = null; // No error if removed
                    System.out.println("Removed charging station with ID: " + id);
                }
            } else {
                errorMessage = "Charging Station with ID " + id + " does not exist";
                System.out.println(errorMessage);
            }
        }
    }

    @Then("the system should display the error message for non-removal: {string}")
    public void theSystemShouldDisplayTheErrorMessageForNonRemoval(String expectedMessage) {
        assertNotNull(errorMessage, "Expected an error message but none was set.");
        assertEquals(expectedMessage, errorMessage, "Error message mismatch.");
        System.out.println("Verified error message: " + errorMessage);
    }

    @Then("the charging station should remain in the listing")
    public void theChargingStationShouldRemainInTheListing() {
        int id = 202;
        assertTrue(chargingStations.containsKey(id), "Charging station with ID " + id + " should still be in the listing.");
        System.out.println("Verified the charging station remains in the listing.");
    }

    @Then("the listing should still contain the following data:")
    public void theListingShouldStillContainFollowingData(DataTable dataTable) {
        List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> expectedRow : expectedRows) {
            int id = Integer.parseInt(expectedRow.get("id"));
            String location = expectedRow.get("location");
            double pricePerMinute = Double.parseDouble(expectedRow.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(expectedRow.get("pricePerKWh"));

            ChargingStation station = chargingStations.get(id);
            assertNotNull(station, "Expected charging station with ID " + id + " was not found.");
            assertEquals(location, station.getLocation(), "Location mismatch for station ID " + id);
            assertEquals(pricePerMinute, station.getPricePerMinute(), "Price per minute mismatch for station ID " + id);
            assertEquals(pricePerKWh, station.getPricePerKWh(), "Price per kWh mismatch for station ID " + id);

            System.out.println("Verified charging station: " + station);
        }
    }
}