package org.example;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import java.util.LinkedList;
import java.util.List;

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

    @When("I add a new charging station")
    public void iAddANewChargingStation() {
        // Erstelle eine neue Ladestation
        newStation = new ChargingStation(102, "City Center", 0.3, 0.6, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        chargingStations.add(newStation);
    }

    @Then("I should be able to input the Charging Station details")
    public void iShouldBeAbleToInputTheChargingStationDetails() {
        // Überprüfe, ob die neue Station existiert und Details korrekt eingegeben wurden
        assert newStation != null;
        System.out.println("Charging Station Added: " + newStation);
    }

    @And("the new charging station should be listed.")
    public void theNewChargingStationShouldBeListed() {
        // Überprüfe, ob die Station in der Liste enthalten ist
        assert chargingStations.contains(newStation);
        System.out.println("Charging Stations List: " + chargingStations);
    }

    @Given("I want to remove a charging station")
    public void iWantToRemoveAChargingStation() {
        // Initialisiere eine Liste mit vorhandenen Stationen
        chargingStations = new LinkedList<>();
        chargingStations.add(new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.AC));
        chargingStations.add(new ChargingStation(102, "City Center", 0.3, 0.6, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC));
    }

    @When("I remove a charging station")
    public void iRemoveAChargingStation() {
        // Entferne eine bestimmte Station
        ChargingStation stationToRemove = chargingStations.get(0); // Beispiel: Entferne die erste Station
        chargingStations.remove(stationToRemove);
        System.out.println("Removed Charging Station: " + stationToRemove);
    }

    @Then("charging Station should be removed from the listing.")
    public void chargingStationShouldBeRemovedFromTheListing() {
        // Überprüfe, ob die Station entfernt wurde
        assert chargingStations.size() == 1;
        System.out.println("Remaining Charging Stations: " + chargingStations);
    }
    @Test
    public void testAddChargingStation() {
        List<ChargingStation> chargingStations = new LinkedList<>();
        ChargingStation newStation = new ChargingStation(102, "City Center", 0.3, 0.6, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        chargingStations.add(newStation);
        assertTrue(chargingStations.contains(newStation));
        assertTrue(newStation.getLocation().equals("City Center"));
        assertFalse(chargingStations.isEmpty());
        assertFalse(newStation.getPricePerMinute() == 0.0);
        assertEquals(1, chargingStations.size());
        assertEquals("City Center", newStation.getLocation());
    }

    @Test
    public void testRemoveChargingStation() {
        List<ChargingStation> chargingStations = new LinkedList<>();
        ChargingStation station1 = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.AC);
        ChargingStation station2 = new ChargingStation(102, "City Center", 0.3, 0.6, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        chargingStations.add(station1);
        chargingStations.add(station2);
        chargingStations.remove(station1);
        assertTrue(chargingStations.contains(station2));
        assertTrue(chargingStations.size() == 1);
        assertFalse(chargingStations.contains(station1));
        assertFalse(chargingStations.isEmpty());
        assertEquals(1, chargingStations.size());
        assertEquals("City Center", station2.getLocation());
    }

    @Test
    public void testChargingStationDetails() {
        ChargingStation newStation = new ChargingStation(102, "City Center", 0.3, 0.6, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        assertTrue(newStation.getLocation().equals("City Center"));
        assertTrue(newStation.getPricePerMinute() == 0.3);
        assertFalse(newStation.getPricePerKWh() == 0.0);
        assertFalse(newStation.getStatus() == STATUS.AUSSER_BETRIEB);
        assertEquals(102, newStation.getStationID());
        assertEquals(CHARGING_TYPE.DC, newStation.getChargingType());
    }

    @Test
    public void testChargingStationList() {
        List<ChargingStation> chargingStations = new LinkedList<>();
        ChargingStation station1 = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.AC);
        ChargingStation station2 = new ChargingStation(102, "City Center", 0.3, 0.6, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        chargingStations.add(station1);
        chargingStations.add(station2);
        assertTrue(chargingStations.contains(station1));
        assertTrue(chargingStations.contains(station2));
        assertFalse(chargingStations.isEmpty());
        assertFalse(chargingStations.size() == 0);
        assertEquals(2, chargingStations.size());
        assertEquals("Downtown", station1.getLocation());
    }

    @Test
    public void testUpdateChargingPrice() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        station.updateChargingPrice(0.6);
        assertTrue(station.getPricePerKWh() == 0.6);
        assertTrue(station.getPricePerMinute() == 0.2);
        assertFalse(station.getPricePerKWh() == 0.5);
        assertFalse(station.getPricePerMinute() == 0.0);
        assertEquals(0.6, station.getPricePerKWh());
        assertEquals(0.2, station.getPricePerMinute());
    }

    @Test
    public void testAddChargingPoint() {
        ChargingStation station = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        ChargingPoints point = new ChargingPoints(1, "Wien", STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.DC);
        station.addCharingPoint(point);
        assertTrue(station.getPointsList().contains(point));
        assertTrue(point.getLocation().equals("Wien"));
        assertFalse(station.getPointsList().isEmpty());
        assertFalse(point.getStatus() == STATUS.AUSSER_BETRIEB);
        assertEquals(1, point.getPointID());
        assertEquals(CHARGING_TYPE.DC, point.getChargingType());
    }
}