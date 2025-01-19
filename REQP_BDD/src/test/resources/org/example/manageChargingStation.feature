Feature: Manage Charging Station
  As an Owner
  I want to manage charging stations
  So that I can ensure optimal operation, availability, and customer satisfaction.

  Scenario: Add Charging Station
  Given I want to add a charging station
  When I add a new charging station with details
    | id  | location       | pricePerMinute | pricePerKWh | status             | chargingType |
    | 201 | Main Street    | 0.3            | 0.6         | IN_BETRIEB_FREI    | DC           |
    | 202 | Second Avenue  | 0.2            | 0.5         | IN_BETRIEB_BESETZT | AC           |
  Then the new charging station should be listed

  Scenario: Remove a Charging Station
  Given I want to remove a charging station
  When I remove a charging station with id
    | id  |
    | 201 |
  Then charging Station should be removed from the listing

      ##############################################
       #            ERROR CASES               #
    ##############################################

  Scenario: Attempt to Remove Non-Existent Charging Station
    Given I want to remove a charging station with an id that does not exist
    When I remove a charging station with the following id
      | id  |
      | 999 |
    Then I should see an error message "Charging Station with ID 999 does not exist"
    And no changes should be made to the listing

  Scenario: Attempt to Add Charging Station with Invalid Details
    Given I want to add a charging station with a negative price per minute value
    When I add a new charging station with invalid details
      | id  | location       | pricePerMinute | pricePerKWh | status          | chargingType |
      | 202 | Main Street    | -0.3           | 0.6         | IN_BETRIEB_FREI |              |
    Then I should see an error message "Invalid details provided for the Charging Station"
    And the charging station should not be added to the listing