package com.qa.playwright.pages.TestAutomationPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;

import java.util.List;

public class testAutomationHomePage extends BasePage {

    public testAutomationHomePage(Page page, ReusableFunctions _reuse) {
        super(page, _reuse);
    }

    String radioBtns = "//input[@type='radio']";
    String checkBoxs = "//label[normalize-space()='Days:']/following-sibling::div/input[@type='checkbox']";
    String pageNationBtn = "//ul[@id='pagination']/li";
    String table = "//table[@id='productTable']";
    String tableRows = "//table[@id='productTable']/tbody/tr";

    public String getUrl() {
        return page.url();
    }

    public void selectGender(String value) {
        _reuse.selectRadioBtnValue(page, radioBtns, value);
    }

    public boolean verifyGenderSelected(String value) {
        return _reuse.isRadioBtnSelected(page, radioBtns, value);
    }

    public void selectDays(List<String> values) {
        _reuse.selectCheckBoxs(page, checkBoxs, values);
    }

    public boolean verifyDaysSelected(List<String> values) {
        return _reuse.isCheckBoxSelected(page, checkBoxs, values);
    }

    public boolean verifyItemPresentInTable(String value) {
        boolean flag = false;
        List<String> values = _reuse.getCellValues(page, table, 2, pageNationBtn);
        for(String i : values) {
            if(i.equalsIgnoreCase(value)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
