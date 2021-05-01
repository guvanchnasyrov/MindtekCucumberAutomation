@HR-6
Feature: Validating Order creation functionality

  Background: These steps will run before each scenario
    Given User navigates to application
    When User logs in with username "Tester" and password "test"
    And User clicks on Order module

  @regression
  Scenario Outline: Validating total calculation functionality
    And User provides product "<Product>" and quantity <Quantity>
    Then User validates total is calculated properly for quantity <Quantity>
    Examples:
    | Product | Quantity |
    | FamilyAlbum | 1    |
    | MyMoney | 5        |
    | ScreenSaver | 20   |

  @regression
  Scenario: Validating order creation functionality
    And User creates and order with data
      | Product     | Quantity | Customer name | Street       | City    | State | Zip   | Card | Card Nr   | Exp Date |
      | FamilyAlbum | 1        | John Doe      | 123 Deer rd. | Chicago | IL    | 12345 | Visa | 124356356 | 12/21    |
      | MyMoney     | 5        | Patel Harsh   | 321 Dee rd.  | New York | NY   | 65432 | Visa | 98765432  | 12/22    |
    Then User validates success message "New order has been successfully added."
    And User validates that created orders are in the list of all orders