package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.*;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class StepDefination extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data;

    static String place_id;

    public StepDefination(){
        if(System.getProperty("env").equalsIgnoreCase("dev"))
           data= new TestDataBuild_dev();
        else if(System.getProperty("env").equalsIgnoreCase("qa"))
            data= new TestDataBuild_qa();
        else
            data = new TestDataBuild();
    }


    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload(String name,String language, String address) throws IOException {

        if(System.getProperty("env").equalsIgnoreCase("dev"))
             res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
        if(System.getProperty("env").equalsIgnoreCase("qa"))
            res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
    }


    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResources apiResources= APIResources.valueOf(resource);

        resspec =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST"))
            response = res.when().post(apiResources.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = res.when().get(apiResources.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            response = res.when().delete(apiResources.getResource());

    }
    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer int1) {
        assertEquals(response.getStatusCode(),200);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {

        assertEquals(getJsonPath(response,keyValue),expectedValue);
    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedname, String resource) throws IOException {

        place_id = getJsonPath(response,"place_id");
        res = given().spec(requestSpecification()).queryParam("place_id",place_id);
        user_calls_with_http_request(resource,"GET");
        String actualName = getJsonPath(response,"name");
        assertEquals(actualName,expectedname);

    }

    @Given("Delete place payload")
    public void delete_place_payload() throws IOException {

            add_place_payload("forDelete", "xyzlanguage", "address");
            user_calls_with_http_request("AddPlaceAPI", "POST");
            place_id = getJsonPath(response, "place_id");
            res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
        }


}
