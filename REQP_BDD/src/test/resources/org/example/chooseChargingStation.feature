Feature: Choosing a charging station
  As a Customer
  I want to choose a charging station
  So that I can use them for my devices

Scenario: View Charging Station Information
    Given I want to view information about a charging station with the station id 101
    When I select a charging station for information
      | station_id |
      | 101        |
      | 102        |
    Then the system should provide general information

  Scenario: Show Charging Station Status
    Given I want to see the status of the charging station
    When I select a charging station for status
      | station_id |
      | 101        |
      | 102        |
    Then the system should show me if the charging station is occupied

    ##############################################
       #            ERROR CASES               #
    ##############################################

  Scenario: Show Charging Station Status
    Given I want to see the status of the charging station
    When I select a charging station for status
      | station_id |
      | 101        |
      | 999        |  # Non-existent station ID
    Then the system should show me if the charging station is occupied

  Scenario: View Charging Station Information with Invalid ID
    Given I want to view information about a charging station
    When I select a charging station for information
      | station_id |
      | abc        |  # Invalid station ID format
    Then the system should provide an error message