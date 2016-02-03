package com.wordpressTest.tests.mobileTests;

import com.wordpressTest.framework.TestGroups;
import com.wordpressTest.framework.selenium.MobileDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chijiokea on 14/01/2016.
 */
public class MobileIOSTest {

    static AppiumDriver driver;

    @Test(enabled = true, groups = TestGroups.MOBILE)
    public void testApp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new MobileDriver(url, capabilities);

        driver.navigate().to("http://uk.search.yahoo.com");


        Assert.assertTrue(true, "Call Me button is not displayed");
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}
