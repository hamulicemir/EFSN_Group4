Feature: Manage Account Data
  As a Customer
  I want to manage my Account
  in order to update my personal information.

  Scenario: Create Account
    When I provide my email, my full name and a valid password
      | email             | name            | password    |
      | max1@example.com  | Max Mustermann  | Passwort123 |
    Then my account should be created with the following data
      | email             | name            | password    |
      | max1@example.com  | Max Mustermann  | Passwort123 |

  Scenario: Top Up Account
    Given I am logged in as a customer
    When I enter an amount of money I want to add to my account balance
    And I provide the payment details and top-up amount
      | amount | balance
      | 50.0   | 0.0
    Then the amount should be added to the account balance
      | balance |
      | 50.0    |

    ##############################################
       #            ERROR CASES               #
    ##############################################

  Scenario: Create Account with an invalid E-Mail
    When I attempt to create the account with an invalid email format
      | email             | name            | password    |
      | max1example.com   | Max Mustermann  | Passwort123 |
    Then the system should display the error message: "This email is invalid. Please enter a valid email adress!"

  Scenario: Invalid Top Up Account
    Given I attempt to top up my account with a negative amount
    When I enter a negative value for the top-up amount
      | amount |
      | -50.0  |
    Then the system should display the error message: "The topup amount has to be positive!"


  ##############################################
     #            EDGE CASES               #
  ##############################################

  Scenario: Create Account with an already registered email
    Given the following email is already registered
      | email            |
      | max1@example.com |
    When I attempt to create the account with the following details
      | email            | name            | password    |
      | max1@example.com | Max Mustermann  | Passwort123 |
    Then the system should display the error message: "This email is not available. Please enter a valid email adress!"