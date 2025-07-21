@News @test
Feature: News Page Screen
  As a user
  I want to see my news screen

  Scenario: News images are loaded
    Given The user successfully logged in to the app
    When There is internet connection
    Then Images are displayed in the rows on the list

    #testing failed but code implemented to pass for now with message
  Scenario: Failed to load images
    Given The user successfully logged in to the app
    When There is no internet connection
    And the user refreshes the page
    Then Error message is displayed with a Retry button

  Scenario: News card is clicked
    Given The news cards are successfully loaded on the screen
    When The user clicks one of the cards
    Then User is navigated to the external browser with a corresponding article loaded