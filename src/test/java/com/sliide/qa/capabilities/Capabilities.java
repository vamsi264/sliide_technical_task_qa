package com.sliide.qa.capabilities;

import org.openqa.selenium.MutableCapabilities;

public class Capabilities {

    private DriverCapabilities driverCapabilities;

    public MutableCapabilities getMutableCapabilities() {
        return driverCapabilities.getMutableCapabilities();
    }

    public void setMutableCapabilities(DriverCapabilities driverCapabilities) {
        this.driverCapabilities = driverCapabilities;
    }
}
