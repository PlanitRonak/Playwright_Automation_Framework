package com.qa.playwright.tests.AmazonTc;

import com.qa.playwright.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {
    @Test
    public void verifyCartDisplayItem() {
        amazonPage.addToCart();
        amazonCartPage = amazonPage.navigateToCart();
        Assert.assertTrue(amazonCartPage.verifyItemPresent());
    }
}
