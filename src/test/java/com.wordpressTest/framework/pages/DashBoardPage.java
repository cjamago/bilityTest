package com.wordpressTest.framework.pages;

import static com.wordpressTest.framework.WebElementMap.getLocator;
import static com.wordpressTest.framework.selenium.DriverFactory.byElement;
import static com.wordpressTest.framework.selenium.DriverFactory.waitUntilElementIsPresent;

public class DashBoardPage {
    public static boolean isAt() {
        if(waitUntilElementIsPresent(getLocator("h1")))
            return byElement("h1").getText().equalsIgnoreCase("Dashboard");
        return false;
    }
}