package com.wordpressTest.tests.blogwebsiteTests;


import com.wordpressTest.framework.pages.PostType;
import com.wordpressTest.framework.workflow.PostCreator;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.wordpressTest.framework.TestGroups.BLOGGING;
import static com.wordpressTest.framework.pages.ListsPostPage.*;
import static org.testng.Assert.assertTrue;

public class AllPostTests extends WordPressBaseTest {

    @Test(enabled = true, groups = BLOGGING)
    public void Can_Create_A_Basic_Post() {

        GoTo(PostType.Posts);
        storePostCount();

        PostCreator.createPost();

        GoTo(PostType.Posts);

        Assert.assertTrue((previousPostCount() + 1) == currentPostCount());

        assertTrue(doesPostExistWithTitle(PostCreator.previousPostTitle));
                //clean up post
                trashPost();
        assertTrue(previousPostCount() == currentPostCount());
        Assert.assertEquals(previousPostCount(), currentPostCount(), "Couldn't trash post");
    }

    @Test(groups = BLOGGING, enabled =true)
    public void Can_Search_A_Post() {

        PostCreator.createPost();

        searchForPost(PostCreator.previousPostTitle);

        assertTrue(doesPostExistWithTitle(PostCreator.previousPostTitle));
    }
}

