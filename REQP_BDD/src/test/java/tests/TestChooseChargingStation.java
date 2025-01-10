package tests;

import org.example.CHARGING_TYPE;
import org.example.ChargingStation;
import org.example.STATUS;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestChooseChargingStation {

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
}