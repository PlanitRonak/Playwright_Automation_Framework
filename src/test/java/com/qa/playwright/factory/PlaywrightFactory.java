package com.qa.playwright.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {

    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties prop;

    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public Page initBrowser(Properties prop) {

        String browserName = prop.getProperty("browser").trim();
        System.out.println("browser name is : " + browserName);

        // playwright = Playwright.create();
        tlPlaywright.set(Playwright.create());

        switch (browserName.toLowerCase()) {
            case "chromium":
                tlBrowser.set(
                        getPlaywright()
                                .chromium()
                                .launch(new LaunchOptions()
                                        .setHeadless(Boolean.getBoolean(prop.getProperty("headless")))
                                        .setArgs(Arrays.asList("--start-maximized"))
                                        .setSlowMo(2000)
                                )
                );
                break;
            case "firefox":
                tlBrowser.set(
                        getPlaywright()
                                .firefox()
                                .launch(new LaunchOptions()
                                        .setHeadless(Boolean.getBoolean(prop.getProperty("headless")))
                                        .setArgs(Arrays.asList("--start-maximized"))
                                        .setSlowMo(2000)
                                )
                );
                break;
            case "safari":
                tlBrowser.set(
                        getPlaywright()
                                .webkit()
                                .launch(new LaunchOptions()
                                        .setHeadless(Boolean.getBoolean(prop.getProperty("headless")))
                                        .setArgs(Arrays.asList("--start-maximized"))
                                        .setSlowMo(2000)
                                )
                );
                break;
            case "chrome":
                tlBrowser.set(
                        getPlaywright()
                                .chromium()
                                .launch(new LaunchOptions()
                                        .setHeadless(Boolean.getBoolean(prop.getProperty("headless")))
                                        .setChannel("chrome")
                                        .setArgs(Arrays.asList("--start-maximized"))
                                        .setSlowMo(2000)
                                )
                );
                break;
            case "edge":
                tlBrowser.set(
                        getPlaywright()
                                .chromium()
                                .launch(new LaunchOptions()
                                        .setHeadless(Boolean.getBoolean(prop.getProperty("headless")))
                                        .setChannel("msedge")
                                        .setSlowMo(2000)
                                )
                );
                break;

            default:
                System.out.println("please pass the right browser name......");
                break;
        }

//      We are using browserContext to maintain isolation in parallel as well as normal testing.
//      Running multiple tests cannot interfere with each other.
//      Important for maintaining independent sessions.
        tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(null)));
        tlPage.set(getBrowserContext().newPage());
        getPage().navigate(prop.getProperty("url").trim());
        return getPage();

    }

    // this method is used to initialize the properties from config file
    public Properties init_prop() {

        try {
            FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
            prop = new Properties();
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;

    }

    // take screenshot
    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        //getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));

        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);

        return base64Path;
    }

}