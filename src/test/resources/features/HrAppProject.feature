@Project
  Feature: Validate data are matching in UI.

    Background:
      Given user navigates to HRPage

    @S1
    Scenario: Find employees from database who works in Europe,
              then validate their first name and last name are matching in UI.
      When user gets data from database who works in Europe
      And user gets first name and last name
      Then validates data from database ara matching in UI


    @S2
    Scenario: Find number of active employees by department name in database,
              then validate their number matching in UI.
      When user gets number of employees from database
      And user gets number of employees from UI
      Then validates data from database ara matching in UI
