Feature: Manage Charging Station
  As an Owner
  I want to manage charging stations
  So that I can ensure optimal operation, availability, and customer satisfaction.

  Scenario: Add Charging Station
  Given I want to add a charging station
  When I add a new charging station with details
    | id  | location       | pricePerMinute | pricePerKWh | status          | chargingType |
    | 201 | Main Street    | 0.3            | 0.6         | IN_BETRIEB_FREI | DC           |
    | 202 | Second Avenue  | 0.2            | 0.5         | IN_BETRIEB_BESETZT | AC        |
  Then I should be able to input the Charging Station details
  And the new charging station should be listed

  Scenario: Remove a Charging Station
  Given I want to remove a charging station
  When I remove a charging station with id
    | id  |
    | 201 |
  Then charging Station should be removed from the listing