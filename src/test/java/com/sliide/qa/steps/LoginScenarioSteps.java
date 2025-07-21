package com.sliide.qa.steps;

import com.sliide.qa.steplib.LoginSteps;
import com.sliide.qa.steplib.NewsSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class LoginScenarioSteps {

    @Steps
    private LoginSteps loginSteps;

    @Steps
    private NewsSteps newsSteps;

    static String username = System.getenv("TEST_USERNAME");
    static String password = System.getenv("TEST_PASSWORD");

    @Given("the user opens the app for the first time")
    public void theUserOpensTheAppForTheFirstTime() {
        loginSteps.verify_login_screen_title();
    }

    @Then("the login screen with username and password entries and login button is displayed")
    public void theLoginScreenIsDisplayed() {
        loginSteps.verify_login_screen_username_field();
        loginSteps.verify_login_screen_password_field();
        loginSteps.verify_login_screen_login_button();
    }

    @Given("the user enters {string} and {string}")
    public void theUserEntersAnd(String username, String password) {
        loginSteps.enter_credentials(username, password);
    }

    @When("the login button is clicked")
    public void theLoginButtonIsClicked() {
        loginSteps.hit_login_button();
    }

    @Then("the error message is displayed")
    public void theErrorMessageIsDisplayed() {
        loginSteps.verify_error_message_for_case();
    }

    @Given("the user provided valid credentials")
    public void theUserProvidedValidCredentials() {
        loginSteps.user_credentials(username, password);
    }

    @Then("the user is taken to the news screen")
    public void userIsTakenToTheNewsScreen() {
        newsSteps.verify_news_page_screen();
    }

    @Given("the user opens app next time")
    public void theUserOpensAppNextTime() {
        userIsTakenToTheNewsScreen();
    }

    @Then("the user is taken straight to the news screen")
    public void userIsTakenStraightToTheNewsScreen() {
        newsSteps.scrolling_screen_upwards();
    }
}
