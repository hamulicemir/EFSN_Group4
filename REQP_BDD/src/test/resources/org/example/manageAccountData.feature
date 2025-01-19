Feature: Manage Account Data
  As a Customer
  I want to manage my Account
  in order to update my personal information.

  Scenario: Create Account
    Given I want to create an account with the following details
      | email             | name            | password    |
      | max1@example.com  | Max Mustermann  | Passwort123 |
    When I provide all the required information
    Then the system should validate the provided data
    And the account should be successfully created

Scenario: Top Up Account
    Given I have an account
    And I want to add funds to my account
    When I click on top-up
    And I provide the payment details and top-up amount
      | amount |
      | 50.0   |
      | 100.0  |
    Then the system should validate the payment details
    And process the payment
    And the funds should be added to my account balance

    ##############################################
       #            ERROR CASES               #
    ##############################################

  Scenario: Create Account with an invalid E-Mail
    Given I want to create an Account with an invalid E-Mail format
      | email          | name           | password    |
      | max1example.com | Max Mustermann | Passwort123 |
    When I attempt to create the account with an invalid email format
    Then the system should display an error message indicating the email is invalid

  Scenario: Invalid Top Up Account
    Given I attempt to top up my account with a negative amount
    When I enter a negative value for the top-up amount
      | amount |
      | -50.0  |
    Then the system should not validate the payment details
    And reject the top-up request
    And display an error message indicating the amount must be positive