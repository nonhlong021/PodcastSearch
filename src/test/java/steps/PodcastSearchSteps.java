package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.aluve.services.Services;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PodcastSearchSteps {
    Services services;
    String id;
    Response response;
    public static void setup() {
        RestAssured.baseURI = "http://your.api.base.url";

    }
    @Given("the podcast {string}")
    public void thePodcast(String name) {
    services.setEndpoint("shows");
    services.setHTTPMethod("GET");
    services.build();
    Response response = services.send();
    if(response.then().extract().jsonPath().getString("x.title").equals(name)) {
        this.id = response.then().extract().jsonPath().getString("x.id");
    }}

    @When("I check the number of episodes in season {string}")
    public void iCheckTheNumberOfEpisodesInSeason(String season) {
        services.setEndpoint("http://your.api.base.url/ /id/"+id);
        services.setHTTPMethod("GET");
        services.build();
        response = services.send();
        
    }

    @Then("it should have {string} episodes")
    public void itShouldHaveEpisodes(String season) {

        List<String> episodes = response.jsonPath().getList(("x.episodes"));
        assertEquals(10, episodes);
    }
}
