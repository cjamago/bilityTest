package com.wordpressTest.framework.workflow;

import com.wordpressTest.framework.DataGenerator;
import com.wordpressTest.framework.pages.ListsPostPage;
import com.wordpressTest.framework.pages.NewPostPage;
import com.wordpressTest.framework.selenium.DriverFactory;

import static com.wordpressTest.framework.WebElementMap.getLocator;

public class PostCreator {

    private static String previousBody;
    public static String previousPostTitle;

    public static void createPost() {
        NewPostPage.GoTo();
        previousBody = createBody();
        NewPostPage.CreatePost(createTitle()).withBody(previousBody).publish();
        previousPostTitle = previousPostTitle();
    }

    private static String createBody() {
        return DataGenerator.getRandomFlatName() + " body";

    }

    private static String createTitle() {
        return DataGenerator.getRandomFlatName() + " title";
    }

    private final static String previousPostTitle() {
        DriverFactory.waitUntilVisible(getLocator("title"));
        return DriverFactory.byElement("title").getAttribute("value");
    }

    public static void initialize() {
        previousPostTitle = null;
        previousBody = null;
    }

    public static void cleanUp() {
        try {
            if (isPostCreated())
                trashUpPost();
        } catch (NullPointerException exe){
            return;
        }

        trashUpPost();
    }

    private static void trashUpPost() {
        ListsPostPage.trashPost();
        initialize();
    }

    private static boolean isPostCreated() {

        return !(previousPostTitle.equals(null) || previousPostTitle.isEmpty());

    }
}
