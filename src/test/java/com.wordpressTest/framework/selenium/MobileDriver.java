package com.wordpressTest.framework.selenium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Capabilities;

import java.net.URL;

/**
 * Created by chijiokea on 27/01/2016.
 */
public class MobileDriver extends AppiumDriver {

    public MobileDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    @Override
    public MobileElement scrollTo(String s) {
        return null;
    }

    @Override
    public MobileElement scrollToExact(String s) {
        return null;
    }
}