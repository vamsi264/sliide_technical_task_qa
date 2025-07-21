package com.sliide.qa.steplib;

import com.sliide.qa.screens.LoginScreen;
import net.serenitybdd.annotations.Step;
import net.thucydides.core.pages.Pages;

public class LoginSteps extends BaseSteps {

    private final LoginScreen loginScreen = new LoginScreen();

    LoginSteps(Pages pages) {
        super(pages);
    }

    private String username;
    private String password;

    @Step
    public void verify_login_screen_title() {
        loginScreen.verify_login_screen_title();
    }

    @Step
    public void verify_login_screen_username_field() {
        loginScreen.verify_login_screen_username_field();
    }

    @Step
    public void verify_login_screen_password_field() {
        loginScreen.verify_login_screen_password_field();
    }

    @Step
    public void verify_login_screen_login_button() {
        loginScreen.verify_login_screen_login_button();
    }

    @Step
    public void enter_credentials(String username, String password) {
        this.username = username;
        this.password = password;

        String actualUsername = username.equalsIgnoreCase("valid") ? "user" :
                username.equalsIgnoreCase("empty") ? "" : "wrongUser";
        String actualPassword = password.equalsIgnoreCase("valid") ? "password" :
                password.equalsIgnoreCase("empty") ? "" : "wrongPass";

        // Enter credentials
        user_credentials(actualUsername, actualPassword);
    }

    @Step
    public void user_credentials(String validUsername, String validPassword) {
        loginScreen.enterUsername(validUsername);
        loginScreen.enterPassword(validPassword);
    }

    @Step
    public void hit_login_button() {
        loginScreen.click_login_btn();
    }

    @Step
    public void verify_error_message_for_case() {
        String caseKey = (username.isEmpty() ? "empty" : username.toLowerCase()) + "_" +
                (password.isEmpty() ? "empty" : password.toLowerCase());

        switch (caseKey) {
            case "valid_invalid":
                loginScreen.verifyErrorMessage("Wrong password");
                break;
            case "empty_empty", "invalid_valid", "invalid_invalid":
                loginScreen.verifyErrorMessage("Wrong user name");
                break;
            default:
                loginScreen.verifyErrorMessage("Unexpected error");
        }
    }
}
