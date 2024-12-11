Feature: Manage Invoices
  As an Owner
  I want to manage invoices

  Scenario: Show Invoice Status
    Given I am logged in an owner account
    When I view the invoice section
    Then I should see a list of invoices sorted by the start time of the charging process
    And I should see a summary of account top-ups and outstanding balance.

  Scenario: Show All Invoices
    Given I am logged in an owner account
    When I request to see all invoices
    Then I should see a comprehensive list of invoices across all locations
    And the list should be filterable by date, location, charging mode, and status.