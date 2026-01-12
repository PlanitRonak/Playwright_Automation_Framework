package com.qa.playwright.pages.Instax;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;

import java.util.List;

public class instaxHomePage extends BasePage {

    public instaxHomePage(Page page, ReusableFunctions _reuse, Logger logger) {
        super(page, _reuse, logger);
    }

    String searchIcon = "//i[@class='las la-search']";
    String searchInputField = "//input[@placeholder='Search for products']";
    String searchResult = "//div[contains(@class, 'product-info')]/h3";
    String homePageProductTitle = "//div[@class='img_product_block']/following-sibling::div/h3";
    String okBtn = "//button[@class='minmaxify-ok']";
    String crossBtn = "//form[@class='nt_mini_cart nt_js_cart flex column h__100 btns_cart_1']//i[@class='close_pp pegk pe-7s-close ts__03 cd']";
    String navBtn = "//ul[@class='rollover-social']/li";

    public void clickSearchIcon() {
        _reuse.clickElement(page.locator(searchIcon));
    }

    public void searchItem(String productName) {
        _reuse.enterText(searchInputField, "Mini");
        _reuse.pressKey("Enter");
    }

    public boolean verifySearch(String productName) {
        return _reuse.verifySearch(searchResult, productName);
    }

    public void addItemToCart(List<String> Items) {
        Locator homePageItems = page.locator(homePageProductTitle);
        Items.forEach(item -> {
            for(int i = 0 ; i < homePageItems.count() ; i++) {
                if(homePageItems.nth(i).textContent().equals(item)) {
                    homePageItems.nth(i).locator("xpath=following-sibling::div[@class='addtocart_box_custom']//button").click();
                    if(page.locator(okBtn).isVisible()) {
                        _reuse.clickElement(page.locator(okBtn));
                        _reuse.clickElement(page.locator(crossBtn));
                    } else {
                        _reuse.clickElement(page.locator(crossBtn));
                    }
                }
            }
        });
        logger.info("Items added");
    }

    public boolean hoverOverElements() {
        boolean flag = true;
        try{
            Locator navBts = page.locator(navBtn);
            _reuse.scrolltoElement(navBtn);
            for(int i = 0 ; i < navBts.count() ; i++) {
                logger.info("Hovered over "+(i+1)+" element.");
                navBts.nth(i).hover();
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}