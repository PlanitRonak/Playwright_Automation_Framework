package com.qa.playwright.pages.OrangeHRMPages;

import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.LocatorHelper;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;

public class orangeHrmHomePage extends BasePage {

    public orangeHrmHomePage(Page page, ReusableFunctions _reuse, Logger logger, LocatorHelper locatorHelper) {
        super(page, _reuse, logger, locatorHelper);
    }

    String leaveNav = "//span[text()='Leave']";

    public orangeHrmLeavePage navigateToLeave() {
        _reuse.clickElement(leaveNav);
        return new orangeHrmLeavePage(page, _reuse, logger, locatorHelper);
    }
}
