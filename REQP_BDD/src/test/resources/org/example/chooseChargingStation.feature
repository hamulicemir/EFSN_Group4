Feature: Choosing a charging station
  As a Customer
  I want to choose a charging station
  So that I can use them for my devices

Scenario: View Charging Station Information
    Given I want to view all charging stations.
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

  Scenario: Non-existent Charging Station Status
    Given I want to see the status of a non-existing charging station
    When I select a non-existing charging station for status
      | station_id |
      | 101        |
      | 999        |
    Then the system should display an error message indicating the station does not exist

  Scenario: Invalid Charging Station ID Format for Information
    Given I want to view information about a charging station with an invalid station id
    When I select a charging station with an invalid station id for information
      | station_id |
      | abc        |
    Then the system should display an error message indicating the station ID is invalid

    ##############################################
       #            EDGE CASES               #
    ##############################################

  Scenario: Multiple Charging Stations Selected Simultaneously
    Given I want to view the status of multiple charging stations at once
    When I select multiple charging stations simultaneously for status
      | station_id |
      | 101        |
      | 102        |
      | 103        |
      | 104        |
    Then the system should display the status of all selected charging stations
      | station_id | status    |
      | 101        | Occupied  |
      | 102        | Available |
      | 103        | Available |
      | 104        | Occupied  |
    And if the system cannot process simultaneous requests, it should display an error.