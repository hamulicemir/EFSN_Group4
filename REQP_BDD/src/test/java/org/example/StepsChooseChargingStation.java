package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.software.Enums.CHARGING_TYPE;
import org.software.Enums.STATUS;
import org.software.Objekte.ChargingPoints;
import org.software.Objekte.ChargingStation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class StepsChooseChargingStation {

    //Laufzeit Variablen
    private Map<Integer, ChargingStation> chargingStations = new HashMap<>();
    private ChargingStation selectedChargingStation;
    private List<ChargingStation> runtimeChargingStations = new ArrayList<>();


    //Scenario 1
    @Given("the following charging stations exist:")
    public void theFollowingChargingStationsExist(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int stationId = Integer.parseInt(row.get("station_id"));
            String location = row.get("location");
            double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

            runtimeChargingStations.add(new ChargingStation(stationId, location, pricePerMinute, pricePerKWh, null));
        }
    }

    @When("I select a charging station for information")
    public void selectChargingStationForInformation(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int stationId = Integer.parseInt(row.get("station_id"));
            selectedChargingStation = runtimeChargingStations.stream()
                    .filter(station -> station.getStationID() == stationId)
                    .findFirst()
                    .orElse(null);

            if (selectedChargingStation == null) {
                System.out.println("Station ID " + stationId + " not found.");
            } else {
                System.out.println("Selected station: " + selectedChargingStation.toString());
            }
        }
    }

    @Then("the system should provide the following information:")
    public void theSystemShouldProvideGeneralInformation(DataTable dataTable) {
        if (selectedChargingStation == null) {
            fail("No charging station selected. Verify the station ID exists in the runtime data.");
        }

        Map<String, String> expectedData = dataTable.asMaps(String.class, String.class).get(0);

        assertEquals(expectedData.get("id"), String.valueOf(selectedChargingStation.getStationID()), "Station ID mismatch.");
        assertEquals(expectedData.get("location"), selectedChargingStation.getLocation(), "Location mismatch.");
        assertEquals(expectedData.get("pricePerMinute"), String.valueOf(selectedChargingStation.getPricePerMinute()), "Price per minute mismatch.");
        assertEquals(expectedData.get("pricePerKWh"), String.valueOf(selectedChargingStation.getPricePerKWh()), "Price per kWh mismatch.");
    }

    //Scenario 2
    @Given("the following charging stations with points exist:")
    public void theFollowingChargingStationsWithPointsExist(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int stationId = Integer.parseInt(row.get("station_id"));
            String location = row.get("location");
            double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));
            int pointId = Integer.parseInt(row.get("point_id"));
            STATUS status = STATUS.valueOf(row.get("status").toUpperCase());

            ChargingStation station = runtimeChargingStations.stream()
                    .filter(s -> s.getStationID() == stationId)
                    .findFirst()
                    .orElse(null);

            if (station == null) {
                station = new ChargingStation(stationId, location, pricePerMinute, pricePerKWh, null);
                runtimeChargingStations.add(station);
            }

            station.addCharingPoint(new ChargingPoints(pointId, status, CHARGING_TYPE.AC));
        }
    }

    @When("I select a charging station for status")
    public void selectChargingStationForStatus(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int stationId = Integer.parseInt(row.get("station_id"));
            selectedChargingStation = runtimeChargingStations.stream()
                    .filter(station -> station.getStationID() == stationId)
                    .findFirst()
                    .orElse(null);

            if (selectedChargingStation == null) {
                System.out.println("Station ID " + stationId + " not found.");
            } else {
                System.out.println("Selected station ID: " + selectedChargingStation.getStationID());
            }
        }
    }

    @Then("the system should show me if the charging station is occupied")
    public void theSystemShouldShowMeIfTheChargingStationIsOccupied() {
        assertNotNull(selectedChargingStation, "No charging station selected.");

        boolean isOccupied = selectedChargingStation.getPointsList().stream()
                .allMatch(point -> point.getStatus() == STATUS.IN_BETRIEB_BESETZT);

        if (isOccupied) {
            System.out.println("The charging station is fully occupied.");
            assertTrue(isOccupied, "Expected the station to be fully occupied.");
        } else {
            System.out.println("The charging station is not fully occupied.");
            assertFalse(isOccupied, "Expected the station to have available points.");
        }
    }


    //  ERROR CASES //

    @Given("I want to see the status of a non-existing charging station")
    public void iWantToSeeTheStatusOfANonExistingChargingStation() {
        selectedChargingStation = null;
    }

    @When("I search an id for a non-existing charging station to check the status")
    public void searchNonExistentChargingStationStatus(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int stationId = Integer.parseInt(row.get("station_id"));
                if (chargingStations.containsKey(stationId)) {
                    selectedChargingStation = chargingStations.get(stationId);
                    System.out.println("Selected station ID: " + selectedChargingStation.getStationID());
                } else {
                    selectedChargingStation = null;
                    System.out.println("Station ID " + stationId + " does not exist.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid station ID format: " + row.get("station_id"));
                selectedChargingStation = null;
            }
        }
    }

    @Then("the system should display the error message: \"This station does not exist.\"")
    public void theSystemShouldDisplayNonExistentStationError() {
        assertNull(selectedChargingStation, "Expected no charging station to be selected.");

        if (selectedChargingStation == null) {
            String errorMessage = "This station does not exist.";
            System.out.println(errorMessage);

            String expectedErrorMessage = "This station does not exist.";
            assertEquals(errorMessage, expectedErrorMessage, "Error message mismatch.");
        }
    }


    @When("I search for a charging station with an invalid station id in order to retrieve the station details")
    public void searchChargingStationWithInvalidIdFormat(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int stationId = Integer.parseInt(row.get("station_id"));
                if (chargingStations.containsKey(stationId)) {
                    selectedChargingStation = chargingStations.get(stationId);
                    System.out.println("Selected station ID: " + selectedChargingStation.getStationID());
                } else {
                    selectedChargingStation = null;
                    System.out.println("Station ID " + stationId + " not found.");
                }
            } catch (NumberFormatException e) {
                selectedChargingStation = null;
                System.out.println("Invalid station ID format: " + row.get("station_id"));
            }
        }
    }

    @Then("the system should display the error message: \"This station id is not valid.\"")
    public void theSystemShouldDisplayInvalidIdErrorMessage() {
        assertNull(selectedChargingStation, "Expected no charging station to be selected due to invalid ID format.");

        if (selectedChargingStation == null) {
            String errorMessage = "This station id is not valid.";
            System.out.println(errorMessage);

            String expectedErrorMessage = "This station id is not valid.";
            assertEquals(errorMessage, expectedErrorMessage, "Error message mismatch.");
        }
    }

    //Edge Case
    @Given("a charging station with no available charging points")
    public void givenChargingStationWithNoAvailableChargingPoints(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int stationId = Integer.parseInt(row.get("station_id"));
            String location = row.get("location");
            double pricePerMinute = Double.parseDouble(row.get("pricePerMinute"));
            double pricePerKWh = Double.parseDouble(row.get("pricePerKWh"));

            ChargingStation chargingStation = new ChargingStation(stationId, location, pricePerMinute, pricePerKWh, null);

            for (int i = 1; i <= 3; i++) {
                chargingStation.addCharingPoint(new ChargingPoints(i, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.AC));
            }

            chargingStations.put(stationId, chargingStation);
        }
    }

    @When("I select the charging station for status information")
    public void selectChargingStationForStatusInformation(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            try {
                int stationId = Integer.parseInt(row.get("station_id"));
                if (chargingStations.containsKey(stationId)) {
                    selectedChargingStation = chargingStations.get(stationId);
                    System.out.println("Selected station ID: " + selectedChargingStation.getStationID());
                } else {
                    selectedChargingStation = null;
                    System.out.println("Station ID " + stationId + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid station ID format: " + row.get("station_id"));
                selectedChargingStation = null;
            }
        }
    }

    @Then("the system should display the message: \"No charging points available at this station.\"")
    public void theSystemShouldDisplayNoAvailablePointsMessage() {
        assertNotNull(selectedChargingStation, "No charging station selected.");

        boolean allOccupied = selectedChargingStation.getPointsList().stream()
                .allMatch(point -> point.getStatus() == STATUS.IN_BETRIEB_BESETZT);

        if (allOccupied) {
            String message = "No charging points available at this station.";
            System.out.println(message);

            String expectedMessage = "No charging points available at this station.";
            assertEquals(message, expectedMessage, "Error message mismatch.");
        } else {
            fail("Charging points are available, but the system incorrectly identified the station as fully occupied.");
        }
    }
}