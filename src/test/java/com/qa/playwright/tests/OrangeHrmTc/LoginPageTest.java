package com.qa.playwright.tests.OrangeHrmTc;

import com.qa.playwright.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {
    @Test(enabled = true)
    public void login() {
        orangeHrmLoginPage.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertTrue(orangeHrmLoginPage.verifyLogin(), "Login Failed. Invalid Credentials");
    }

    @Test
    public void testLinks() {
        Assert.assertTrue(orangeHrmLoginPage.verifyLinks());
    }
}
