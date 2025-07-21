package com.sliide.qa.capabilities;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.MutableCapabilities;

import static com.sliide.qa.utils.Properties.*;

public class AndroidDriverCapabilities implements DriverCapabilities {

    UiAutomator2Options options = new UiAutomator2Options();

    @Override
    public MutableCapabilities getMutableCapabilities() {
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.setCapability(String.valueOf(options.getAppPackage()), appPackage());
        mutableCapabilities.setCapability(String.valueOf(options.getAppActivity()), appActivity());
        mutableCapabilities.setCapability(String.valueOf(options.autoGrantPermissions()), autoGrantPermissions());
        mutableCapabilities.setCapability(String.valueOf(options.noSign()), noSign());
        mutableCapabilities.setCapability("RESET_KEYBOARD", resetKeyboard());
        mutableCapabilities.setCapability("UNICODE_KEYBOARD", unicodeKeyboard());

        return mutableCapabilities;
    }
}
