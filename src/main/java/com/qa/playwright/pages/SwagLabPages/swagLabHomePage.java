package com.qa.playwright.pages.SwagLabPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;

import java.util.List;

public class swagLabHomePage extends BasePage {

    public swagLabHomePage(Page page, ReusableFunctions _reuse) {
        super(page, _reuse);
    }

    String menuBtn = "//button[@id='react-burger-menu-btn']";
    String logoutBtn = "//a[@id='logout_sidebar_link']";
    String filterDropDown = "//select[@class='product_sort_container']";
    String itemPrices = "//div[@class='pricebar']/div";
    String titleOfProduct = "//div[@class='inventory_item'][1]/div[2]//div[@class='inventory_item_name ']";
    String titlesOfProducts = "//div[@class='inventory_item']/div[2]//div[@class='inventory_item_name ']";
    String cartBtn = "//a[@class='shopping_cart_link']";

    public void selectOption() {
        _reuse.selectOption(filterDropDown, "Price (low to high)");
    }

    public boolean verifyFilter() {
        boolean flag = true;
        if(_reuse.verifyElementsLocated(itemPrices)){
            Locator prices = page.locator(itemPrices);
            List<String> list = prices.allTextContents();
            String priceOfFirst =_reuse.getPrice(list.get(0));
            for(int i = 1 ; i < list.size() ; i++) {
                String price = _reuse.getPrice(list.get(i));
                if(Double.parseDouble(priceOfFirst) <= Double.parseDouble(price)) {
                    flag = true;
                    priceOfFirst = price;
                } else {
                    flag = false;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public boolean verifyHover() throws InterruptedException {
        _reuse.hoverOver(titleOfProduct);
        Thread.sleep(2000);
        return true;
    }

    public void logOut() {
        page.click(menuBtn);
        page.click(logoutBtn);
    }

    public void addToCart(String[] products) throws InterruptedException {
        Locator titles = page.locator(titlesOfProducts);
        for (String product : products) {
            for (int i = 0 ; i < titles.count() ; i++) {
                if(titles.nth(i).textContent().equalsIgnoreCase(product)) {
                    titles.nth(i).locator("//parent::a/parent::div/following-sibling::div//button").click();
                    System.out.println(product);
                }
            }
        }
    }

    public swagLabCartPage navigateToCartPage() {
        page.locator(cartBtn).click();
        return new swagLabCartPage(page, _reuse);
    }
}
