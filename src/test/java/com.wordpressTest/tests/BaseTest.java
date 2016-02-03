package com.wordpressTest.tests;

import com.wordpressTest.framework.selenium.BrowseDriver;

/**
 * Created by chijiokea on 21/01/2016.
 */
public class BaseTest extends BrowseDriver {

    protected void initializeTestBaseSetup(String browserType, String appURL) {
        try {
            setDriver(browserType, appURL);

        } catch (Exception e) {
            System.out.println("Error....." + e.getStackTrace());
        }
    }
}
