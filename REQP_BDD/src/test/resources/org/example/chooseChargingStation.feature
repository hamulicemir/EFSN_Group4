Feature: Choosing a charging station
  As a Customer
  I want to choose a charging station
  So that I can use them for my devices


  Scenario: View Charging Station Information
    Given the following charging stations exist:
      | station_id | location    | pricePerMinute | pricePerKWh |
      | 101        | Main Street | 0.3            | 0.6         |
    When I select a charging station for information
      | station_id |
      | 101        |
    Then the system should provide the following information:
      | id  | location    | pricePerMinute | pricePerKWh |
      | 101 | Main Street | 0.3            | 0.6         |

  Scenario: Show Charging Station Status
    Given the following charging stations with points exist:
      | station_id | location       | pricePerMinute | pricePerKWh | point_id | status              |
      | 102        | Main Street    | 0.3            | 0.6         | 1        | IN_BETRIEB_BESETZT  |
      | 102        | Main Street    | 0.3            | 0.6         | 2        | IN_BETRIEB_FREI     |
    When I select a charging station for status
      | station_id |
      | 102        |
    Then the system should show me if the charging station is occupied

    ##############################################
       #            ERROR CASES               #
    ##############################################

  Scenario: Non-existent Charging Station Status
    When I search an id for a non-existing charging station to check the status
      | station_id |
      | 999        |
    Then the system should display the error message: "This station does not exist."

  Scenario: Invalid Charging Station ID Format for Information
    When I search for a charging station with an invalid station id in order to retrieve the station details
      | station_id |
      | abc        |
    Then the system should display the error message: "This station id is not valid."

    ##############################################
       #            EDGE CASES               #
    ##############################################

  Scenario: Charging Station with No Available Charging Points
    Given a charging station with no available charging points
      | station_id | location       | pricePerMinute | pricePerKWh |
      | 103        | Elm Street     | 0.5            | 0.8         |
    When I select the charging station for status information
      | station_id |
      | 103        |
    Then the system should display the message: "No charging points available at this station."
