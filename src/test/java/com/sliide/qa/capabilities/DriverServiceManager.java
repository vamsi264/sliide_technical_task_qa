package com.sliide.qa.capabilities;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

public class DriverServiceManager {

    private String message;
    private final DriverService driverService;
    private static final Logger LOG = getLogger(DriverServiceManager.class);

    public DriverServiceManager(DriverService driverService) {
        this.driverService = driverService;
    }

    public void start() {
        try {
            driverService.start();
            LOG.info("Driver service is now running on: {}", driverService.getUrl());
        } catch (IOException e) {
            message = format("Unable to start driver service, due to %s", Arrays.toString(e.getStackTrace()));
            LOG.error(message);
            throw new UnreachableBrowserException(message);
        }
    }

    public void stop() {
        try {
            Runtime.getRuntime().exec("killall node");
            System.out.println("Appium killed.");
        } catch (Exception e) {
            System.out.println("Appium kill error: " + e.getMessage());
        }
        try {
            if (isRunning()) {
                driverService.close();
                driverService.stop();
                LOG.info("Stopped driver service: {}", driverService.getUrl());
            }
        } catch (WebDriverException e) {
            message = format("Unable to stop driver service, due to %s", Arrays.toString(e.getStackTrace()));
            LOG.error(message);
            throw new UnreachableBrowserException(message);
        }
    }

    public URL getUrl() {
        try {
            return driverService.getUrl();
        } catch (WebDriverException e) {
            message = format("Unable to get driver service URL, due to %s", Arrays.toString(e.getStackTrace()));
            LOG.error(message);
            throw new UnreachableBrowserException(message);
        }
    }

    public boolean isRunning() {
        try {
            return driverService.isRunning();
        } catch (UnreachableBrowserException | NullPointerException e) {
            message = format("Unable to confirm driver service is running, due to %s", Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
