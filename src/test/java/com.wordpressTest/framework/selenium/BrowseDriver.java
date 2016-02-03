package com.wordpressTest.framework.selenium;

import org.openqa.selenium.WebDriver;

import static com.wordpressTest.framework.selenium.DriverFactory.*;


/**
 * Created by chijiokea on 28/01/2016.
 */
public class BrowseDriver {

    protected WebDriver driver;

    protected void setDriver(String browserType, String appURL) {

        switch (browserType) {
            case "chrome":
                initChromeDriver();
                driver = DriverFactory.Instance;
                openBrowser(appURL);
                break;
            case "firefox":
                initFirefoxDriver();
                driver = DriverFactory.Instance;
                openBrowser(appURL);
                break;
            case "safari":
                initSafariDriver();
                driver = DriverFactory.Instance;
                openBrowser(appURL);
                break;
            default:
                System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
                initFirefoxDriver();
                driver = DriverFactory.Instance;
                openBrowser(appURL);
        }
    }

    private  WebDriver openBrowser(String appURL) {
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

}
