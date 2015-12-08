package com.wordpressTest.framework.Navigation;

import com.wordpressTest.framework.selenium.DriverFactory;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.wordpressTest.framework.WebElementMap.getLocator;
import static com.wordpressTest.framework.selenium.DriverFactory.Instance;

/**
 * Created by chijiokea on 28/10/2015.
 */
public class MenuSelector {

    public static void select(String topLeveMenuId, String subMenuLinkText) {

        WebElement webElement = DriverFactory.byElement(topLeveMenuId);

        Actions actions = new Actions(Instance);
        try {
            actions.moveToElement(webElement).perform();
            DriverFactory.waitUntilClickable(getLocator(subMenuLinkText)).click();
        } catch (StaleElementReferenceException exc) {
            return;
        }
    }
}
