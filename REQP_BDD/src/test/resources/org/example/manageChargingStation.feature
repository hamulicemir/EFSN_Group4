Feature: Manage Charging Station
  As an Owner
  I want to manage charging stations
  So that I can ensure optimal operation, availability, and customer satisfaction.

  Scenario: Add Charging Station
  When I add a new charging station with details
    | id  | location       | pricePerMinute | pricePerKWh |
    | 201 | Main Street    | 0.3            | 0.6         |
  Then the new charging station should be listed
    | id  | location       | pricePerMinute | pricePerKWh |
    | 201 | Main Street    | 0.3            | 0.6         |

  Scenario: Remove a Charging Station
    Given the following charging stations are existing:
      | id  | location    | pricePerMinute | pricePerKWh |
      | 201 | Main Street | 0.3            | 0.6         |
      | 202 | Elm Street  | 0.4            | 0.7         |
    When I remove a charging station with id
      | id  |
      | 201 |
    Then the charging station should no longer appear in the listing:
      | id  |
      | 201 |
    And the listing should now contain:
      | id  | location       | pricePerMinute | pricePerKWh |
      | 202 | Elm Street     | 0.4            | 0.7         |

    ##############################################
     #            ERROR CASES               #
    ##############################################

  Scenario: Attempt to Remove Non-Existent Charging Station
    Given the following charging stations with this data exist:
      | id  | location       | pricePerMinute | pricePerKWh |
      | 201 | Main Street    | 0.3            | 0.6         |
      | 202 | Elm Street     | 0.4            | 0.7         |
    When I attempt to remove a charging station with the following id:
      | id  |
      | 999 |
    Then the system should display the error message for non-exisiting Station: "Station 999 does not exist"
    And no changes should be made to the listing
    And the listing should still contain:
      | id  | location       | pricePerMinute | pricePerKWh |
      | 201 | Main Street    | 0.3            | 0.6         |
      | 202 | Elm Street     | 0.4            | 0.7         |

  Scenario: Attempt to Add Charging Station with Invalid Details
    When I add a new charging station with a negative price per minute value
      | id  | location       | pricePerMinute | pricePerKWh |
      | 202 | Main Street    | -0.3           | 0.6         |
    Then the system should display the error message for invalid details: "Invalid details provided for the Charging Station"
    And the charging station should not be added to the listing

    ##############################################
       #            EDGE CASES               #
    ##############################################

  Scenario: Attempt to Remove Charging Station with Active Sessions
    Given a charging station with ID 202 is currently in use
      | id  | location       | pricePerMinute | pricePerKWh |
      | 202 | Elm Street     | 0.4            | 0.7         |
    When I attempt to remove the charging station with the following id
      | id  |      status        |
      | 202 | IN_BETRIEB_BESETZT |
    Then the system should display the error message for non-removal: "Charging Station with active sessions cannot be removed"
    And the charging station should remain in the listing
    And the listing should still contain the following data:
      | id  | location       | pricePerMinute | pricePerKWh |
      | 202 | Elm Street     | 0.4            | 0.7         |
