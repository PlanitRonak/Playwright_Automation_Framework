package com.qa.playwright.utilities;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReusableFunctions {
    Logger logger;

    public ReusableFunctions(Logger logger) {
        this.logger = logger;
    }

    public void selectOption(Page page, String dropDown, String value) {
        page.locator(dropDown).selectOption(new SelectOption().setLabel(value));
    }

    public String getPrice(String input) {
        return input.replaceAll("[^\\d.]", "");
    }

    public void hoverOver(Page page, String locator) {
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

    public void keyPress(Page page, String key) {
        page.keyboard().press(key);
    }

    public void sendKeys(Page page, String locator, String value) {
        try {
            page.click(locator);
            page.fill(locator, value);
        } catch (Exception e) {
            System.out.println("Error While Sending Keys");
        }
    }

    public String getCurrentTabTitle(Page page) {
        return page.title();
    }

    public void closeCurrentTab(Page page) {
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

    public boolean verifyElementsLocated (Page page, String locator) {
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

    public void enterText (Page page, String locator,String Value) {
        try{
            page.fill(locator, Value);
        } catch (Exception e) {
            System.out.println("Error While Entering Text");
        }
    }

    public void selectRadioBtnValue(Page page, String locator, String value) {
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

    public boolean isRadioBtnSelected(Page page, String locator, String value) {
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

    public void selectCheckBoxs(Page page, String locator, List<String> values) {
        try {
            logger.info("Locating Check Boxs");
            Locator checkBox = page.locator(locator);
            values.forEach(value -> {
                for (int i = 0; i < checkBox.count(); i++) {
                    String Value = checkBox.nth(i).getAttribute("value");
                    if (Value.equalsIgnoreCase(value)) {
                        logger.info("Selecting "+Value);
                        checkBox.nth(i).click();
                        break;
                    }
                }
            });
        } catch (Exception e) {
            logger.error("Failed to Locate Check Boxs");
        }
    }

    public boolean isCheckBoxSelected(Page page, String locator, List<String> values) {
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

    public List<String> getCellValues(Page page, String locator, int cellNo) {
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

    public List<String> getCellValues(Page page, String locator, int cellNo, String pageNation) {
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
}
