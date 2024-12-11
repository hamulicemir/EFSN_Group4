Feature: Manage Account Data
  As a Customer
  I want to manage my Account
  in order to update my personal information.

  Scenario: Create Account
    Given I want to create an account
    When I provide all the required information
    Then the system should validate the provided data
    And the account should be successfully created

  Scenario: Top Up Account
    Given I have an account
    And I want to add funds to my account
    When I click on top-up
    And I provide the payment details and top-up amount
    Then the system should validate the payment details
    And process the payment
    And the funds should be added to my account balance

