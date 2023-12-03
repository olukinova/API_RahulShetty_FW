Feature: Validating Place API

  @AddPlace
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>", "<language>" and "<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the API call got success with the status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify that place_id created maps to "<name>" using "getPlaceAPI"

    Examples:
    | name | language | address           |
    |AHouse| English  | World cross center|
    |BHouse| Spanish  | Sea cross center  |
    |CHouse| Turkish  | Mountain cross center  |

  @DeletePlace
    Scenario: Verify if Delete Place functionality works
      Given DeletePlaceAPI Payload
      When user calls "DeletePlaceAPI" with "POST" http request
      Then the API call got success with the status code 200
      And "status" in response body is "OK"
