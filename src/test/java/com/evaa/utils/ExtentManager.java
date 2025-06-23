package com.evaa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
            File reportFile = new File(reportPath);
            if (reportFile.exists()) {
                reportFile.delete();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("Evaa Automation Test Report");
            sparkReporter.config().setDocumentTitle("Test Execution Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("dd MMM yyyy, HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("QA Engineer", "Satish Gaware");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("OS", "Windows 10 Pro");
            extent.setSystemInfo("Java Version", "21");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }

    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void removeTest() {
        extentTest.remove();
    }
}
