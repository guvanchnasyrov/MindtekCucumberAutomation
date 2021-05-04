@HR-5
Feature: Validating Search functionality

  Background:
    Given User navigates to Etsy application
    When User searches for "carpet"

  @smoke @regression
  Scenario: Validating search result is matching with searched item
    Then User validates search results contain
      | carpet  | rug         |
      | oal rug | turkish rug |

  @regression
  Scenario: Validating price range functionality for searched item
    And User selects price range more than 1000
    Then User validates price range is more than 1000

  @regression
  Scenario Outline: Validating all price range functionality for searched item
    And User selects price range between <Low> and <High>
    Then User validates price range between <Low> and <High>
    Examples:
      | Low | High |
      | 0   | 50   |
      | 50  | 500  |
      | 500 | 1000 |
      | 123 | 500  |