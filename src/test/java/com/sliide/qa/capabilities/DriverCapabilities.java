package com.sliide.qa.capabilities;

import org.openqa.selenium.MutableCapabilities;

public interface DriverCapabilities {
    MutableCapabilities getMutableCapabilities();

    String NO_RESET = "appium.noReset";
    String CLEAR_SYSTEM_FILES = "appium.clearSystemFiles";
    String PLATFORM_VERSION = "appium.platformVersion";
    String DEVICE_NAME = "appium.deviceName";
    String APP = "appium.app";
    String FULL_RESET = "appium.fullReset";
    String NEW_COMMAND_TIMEOUT = "appium.newCommandTimeout";
    String AUTOMATION_NAME = "automationName";
}
