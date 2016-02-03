package com.wordpressTest.framework.pages;


import com.wordpressTest.framework.selenium.DriverFactory;
import org.openqa.selenium.WebElement;

import static com.wordpressTest.framework.WebElementMap.getLocator;

public class PostPage {

    public static String Title() {
        DriverFactory.waitUntilElementIsPresent(getLocator("rowTitle"));
        WebElement entryTitle = DriverFactory.byElements("rowTitle").get(0);
        if(entryTitle != null)
           return entryTitle.getText();
        return "";
    }
}
