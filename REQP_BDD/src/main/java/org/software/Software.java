package org.software;

import org.software.Enums.CHARGING_TYPE;
import org.software.Enums.STATUS;
import org.software.Objekte.*;

import java.util.*;

public class Software {

    private List<Customer> customerList = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        Software software = new Software();
        software.startProgram();
    }

    public void startProgram() {
        // Predefined customer data
        Customer customer1 = new Customer(1, "john.doe@example.com", "John Doe", 100.0, "password123");
        Customer customer2 = new Customer(2, "jane.smith@example.com", "Jane Smith", 150.0, "securePass");
        Customer customer3 = new Customer(3, "alice.brown@example.com", "Alice Brown", 200.0, "alice123");
        Customer customer4 = new Customer(4, "bob.jones@example.com", "Bob Jones", 50.0, "bobPassword");

        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        customerList.add(customer4);

        System.out.println("Customers added:");
        customerList.forEach(System.out::println);

        // Predefined charging station data
        ChargingStation station1 = new ChargingStation(101, "Main Street", 0.25, 0.50, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        ChargingStation station2 = new ChargingStation(102, "Highway 1", 0.30, 0.60, STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.DC);
        ChargingStation station3 = new ChargingStation(103, "City Center", 0.20, 0.45, STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        ChargingStation station4 = new ChargingStation(104, "Suburb", 0.22, 0.40, STATUS.AUSSER_BETRIEB, CHARGING_TYPE.DC);

        System.out.println("\nCharging stations added:");
        System.out.println(station1);
        System.out.println(station2);
        System.out.println(station3);
        System.out.println(station4);

        // Predefined charging points data
        ChargingPoints point1 = new ChargingPoints(1, "Main Street - Point A", STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        ChargingPoints point2 = new ChargingPoints(2, "Highway 1 - Point B", STATUS.IN_BETRIEB_BESETZT, CHARGING_TYPE.DC);
        ChargingPoints point3 = new ChargingPoints(3, "City Center - Point C", STATUS.IN_BETRIEB_FREI, CHARGING_TYPE.AC);
        ChargingPoints point4 = new ChargingPoints(4, "Suburb - Point D", STATUS.AUSSER_BETRIEB, CHARGING_TYPE.DC);

        System.out.println("\nCharging points added:");
        System.out.println(point1);
        System.out.println(point2);
        System.out.println(point3);
        System.out.println(point4);

        // Predefined orders data
        Order order1 = new Order(1, customer1, station1, point1, new Date(), new Date(), 20);
        Order order2 = new Order(2, customer2, station2, point2, new Date(), new Date(), 30);
        Order order3 = new Order(3, customer3, station3, point3, new Date(), new Date(), 25);
        Order order4 = new Order(4, customer4, station4, point4, new Date(), new Date(), 15);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);

        System.out.println("\nOrders added:");
        orders.forEach(System.out::println);

        // Predefined invoice data
        Invoice invoice1 = new Invoice(1, order1);
        Invoice invoice2 = new Invoice(2, order2);
        Invoice invoice3 = new Invoice(3, order3);
        Invoice invoice4 = new Invoice(4, order4);

        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);

        System.out.println("\nInvoices added:");
        invoices.forEach(System.out::println);

        System.out.println("\nProgram initialized with predefined data.");
    }
}
