package org.example;

import io.cucumber.java.en.*;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("org.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class StepsChooseChargingStation {

    private ChargingStation chargingStation;
    private String stationInformation;
    private STATUS stationStatus;

    @Given("I want to view information about a charging station")
    public void iWantToViewInformationAboutAChargingStation() {
        // Initialisiere eine Beispiel-Ladestation mit allen Daten
        chargingStation = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
    }

    @When("I select a charging station for information")
    public void iSelectAChargingStationForInformation() {
        // Rufe Informationen der Ladestation ab
        stationInformation = chargingStation.toString();
    }

    @Then("the system should provide general information")
    public void theSystemShouldProvideGeneralInformation() {
        // Überprüfe, ob die Informationen korrekt sind
        assert stationInformation != null && !stationInformation.isEmpty();
        System.out.println("Station Information: " + stationInformation);
    }

    @Given("I want to see the status of the charging station")
    public void iWantToSeeTheStatusOfTheChargingStation() {
        // Initialisiere dieselbe Ladestation mit einem festen Status
        chargingStation = new ChargingStation(101, "Downtown", 0.2, 0.5, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
    }

    @When("I select a charging station for status")
    public void iSelectAChargingStationForStatus() {
        // Rufe den Status der Ladestation ab
        stationStatus = chargingStation.getStatus();
    }

    @Then("the system should show me if the charging station is occupied")
    public void theSystemShouldShowMeIfTheChargingStationIsOccupied() {
        // Überprüfe, ob der Status korrekt ist
        assert stationStatus != null : "Station status is not defined!";
        assert stationStatus == STATUS.IN_BETRIEB_BESETZT || stationStatus == STATUS.IN_BETRIEB_FREI :
                "Station status must be IN_BETRIEB_BESETZT or IN_BETRIEB_FREI";
        System.out.println("Charging Station Status: " + stationStatus);
    }
}