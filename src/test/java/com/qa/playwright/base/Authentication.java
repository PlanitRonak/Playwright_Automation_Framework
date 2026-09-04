package com.qa.playwright.base;

import com.microsoft.playwright.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Authentication extends BaseTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
            );

            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://www.saucedemo.com/");
            page.getByPlaceholder("Username").fill("standard_user");
            page.getByPlaceholder("Password").fill("secret_sauce");
            page.click("//input[@id='login-button']");

            Path authpath = Paths.get(".auth/user.json");
            Files.createDirectories(authpath.getParent());
            page.waitForTimeout(1000);
            context.storageState(
                    new BrowserContext.StorageStateOptions()
                            .setPath(authpath)
            );

            browser.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
