package com.sliide.qa.steplib;

import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

import static org.openqa.selenium.support.PageFactory.initElements;

public class BaseSteps extends ScenarioSteps {

    BaseSteps(Pages pages) {
        initElements(pages.getDriver(), this);
    }
}
