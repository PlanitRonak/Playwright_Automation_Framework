package com.qa.playwright.pages.OrangeHRMPages;

import com.microsoft.playwright.*;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.LocatorHelper;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;

public class orangeHrmLoginPage extends BasePage {

    public orangeHrmLoginPage(Page page, ReusableFunctions _reuse, Logger logger, LocatorHelper locatorHelper) {
        super(page, _reuse, logger, locatorHelper);
    }

    String usernameField = "//input[@placeholder='Username']";
    String passwordField = "//input[@placeholder='Password']";
    String[] passwordFields = {"//button[type='submit']", "//input[@placeholder='Password']"};
    String submitBtn = "//button[normalize-space()='Login']";
    String links = "//a";

    public void enterCredentials(String username, String password) {
        logger.info("Entering Credentials");
        _reuse.enterText(usernameField, username);
        _reuse.enterText(locatorHelper, passwordFields, password);
        _reuse.clickElement(page.locator(submitBtn));
    }

    public boolean verifyLogin() {
        logger.info("Verifying Logging");
        return page.url().equals("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
    }

    public boolean verifyLinks() {
        return _reuse.verifyLink(links);
    }

    public orangeHrmHomePage navigateToHomePage() {
        page.waitForLoadState();
        return new orangeHrmHomePage(page, _reuse, logger, locatorHelper);
    }
}
