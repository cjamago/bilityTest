package com.wordpressTest.framework.pages;

import com.wordpressTest.framework.selenium.DriverFactory;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static com.wordpressTest.framework.WebElementMap.getLocator;
import static com.wordpressTest.framework.selenium.DriverFactory.Instance;
import static com.wordpressTest.framework.selenium.DriverFactory.byElement;

public class DashBoardPage {
    public static boolean isAt() {
        Instance.manage().timeouts().pageLoadTimeout(30, TimeUnit.MICROSECONDS);
        DriverFactory.waitUntilPresent(getLocator("h1"));
        WebElement headers = byElement("h1");
        if(headers.isDisplayed())
            return headers.getText().equalsIgnoreCase("Dashboard");
        return false;
    }
}