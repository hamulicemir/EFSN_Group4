Feature: Manage Charging Station
  As an Owner
  I want to manage charging stations
  So that I can ensure optimal operation, availability, and customer satisfaction.


  Scenario: Add Charging Station
  When I add a new charging station with details
    | id  | location       | pricePerMinute | pricePerKWh | status             | chargingType |
    | 201 | Main Street    | 0.3            | 0.6         | IN_BETRIEB_FREI    | DC           |
  Then the new charging station should be listed
    | id  | location       | pricePerMinute | pricePerKWh | status             | chargingType |
    | 201 | Main Street    | 0.3            | 0.6         | IN_BETRIEB_FREI    | DC           |

  Scenario: Remove a Charging Station
  When I remove a charging station with id
    | id  |
    | 201 |
  Then charging Station should be removed from the listing

    ##############################################
     #            ERROR CASES               #
    ##############################################

  Scenario: Attempt to Remove Non-Existent Charging Station
    When I remove a charging station with the following id
      | id  |
      | 999 |
    Then I should see an error message "Charging Station with ID 999 does not exist"
    And no changes should be made to the listing

  Scenario: Attempt to Add Charging Station with Invalid Details
    When I add a new charging station with a negative price per minute value
      | id  | location       | pricePerMinute | pricePerKWh | status          | chargingType |
      | 202 | Main Street    | -0.3           | 0.6         | IN_BETRIEB_FREI |      AC      |
    Then the system should display the error message: "Invalid details provided for the Charging Station"
    And the charging station should not be added to the listing

    ##############################################
       #            EDGE CASES               #
    ##############################################

  Scenario: Attempt to Remove Charging Station with Active Sessions
    Given a charging station with ID 202 is currently in use
    When I attempt to remove the charging station with the following id
      | id  |      status        |
      | 202 | IN_BETRIEB_BESETZT |
    Then the system should display the error message: "Charging Station with active sessions cannot be removed"
    And the charging station should remain in the listing