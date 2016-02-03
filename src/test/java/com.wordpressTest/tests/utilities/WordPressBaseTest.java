package com.wordpressTest.tests.utilities;

import com.wordpressTest.framework.pages.LoginPage;
import com.wordpressTest.framework.selenium.DriverFactory;
import com.wordpressTest.framework.workflow.PostCreator;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.Collections;

public class WordPressBaseTest {

    @BeforeClass(alwaysRun = true)
    public void init() {
        setupBaseTest(this.getClass().getName());
        PostCreator.initialize();
        LoginPage.GoTo();
        LoginPage.loginAs(DriverFactory.getUserName()).withPassword(DriverFactory.getPassword()).Login();
    }

    public void setupBaseTest(String testClassName) {
        DriverFactory.initialize(testClassName, Collections.<String>emptyList());
    }

    @AfterMethod(alwaysRun = true)
    protected void setSauceLabsSessionStatusToFailed(ITestResult result) {
        DriverFactory.setSauceLabsSessionStatusToFailed(result);
    }

    @AfterClass
    public void cleanUp(){
        DriverFactory.getDriver().close();
    }
}
