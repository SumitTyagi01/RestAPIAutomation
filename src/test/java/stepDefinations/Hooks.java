package stepDefinations;


import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        StepDefination m = new StepDefination();
        if(StepDefination.place_id==null) {
            m.add_place_payload("sumit", "english", "noida");
            m.user_calls_with_http_request("AddPlaceAPI", "POST");
            m.verify_place_id_created_maps_to_using("sumit", "GETPlaceAPI");
        }
    }
}
