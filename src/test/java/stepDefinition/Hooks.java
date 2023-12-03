package stepDefinition;



import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        // write a code that will give you place ID
        // generate this code only if place ID is Null (when we want to run 'delete' scenario separately

        PlaceValidationsStep placeValidationsStep = new PlaceValidationsStep();

        if (PlaceValidationsStep.place_id == null) { //here we use class name since place_id is static variable
            placeValidationsStep.add_place_payload_with_and("Shetty", "French", "Asia");
            placeValidationsStep.user_calls_with_post_http_request("AddPlaceAPI", "POST");
            placeValidationsStep.verify_that_place_id_created_maps_to_using_get_place_api("Shetty", "getPlaceAPI");

        }
    }

}
