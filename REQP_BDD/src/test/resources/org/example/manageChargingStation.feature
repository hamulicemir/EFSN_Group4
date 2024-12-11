Feature: Manage Charging Station
  As an Owner
  I want to manage charging stations
  So that I can ensure optimal operation, availability, and customer satisfaction.

Scenario: Add Charging Station
  Given I want to add a charging station
  When I add a new charging station
  Then I should be able to input the Charging Station details
  And the new charging station should be listed.

Scenario: Remove a Charging Station
  Given I want to remove a charging station
  When I remove a charging station
  Then charging Station should be removed from the listing.

