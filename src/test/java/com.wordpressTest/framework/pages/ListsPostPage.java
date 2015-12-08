package com.wordpressTest.framework.pages;

import com.wordpressTest.framework.Navigation.LeftNavigate;
import com.wordpressTest.framework.selenium.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.function.Predicate;

import static com.wordpressTest.framework.WebElementMap.getLocator;
import static com.wordpressTest.framework.selenium.DriverFactory.*;

public class ListsPostPage {

    static Predicate<String> doesElementsExistWithTitle = title ->
            Instance.findElements(By.linkText(title)).stream().findFirst().isPresent();

    private static int lastCount;

    public static void GoTo(PostType postType) {

        switch (postType) {
            case Page:
                LeftNavigate.Pages.AllPages.select();
                break;
            case Posts:
                LeftNavigate.Posts.AllPost.select();
                break;
        }

    }

    public static void selectPost(String selectedPostName) {
        byElement(selectedPostName).click();
    }

    public static void storePostCount() {
        lastCount = getPostCount();
    }

    private static int getPostCount() {
        waitUntilVisible(getLocator("displayingNum"));
        String displayingNum = byElement("displayingNum").getText().replace(")", "").replace("(", "");
        return Integer.parseInt(displayingNum);
    }

    public static int previousPostCount() {
        return lastCount;
    }

    public static int currentPostCount() {
        return getPostCount();
    }

    public static boolean doesPostExistWithTitle(String title) {
        return doesElementsExistWithTitle.test(title);
    }

    public static void trashPost() {
        DriverFactory.byElements("rowTitle").stream().findFirst().ifPresent(
                webElement1 -> {
                    Actions actions = new Actions(Instance);
                    try {
                        actions.moveToElement(webElement1).perform();
                        DriverFactory.waitUntilClickable(getLocator("submitDelete")).click();
                    } catch (StaleElementReferenceException exc) {
                        return;
                    }
                }
        );
    }

    public static void searchForPost(String searchString) {
        if (!isAt("Posts"))
            GoTo(PostType.Posts);

        byElement("postSearchInput").sendKeys(searchString);

        byElement("searchButton").click();

    }

    private static boolean isAt(String header) {
        List<WebElement> h1 = byElements("h1");
        if (h1.size() > 0)
            return h1.get(0).getText().equalsIgnoreCase(header);
        return false;
    }
}
