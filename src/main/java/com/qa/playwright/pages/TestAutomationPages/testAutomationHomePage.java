package com.qa.playwright.pages.TestAutomationPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;

import java.util.List;

public class testAutomationHomePage extends BasePage {

    public testAutomationHomePage(Page page, ReusableFunctions _reuse, Logger logger) {
        super(page, _reuse, logger);
    }

    String radioBtns = "//input[@type='radio']";
    String checkBoxs = "//label[normalize-space()='Days:']/following-sibling::div/input[@type='checkbox']";
    String pageNationBtn = "//ul[@id='pagination']/li";
    String table = "//table[@id='productTable']";
    String tableRows = "//table[@id='productTable']/tbody/tr";
    String formElement = "//h2[normalize-space()='Form']";

    public String getUrl() {
        return page.url();
    }

    public void selectGender(String value) {
        _reuse.selectRadioBtnValue(radioBtns, value);
    }

    public boolean verifyGenderSelected(String value) {
        return _reuse.isRadioBtnSelected(radioBtns, value);
    }

    public void selectDays(List<String> values) {
        _reuse.selectCheckBoxs(checkBoxs, values);
    }

    public boolean verifyDaysSelected(List<String> values) {
        return _reuse.isCheckBoxSelected(checkBoxs, values);
    }

    public boolean verifyItemPresentInTable(String value) {
        boolean flag = false;
        List<String> values = _reuse.getCellValues(table, 2, pageNationBtn);
        for(String i : values) {
            if(i.equalsIgnoreCase(value)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void scrollingIntoElement() {
//        _reuse.scrolltoElement(page, formElement);
        _reuse.scrollToBottom();
        _reuse.scrollToTop();
    }
}
