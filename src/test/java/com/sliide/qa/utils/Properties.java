package com.sliide.qa.utils;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Map;

import static java.lang.Boolean.getBoolean;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class Properties {

    private static final Map<String, String> sys = System.getenv();

    private static String getProperty(String key) {
        EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();

        Logger log = getLogger(Properties.class);

        if (StringUtils.isEmpty(key)) {
            String msg = key + " cannot be null or empty";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return env.getProperty(key);
    }

    public static String getSysProperty(String property) {
        if (property.isEmpty())
            throw new RuntimeException(property + "must be provided");

        return sys.get(property);
    }

    public static String getWebDriverProvided() {
        return getProperty("webdriver.driver");
    }

    public static String automationName() {
        return getProperty("automationName");
    }

    public static String appPath() {
        return getProperty("appium.app");
    }

    public static String platformName() {
        return getProperty("appium.platformName");
    }

    public static String platformVersion() {
        return getProperty("appium.platformVersion");
    }

    public static String deviceName() {
        return getProperty("appium.deviceName");
    }

    public static boolean takesScreenshot() {
        return getBoolean("appium.takesScreenshot");
    }

    public static boolean noReset() {
        return parseBoolean(getProperty("appium.noReset"));
    }

    public static boolean fullReset() {
        return parseBoolean(getProperty("appium.fullReset"));
    }

    public static int newCommandTimeout() {
        return parseInt(getProperty("appium.newCommandTimeout"));
    }

    /***
     Android-specific capabilities, see <a href="http://appium.io/docs/en/writing-running-appium/caps/index.html#android-only">...</a>
     <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/android/activity-startup.md">...</a>
     **/
    public static String appPackage() {
        return getProperty("appium.appPackage");
    }

    public static String appActivity() {
        return getProperty("appium.appActivity");
    }

    public static boolean resetKeyboard() {
        return parseBoolean(getProperty("resetKeyboard"));
    }

    public static boolean unicodeKeyboard() {
        return true;
    }

    public static boolean noSign() {
        return parseBoolean(getProperty("noSign"));
    }

    public static boolean autoGrantPermissions() {
        return parseBoolean(getProperty("autoGrantPermissions"));
    }
}
