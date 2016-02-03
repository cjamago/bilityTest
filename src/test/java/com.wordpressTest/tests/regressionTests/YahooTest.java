package com.wordpressTest.tests.regressionTests;

import com.wordpressTest.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class YahooTest extends BaseTest {

    @Parameters({ "browserType", "appURL" })
    @BeforeClass
    public void setUp(String browserType, String appUR){
        initializeTestBaseSetup(browserType, appUR);
    }

    @Test(enabled = true, groups = "smoke")
    public void yahooSearch() {

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);


        (new WebDriverWait(driver, 10)).until((WebDriver d) -> d.getTitle().toLowerCase().startsWith("yahoo"));
        WebElement element = driver.findElement(By.id("UHSearchBox"));

        element.sendKeys("Cheese!");

        element.submit();


        (new WebDriverWait(driver, 10)).until((WebDriver d) -> d.getTitle().toLowerCase().startsWith("cheese!"));

        // Should see: "cheese! - Google Search"
        Assert.assertTrue("Cheese! - Yahoo Search Results".equalsIgnoreCase(driver.getTitle()));
    }

    @AfterClass(alwaysRun = true)
    private void tearDown() {
        if(driver!=null) {
            driver.quit();
        }
    }
}
