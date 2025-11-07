package com.qa.playwright.pages.AmazonPages;

import com.microsoft.playwright.Page;

public class HomePage {

    private Page page;

    public HomePage(Page page) {
        this.page = page;
    }

//    Locators
    private String searchBar = "//input[@id='twotabsearchtextbox']";
    private String searchIcon = "//input[@id='nav-search-submit-button']";
    private String addToCartBtn = "//div[@role='listitem'][1]//button";
    private String cartIconNav = "//a[@id='nav-cart']";
    private String cartCount = "//span[@id='nav-cart-count']";

    public void addToCart() {
        page.fill(searchBar, "imac");
        page.click(searchIcon);
        page.click(addToCartBtn);
    }

    public boolean verifyItemAdded() {
        return page.textContent(cartCount).equals("1");
    }

    public CartPage navigateToCart() {
        page.click(cartIconNav);
        return new CartPage(page);
    }
}
