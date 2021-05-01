@HR-8
Feature: Validating functionalities in View All Orders part

  @regression
  Scenario Outline: Validating delete selected order functionality in View All Orders part
    Given User navigates to application
    When User logs in with username "Tester" and password "test"
    And User clicks on View all orders module
    And User deletes selected products "<Product>"
    Then User validates deleted orders
    Examples:
      | Product     |
      | FamilyAlbum |

  @smoke @regression
  Scenario: Validating edit order info functionality in View All Orders part
    Given User navigates to application
    When User logs in with username "Tester" and password "test"
    And User clicks on View all orders module
    And User edits selected order data
      | Name       | Product     | Quantity | Date       | Street          | City                | State  | Zip   | Card | Card Number  | Exp   |
      | Mark Smith | FamilyAlbum | 1        | 03/07/2010 | 9, Maple Valley | Whitestone, British | Canada | 76743 | Visa | 770077007700 | 01/09 |
    Then User validates edited selected order data
