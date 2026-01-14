package com.qa.playwright.pages.OrangeHRMPages;

import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;

public class orangeHrmLeavePage extends BasePage {

    public orangeHrmLeavePage(Page page, ReusableFunctions _reuse, Logger logger) {
        super(page, _reuse, logger);
    }

    public boolean verifyNavigation() {
        page.waitForLoadState();
        logger.info("Verifying URL.");
        return page.url().equals("https://opensource-demo.orangehrmlive.com/web/index.php/leave/viewLeaveList");
    }

    public void applyLeave(String startDate, String endDate) {
        logger.info("Start date: "+startDate);
        logger.info("End date: "+endDate);
    }
}
