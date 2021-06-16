@HR-0614
Feature: Validating account balance update for API calls

  Scenario: Validating account balance update for API calls
    Given User creates customers with post api call using data
      | Name                 | Address             | isActive |
      | Srinivas Govindrajan | 123 Washington ave. | true     |
    When User creates account for a customer
      | accountType | Checking |
      | balance     | 5000     |
    Then User validates that customer linked to created account
      | accountType | Checking |
      | balance     | 5000     |
    When User updates account balance with put api call
      | accountType | Checking |
      | balance     | 30000    |
    Then User validates that account updated
      | accountType | Checking |
      | balance     | 30000    |
    When User deletes created account
    Then User validates that account is deleted
    When User deletes created customer
    Then User validates that customer is deleted