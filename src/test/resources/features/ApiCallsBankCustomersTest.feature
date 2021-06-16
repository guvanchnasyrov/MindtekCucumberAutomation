@api @smoke @regression @HR-0613.2
Feature: Validating customers api calls

  Scenario: Validating create customer api call
    Given User creates customers with post api call using data
      | Name       | Address             | isActive |
      | John Smith | 123 Washington ave. | true     |
    Then User validates that customer is created with data
      | Name       | Address             | isActive |
      | John Smith | 123 Washington ave. | true     |
    When User deletes created customer
    Then User validates that customer is deleted

  Scenario: Validating creating account for a customer
    Given User creates customers with post api call using data
      | Name                 | Address             | isActive |
      | Srinivas Govindrajan | 123 Washington ave. | true     |
    When User creates account for a customer
      | accountType | Checking |
      | balance     | 123.5    |
    Then User validates that customer linked to created account
      | accountType | Checking |
      | balance     | 123.5    |
    When User deletes created account
    Then User validates that account is deleted
    When User deletes created customer
    Then User validates that customer is deleted

