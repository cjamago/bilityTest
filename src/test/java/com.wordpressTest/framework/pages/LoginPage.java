package com.wordpressTest.framework.pages;

import com.wordpressTest.framework.components.LoginCommand;
import com.wordpressTest.framework.selenium.DriverFactory;

import static com.wordpressTest.framework.selenium.DriverFactory.baseAddress;

public class LoginPage {


    public static void GoTo() {
        DriverFactory.Instance.navigate().to(baseAddress());
        DriverFactory.Instance.manage().window().maximize();
    }

    public static LoginCommand loginAs(String userName) {
        return new LoginCommand(userName);
    }
}

