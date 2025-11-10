package com.qa.playwright.pages.AmazonPages;

import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;

public class CartPage extends BasePage {

    public CartPage (Page page, ReusableFunctions _reuse) {
        super(page, _reuse);
    }

//    Locators
    private String cartItems = "//ul[@data-name='Active Items']//div[@class='sc-list-item-content']";
    private String deleteBtn = "//span[@class='sc-quantity-stepper']//button[1]";

    public boolean verifyItemPresent() {
        return page.isVisible(cartItems);
    }
}
