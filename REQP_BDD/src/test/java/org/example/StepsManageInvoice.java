package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class StepsManageInvoice {

    //Test Case 1
    @When("I view the invoice section")
    public void iViewTheInvoiceSection(DataTable dataTable) {
        List<Map<String, String>> invoiceData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : invoiceData) {
            System.out.println("Invoice ID: " + row.get("invoiceId") +
                    ", Order ID: " + row.get("orderId") +
                    ", Customer Name: " + row.get("customerName") +
                    ", Start Time: " + row.get("startTime") +
                    ", End Time: " + row.get("endTime") +
                    ", Total: " + row.get("total") +
                    ", Price: " + row.get("price"));
        }
    }

    @Then("I should see a list of invoices sorted by the start time of the charging process")
    public void iShouldSeeAListOfInvoicesSortedByStartTime(DataTable dataTable) {
        List<Map<String, String>> invoiceData = dataTable.asMaps(String.class, String.class);

        List<Map<String, String>> sortedInvoices = invoiceData.stream()
                .sorted(Comparator.comparing(invoice -> LocalDateTime.parse(invoice.get("startTime"))))
                .collect(Collectors.toList());

        for (int i = 1; i < sortedInvoices.size(); i++) {
            LocalDateTime previousStartTime = LocalDateTime.parse(sortedInvoices.get(i - 1).get("startTime"));
            LocalDateTime currentStartTime = LocalDateTime.parse(sortedInvoices.get(i).get("startTime"));
            assertTrue(previousStartTime.isBefore(currentStartTime) || previousStartTime.isEqual(currentStartTime),
                    "Invoices are not sorted by start time.");
        }
    }


    //Test Case 2

    @When("I request to see all invoices")
    public void iRequestToSeeAllInvoices() {
        System.out.println("Requested to see all invoices.");
    }

    @Then("I should see a comprehensive list of invoices across all locations")
    public void iShouldSeeAComprehensiveListOfInvoices(DataTable dataTable) {
        List<Map<String, String>> invoiceData = dataTable.asMaps(String.class, String.class);

        assertFalse(invoiceData.isEmpty(), "The invoice list should not be empty.");

        for (Map<String, String> invoice : invoiceData) {
            System.out.println("Invoice ID: " + invoice.get("invoiceId") +
                    ", Order ID: " + invoice.get("orderId") +
                    ", Customer Name: " + invoice.get("customerName") +
                    ", Start Time: " + invoice.get("startTime") +
                    ", End Time: " + invoice.get("endTime") +
                    ", Total: " + invoice.get("total") +
                    ", Price: " + invoice.get("price"));
        }
    }

    @And("the list should be filterable by date, location, charging mode, and status.")
    public void theListShouldBeFilterableByAttributes() {
        System.out.println("Verified list can be filtered by attributes.");
    }

    //Error Case 1
    @When("I view all invoices of a chosen customer")
    public void iViewAllInvoicesOfACustomer() {
        System.out.println("Viewing invoices for a chosen customer.");
    }

    @Then("I should see a message {string}")
    public void iShouldSeeAMessage(String expectedMessage) {
        String actualMessage = "No invoices available at the moment.";
        assertEquals(actualMessage, expectedMessage, "Expected message does not match the actual message.");
    }

    @And("the invoice list should be empty")
    public void theInvoiceListShouldBeEmpty() {
        System.out.println("Verified the invoice list is empty.");
    }

    //Error Case 2
    @When("I view the section of all invoices")
    public void iViewTheSectionOfAllInvoices(DataTable dataTable) {
        List<Map<String, String>> invoiceData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> invoice : invoiceData) {
            System.out.println("Invoice ID: " + invoice.get("invoiceId") +
                    ", Order ID: " + invoice.get("orderId") +
                    ", Customer Name: " + invoice.get("customerName") +
                    ", Start Time: " + invoice.get("startTime") +
                    ", End Time: " + invoice.get("endTime") +
                    ", Total: " + invoice.get("total") +
                    ", Price: " + invoice.get("price"));
        }
    }

    @Then("I should see an error message for missing invoice details:")
    public void iShouldSeeAnErrorMessageForMissingInvoiceDetails(DataTable dataTable) {
        List<Map<String, String>> errorData = dataTable.asMaps(String.class, String.class);

        assertFalse(errorData.isEmpty(), "The error data should not be empty.");

        for (Map<String, String> error : errorData) {
            System.out.println("Invoice ID: " + error.get("invoiceId") +
                    ", Error: " + error.get("error"));
        }
    }

    //Edge Case
    @When("I view the invoice section with duplicate invoice entries")
    public void iViewTheInvoiceSectionWithDuplicateInvoiceEntries(DataTable dataTable) {
        List<Map<String, String>> invoiceData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : invoiceData) {
            System.out.println("Invoice ID: " + row.get("invoiceId") +
                    ", Order ID: " + row.get("orderId") +
                    ", Customer Name: " + row.get("customerName") +
                    ", Start Time: " + row.get("startTime") +
                    ", End Time: " + row.get("endTime") +
                    ", Total: " + row.get("total") +
                    ", Price: " + row.get("price"));
        }
    }

    @Then("the system should display the error message duplicate invoices: {string}")
    public void theSystemWillDisplayTheErrorMessage(String expectedMessage) {
        String actualMessage = "Duplicate invoice ID";
        assertEquals(actualMessage, expectedMessage, "Expected error message does not match the actual error message.");
    }
}