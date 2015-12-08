package com.wordpressTest.framework.pages;

import com.wordpressTest.framework.Navigation.LeftNavigate;
import com.wordpressTest.framework.components.CreatePostCommand;
import com.wordpressTest.framework.selenium.DriverFactory;

import static com.wordpressTest.framework.WebElementMap.getLocator;

public class NewPostPage {

    public static void GoTo() {
        LeftNavigate.Posts.addNew.Select();
    }

    public static CreatePostCommand CreatePost(String title) {

        return new CreatePostCommand(title);
    }

    public static void GoToNewPost() {
        DriverFactory.waitUntilClickable(getLocator("ViewPosts")).click();
    }

    public static String Title() {
        String title = DriverFactory.byElement("title").getAttribute("value");
        if (title != null)
            return title;
        return "";
    }

    public static boolean IsInEditMode() {
        DriverFactory.waitUntilPresent(getLocator("h1"));
        return DriverFactory.byElement("h1").getText().startsWith("Edit Page");
    }
}

