package com.qa.playwright.pages.SwagLabPages;

import com.microsoft.playwright.Page;

public class swagLabHomePage {
    private Page page;

    public swagLabHomePage(Page page) {
        this.page = page;
    }

    String menuBtn = "//button[@id='react-burger-menu-btn']";
    String logoutBtn = "//a[@id='logout_sidebar_link']";

    public void logOut() {
        page.click(menuBtn);
        page.click(logoutBtn);
    }
}
