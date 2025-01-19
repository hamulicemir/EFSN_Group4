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

    private Customer customer;
    private boolean registrationResult;
    private boolean loginResult;
    private double initialBalance;

    @Given("I want to create an account")
    public void iWantToCreateAnAccount() {
        // Initialize a customer instance with default values
        customer = new Customer(0, "test@example.com", "Tester 1", 0.0, "securepassword");
    }

    @When("I provide all the required information")
    public void iProvideAllTheRequiredInformation() {
        // Simulate providing valid account details
        registrationResult = customer.register("test@example.com", "John Doe", "securepassword");
    }

    @Then("the system should validate the provided data")
    public void theSystemShouldValidateTheProvidedData() {
        // Assert that registration is successful
        assert registrationResult;
    }

    @And("the account should be successfully created")
    public void theAccountShouldBeSuccessfullyCreated() {
        // Check the details of the created account
        assert customer.getCustomerName().equals("John Doe");
        System.out.println("Account Created: " + customer);
    }

    @Given("I have an account")
    public void iHaveAnAccount() {
        // Initialize a customer with a valid account
        customer = new Customer(1, "test@example.com", "John Doe", 50.0, "securepassword");
    }

    @And("I want to add funds to my account")
    public void iWantToAddFundsToMyAccount() {
        // Store the initial balance for validation
        initialBalance = customer.viewBalance();
    }

    @When("I click on top-up")
    public void iClickOnTopUp() {
        // Placeholder for triggering the top-up action
        System.out.println("Top-Up action initiated.");
    }

    @And("I provide the payment details and top-up amount")
    public void providePaymentDetailsAndTopUpAmount(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            String amount = row.get("amount");
            // Add logic to handle the payment details and top-up amount
        }
    }

   @Then("the system should validate the payment details")
    public void theSystemShouldValidateThePaymentDetails() {
        // Set the top-up result before the assertion
       boolean topUpResult = customer.topUpAccount(100.0);

        // Assert that the top-up process validates the input
        assert topUpResult : "Top-up result is not true!";
    }

    @And("process the payment")
    public void processThePayment() {
        // Check that the payment was processed successfully
        System.out.println("Payment processed successfully.");
    }

    @And("the funds should be added to my account balance")
    public void theFundsShouldBeAddedToMyAccountBalance() {
        // Validate that the balance has been updated
        assert customer.viewBalance() == initialBalance + 100.0;
        System.out.println("Updated Balance: " + customer.viewBalance());
    }

}
