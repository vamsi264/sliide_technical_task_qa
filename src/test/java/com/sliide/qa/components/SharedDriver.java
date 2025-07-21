package com.sliide.qa.components;

import com.sliide.qa.capabilities.AndroidDriverCapabilities;
import com.sliide.qa.capabilities.Capabilities;
import com.sliide.qa.capabilities.DriverServiceManager;
import com.sliide.qa.utils.Properties;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.*;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;

import static com.sliide.qa.capabilities.DriverCapabilities.*;
import static com.sliide.qa.utils.PageObject.*;
import static com.sliide.qa.utils.Properties.*;
import static java.lang.String.format;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;
import static org.slf4j.LoggerFactory.getLogger;

public class SharedDriver implements DriverSource {

    private String message;
    public static Scenario scenario;
    public static final long TIMEOUT = 60;
    public static String platformName = platformName().toLowerCase();
    private static final Logger LOG = getLogger(SharedDriver.class);

    DriverServiceManager dsm = new DriverServiceManager(getDriverService());
    private boolean shouldResume = false;

    @Before
    public void setup(Scenario scenario) {
        SharedDriver.scenario = scenario;
        shouldResume = scenario.getSourceTagNames().contains("@ResumeApp");

        if (!shouldResume) {
            // Fresh start
            runShellScript("adb shell am force-stop com.test.news");
            runShellScript("adb shell am start -n com.test.news/com.test.news.MainActivity");
        } else {
            // Just bring it to foreground without restarting
        runShellScript("adb shell am start -n com.test.news/com.test.news.MainActivity");
        }
    }

    @After
    public void teardown() {
        if (isWebDriverProvided()) {
                if (getDriver() != null) {
                    LOG.warn("Quitting Appium driver...");
                    getDriver().quit();
                }
        }
        dsm.stop();
        turn_on_wifi_if_off();
    }

    @Override
    public WebDriver newDriver() {
        Capabilities capabilities = new Capabilities();
        switch (platformName) {
            case "android":
                capabilities.setMutableCapabilities(new AndroidDriverCapabilities());
                break;
            case "ios":
                // ios capabilities
                break;
            default:
                message = format("%s is unsupported, try either iOS or Android.", platformName);
                LOG.error(message);
                throw new UnsupportedCommandException(message);
        }
        MutableCapabilities mutableCapabilities = capabilities.getMutableCapabilities();
        setMutableCapabilities(mutableCapabilities);

        dsm.start();
        return new AndroidDriver(dsm.getUrl(), mutableCapabilities);
    }

    @Override
    public boolean takesScreenshots() {
        return takesScreenshot();
    }

    private void setMutableCapabilities(MutableCapabilities mutableCapabilities) {

        mutableCapabilities.setCapability(AUTOMATION_NAME, automationName());
        mutableCapabilities.setCapability(PLATFORM_NAME, platformName);
        mutableCapabilities.setCapability(PLATFORM_VERSION, platformVersion());
        mutableCapabilities.setCapability(DEVICE_NAME, deviceName());
        mutableCapabilities.setCapability(APP, appPath());
        mutableCapabilities.setCapability(NO_RESET, noReset());
        mutableCapabilities.setCapability(FULL_RESET, fullReset());
        mutableCapabilities.setCapability(CLEAR_SYSTEM_FILES, true);
        mutableCapabilities.setCapability(NEW_COMMAND_TIMEOUT, newCommandTimeout());
        mutableCapabilities.setCapability("forceAppLaunch", true);
    }

    public static boolean isAndroid() {
        return platformName.equalsIgnoreCase("Android");
    }

    public DriverService getDriverService() {
        try {
            LOG.warn("Starting Appium driver local service...");
            return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withIPAddress("127.0.0.1").usingAnyFreePort());
        } catch (AppiumServerHasNotBeenStartedLocallyException err) {
            message = format("Unable to start Appium driver service locally, due to %s", err.getMessage());
            LOG.error(message);
            throw new AppiumServerHasNotBeenStartedLocallyException(message);
        }
    }

    private boolean isWebDriverProvided() {
        return Properties.getWebDriverProvided().equalsIgnoreCase("provided");
    }

}
