package com.qa.playwright.base;

import com.microsoft.playwright.Page;
import com.qa.playwright.utilities.ReusableFunctions;

public class BasePage {
    protected Page page;
    protected ReusableFunctions _reuse;

    public BasePage(Page page, ReusableFunctions _reuse) {
        this.page = page;
        this._reuse = _reuse;
    }
}
