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
import org.software.Enums.STATUS;
import org.software.Objekte.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.*;


public class StepsManageInvoice {

    @Given("I am logged in an owner account")
    public void iAmLoggedInAnOwnerAccount() {
    }

    @When("I view the invoice section")
    public void iViewTheInvoiceSection(DataTable dataTable) {

    }

    @Then("I should see a list of invoices sorted by the start time of the charging process")
    public void iShouldSeeAListOfInvoicesSortedByStartTime() {

    }

    @And("I should see a summary of account top-ups and outstanding balance.")
    public void iShouldSeeASummaryOfAccountTopUpsAndOutstandingBalance(DataTable summaryTable) {
        
    }

    @When("I request to see all invoices")
    public void iRequestToSeeAllInvoices() {

    }

    @Then("I should see a comprehensive list of invoices across all locations")
    public void iShouldSeeAComprehensiveListOfInvoices(DataTable dataTable) {

    }

    @And("the list should be filterable by date, location, charging mode, and status.")
    public void theListShouldBeFilterableByAttributes() {

    }

    @When("I view all invoices of a chosen customer")
    public void iViewAllInvoicesOfACustomer() {

    }

    @Then("I should see a message {string}")
    public void iShouldSeeAMessage(String expectedMessage) {

    }

    @And("the invoice list should be empty")
    public void theInvoiceListShouldBeEmpty() {

    }

    @When("I view the section of all invoices")
    public void iViewTheSectionOfAllInvoices(DataTable dataTable) {
    
    }

    @Then("I should see an error message for missing invoice details:")
    public void iShouldSeeAnErrorMessageForMissingInvoiceDetails(DataTable dataTable) {

    }
}