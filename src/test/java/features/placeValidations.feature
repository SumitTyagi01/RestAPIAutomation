Feature: Validating Place API's

  @smoke
  Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When User calls "AddPlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "GETPlaceAPI"

    Examples:
      |name  | language | address           |
      |xyz   | english  | world cross centre|
      |aaa   | french   | paris             |
      |xyz   | hindi    | Noida             |

  @regression
  Scenario: Verify if Delete Place functionality is working
    Given Delete place payload
    When User calls "DeletePlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"

  @regression
  Scenario: Verify if Delete Place functionality is working
    Given Delete place payload
    When User calls "DeletePlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"

