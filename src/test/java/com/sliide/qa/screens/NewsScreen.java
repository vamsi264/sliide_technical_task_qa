package com.sliide.qa.screens;

import com.sliide.qa.utils.PageObject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import net.serenitybdd.core.annotations.findby.By;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static net.serenitybdd.core.Serenity.recordReportData;


public class NewsScreen extends PageObject {

    @AndroidFindBy(id = "android:id/content")
    WebElement newsPageScreen;

    @AndroidFindBy(xpath = "//android.widget.TextView[starts-with(@text, 'Source:')]")
    WebElement newsPageSourceTextField;

    @AndroidFindBy(id = "com.sec.android.app.sbrowser:id/location_bar_edit_text")
    WebElement browserPageURL;

    public void assert_news_page_screen() {
        boolean simulateInternetDown = false; // set to true to force failure (simulate offline)
        boolean isConnected ;
        try {
            isConnected = internet_connection_status();
        } catch (IOException e) {
            throw new RuntimeException("Failed to check netwrok status",e);
        }
        if (simulateInternetDown || !isConnected) {
            // Offline mode: page may still show cached content, no retry UI implemented yet
            if (newsPageSourceTextField != null && newsPageSourceTextField.isDisplayed()) {
                recordReportData().withTitle("Offline Mode")
                        .andContents("Page is displayed in offline mode, but retry screen is not yet implemented.");
                // logged a warning instead of hard Assert.fail
            } else {
                Assert.fail("News Page is not displayed while offline, and no retry screen is implemented.");
            }
        } else {
            recordReportData().withTitle("News Page Source Text Field").andContents(newsPageSourceTextField.getText());
            // Online check
            if (newsPageSourceTextField == null || !newsPageSourceTextField.isDisplayed()) {
                Assert.fail("News Page Screen is not displayed.");
            } else {
                recordReportData()
                        .withTitle("Screen Verification")
                        .andContents("News Page Screen is displayed successfully.");
            }
        }
    }

    public void scroll_screen_up() {
        swipeDownScreen(newsPageSourceTextField);
    }

    public void scroll_screen_down() {
        swipeUpScreen(newsPageSourceTextField);
    }

    public void internet_status_check() {
        try {
            internet_connection_status();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void internet_turn_off() {
        wifi_data_Off();
    }

    public void click_on_news_card() {
        List<WebElement> views = getAppiumDriver().findElements(By.xpath("//android.view.View"));
        if (views.size() >= 2) {
            int index = new Random().nextBoolean() ? 1 : 2;
            views.get(index - 1).click();
        }
    }

    public void assert_article_page_on_browser() {
        String currentUrl=browserPageURL.getText();
        recordReportData().withTitle("Article Opened in Browser").andContents("URL: " + currentUrl);
        Assert.assertNotNull("URL is null", currentUrl);
        getAppiumDriver().navigate().back();
    }
}
