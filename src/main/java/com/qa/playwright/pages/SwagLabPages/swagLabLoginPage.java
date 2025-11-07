package com.qa.playwright.pages.SwagLabPages;

import com.microsoft.playwright.Page;

public class swagLabLoginPage {
    private Page page;
    public swagLabLoginPage(Page page) {
        this.page = page;
    }

    String userNameField = "//input[@id='user-name']";
    String passwordField = "//input[@id='password']";
    String loginBtn = "//input[@id='login-button']";

    public void enterUsername(String username) {
        page.fill(userNameField, username);
    }

    public void enterPassword(String password) {
        page.fill(passwordField, password);
    }

    public void clickLogin() {
        page.click(loginBtn);
    }

    public boolean verifyLogin() {
        return page.url().equals("https://www.saucedemo.com/inventory.html");
    }

    public boolean verifyStatus(String status) {
        return status.equals("valid");
    }

    public swagLabHomePage navigateToHomePage() {
        return new swagLabHomePage(page);
    }
}
