Feature: Manage Invoices
  As an Owner
  I want to manage invoices

  Scenario: Show Invoice Status
    When I view the invoice section
    Then I should see a list of invoices sorted by the start time of the charging process
      | invoiceId | orderId | customerName | startTime           | endTime             | total |  price  |
      | 1         | 101     | John Doe     | 2023-01-01T10:00:00 | 2023-01-01T11:00:00 | 50.0  |   0.5   |
      | 2         | 102     | Jane Smith   | 2023-01-02T12:00:00 | 2023-01-02T13:00:00 | 60.0  |   0.6   |
      | 3         | 103     | Bob Johnson  | 2023-01-03T14:00:00 | 2023-01-03T15:00:00 | 70.0  |   0.7   |

  Scenario: Show All Invoices
    When I request to see all invoices
    Then I should see a comprehensive list of invoices across all locations
      | invoiceId | orderId | customerName | startTime           | endTime             | total |  price  |
      | 1         | 101     | John Doe     | 2023-01-01T10:00:00 | 2023-01-01T11:00:00 | 50.0  |   0.5   |
      | 2         | 102     | Jane Smith   | 2023-01-02T12:00:00 | 2023-01-02T13:00:00 | 60.0  |   0.4   |
      | 3         | 103     | Bob Johnson  | 2023-01-03T14:00:00 | 2023-01-03T15:00:00 | 70.0  |   0.7   |
    And the list should be filterable by date, location, charging mode, and status.

    ##############################################
       #            ERROR CASES               #
    ##############################################

  Scenario: No Invoices Available
    When I view all invoices of a chosen customer
    Then I should see a message "No invoices available at the moment."
    And the invoice list should be empty

  Scenario: Show Invoice Status - Error Case
    When I view the section of all invoices
      | invoiceId | orderId | customerName | startTime           | endTime             | total | price |
      | 1         | 101     | John Doe     | 2023-01-01T10:00:00 | 2023-01-01T11:00:00 | 50.0  |       |
      | 2         | 102     | Jane Smith   | 2023-01-02T12:00:00 |                     | 60.0  | 0.60  |
      | 3         | 103     | Bob Johnson  |                     | 2023-01-03T15:00:00 | 70.0  | 0.70  |
    Then I should see an error message for missing invoice details:
      | invoiceId | error                |
      | 1         | Missing price        |
      | 2         | Missing endTime      |
      | 3         | Missing startTime    |

    ##############################################
       #            EDGE CASES               #
    ##############################################

  Scenario: Duplicate Invoice IDs
    When I view the invoice section with duplicate invoice entries
      | invoiceId | orderId | customerName | startTime           | endTime             | total | price |
      | 1         | 101     | John Doe     | 2023-01-01T10:00:00 | 2023-01-01T11:00:00 | 50.0  | 0.5   |
      | 1         | 104     | Alice Brown  | 2023-01-04T14:00:00 | 2023-01-04T15:00:00 | 80.0  | 0.8   |
    Then the system should display the error message: "Duplicate invoice ID"