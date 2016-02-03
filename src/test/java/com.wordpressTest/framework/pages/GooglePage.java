package com.wordpressTest.framework.pages;

import com.wordpressTest.framework.selenium.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.wordpressTest.framework.selenium.DriverFactory.*;

public class GooglePage {

    public static void typeWordsToSearch(String searchWord) {
        WebElement element = Instance.findElement(By.name("q"));
        element.sendKeys(searchWord);
        element.submit();
    }

    public static boolean pageTitleContains(String pageTitle) {
        DriverFactory.Wait();
        return Instance.getTitle().toLowerCase().startsWith(pageTitle);
    }
}
