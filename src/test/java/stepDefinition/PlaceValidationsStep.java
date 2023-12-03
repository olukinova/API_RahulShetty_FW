package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resourses.APIResourses;
import resourses.TestDataBuild;
import resourses.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class PlaceValidationsStep extends Utils {

    RequestSpecification completeRequest;
    ResponseSpecification responseSpecificationSpec;
    Response response;
    TestDataBuild testData = new TestDataBuild();
    static String place_id; // we use static keyword => all test cases in a particular run will refer to same var
    // if you don't put static and next scenario is started,
    // all vars are set to null and in the next scenario you will not be able to use it

    @Given("Add Place Payload with {string}, {string} and {string}")
    public void add_place_payload_with_and(String name, String language, String address) throws IOException {

        // RESPONSE specification
        completeRequest = given().spec(requestSpecification()) // here we're finalizing our request by adding body
                .body(testData.AddPlacePayload(name, language, address)); // here we specify all parameters required for request including baseURI, query params,
        System.out.println("Request is done");
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_post_http_request(String resource, String httpMethod ) {
       APIResourses resourceAPI = APIResourses.valueOf(resource);// this is how we create object of enum
        System.out.println(resourceAPI.getResource());

        // here we specify common steps for the response (analogue for requestSpecification() method in Utils
        responseSpecificationSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(httpMethod.equalsIgnoreCase("POST")) {
            response = completeRequest.when().post(resourceAPI.getResource());
        } else if (httpMethod.equalsIgnoreCase("GET")) {
            response = completeRequest.when().get(resourceAPI.getResource());
        } else if (httpMethod.equalsIgnoreCase("delete")) {
            response = completeRequest.when().delete(resourceAPI.getResource());
        }

    }
    @Then("the API call got success with the status code {int}")
    public void the_api_call_got_success_with_the_status_code(Integer int1) {
        assertEquals(response.getStatusCode(), 200);
        System.out.println("Status code is: " + response.getStatusCode());

    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
        String actualValue = getJsonPath(response, key);
        assertEquals(expectedValue, actualValue);
        System.out.println("Key " +  key + " is " + actualValue);
    }

    @Then("verify that place_id created maps to {string} using {string}")
    public void verify_that_place_id_created_maps_to_using_get_place_api(String expectedName, String resource) throws IOException {
        // Pseudo code:
        // prepare request spec
        place_id = getJsonPath(response, "place_id");
        completeRequest = given().spec(requestSpecification())
                .queryParam("place_id", place_id);

        // hit the URL
        user_calls_with_post_http_request(resource, "GET");

        //getting name from a new response
        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, expectedName);
        //

    }

    @Given("DeletePlaceAPI Payload")
    public void delete_place_api_payload() throws IOException {
        completeRequest = given().spec(requestSpecification()).body(testData.deletePlacePayload(place_id));
    }

}
