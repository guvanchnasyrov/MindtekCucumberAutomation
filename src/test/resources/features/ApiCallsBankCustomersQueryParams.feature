@api @HR-0611 @regression
Feature: Validating query parameters in get customers api call

  Background: Repeats
    Given User creates customers with post api call using data
      | Name        | Address             | isActive |
      | John Smith  | 123 Washington ave. | true     |
      | Steve Jobs  | 123 Apple ave.      | true     |
      | John Doe    | 123 Example st.     | true     |
      | Patel Harsh | 123 Example st.     | true     |
      | Alex Clark  | 123 Example st.     | true     |
      | Brad Pitt   | 123 Example st.     | true     |

  Scenario Outline: Validating order customers get api call
    When User sends get customers api call with "<order>" order
    Then User validates that customers are in "<order>" order
#    And User cleans data with delete api call
    Examples:
      | order |
      | asc   |
      | desc  |

  @HR-0613
  Scenario Outline: Validating limit customers query parameter in get api call
    When User sends get customers api call with "<limit>" limit
    Then User validates that get customers response has "<limit>" customers
    Examples:
      | limit |
      | 3     |
      | 10    |
