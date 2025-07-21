@Login @test
Feature: Login Screen
  As a user
  I want to log in to the app

  Scenario: User opens the android app first time (when not logged in yet)
    Given the user opens the app for the first time
    Then the login screen with username and password entries and login button is displayed

  Scenario Outline: User login fails with invalid or missing credentials
    Given the user enters "<username>" and "<password>"
    When the login button is clicked
    Then the error message is displayed
    Examples:
      | username | password | //
      | valid    | invalid  | # Case 1: correct username, wrong password
      |          |          | # Case 2: empty fields
      | invalid  | valid    | # Case 3: wrong username, correct password
      | invalid  | invalid  | # Case 4: wrong username, wrong password

  Scenario: User logged in successfully
    Given the user provided valid credentials
    When the login button is clicked
    Then the user is taken to the news screen

  @ResumeApp
  Scenario: User opens app next time (when previously logged in)
    Given the user opens app next time
    Then the user is taken straight to the news screen