package com.wordpressTest.framework.components;

import com.wordpressTest.framework.DataGenerator;
import com.wordpressTest.framework.selenium.DriverFactory;

import static com.wordpressTest.framework.WebElementMap.getLocator;

public class CreatePostCommand {

    private final String title;
    private String postBody;

    public CreatePostCommand(String title) {
        this.title = title;
    }

    public CreatePostCommand withBody(String postBody) {
        this.postBody = postBody;
        return this;
    }

    public void publish() {
        DriverFactory.waitUntilVisible(getLocator("title"));
        DriverFactory.byElement("title").sendKeys(DataGenerator.getRandomFlatName());

        DriverFactory.byElement("PostsBody").sendKeys(postBody);
        DriverFactory.byElement("publish").click();
    }
}
