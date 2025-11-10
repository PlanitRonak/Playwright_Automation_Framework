package com.qa.playwright.tests.SwagLabTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.utilities.DataProviders;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {
    @Test(dataProvider = "swagLabData", dataProviderClass = DataProviders.class, enabled = true)
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
}
