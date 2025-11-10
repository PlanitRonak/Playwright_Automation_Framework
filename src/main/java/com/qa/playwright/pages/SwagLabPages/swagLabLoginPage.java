package com.qa.playwright.pages.SwagLabPages;

import com.microsoft.playwright.Page;
import com.qa.playwright.base.BasePage;
import com.qa.playwright.utilities.ReusableFunctions;

public class swagLabLoginPage extends BasePage {

    public swagLabLoginPage(Page page, ReusableFunctions _reuse) {
        super(page, _reuse);
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

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public swagLabHomePage navigateToHomePage() {
        return new swagLabHomePage(page, _reuse);
    }
}
