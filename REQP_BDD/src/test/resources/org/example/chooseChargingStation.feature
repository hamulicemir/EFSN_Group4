Feature:
  As a Customer
  I want to choose a charging station
  So that I can use them for my devices

  Scenario: View Charging Station Information
    Given I want to view information about a charging station
    When I select a charging station
    Then the system should provide general information

  Scenario: Show Charging Station Status
    Given I want to see the status of the charging station
    When I select a charging station
    Then the system should show me if the charging station is occupied