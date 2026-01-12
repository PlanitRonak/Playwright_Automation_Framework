package com.qa.playwright.tests.InstaxTc;

import com.qa.playwright.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class HomePageTest extends BaseTest {
    @Test(priority = 1, enabled = true)
    public void testSearch() {
        logger.info("Search Test Started.");
        instaxHomePage.clickSearchIcon();
        instaxHomePage.searchItem("Mini");
        Assert.assertTrue(instaxHomePage.verifySearch("Mini"));
        logger.info("Search Test Ended.");
    }

    @Test(priority = 2, enabled = true)
    public void testAddToCart() {
        logger.info("Add to Cart Test Started.");
        instaxHomePage.addItemToCart(Arrays.asList(new String[]{"Instax WIDE Evo Premium Edition", "INSTAX Mini 99"}));
        logger.info("Add to Cart test Ended.");
    }

    @Test(priority = 3, enabled = true)
    public void testHover() {
        logger.info("Hover test Started");
        Assert.assertTrue(instaxHomePage.hoverOverElements());
        logger.info("Hover Over test ended");
    }
}
