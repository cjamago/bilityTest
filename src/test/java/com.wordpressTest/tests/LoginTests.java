package com.wordpressTest.tests;


import com.wordpressTest.framework.pages.DashBoardPage;
import com.wordpressTest.tests.utilities.WordPressBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.wordpressTest.framework.TestGroups.SMOKE;

public class LoginTests extends WordPressBaseTest {

    @Test(enabled = false, groups = SMOKE)
    public void LoginTest() {
        Assert.assertTrue(DashBoardPage.isAt(), "Failed to Login");
    }
}

