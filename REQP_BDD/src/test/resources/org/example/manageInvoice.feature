Feature: Manage Invoices
  As an Owner
  I want to manage invoices

  Scenario: Show Invoice Status
    Given I am logged in an owner account
      | email | password |
      | admin@example.com | admin123 |
    When I view the invoice section
      | invoiceId | orderId | customerName | startTime           | endTime             | total |
      | 1         | 101     | John Doe     | 2023-01-01T10:00:00 | 2023-01-01T11:00:00 | 50.0  |
      | 2         | 102     | Jane Smith   | 2023-01-02T12:00:00 | 2023-01-02T13:00:00 | 60.0  |
      | 3         | 103     | Bob Johnson  | 2023-01-03T14:00:00 | 2023-01-03T15:00:00 | 70.0  |
    Then I should see a list of invoices sorted by the start time of the charging process

    And I should see a summary of account top-ups and outstanding balance.
      | totalTopUps | totalOutstandingBalance |
      | 1000.0      | 250.0              |

  Scenario: Show All Invoices
    Given I am logged in an owner account
      | invoiceId | orderId | customerName | startTime           | endTime             | total |
      | 1         | 101     | John Doe     | 2023-01-01T10:00:00 | 2023-01-01T11:00:00 | 50.0  |
      | 2         | 102     | Jane Smith   | 2023-01-02T12:00:00 | 2023-01-02T13:00:00 | 60.0  |
      | 3         | 103     | Bob Johnson  | 2023-01-03T14:00:00 | 2023-01-03T15:00:00 | 70.0  |
    When I request to see all invoices
    Then I should see a comprehensive list of invoices across all locations
    And the list should be filterable by date, location, charging mode, and status.

    ##############################################
       #            ERROR CASES               #
    ##############################################

  Scenario: Show Invoice Status
    Given I am logged in an owner account
      | email | password |
      | admin@example.com | admin123 |
    When I view the invoice section
    Then I should see a list of invoices sorted by the start time of the charging process
    And I should see a summary of account top-ups and outstanding balance.
      | totalTopUps | totalOutstandingBalance |
      | 1000.0      | 250.0              |

  Scenario: Show All Invoices
    Given I am logged in an owner account
    When I request to see all invoices
    Then I should see a comprehensive list of invoices across all locations
    And the list should be filterable by date, location, charging mode, and status.
