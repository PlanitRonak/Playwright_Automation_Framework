package com.qa.playwright.utilities;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.qa.playwright.pages.SwagLabPages.swagLabCartPage;
import com.qa.playwright.pages.SwagLabPages.swagLabHomePage;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReusableFunctions {
    Page page;
    Logger logger;

    public ReusableFunctions(Page page, Logger logger) {
        this.page = page;
        this.logger = logger;
    }

    public void clickElement(Locator element){
        if(element.isDisabled()) {
            logger.error("Element not clickable");
            Assert.fail(element+" not Clickable");
        } else {
            logger.info("Element Clicked");
            element.click();
        }
    }

    public void clickElement(String element){
        page.waitForSelector(element);
        Locator locatedElement = page.locator(element);
        if(locatedElement.isDisabled()) {
            logger.error("Element not clickable");
            Assert.fail(element+" not Clickable");
        } else {
            logger.info("Element Clicked");
            locatedElement.click();
        }
    }

    public void selectOption(String dropDown, String value) {
        page.locator(dropDown).selectOption(new SelectOption().setLabel(value));
    }

    public void selectCustomDropDown(String optionsLocator, String value) {
        page.waitForSelector(optionsLocator);
        Locator options = page.locator(optionsLocator);
        for (int i = 0 ; i < options.count() ; i++) {
            if(options.nth(i).textContent().equals(value)){
                clickElement(options.nth(i));
                break;
            }
        }
    }

    public String getPrice(String input) {
        return input.replaceAll("[^\\d.]", "");
    }

    public void hoverOver(String locator) {
        Locator element = page.locator(locator);
        element.hover();
    }

    public String OTPExtract(String text) {
        String otp = "";
        // Regex to match 6 digits
        String regex = "\\b\\d{6}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // Find and print the 6-digit OTP
        if (matcher.find()) {
            otp = matcher.group();
            System.out.println("Extracted OTP: " + otp);
        } else {
            System.out.println("No OTP found in the text.");
        }
        return otp;
    }

    public void keyPress(String key) {
        page.keyboard().press(key);
    }

    /**
     * Presses a key on the keyboard with logging and error handling.
     * This is a reusable generic function to simulate pressing any key from the keyboard.
     * @param key The key to press (e.g., "Enter", "a", "Control+a", "Tab")
     */
    public void pressKey(String key) {
        try {
            logger.info("Pressing key: " + key);
            page.keyboard().press(key);
            logger.info("Successfully pressed key: " + key);
        } catch (Exception e) {
            logger.error("Error while pressing key: " + key + ". Error: " + e.getMessage());
            throw new RuntimeException("Failed to press key: " + key, e);
        }
    }

    public void sendKeys(String locator, String value) {
        try {
            page.click(locator);
            page.fill(locator, value);
        } catch (Exception e) {
            System.out.println("Error While Sending Keys");
        }
    }

    public String getCurrentTabTitle() {
        return page.title();
    }

    public void closeCurrentTab() {
        // Close the current tab
        page.close();
        // Get all open tabs (pages) from the same context
        List<Page> pages = page.context().pages();
        // Switch to the last tab if it exists
        if (!pages.isEmpty()) {
            Page lastPage = pages.get(pages.size() - 1);
            lastPage.bringToFront();   // Brings the tab to focus
        }
    }

    public Page openAndSwitchToNewTab(Page currentPage, String url) {
        // Create a listener to capture the new tab (Page)
        BrowserContext context = currentPage.context();
        // Wait for the new page (tab) to open
        Page newPage = context.waitForPage(() -> {
            // Open a new tab via JavaScript or Ctrl+T-like behavior
            currentPage.evaluate("window.open('" + url + "', '_blank')");
        });
        // Bring the new tab to the front (focus)
        newPage.bringToFront();
        // Wait for navigation to complete
        newPage.waitForLoadState(LoadState.LOAD);
        // (Optional) Verify the URL
        if (!newPage.url().equals(url)) {
            newPage.navigate(url);
            newPage.waitForURL(url);
        }
        return newPage;
    }

    public boolean verifyElementsLocated (String locator) {
        boolean flag = true;
        Locator elements = page.locator(locator);
        int count = elements.count();
        for (int i = 0 ; i < count ; i++) {
            if(elements.nth(i).isVisible()){
                System.out.println("Element is Visible : "+elements.nth(i).textContent());
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public void enterText (String locator,String Value) {
        try{
            logger.info("Entering Text");
            page.fill(locator, Value);
        } catch (Exception e) {
            logger.error("Error while entering text");
            Assert.fail("Error while entering text");
        }
    }

    public void enterText (LocatorHelper locatorHelper, String[] locators, String value) {
        try{
            logger.info("Entering Text");
            locatorHelper.getElement(locators).fill(value);
        } catch (Exception e) {
            logger.error("Error while entering text");
            Assert.fail("Error while entering text");
        }
    }

    public void selectRadioBtnValue(String locator, String value) {
        try{
            logger.info("Locating Radio Button");
            Locator radioBtns = page.locator(locator);
            for (int i = 0 ; i < radioBtns.count() ; i++) {
                String attributeValue = radioBtns.nth(i).getAttribute("value");
                if(attributeValue.equalsIgnoreCase(value)) {
                    radioBtns.nth(i).click();
                }
            }
        } catch (Exception e) {
            logger.error("Failed to Locate Radio Btn");
        }
    }

    public boolean isRadioBtnSelected(String locator, String value) {
        boolean flag = false;
        try{
            logger.info("Locating Radio Button");
            Locator radioBtns = page.locator(locator);
            for (int i = 0 ; i < radioBtns.count() ; i++) {
                String attributeValue = radioBtns.nth(i).getAttribute("value");
                logger.info("Radio Button with Value "+attributeValue+" is Present");
                if(attributeValue.equalsIgnoreCase(value)) {
                    logger.info("Clicking on "+attributeValue);
                    flag = radioBtns.nth(i).isChecked();
                }
            }
        } catch (Exception e) {
            logger.error("Failed to Locate Radio Btn");
        }
        return flag;
    }

    public void selectCheckBoxs(String locator, List<String> values) {
        try {
            logger.info("Locating Check Boxs");
            Locator checkBox = page.locator(locator);
            values.forEach(value -> {
                for (int i = 0; i < checkBox.count(); i++) {
                    String Value = checkBox.nth(i).getAttribute("value");
                    if (Value.equalsIgnoreCase(value)) {
                        logger.info("Selecting "+Value);
                        checkBox.nth(i).click();
                        if(!checkBox.nth(i).isChecked()) {
                            logger.error("Failed to Check the checkbox");
                            Assert.fail("Failed to Select the checkbox");
                        }
                        break;
                    }
                }
            });
        } catch (Exception e) {
            logger.error("Failed to Locate Check Boxs");
        }
    }

    public boolean isCheckBoxSelected(String locator, List<String> values) {
        boolean flag = false;
        try {
            logger.info("Locating Check Boxs");
            Locator checkBox = page.locator(locator);
            for (String value : values) {
                for (int j = 0; j < checkBox.count(); j++) {
                    String attValue = checkBox.nth(j).getAttribute("value");
                    if (attValue.equalsIgnoreCase(value)) {
                        logger.info("Checking if " + attValue + " is Selected or not");
                        flag = checkBox.nth(j).isChecked();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to Locate Check Boxs");
        }
        return flag;
    }

    public List<String> getCellValues(String locator, int cellNo) {
        List<String> values = new ArrayList<>();
        try{
            Locator table = page.locator(locator);
            int rows = table.locator("//tr").count();
            for (int i = 1 ; i < rows ; i++) {
                values.add(table.locator("//tbody/tr["+i+"]/td["+cellNo+"]").textContent());
            }
        } catch (Exception e) {
            logger.error("Error while finding value xpath might be incorrect.");
        }
        return values;
    }

    public List<String> getCellValues(String locator, int cellNo, String pageNation) {
        logger.info("Capture Values of Table");
        Locator pageNationBtn = page.locator(pageNation);
        List<String> values = new ArrayList<>();
        logger.info("Scanning the Table to get Values of "+cellNo+" Column.");
        for (int i = 0 ; i < pageNationBtn.count() ; i++) {
            logger.info("Getting Values from Page "+(i+1));
            pageNationBtn.nth(i).click();
            try{
                Locator table = page.locator(locator);
                int rows = table.locator("//tr").count();
                for (int j = 1 ; j < rows ; j++) {
                    logger.info("Getting value of row "+j+" and cell "+cellNo);
                    values.add(table.locator("//tbody/tr["+j+"]/td["+cellNo+"]").textContent());
                }
            } catch (Exception e) {
                logger.error("Error while finding value xpath might be incorrect.");
            }
        }
        logger.info("Scanning Complete");
        return values;
    }

    public void scrolltoElement(String element) {
        page.waitForSelector(element);
        ElementHandle handle = page.querySelector(element);
        if (handle != null) {
            // runs in page context: smooth = false for predictable test behavior
            page.evaluate("el => el.scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'})", handle);
        }
    }

    public void scrollToBottom() {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTop() {
        page.evaluate("window.scrollTo(0, 0)");
    }

    public void validateCart(swagLabHomePage HomePage, swagLabCartPage CartPage, String[] products, String firstName, String lastName, String zip, String total) throws InterruptedException {
        logger.info("Add to Cart Test Start");
        logger.info("Adding Items to Cart");
        HomePage.addToCart(products);
        logger.info("Navigating to Cart Page");
        CartPage = HomePage.navigateToCartPage();
        logger.info("CheckOut with "+firstName+" "+lastName+" "+zip);
        CartPage.checkOut(firstName, lastName, zip);
        logger.info("Verifying if the price is "+total+" or not.");
        Assert.assertEquals(CartPage.getPrice(), total, "Price is Different");
        HomePage = CartPage.navigateToHome();
        logger.info("Add to Cart test Ended");
    }

    public boolean verifySearch(String Locator, String productName) {
        Locator elements = page.locator(Locator);
        boolean flag = false;
        logger.info("Verifying Elements");
        for (int i = 0 ; i < elements.count() ; i++) {
            if (elements.nth(i).textContent().contains(productName)) flag = true;
        }
        return flag;
    }

    public void addToCart(List<String> Items, Locator homePageItems) {
        Items.forEach(item -> {
            for(int i = 0 ; i < homePageItems.count() ; i++) {
                if(homePageItems.nth(i).textContent().equals(item)) {
                    homePageItems.nth(i).locator("xpath=following-sibling::div[@class='addtocart_box_custom']//button").click();
                }
            }
        });
    }

    public boolean verifyLink(String anchorTagLocator) {
        logger.info("Verifying Links");
        page.waitForSelector(anchorTagLocator);
        Locator linkElements = page.locator(anchorTagLocator);
        for (int i = 0 ; i < linkElements.count() ; i++) {
            String href = linkElements.nth(i).getAttribute("href");
            if (href != null && !href.isEmpty()) {
                try {
                    // Create API request context
                    APIRequestContext request = Playwright.create().request().newContext(new APIRequest.NewContextOptions()
                            .setExtraHTTPHeaders(
                                    Map.of(
                                            "User-Agent",
                                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120",
                                            "Accept", "*/*",
                                            "Accept-Language", "en-US,en;q=0.9"
                                    )
                            )

                    );
                    APIResponse response = request.get(href);
                    int statusCode = response.status();
                    if(statusCode == 999) {
                        logger.info("Blocked (999): " + href + " | Status: " + statusCode);
                        continue;
                    } else if (statusCode >= 400) {
                        logger.info("Broken link: " + href + " | Status: " + statusCode);
                    } else {
                        logger.info("Valid link: " + href + " | Status: " + statusCode);
                    }
                    request.dispose();
                } catch (Exception e) {
                    logger.error("Error checking link: " + href + " " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
}
