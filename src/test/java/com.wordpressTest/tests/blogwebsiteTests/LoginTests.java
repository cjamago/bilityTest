package com.wordpressTest.tests.blogwebsiteTests;


import com.wordpressTest.framework.pages.DashBoardPage;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.annotations.Test;

import static com.wordpressTest.framework.TestGroups.BLOGGING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LoginTests extends WordPressBaseTest {

    @Test(enabled = true, groups = BLOGGING)
    public void LoginTest() throws Exception {
        assertThat(DashBoardPage.isAt(), is(true));
    }
}

