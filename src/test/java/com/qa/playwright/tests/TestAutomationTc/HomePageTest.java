package com.qa.playwright.tests.TestAutomationTc;

import com.qa.playwright.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class HomePageTest extends BaseTest {

    @Test(enabled = false)
    public void checkUrl() {
        logger.info("Checking the Url and matching with the Actual Url");
        Assert.assertEquals(testAutomationHomePage.getUrl(), prop.getProperty("url"));
        logger.info("Url Matched");
    }

    @Test(enabled = false)
    public void testRadioBtn() {
        String gender = "Male";
        logger.info("Testing Radio Button's");
        logger.info("Selecting "+gender);
        testAutomationHomePage.selectGender(gender);
        logger.info("Checking if "+gender+" is Selected or not");
        boolean status = testAutomationHomePage.verifyGenderSelected(gender);
        Assert.assertTrue(status, "Radio Button is not Selected");
        logger.info(gender+" is Selected");
    }

    @Test(enabled = false)
    public void testCheckBox() {
        List<String> itemsToSelect = Arrays.asList("monday", "tuesday", "thursday");
        logger.info("Testing Checkbox");
        logger.info("Selecting : "+ itemsToSelect);
        testAutomationHomePage.selectDays(itemsToSelect);
        boolean status = testAutomationHomePage.verifyDaysSelected(itemsToSelect);
        Assert.assertTrue(status, "Check Boxs are not selected");
        logger.info(itemsToSelect+" are selected");
    }

    @Test
    public void testWebTables() {
        String element = "Portable Charger";
        logger.info("Checking if "+element+" is present or not");
        Assert.assertTrue(testAutomationHomePage.verifyItemPresentInTable(element), "Element is not present");
        logger.info(element+" is present");
    }
}
