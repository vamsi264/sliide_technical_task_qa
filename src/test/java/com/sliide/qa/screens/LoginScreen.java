package com.sliide.qa.screens;

import com.sliide.qa.utils.PageObject;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

import static net.serenitybdd.core.Serenity.recordReportData;

public class LoginScreen extends PageObject {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Login']")
    WebElement asserTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='User name']")
    WebElement usernameField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Password']")
    WebElement passwordField;

    @AndroidFindBys({
            @AndroidBy(className = "android.widget.EditText")
    })
    public List<WebElement> inputFields;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Login']")
    WebElement loginText;

    @AndroidFindBy(className = "android.widget.Button")
    WebElement loginBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Wrong password' or @text='Wrong user name']")
    WebElement errorMessage;


    public void verify_login_screen_title() {
        Assert.assertTrue("Login Screen Title is Displayed", asserTitle.isDisplayed());
        recordReportData().withTitle("Login Screen Title").andContents(asserTitle.getText());
    }

    public void verify_login_screen_username_field() {
        Assert.assertTrue("Username Field is Displayed", usernameField.isDisplayed());
        recordReportData().withTitle("Username Field Text").andContents(usernameField.getText());
    }

    public void verify_login_screen_password_field() {
        Assert.assertTrue("Password Field is Displayed", passwordField.isDisplayed());
        recordReportData().withTitle("Password Field Text").andContents(passwordField.getText());
    }

    public void verify_login_screen_login_button() {
        Assert.assertTrue("Login Button is Displayed", loginText.isDisplayed());
        recordReportData().withTitle("Login Button Text").andContents(loginText.getText());
    }

    public void click_login_btn() {
        click(loginBtn);
    }

    public void verifyErrorMessage(String message) {
        String actualMessage = errorMessage.getText();
        Assert.assertEquals("Error message mismatch", message, actualMessage);
        recordReportData().withTitle("Error Message").andContents(actualMessage);
    }

    public void enterUsername(String actualUsername) {
        inputFields.get(0).sendKeys(actualUsername);
    }

    public void enterPassword(String actualPassword) {
        inputFields.get(1).sendKeys(actualPassword);
    }
}
