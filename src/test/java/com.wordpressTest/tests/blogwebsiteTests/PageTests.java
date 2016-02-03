package com.wordpressTest.tests.blogwebsiteTests;

import com.wordpressTest.framework.pages.ListsPostPage;
import com.wordpressTest.framework.pages.NewPostPage;
import com.wordpressTest.framework.pages.PostType;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PageTests extends WordPressBaseTest {

    @Test(enabled = false)
    public void Can_Edit_A_Page() {

        ListsPostPage.GoTo(PostType.Page);
        ListsPostPage.selectPost("SamplePage");

        assertThat("wasn't in edit mode", NewPostPage.IsInEditMode(), is(true));

        assertThat("Sample Page not displace", NewPostPage.Title(), equalTo("Sample Page"));
    }
}
