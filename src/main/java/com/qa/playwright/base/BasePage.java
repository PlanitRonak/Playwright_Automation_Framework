package com.qa.playwright.base;

import com.microsoft.playwright.Page;
import com.qa.playwright.utilities.ReusableFunctions;

import org.apache.log4j.Logger;

public class BasePage {
    protected Page page;
    protected ReusableFunctions _reuse;
    protected Logger logger;

    public BasePage(Page page, ReusableFunctions _reuse, Logger logger) {
        this.page = page;
        this._reuse = _reuse;
        this.logger = logger;
    }
}
