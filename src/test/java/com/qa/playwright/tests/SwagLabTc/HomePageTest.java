package com.qa.playwright.tests.SwagLabTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
    @BeforeClass
    public void login() {
        logger.info("Logging into Application");
        swagloginPage.login(prop.getProperty("username"), prop.getProperty("password"));
        logger.info("Navigating to HomePage");
        swagLabHomePage = swagloginPage.navigateToHomePage();
    }

    @Test(priority = 1, enabled = false)
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

    @Test(dataProvider = "swagLabCartData", dataProviderClass = DataProviders.class)
    public void testCart(String firstName, String lastName, String zip, String total, String[] products) throws InterruptedException {
        logger.info("Add to Cart Test Start");
        boolean flag = false;
        logger.info("Adding Items to Cart");
        swagLabHomePage.addToCart(products);
        logger.info("Navigating to Cart Page");
        swagLabCartPage = swagLabHomePage.navigateToCartPage();
        logger.info("CheckOut with "+firstName+" "+lastName+" "+zip);
        swagLabCartPage.checkOut(firstName, lastName, zip);
        logger.info("Verifying if the price is "+total+" or not.");
        flag = swagLabCartPage.verifyPrice(total);
        swagLabHomePage = swagLabCartPage.navigateToHome();
        logger.info("Add to Cart test Ended");
        Assert.assertTrue(flag, "Price is Different");
    }
}
