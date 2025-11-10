package com.qa.playwright.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.qa.playwright.pages.AmazonPages.CartPage;
import com.qa.playwright.pages.AmazonPages.HomePage;
import com.qa.playwright.pages.PracticeHomePage;
import com.qa.playwright.pages.SwagLabPages.swagLabHomePage;
import com.qa.playwright.pages.SwagLabPages.swagLabLoginPage;
import com.qa.playwright.pages.TestAutomationPages.testAutomationHomePage;
import com.qa.playwright.utilities.ReusableFunctions;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;

import com.microsoft.playwright.Page;
import com.qa.playwright.factory.PlaywrightFactory;
import com.qa.playwright.pages.LoginPage;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    PlaywrightFactory pf;
    Page page;
    protected static Properties prop;
    protected SoftAssert softAssert;

    protected static final Logger logger = Logger.getLogger(BaseTest.class);
    protected ReusableFunctions _reuse;
//    Pages
    protected PracticeHomePage homePage;
    protected LoginPage loginPage;
    protected HomePage amazonPage;
    protected CartPage amazonCartPage;
    protected swagLabLoginPage swagloginPage;
    protected swagLabHomePage swagLabHomePage;
    protected testAutomationHomePage testAutomationHomePage;

    @Parameters({ "browser" })
    @BeforeClass
    public void setup(String browserName) {
        pf = new PlaywrightFactory();

        prop = pf.init_prop();
        initLogger();

        softAssert = new SoftAssert();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }
        logger.info("Browser initialize");
        page = pf.initBrowser(prop);
        _reuse = new ReusableFunctions(logger);

        homePage = new PracticeHomePage(page);
        amazonPage = new HomePage(page, _reuse);
        swagloginPage = new swagLabLoginPage(page, _reuse);
        testAutomationHomePage = new testAutomationHomePage(page, _reuse);
    }

    @AfterClass
    public void tearDown() {
        logger.info("Closing browser");
        page.context().browser().close();
    }

    private void initLogger() {
        try {
            String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss").format(new Date());
            System.setProperty("current.date", timestamp);
            System.setProperty("projectName", prop.getProperty("projectName"));

            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("config/log4j.properties"));
            PropertyConfigurator.configure(props);

            logger.info("Log4j initialized for this run: " + timestamp);
        } catch (Exception e) {
            System.err.println("Error initializing Log4j: " + e.getMessage());
        }
    }

}