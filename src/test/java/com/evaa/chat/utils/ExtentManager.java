package com.evaa.chat.utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;

	public static ExtentReports getReportInstance() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";

			// ✅ Delete old report to start fresh
			File reportFile = new File(reportPath);
			if (reportFile.exists()) {
				reportFile.delete();
			}

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
			sparkReporter.config().setReportName("Evaa Automation Test Report");
			sparkReporter.config().setDocumentTitle("Test Execution Report");
			sparkReporter.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			// ✅ Add environment details
			extent.setSystemInfo("QA Engineer", "Satish Gaware");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("OS", System.getProperty("Windows 10 Pro"));
			extent.setSystemInfo("Java Version", System.getProperty("21"));
			extent.setSystemInfo("Browser", "Chrome");
		}
		return extent;
	}
}
