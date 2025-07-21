package com.sliide.qa.steplib;

import com.sliide.qa.screens.NewsScreen;
import net.serenitybdd.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

public class NewsSteps extends ScenarioSteps {

    private final NewsScreen newsScreen = new NewsScreen();

    NewsSteps(Pages pages) {
        super(pages);
    }

    @Step
    public void verify_news_page_screen() {
        newsScreen.assert_news_page_screen();
    }

    @Step
    public void scrolling_screen_upwards() {
        newsScreen.scroll_screen_up();
    }

    @Step
    public void scroll_down_screen() {
        newsScreen.scroll_screen_down();
    }

    @Step
    public void internet_connection_status_check() {
        newsScreen.internet_status_check();
    }

    @Step
    public void internet_turned_off() {
        newsScreen.internet_turn_off();
    }

    @Step
    public void click_news_article() {
        newsScreen.click_on_news_card();
    }

    @Step
    public void verify_news_article_on_browser() {
        newsScreen.assert_article_page_on_browser();
    }


}
