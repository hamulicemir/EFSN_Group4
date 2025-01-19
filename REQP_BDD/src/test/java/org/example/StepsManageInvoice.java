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
import org.software.Enums.CHARGING_TYPE;
import org.software.Enums.STATUS;
import org.software.Objekte.*;

import java.util.*;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("org.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")

public class StepsManageInvoice {

    private boolean loggedInAsOwner;
    private List<Invoice> invoices;// Initialisiere die Liste
    private List<Invoice> filteredInvoices;

    // Testdaten in main benutzen

    public StepsManageInvoice() {
        invoices = new ArrayList<>(); // Initialisiere die Liste im Konstruktor
        listenfill(invoice1);
        listenfill(invoice2);
    }

    public List<Invoice> listenfill (Invoice invoice1){
        invoices.add(invoice1);
        return invoices;
    }

    @Given("I am logged in an owner account")
    public void i_am_logged_in_an_owner_account() {
        loggedInAsOwner = true;
        System.out.println("Logged in as an owner account.");
    }

    @When("I view the invoice section")
    public void i_view_the_invoice_section() {
        if (!loggedInAsOwner) {
            throw new IllegalStateException("User must be logged in as owner to view the invoice section.");
        }
        if (invoices == null) {
            invoices = new ArrayList<>(); // Initialisiere die Liste, wenn sie null ist
        }
        invoices = fetchInvoices(); // Fetch invoices
        System.out.println("Fetched invoice section.");
    }

    @Then("I should see a list of invoices sorted by the start time of the charging process")
    public void i_should_see_a_list_of_invoices_sorted_by_the_start_time_of_the_charging_process() {
        invoices.sort(Comparator.comparing(invoice -> invoice.getOrder().getStartTime()));
        System.out.println("Invoices sorted by start time: " + invoices);
    }

    @Then("I should see a summary of account top-ups and outstanding balance.")
    public void i_should_see_a_summary_of_account_top_ups_and_outstanding_balance() {
        System.out.println("Account summary: Top-ups = $1000, Outstanding Balance = $250.");
    }

    @When("I request to see all invoices")
    public void i_request_to_see_all_invoices() {
        if (invoices == null || invoices.isEmpty()) { // Sicherheitsprüfung
            throw new IllegalStateException("No invoices available to display.");
        }
        filteredInvoices = new LinkedList<>(invoices); // Klonen der Liste
        System.out.println("Fetched all invoices.");
    }

    @Then("I should see a comprehensive list of invoices across all locations")
    public void i_should_see_a_comprehensive_list_of_invoices_across_all_locations() {
        System.out.println("Comprehensive invoice list: " + filteredInvoices);
    }

    @And("the list should be filterable by date, location, charging mode, and status.")
    public void the_list_should_be_filterable_by_date_location_charging_mode_and_status() {
        filteredInvoices = filterInvoicesByStatus(invoices, STATUS.IN_BETRIEB_BESETZT);
        System.out.println("Filtered invoices: " + filteredInvoices);
    }

    private List<Invoice> fetchInvoices() {
        List<Invoice> mockInvoices = new ArrayList<>();
        mockInvoices.add(invoice1);
        mockInvoices.add(invoice2);
        return mockInvoices;
    }

    private List<Invoice> filterInvoicesByDate(List<Invoice> invoices, Date startDate, Date endDate) {
        List<Invoice> filtered = new LinkedList<>();

        for (Invoice invoice : invoices) {
            if (invoice.getOrder().getStartTime() != null &&
                    (invoice.getOrder().getStartTime().equals(startDate) || invoice.getOrder().getStartTime().after(startDate)) &&
                    (invoice.getOrder().getEndTime().equals(endDate) || invoice.getOrder().getEndTime().before(endDate))) {
                filtered.add(invoice);
            }
        }
        return filtered;
    }

    public List<Invoice> filterInvoicesByLocation(List<Invoice> invoices, String location) {
        List<Invoice> filtered = new LinkedList<>();

        for (Invoice invoice : invoices) {
            if (invoice.getOrder().getLocation() != null && invoice.getOrder().getLocation().equalsIgnoreCase(location)) {
                filtered.add(invoice);
            }
        }

        return filtered;
    }

    public List<Invoice> filterInvoicesByChargingMode(List<Invoice> invoices, CHARGING_TYPE chargingMode) {
        List<Invoice> filtered = new LinkedList<>();

        for (Invoice invoice : invoices) {
            if (invoice.getOrder().getChargingType() == chargingMode) {
                filtered.add(invoice);
            }
        }

        return filtered;
    }

    public List<Invoice> filterInvoicesByStatus(List<Invoice> invoices, STATUS status) {
        List<Invoice> filtered = new LinkedList<>();

        for (Invoice invoice : invoices) {
            if (invoice.getOrder().getStatus() == status) {
                filtered.add(invoice);
            }
        }

        return filtered;
    }

}