@HR20
Feature: Validate create employee functionality

  @regression @smoke
  Scenario: Validate created employee persisted in database
    Given user navigates to login page
    When user logs in to HrApp
    And creates new employee
    Then user validates new employee is created in database
    #And user cleans created data