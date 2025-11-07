package com.qa.playwright.pages.AmazonPages;

import com.microsoft.playwright.Page;

public class CartPage {
    private Page page;

    public CartPage (Page page) {
        this.page = page;
    }

//    Locators
    private String cartItems = "//ul[@data-name='Active Items']//div[@class='sc-list-item-content']";
    private String deleteBtn = "//span[@class='sc-quantity-stepper']//button[1]";

    public boolean verifyItemPresent() {
        return page.isVisible(cartItems);
    }
}
