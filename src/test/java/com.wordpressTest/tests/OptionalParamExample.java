package com.wordpressTest.tests;

import com.wordpressTest.framework.TestGroups;
import org.testng.annotations.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OptionalParamExample extends BaseTest {

    @Parameters({ "browserType", "appURL" })
    @BeforeClass
    private void setUp(String browserType, String appUR){
        initializeTestBaseSetup(browserType, appUR);
    }

    @Parameters("param one")
    @Test(groups = TestGroups.SMOKE)
    public void testOptionParamOne(String paramOne) {

        System.out.println("Parameter passed from XML ::" + paramOne);
    }

    @Parameters("param two")
    @Test(groups = TestGroups.SMOKE)
    public void testOptionParamTwo(@Optional("two") String paramTwo) {

        System.out.println("Optional value passed ::" + paramTwo);
    }

    @Test(groups = TestGroups.SMOKE)
    public void testGoogleSearch(){
        assertThat(driver.getTitle(), is("Google"));
    }

    @AfterClass(alwaysRun = true)
    private void tearDown() {
        driver.quit();
    }
}