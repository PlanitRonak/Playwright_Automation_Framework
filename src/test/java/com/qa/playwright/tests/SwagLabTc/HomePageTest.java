package com.qa.playwright.tests.SwagLabTc;

import com.qa.playwright.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
    @BeforeMethod
    public void login() {
        logger.info("Logging into Application");
        swagloginPage.login(prop.getProperty("username"), prop.getProperty("password"));
        logger.info("Navigating to HomePage");
        swagLabHomePage = swagloginPage.navigateToHomePage();
    }

    @Test(priority = 1, enabled = true)
    public void testFilter() {
        logger.info("Filter Test Case Started");
        logger.info("Selecting Option");
        swagLabHomePage.selectOption();
        logger.info("Checking if the Filter is Applied or not");
        Assert.assertTrue(swagLabHomePage.verifyFilter(), "Filter Not Applied");
        logger.info("Filter Test Case Finished");
    }

    @Test(enabled = false)
    public void testHover() throws InterruptedException {
        logger.info("Hover Test Case Started");
        Assert.assertTrue(swagLabHomePage.verifyHover());
    }
}
