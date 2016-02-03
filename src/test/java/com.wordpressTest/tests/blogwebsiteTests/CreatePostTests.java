package com.wordpressTest.tests.blogwebsiteTests;


import com.wordpressTest.framework.pages.NewPostPage;
import com.wordpressTest.framework.pages.PostPage;
import com.wordpressTest.framework.workflow.PostCreator;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.wordpressTest.framework.TestGroups.BLOGGING;

public class CreatePostTests extends WordPressBaseTest {

    @Test(enabled = true, groups = BLOGGING)
    public void Can_Create_A_Basic_Post() {

        PostCreator.createPost();

        NewPostPage.GoToNewPost();

        Assert.assertEquals(PostPage.Title(), PostCreator.previousPostTitle, "Did not match new post");
    }
}

