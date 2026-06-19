package com.qa.playwright.tests.OrangeHrmTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.utilities.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {
    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
    public void login() {
        orangeHrmLoginPage.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertTrue(orangeHrmLoginPage.verifyLogin(), "Login Failed. Invalid Credentials");
    }

    @Test(enabled = false)
    public void testLinks() {
        Assert.assertTrue(orangeHrmLoginPage.verifyLinks());
    }
}
