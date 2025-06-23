package com.evaa.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.evaa.baseclass.EvvaChatBaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    private static final Logger log = LogManager.getLogger(Listener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.setTest(ExtentManager.getReportInstance().createTest(testName));
        log.info("🟡 Test Started: " + testName);
        System.out.print( testName+": ");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.getTest().pass("Test Passed: " + testName);
        System.out.println("\u001B[32mPASS\u001B[0m ");  // Green
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(EvvaChatBaseClass.getDriver(), testName);
            ExtentManager.getTest().fail(
                "Test Failed: " + testName + "<br>Reason: " + result.getThrowable().getMessage(),
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        } catch (Exception e) {
            ExtentManager.getTest().fail("Test Failed: " + testName + " (screenshot capture failed)");
        }

        System.out.println("\u001B[31mFAIL\u001B[0m ");  // Red
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Check if it was skipped because max retry failed
        Throwable throwable = result.getThrowable();
        if (throwable != null && throwable.getMessage() != null && throwable.getMessage().contains("SKIPPED")) {
            // Retry skip, avoid logging
            return;
        }

        String testName = result.getMethod().getMethodName();
        ExtentManager.getTest().skip("Test Skipped: " + testName);
        System.out.println("\u001B[33m⚠️ SKIPPED\u001B[0m ");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getReportInstance().flush();
        log.info("📄 Extent Report flushed after all tests.");
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("📋 Test Suite Started: " + context.getName());
    }
}
