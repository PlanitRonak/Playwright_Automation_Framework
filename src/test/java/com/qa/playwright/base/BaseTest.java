package com.qa.playwright.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.qa.playwright.pages.AmazonPages.CartPage;
import com.qa.playwright.pages.AmazonPages.HomePage;
import com.qa.playwright.pages.Instax.instaxHomePage;
import com.qa.playwright.pages.OrangeHRMPages.orangeHrmHomePage;
import com.qa.playwright.pages.OrangeHRMPages.orangeHrmLeavePage;
import com.qa.playwright.pages.OrangeHRMPages.orangeHrmLoginPage;
import com.qa.playwright.pages.PracticeHomePage;
import com.qa.playwright.pages.SwagLabPages.swagLabCartPage;
import com.qa.playwright.pages.SwagLabPages.swagLabHomePage;
import com.qa.playwright.pages.SwagLabPages.swagLabLoginPage;
import com.qa.playwright.pages.TestAutomationPages.testAutomationHomePage;
import com.qa.playwright.utilities.LocatorHelper;
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
    protected Properties prop;
    protected SoftAssert softAssert;

    protected static final Logger logger = Logger.getLogger(BaseTest.class);
    protected LocatorHelper locatorHelper;
    protected ReusableFunctions _reuse;
//    Pages
    protected PracticeHomePage homePage;
    protected LoginPage loginPage;
    protected HomePage amazonPage;
    protected CartPage amazonCartPage;
    protected swagLabLoginPage swagloginPage;
    protected swagLabHomePage swagLabHomePage;
    protected swagLabCartPage swagLabCartPage;
    protected testAutomationHomePage testAutomationHomePage;
    protected instaxHomePage instaxHomePage;
    protected orangeHrmLoginPage orangeHrmLoginPage;
    protected orangeHrmHomePage orangeHrmHomePage;
    protected orangeHrmLeavePage orangeHrmLeavePage;

    @Parameters({ "browser" })
    @BeforeMethod(alwaysRun = true)
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
        locatorHelper = new LocatorHelper(page);
        _reuse = new ReusableFunctions(page, logger);

//      Optimization you can create a pageobjectmanager class to keep this method clean
//        PageObjectManager pom = new PageObjectManager(page);
//        pom.getLoginPage().login();
//        pom.getHomePage().verifyDashboard();

//        the .getLoginPage() will return the LoginPage Object.

        homePage = new PracticeHomePage(page);
        amazonPage = new HomePage(page, _reuse, logger, locatorHelper);
        swagloginPage = new swagLabLoginPage(page, _reuse, logger, locatorHelper);
        testAutomationHomePage = new testAutomationHomePage(page, _reuse, logger, locatorHelper);
        instaxHomePage = new instaxHomePage(page, _reuse, logger, locatorHelper);
        orangeHrmLoginPage = new orangeHrmLoginPage(page, _reuse, logger, locatorHelper);
    }

    @AfterMethod(alwaysRun = true)
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