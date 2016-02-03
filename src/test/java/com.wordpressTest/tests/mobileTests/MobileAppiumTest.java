package com.wordpressTest.tests.mobileTests;

import com.wordpressTest.framework.TestGroups;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Predicate;

/**
 * Created by chijiokea on 14/01/2016.
 */
public class MobileAppiumTest {

    static AppiumDriver driver;

    private static Predicate<By> waitUntilElementIsDisplayed = (by) ->
            (new WebDriverWait(driver, 10)).until((WebDriver d) -> d.findElement(by)).isDisplayed();

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

    @Test(enabled = true, groups = TestGroups.MOBILE)
    public void testApp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4.4");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Google Nexus 10");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/WhatsAppMessenger.com.apk");
        capabilities.setCapability("app-package", "com.whatsapp");
        capabilities.setCapability("app-activity", "com.whatsapp.Main");
        capabilities.setCapability("udid", "192.168.57.101:5555");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new MobileDriver(url, capabilities);

        waitUntilElementIsDisplayed.test(By.className("android.widget.Button"));

        driver.findElement(By.className("android.widget.Button")).click();

        driver.findElement(By.id("com.whatsapp:id/eula_accept")).click();

        WebElement code = driver.findElement(By.id("com.whatsapp:id/registration_cc"));

        code.clear();

        code.sendKeys("44");

        driver.findElement(By.id("com.whatsapp:id/registration_phone")).sendKeys("7877755777");

        driver.findElement(By.id("com.whatsapp:id/registration_submit")).click();

        waitUntilElementIsDisplayed.test(By.id("android:id/button1"));

        driver.findElement(By.id("android:id/button1")).click();

        waitUntilElementIsDisplayed.test(By.className("android.widget.Button"));

        Assert.assertTrue(
                driver.findElement(By.className("android.widget.Button")).getText().equalsIgnoreCase("Call me"),
                "Call Me button is not displayed");
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}
