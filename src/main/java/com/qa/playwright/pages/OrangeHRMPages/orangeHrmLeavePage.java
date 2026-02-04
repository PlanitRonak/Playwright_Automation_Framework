package com.qa.playwright.pages.OrangeHRMPages;

import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;

public class orangeHrmLeavePage extends BasePage {

    public orangeHrmLeavePage(Page page, ReusableFunctions _reuse, Logger logger) {
        super(page, _reuse, logger);
    }

    String name = "//p[@class='oxd-userdropdown-name']";
    String assignLeaveBtn = "//a[normalize-space()='Assign Leave']";
    String hintBox = "//input[@placeholder='Type for hints...']";
    String suggestionList = "//div[@role='option']";
    String leaveTypeDropDown = "//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']";
    String leaveTypeOption = "//div[@role='listbox']//div";
    String StartDate = "//label[normalize-space()='From Date']/parent::div/following-sibling::div//input";
    String EndDate = "//label[normalize-space()='To Date']/parent::div/following-sibling::div//input";
    String assignBtn = "//button[normalize-space()='Assign']";
    String successfullMessage = "//div[@role='document']";

    public boolean verifyNavigation() {
        page.waitForLoadState();
        logger.info("Verifying URL.");
        return page.url().equals("https://opensource-demo.orangehrmlive.com/web/index.php/leave/viewLeaveList");
    }

    public boolean applyLeave(String startDate, String endDate) {
        logger.info("Apply Leave Test start");
        logger.info("Start date: "+startDate);
        logger.info("End date: "+endDate);
        _reuse.clickElement(assignLeaveBtn);
        _reuse.enterText(hintBox, page.locator(name).textContent());
        page.waitForSelector(suggestionList);
        _reuse.pressKey("ArrowDown");
        _reuse.pressKey("Enter");
        _reuse.clickElement(leaveTypeDropDown);
        _reuse.selectCustomDropDown(leaveTypeOption, "CAN - Personal");
        _reuse.clickElement(StartDate);
        _reuse.enterText(StartDate, startDate);
        _reuse.clickElement(EndDate);
        _reuse.pressKey("Control+A");
        _reuse.pressKey("Backspace");
        _reuse.enterText(EndDate, endDate);
        _reuse.clickElement(assignBtn);
        page.waitForSelector(successfullMessage);
        boolean status = page.locator(successfullMessage).isVisible();
        if(status) logger.info("Leave Assigned successfully");
        return status;
    }
}
