package com.qa.playwright.tests.OrangeHrmTc;

import com.qa.playwright.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeavePageTest extends BaseTest {
    @BeforeMethod
    public void setUp() {
        orangeHrmLoginPage.enterCredentials(prop.getProperty("username"), prop.getProperty("password"));
        orangeHrmHomePage = orangeHrmLoginPage.navigateToHomePage();
        orangeHrmLeavePage = orangeHrmHomePage.navigateToLeave();
    }
    @Test
    public void navigateToLeave() {
        Assert.assertTrue(orangeHrmLeavePage.verifyNavigation(), "Navigation Failed.");
    }
    @Test
    public void applyLeaveTest() {
        orangeHrmLeavePage.applyLeave("2026-1-1", "2026-31-12");
    }
}