@api @smoke @regression @HR-21
Feature:Validation Authorization api calls
  @api
  Scenario: Validating success response in api call sending with token
    Given User sends get customers api call with access token
    Then User validates 200 status code
  @api
  Scenario: Validating forbidden response in api call when sending without token
    Given User sends get customers api call without access token
    Then User validates 403 status code