package com.wordpressTest.tests;


import com.wordpressTest.framework.pages.NewPostPage;
import com.wordpressTest.framework.pages.PostPage;
import com.wordpressTest.framework.workflow.PostCreator;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.wordpressTest.framework.TestGroups.SMOKE;

public class CreatePostTests extends WordPressBaseTest {

    @Test(enabled = false, groups = SMOKE)
    public void Can_Create_A_Basic_Post() {

        PostCreator.createPost();

        NewPostPage.GoToNewPost();

        Assert.assertEquals(PostPage.Title(), PostCreator.previousPostTitle, "Did not match new post");
    }
}

