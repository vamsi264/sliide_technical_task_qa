@News
Feature: News Page Screen
  As a user
  I want to see my news screen

  Scenario: News images are loaded
    Given the user successfully logged in to the app
    When there is internet connection
    Then the images are displayed in the rows on the list

    #testing failed but code implemented to pass for now with message
  Scenario: Failed to load images
    Given the user successfully logged in to the app
    When there is no internet connection
    And the user refreshes the page
    Then the error message is displayed with a Retry button

  Scenario: News card is clicked
    Given the news cards are successfully loaded on the screen
    When the user clicks one of the cards
    Then the user is navigated to the external browser with a corresponding article loaded