package com.qa.playwright.tests.SwagLabTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.utilities.DataProviders;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {
    @Test(dataProvider = "swagLabData", dataProviderClass = DataProviders.class, enabled = false)
    public void testLogin(String username, String password, String status) {
        logger.info("Login TestCases started");
        swagloginPage.enterUsername(username);
        swagloginPage.enterPassword(password);
        logger.info("Entered credentials with "+username+" and "+password);
        swagloginPage.clickLogin();
//        This will check that the login is successful and also the credentials were valid
        logger.info("Verify login");
        boolean loginStatus = swagloginPage.verifyLogin();
        boolean validLogin = swagloginPage.verifyStatus(status);

        if(loginStatus) {
            logger.info("Login Successful");
            swagLabHomePage = swagloginPage.navigateToHomePage();
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
                String firstName = (String) user[0];
                String lastName = (String) user[1];
                String zip = (String) user[2];
                String total = (String) user[3];
                String[] products = (String[]) user[4];
                _reuse.validateCart(swagLabHomePage, swagLabCartPage, products, firstName, lastName, zip, total);
                swagLabHomePage.logOut();
            } else {
                logger.error("Login Unsuccessful");
            }
            logger.info("Login TestCases ended");
        }
    }

//    public void testCart(String firstName, String lastName, String zip, String total, String[] products) throws InterruptedException {
//
//    }
}
