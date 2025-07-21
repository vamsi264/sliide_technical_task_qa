package com.sliide.qa.steps;

import com.sliide.qa.steplib.LoginSteps;
import com.sliide.qa.steplib.NewsSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import static com.sliide.qa.steps.LoginScenarioSteps.password;
import static com.sliide.qa.steps.LoginScenarioSteps.username;

public class NewsScenarioSteps {

    @Steps
    private NewsSteps newsSteps;

    @Steps
    private LoginSteps loginSteps;

    @Given("The user successfully logged in to the app")
    public void theUserSuccessfullyLoggedInToTheApp() {
        loginSteps.user_credentials(username,password);
        loginSteps.hit_login_button();
    }

    @When("There is internet connection")
    public void thereIsInternetConnection() {
        newsSteps.internet_connection_status_check();
    }

    @Then("Images are displayed in the rows on the list")
    public void imagesAreDisplayedInTheRowsOnTheList() {
        newsSteps.verify_news_page_screen();
        newsSteps.scrolling_screen_upwards();
    }

    @When("There is no internet connection")
    public void thereIsNoInternetConnection() {
        newsSteps.internet_turned_off();
    }

    @And("the user refreshes the page")
    public void theUserRefreshesThePage() {
        newsSteps.scroll_down_screen();
    }

    @Then("Error message is displayed with a Retry button")
    public void failedToLoadNewsErrorMessageIsDisplayedWithARetryButton() {
        newsSteps.verify_news_page_screen();
    }

    @Given("The news cards are successfully loaded on the screen")
    public void theNewsCardsAreSuccessfullyLoadedOnTheScreen() {
        theUserSuccessfullyLoggedInToTheApp();
        newsSteps.verify_news_page_screen();
    }

    @When("The user clicks one of the cards")
    public void theUserClicksOneOfTheCards() {
        newsSteps.click_news_article();
    }

    @Then("User is navigated to the external browser with a corresponding article loaded")
    public void userIsNavigatedToTheExternalBrowserWithACorrespondingArticleLoaded() {
        newsSteps.verify_news_article_on_browser();
    }
}
