package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.software.Objekte.Customer;
import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class StepsManageAccountData {

    private String errorMessage;
    private Customer customer;

    //Scenario 1
    private Customer createdCustomer;

    @When("I provide my email, my full name and a valid password")
    public void provideEmailFullNameAndPassword(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String email = row.get("email");
            String name = row.get("name");
            String password = row.get("password");

            // Simuliere das Erstellen eines Kundenkontos
            createdCustomer = new Customer(1, email, name, 0.0, password); // customerID=1 als Beispiel
            System.out.println("Account created: " + createdCustomer);
        }
    }

    @Then("my account should be created with the following data")
    public void accountShouldBeCreatedWithExpectedData(DataTable dataTable) {
        List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> expectedData : expectedRows) {
            assertNotNull(createdCustomer, "No account was created.");

            assertEquals(expectedData.get("email"), createdCustomer.getCustomerEmail(), "Email mismatch.");
            assertEquals(expectedData.get("name"), createdCustomer.getCustomerName(), "Name mismatch.");
            assertEquals(expectedData.get("password"), createdCustomer.getCustomerPassword(), "Password mismatch.");

            System.out.println("Account verification passed for: " + expectedData);
        }
    }

    //Scenario 2
    private Customer loggedInCustomer;

    @Given("I am logged in as a customer")
    public void iAmLoggedInAsCustomer() {
        loggedInCustomer = new Customer(1, "customer@example.com", "John Doe", 0.0, "securePassword");
        System.out.println("Customer logged in: " + loggedInCustomer);
    }

    @When("I enter an amount of money I want to add to my account balance")
    public void enterTopUpAmount() {
        System.out.println("Preparing to top up the account...");
    }

    @And("I provide the payment details and top-up amount")
    public void providePaymentDetailsAndTheTopUpAmount(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            double amount = Double.parseDouble(row.get("amount"));
            double currentBalance = loggedInCustomer.viewBalance();

            boolean success = loggedInCustomer.topUpAccount(amount);
            if (success) {
                System.out.println("Successfully added " + amount + " to account. Previous balance: " + currentBalance);
            } else {
                System.out.println("Failed to top up the account.");
            }
        }
    }

    @Then("the amount should be added to the account balance")
    public void verifyUpdatedAccountBalance(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            double expectedBalance = Double.parseDouble(row.get("balance"));
            double actualBalance = loggedInCustomer.viewBalance();

            assertEquals(expectedBalance, actualBalance, "Account balance mismatch.");

            System.out.println("Balance verification passed. Current balance: " + actualBalance);
        }
    }

    //Error Case 1


    @When("I attempt to create the account with an invalid email format")
    public void attemptToCreateAccountWithInvalidEmailFormat(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String email = row.get("email");
            String name = row.get("name");
            String password = row.get("password");

            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                errorMessage = "This email is invalid. Please enter a valid email address!";
                System.out.println(errorMessage);
            } else {
                errorMessage = null; // Keine Fehlermeldung bei gültigem Format
            }
        }
    }

    @Then("the system should display the error message: \"This email is invalid. Please enter a valid email address!\"")
    public void verifyInvalidEmailErrorMessage() {
        assertNotNull(errorMessage, "Expected an error message for invalid email format.");
        assertEquals("This email is invalid. Please enter a valid email address!", errorMessage, "Error message mismatch.");
        System.out.println("Error message verification passed: " + errorMessage);
    }

    //Error Case 2

    @Given("I attempt to top up my account with a negative amount")
    public void attemptToTopUpWithNegativeAmount() {
        loggedInCustomer = new Customer(1, "customer@example.com", "John Doe", 0.0, "securePassword");
        System.out.println("Customer logged in: " + loggedInCustomer);
    }


    @When("I enter a negative value for the top-up amount")
    public void enterNegativeTopUpAmount(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            double amount = Double.parseDouble(row.get("amount"));

            if (amount <= 0) {
                errorMessage = "The topup amount has to be positive!";
                System.out.println(errorMessage);
            } else {
                boolean success = loggedInCustomer.topUpAccount(amount);
                if (!success) {
                    errorMessage = "Failed to top up the account.";
                }
            }
        }
    }

    @Then("the system should display the error message: \"The topup amount has to be positive!\"")
    public void verifyTopUpErrorMessage() {
        assertNotNull(errorMessage, "Expected an error message for negative top-up amount.");
        assertEquals("The topup amount has to be positive!", errorMessage, "Error message mismatch.");
        System.out.println("Error message verification passed: " + errorMessage);
    }

    //Edge Case
    private List<String> registeredEmails = new ArrayList<>();

    @Given("the following email is already registered")
    public void emailAlreadyRegistered(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            registeredEmails.add(row.get("email"));
        }
        System.out.println("Registered emails: " + registeredEmails);
    }

    @When("I attempt to create the account with the following details")
    public void attemptToCreateAccountWithDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String email = row.get("email");
            String name = row.get("name");
            String password = row.get("password");

            if (registeredEmails.contains(email)) {
                errorMessage = "This email is not available. Please enter a valid email address!";
                System.out.println(errorMessage);
            } else {
                errorMessage = null;
                Customer newCustomer = new Customer(1, email, name, 0.0, password);
                System.out.println("Account successfully created: " + newCustomer);
            }
        }
    }

    @Then("the system should display the error message: \"This email is not available. Please enter a valid email address!\"")
    public void verifyEmailAlreadyRegisteredErrorMessage() {
        assertNotNull(errorMessage, "Expected an error message for already registered email.");
        assertEquals("This email is not available. Please enter a valid email address!", errorMessage, "Error message mismatch.");
        System.out.println("Error message verification passed: " + errorMessage);
    }
}