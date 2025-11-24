package com.qa.playwright.tests.SwagLabTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.utilities.DataProviders;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {
    @Test(dataProvider = "swagLabData", dataProviderClass = DataProviders.class, enabled = false)
    public void testLogin(String username, String password, String status) {
        logger.info("Login TestCases started");
        swagloginPage.enterUsername(username);
        swagloginPage.enterPassword(password);
        logger.info("Entered credentials");
        swagloginPage.clickLogin();
//        This will check that the login is successful and also the credentials were valid
        logger.info("Verify login");
        boolean loginStatus = swagloginPage.verifyLogin();
        boolean validLogin = swagloginPage.verifyStatus(status);

        if(loginStatus) {
            logger.info("Login Successful");
            swagLabHomePage = swagloginPage.navigateToHomePage();
            swagLabHomePage.logOut();
        } else {
            logger.error("Login Unsuccessful");
        }

        logger.info("Login TestCases ended");
        Assert.assertEquals(loginStatus, validLogin);
    }

    @Test(dataProvider = "swagLabLoginCartData", dataProviderClass = DataProviders.class, enabled = true)
    public void testPricesForDifferentAcc(String username, String password, String valid, Object[][] cartDetails) throws InterruptedException {
        for (Object[] user : cartDetails) {
            logger.info("Login TestCases started");
            swagloginPage.enterUsername(username);
            swagloginPage.enterPassword(password);
            logger.info("Entered credentials");
            swagloginPage.clickLogin();
//        This will check that the login is successful and also the credentials were valid
            logger.info("Verify login");
            boolean loginStatus = swagloginPage.verifyLogin();
            if(loginStatus) {
                logger.info("Login successful");
                swagLabHomePage = swagloginPage.navigateToHomePage();
                testCart((String) user[0],(String) user[1],(String) user[2],(String) user[3], (String[]) user[4]);
                swagLabHomePage.logOut();
            } else {
                logger.error("Login Unsuccessful");
            }
            logger.info("Login TestCases ended");
        }
    }

    public void testCart(String firstName, String lastName, String zip, String total, String[] products) throws InterruptedException {
        logger.info("Add to Cart Test Start");
        logger.info("Adding Items to Cart");
        swagLabHomePage.addToCart(products);
        logger.info("Navigating to Cart Page");
        swagLabCartPage = swagLabHomePage.navigateToCartPage();
        logger.info("CheckOut with "+firstName+" "+lastName+" "+zip);
        swagLabCartPage.checkOut(firstName, lastName, zip);
        logger.info("Verifying if the price is "+total+" or not.");
        Assert.assertTrue(swagLabCartPage.verifyPrice(total), "Price is Different");
        swagLabHomePage = swagLabCartPage.navigateToHome();
        logger.info("Add to Cart test Ended");
    }
}
