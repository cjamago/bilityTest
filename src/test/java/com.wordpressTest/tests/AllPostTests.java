package com.wordpressTest.tests;


import com.wordpressTest.framework.pages.ListsPostPage;
import com.wordpressTest.framework.pages.PostType;
import com.wordpressTest.framework.workflow.PostCreator;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.wordpressTest.framework.TestGroups.SMOKE;
import static com.wordpressTest.framework.pages.ListsPostPage.*;
import static org.testng.Assert.assertTrue;

public class AllPostTests extends WordPressBaseTest {

    @Test(enabled = false, groups = SMOKE)
    public void Can_Create_A_Basic_Post() {

        GoTo(PostType.Posts);
        ListsPostPage.storePostCount();

        PostCreator.createPost();

        GoTo(PostType.Posts);

        Assert.assertTrue((previousPostCount() + 1) == currentPostCount());

        assertTrue(doesPostExistWithTitle(PostCreator.previousPostTitle));
                //clean up post
                ListsPostPage.trashPost();
        assertTrue(previousPostCount() == currentPostCount());
        Assert.assertEquals(previousPostCount(), currentPostCount(), "Couldn't trash post");
    }

    @Test(groups = SMOKE, enabled = false)
    public void Can_Search_A_Post() {

        PostCreator.createPost();

        ListsPostPage.searchForPost(PostCreator.previousPostTitle);

        assertTrue(doesPostExistWithTitle(PostCreator.previousPostTitle));
    }
}

