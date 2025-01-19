package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.software.Objekte.Customer;
import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("org.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")

public class StepsManageAccountData {

    private String errorMessage;
    private Customer customer;

    @Given("I want to create an account with the following details")
    public void iWantToCreateAnAccountWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> accountDetails = data.get(0);
        String email = accountDetails.get("email");
        String name = accountDetails.get("name");
        String password = accountDetails.get("password");
        customer = new Customer(1, email, name, 0.0, password); // Create a new customer with initial balance 0
    }

    @When("I provide all the required information")
    public void iProvideAllTheRequiredInformation() {
        boolean isRegistered = customer.register(customer.getCustomerEmail(), customer.getCustomerName(), customer.getCustomerPassword());
        assertTrue(isRegistered, "Registration failed.");
    }

    @Then("the system should validate the provided data")
    public void theSystemShouldValidateTheProvidedData() {

    }

    @Then("the account should be successfully created")
    public void theAccountShouldBeSuccessfullyCreated() {
        assertEquals("Account creation failed", "Max Mustermann", customer.getCustomerName());
    }

    @Given("I have an account")
    public void iHaveAnAccount() {
        customer = new Customer(1, "max1@example.com", "Max Mustermann", 0.0, "Passwort123");
    }

    @And("I want to add funds to my account")
    public void iWantToAddFundsToMyAccount() {

    }

    @When("I click on top-up")
    public void iClickOnTopUp() {
        // Simulate clicking top-up
    }

    @And("I provide the payment details and top-up amount")
    public void iProvideThePaymentDetailsAndTopUpAmount(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            double amount = Double.parseDouble(row.get("amount"));
            boolean isTopUpSuccessful = customer.topUpAccount(amount);
            if (amount <= 0) {
                assertFalse(isTopUpSuccessful, "Top-up should fail for invalid amount");
            } else {
                assertTrue(isTopUpSuccessful, "Top-up should succeed for valid amount");
            }
        }
    }

    @Then("the system should validate the payment details")
    public void theSystemShouldValidateThePaymentDetails() {
        assertTrue(customer.viewBalance() >= 0, "Balance should be non-negative");
    }

    @Then("the funds should be added to my account balance")
    public void theFundsShouldBeAddedToMyAccountBalance() {
        double balance = customer.viewBalance();
        assertTrue(balance > 0, "Funds not added to account balance");
    }

    @Given("I want to create an Account with an invalid E-Mail format")
    public void iWantToCreateAnAccountWithAnInvalidEMailFormat(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> accountDetails = data.get(0);
        customer = new Customer(1, accountDetails.get("email"), accountDetails.get("name"), 0.0, accountDetails.get("password"));
    }

    @When("I attempt to create the account with an invalid email format")
    public void iAttemptToCreateTheAccountWithAnInvalidEmailFormat() {
        if (!customer.getCustomerEmail().contains("@")) {
            errorMessage = "Invalid email format";
        }
    }

    @Then("the system should display an error message indicating the email is invalid")
    public void theSystemShouldDisplayAnErrorMessageIndicatingTheEmailIsInvalid() {
        assertEquals("Invalid email format", errorMessage);
    }

    @Given("I attempt to top up my account with a negative amount")
    public void iAttemptToTopUpMyAccountWithANegativeAmount() {
        customer = new Customer(1, "max1@example.com", "Max Mustermann", 100.0, "Passwort123");
    }

    @When("I click on top-up")
    public void iClickOnTopUpNegativeAmount() {
        // Simulate clicking top-up
    }

    @And("I provide the payment details and top-up amount")
    public void iProvideThePaymentDetailsAndTopUpAmountNegative(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            double amount = Double.parseDouble(row.get("amount"));
            boolean isTopUpSuccessful = customer.topUpAccount(amount);
            if (amount <= 0) {
                assertFalse(isTopUpSuccessful, "Top-up should fail for invalid amount");
                errorMessage = "Amount must be positive";
            }
        }
    }

    @Then("the system should validate the payment details")
    public void theSystemShouldValidateThePaymentDetailsNegative() {
        assertTrue(customer.viewBalance() == 100.0, "Balance should remain the same");
    }

    @Then("reject the top-up request")
    public void rejectTheTopUpRequest() {
        System.out.println("Top-up request rejected");
    }

    @Then("display an error message indicating the amount must be positive")
    public void displayAnErrorMessageIndicatingTheAmountMustBePositive() {
        System.out.println("Amount must be positive");
    }
}
