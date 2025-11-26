package com.qa.playwright.pages.AmazonPages;

import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;

public class HomePage {
    private Page page;
    private ReusableFunctions _reuse;
    private Logger logger;
    public HomePage(Page page, ReusableFunctions _reuse, Logger logger) {
//        super(page, _reuse);
        this.page=page;
        this._reuse=_reuse;
        this.logger = logger;
    }

//    Locators
    private String searchBar = "//input[@id='twotabsearchtextbox']";
    private String searchIcon = "//input[@id='nav-search-submit-button']";
    private String addToCartBtn = "//div[@role='listitem'][1]//button";
    private String cartIconNav = "//a[@id='nav-cart']";
    private String cartCount = "//span[@id='nav-cart-count']";


    public void addToCart() {
//        if(page.locator())
//        page.click();
        page.fill(searchBar, "imac");
        page.click(searchIcon);
        page.click(addToCartBtn);
    }

    public boolean verifyItemAdded() throws InterruptedException {
        Thread.sleep(2000);
        return page.textContent(cartCount).equals("1");
    }

    public CartPage navigateToCart() {
        page.click(cartIconNav);
        return new CartPage(page, _reuse, logger);
    }
}
