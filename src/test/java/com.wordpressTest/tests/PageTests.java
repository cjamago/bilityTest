package com.wordpressTest.tests;

import com.wordpressTest.framework.pages.ListsPostPage;
import com.wordpressTest.framework.pages.NewPostPage;
import com.wordpressTest.framework.pages.PostType;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.wordpressTest.framework.TestGroups.SMOKE;

public class PageTests extends WordPressBaseTest {

    @Test(enabled = false, groups = SMOKE)
    public void Can_Edit_A_Page() {

        ListsPostPage.GoTo(PostType.Page);
        ListsPostPage.selectPost("SamplePage");

        Assert.assertTrue(NewPostPage.IsInEditMode(), "wasn't in edit mode");

        Assert.assertEquals("Sample Page",  NewPostPage.Title());
    }
}
