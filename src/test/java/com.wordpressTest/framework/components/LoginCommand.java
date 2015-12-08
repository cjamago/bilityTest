package com.wordpressTest.framework.components;

import com.wordpressTest.framework.selenium.DriverFactory;

public class LoginCommand {

    private String userName;
    private String password;

    public LoginCommand(String userName) {
        this.userName = userName;
    }

    public LoginCommand withPassword(String password) {
        this.password = password;
        return this;
    }

    public void Login() {

        DriverFactory.byElement("userLogin").clear();
        DriverFactory.byElement("userLogin").sendKeys(userName);

        DriverFactory.byElement("passwd").clear();
        DriverFactory.byElement("passwd").sendKeys(password);

        DriverFactory.byElement("loginSubmit").click();
    }
}
