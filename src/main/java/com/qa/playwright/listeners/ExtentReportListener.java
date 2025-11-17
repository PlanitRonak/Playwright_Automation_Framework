package com.qa.playwright.listeners;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import static com.qa.playwright.factory.PlaywrightFactory.takeScreenshot;

public class ExtentReportListener implements ITestListener {
    Properties property;

    public ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    private ThreadLocal<ExtentReports> extent = new ThreadLocal<ExtentReports>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
        FileInputStream ip = null;
        try {
            ip = new FileInputStream("./src/test/resources/config/config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        property = new Properties();
        try {
            property.load(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String projectName = property.getProperty("projectName");

        String OUTPUT_FOLDER = "./Reports/"+projectName+"/";

        Path path = Paths.get(OUTPUT_FOLDER);
        // if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // fail to create directory
                e.printStackTrace();
            }
        }

//        extent = new ExtentReports();
        extent.set(new ExtentReports());
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss").format(new Date());
        String reportFile = OUTPUT_FOLDER + projectName + "_" + timestamp + "_" + Thread.currentThread().getId() + ".html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportFile);
        reporter.config().setReportName(projectName+" Automation Test Results");
        extent.get().attachReporter(reporter);
        extent.get().setSystemInfo("System", "WINDOWS");
        String browser = context.getCurrentXmlTest().getParameter("browser");
        extent.get().setSystemInfo("Broswer", browser);
        extent.get().setSystemInfo("Author", "Planit Testing");
//		extentReports.setSystemInfo("Build", "1.1");
        extent.get().setSystemInfo("Team", "QA");
        extent.get().setSystemInfo("Customer Name", projectName);
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extent.get().flush();
        test.remove();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println(methodName + " started!");
        ExtentTest extentTest = extent.get().createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        /*
         * methodName = StringUtils.capitalize(StringUtils.join(StringUtils.
         * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
         */
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("Test passed");
//        test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(), result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}