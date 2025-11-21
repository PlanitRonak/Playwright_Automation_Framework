package com.qa.playwright.pages.SwagLabPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;

public class swagLabCartPage extends BasePage {

    public swagLabCartPage(Page page, ReusableFunctions _reuse) {
        super(page, _reuse);
    }

    String checkoutBtn = "//button[@id='checkout']";
    String firstNameField = "//input[@id='first-name']";
    String lastNameField = "//input[@id='last-name']";
    String zipField = "//input[@id='postal-code']";
    String continueBtn = "//input[@id='continue']";
    String priceTag = "//div[@class='summary_total_label']";
    String finish = "//button[@id='finish']";
    String backHome = "//button[@id='back-to-products']";

    public void checkOut(String firstName, String lastName, String zip) {
        page.locator(checkoutBtn).click();
        page.locator(firstNameField).fill(firstName);
        page.locator(lastNameField).fill(lastName);
        page.locator(zipField).fill(zip);
        page.locator(continueBtn).click();
    }

    public boolean verifyPrice(String price) {
        String priceTitle = page.locator(priceTag).textContent();
        String itemPrice = _reuse.getPrice(priceTitle);
        return itemPrice.equals(price);
    }

    public swagLabHomePage navigateToHome() {
        page.locator(finish).click();
        page.locator(backHome).click();
        return new swagLabHomePage(page, _reuse);
    }
}
