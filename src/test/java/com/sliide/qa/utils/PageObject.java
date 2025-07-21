package com.sliide.qa.utils;

import com.sliide.qa.components.SharedDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFacade;
import net.thucydides.core.webdriver.exceptions.ElementNotFoundAfterTimeoutError;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;

import static com.sliide.qa.components.SharedDriver.TIMEOUT;
import static com.sliide.qa.components.SharedDriver.isAndroid;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;
import static net.serenitybdd.core.Serenity.recordReportData;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;

public class PageObject extends net.serenitybdd.core.pages.PageObject {

    private static final Logger LOG = getLogger(PageObject.class);

    public void write(String text) {
        LOG.info(text);
        System.out.printf("%s%n", text);
        SharedDriver.scenario.log(text);
    }

    public AppiumDriver getAppiumDriver() {
        return (AppiumDriver) ((WebDriverFacade) getDriver()).getProxiedDriver();
    }

    public boolean isDisplayed(WebElement element, long timeout) {
        try {
            return waitUntilVisible(element, timeout).isDisplayed();
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            return false;
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return waitUntilVisible(element).isDisplayed();
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            return false;
        }
    }

    public void switchToWebView() {
        waitABit(5000);
        String webView = isAndroid() ? "WEBVIEW_chrome" : "";
        LOG.warn("Context: {}", getAppiumDriver().getWindowHandles()); //.getContextHandles());
        getAppiumDriver().switchTo().window(webView);
    }

    public void switchToNativeApp() {
        waitABit(5000);
        getAppiumDriver().switchTo().window("NATIVE_APP");
    }

    public WebElement element(String id) {
        try {
            return isAndroid() ? getAppiumDriver().findElement(new ByAll(By.id(id))) : getAppiumDriver().findElement(new By.ById(id));
        } catch (NoSuchElementException | NotFoundException | StaleElementReferenceException err) {
            String message = format("Unable to locate element with ID: %s, due to %s", id, err.getCause());
            LOG.error(message);
            throw new NoSuchElementException(message);
        }
    }

    public void click(List<WebElement> elements, String text) {
        click(element(elements, text));
    }

    public WebElement element(List<WebElement> elements, String text) {
        try {
            return elements.stream().filter(
                    e -> containsIgnoreCase(e.getText(), text) || containsIgnoreCase(e.getAttribute("text"), text)
            ).findFirst().orElseThrow(NoSuchElementException::new);
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            LOG.error(err.getLocalizedMessage(), err);
            throw new ElementNotFoundAfterTimeoutError(err.getMessage(), err);
        }
    }

    public void click(WebElement element) {
        waitFor(waitUntilVisible(element)).waitUntilClickable().click();
    }

    public void typeInto(WebElement element, String text) {
        WebElement e = waitUntilVisible(element);
        waitABit(5000);
        if (!isEmpty(e.getText())) {
            e.clear();
        }

        write(format("Inputting data: %s", text));
        ((WebElementFacade) e).type(text);
    }

    public void waitUntilInvisible(WebElement element) {
        try {
            waitForCondition().withTimeout(ofSeconds(TIMEOUT)).until(ExpectedConditions.invisibilityOf(element));
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            LOG.error(err.getLocalizedMessage(), err);
            throw new TimeoutException(err.getMessage(), err);
        }
    }

    public WebElement waitUntilVisible(WebElement element, long timeout) {
        try {
            return waitFor(element).waitUntilVisible().withTimeoutOf(ofSeconds(timeout));
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            LOG.error(err.getLocalizedMessage(), err);
            throw new ElementNotFoundAfterTimeoutError(err.getMessage(), err);
        }
    }

    public WebElement waitUntilVisible(WebElement element) {
        try {
            return waitUntilVisible(element, TIMEOUT);
        } catch (NotFoundException | TimeoutException | StaleElementReferenceException | NoSuchElementException |
                 ElementNotFoundAfterTimeoutError err) {
            LOG.error(err.getLocalizedMessage(), err);
            throw new ElementNotFoundAfterTimeoutError(err.getMessage(), err);
        }
    }

    public static void wifi_data_Off() {
        try {
            Runtime.getRuntime().exec("adb shell svc wifi disable");
            Thread.sleep(5000);
            Runtime.getRuntime().exec("adb shell svc data disable");
            Thread.sleep(5000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wifi_data_On() {
        try {
            Thread.sleep(5000);
            Runtime.getRuntime().exec("adb shell svc wifi enable");
            Thread.sleep(5000);
            Runtime.getRuntime().exec("adb shell svc data enable");
            Thread.sleep(5000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isWifiEnabled() throws IOException {
        String command = "adb shell dumpsys wifi | grep 'Wi-Fi is'";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.toLowerCase().contains("wi-fi is enabled")) {
                return true;
            }
        }
        return false;
    }

    public static void turn_on_wifi_if_off() {
        try {
            if (!isWifiEnabled()) {
                wifi_data_On();
                System.out.println("Wi-Fi was OFF, turned ON in cleanup.");
            } else {
                System.out.println("Wi-Fi is already ON.");
            }
        } catch (IOException e) {
            System.err.println("⚠ Failed to check or set Wi-Fi state.");
            e.printStackTrace();
        }
    }

    public static boolean internet_connection_status() throws IOException {
        String command = "adb shell dumpsys connectivity | grep -i 'active network'";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        boolean isConnected = false;

        while ((line = reader.readLine()) != null) {
            line = line.toLowerCase();
            recordReportData().withTitle("Line Information").andContents(line);
            if ((line.contains("wifi active networks") || line.contains("cellular active networks"))
                    && !line.contains("{}")) {
                isConnected = true;
                break;
            }
        }
//        Assert.assertTrue("No internet connection detected!", isConnected);
        System.out.println(isConnected ? "Internet is connected." : "No internet connection detected.");
        return isConnected;
    }

    public void swipeUpScreen(WebElement element) {
        waitUntilVisible(element);
        try {
            getAppiumDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).flingBackward();"));
        } catch (Exception ignored) {

        }
    }

    public void swipeDownScreen(WebElement element) {
        waitUntilVisible(element);
        try {
            getAppiumDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).flingForward();"));
        } catch (Exception ignored) {

        }
    }

    public static void runShellScript(String command) {
        int iExitValue;
        String sCommandString;
        sCommandString = command;
        CommandLine oCmdLine = CommandLine.parse(sCommandString);
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            iExitValue = oDefaultExecutor.execute(oCmdLine);
        } catch (ExecuteException e) {
            System.out.println("Fail");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Denied");
            e.printStackTrace();
        }
    }
}
