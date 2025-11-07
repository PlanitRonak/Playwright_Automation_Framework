package com.qa.playwright.tests;

import com.qa.playwright.utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.constants.AppConstants;

public class HomePageTest extends BaseTest {

    @Test(enabled = false)
    public void homePageTitleTest() {
        String actualTitle = homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
    }

    @Test(enabled = false)
    public void homePageURLTest() {
        String actualURL = homePage.getHomePageURL();
        Assert.assertEquals(actualURL, prop.getProperty("url"));
    }

    @Test(dataProvider = "getProductData", dataProviderClass = DataProviders.class,enabled = true)
    public void searchTest(String productName) throws InterruptedException {
        Thread.sleep(5000);
        String actualSearchHeader = homePage.doSearch(productName);
        Assert.assertEquals(actualSearchHeader, "Search - " + productName);
    }

}
