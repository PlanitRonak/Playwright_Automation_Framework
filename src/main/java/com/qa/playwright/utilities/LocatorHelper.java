package com.qa.playwright.utilities;
import com.microsoft.playwright.*;
public class LocatorHelper {
    private final Page page;

    public LocatorHelper(Page page) {
        this.page = page;
    }

    public Locator getElement(String... locators) {
        for (String locator : locators) {
            try {
                Locator element = page.locator(locator);
                if (element.count() > 0) {
                    System.out.println("Using Locator: " + locator);
                    return element;
                }
            } catch (Exception e) {
                System.out.println("Failed Locator: " + locator);
            }
        }
        throw new RuntimeException("No valid locator found");
    }
}