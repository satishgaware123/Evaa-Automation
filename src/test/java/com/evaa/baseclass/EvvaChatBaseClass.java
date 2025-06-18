package com.evaa.baseclass;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.evaa.chatbot.pom.BookAppointment;
import com.evaa.utils.ConfigReader;
import com.evaa.utils.ExtentManager;
import com.evaa.utils.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EvvaChatBaseClass {

	protected WebDriver driver;
	protected BookAppointment pom;
	protected WebDriverWait wait;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static ConfigReader config;

	private static final Logger log = LogManager.getLogger(EvvaChatBaseClass.class);
	private static final String LOG_FILE_PATH = "logs/test-execution.log";

	// User Details
	protected String userName;
	protected String userPassword;
	protected String dob;
	protected String phone;
	protected String email;
	protected String botUrl;
	protected String maximeyesURL;
	protected String maxUserName;
	protected String maxUserPass;
	protected String adminURL;
	protected String URL;

	@BeforeSuite
	public void cleanScreenshotFolder() {
		String screenshotPath = System.getProperty("user.dir") + File.separator + "screenshots";
		File folder = new File(screenshotPath);
		if (folder.exists() && folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				if (file.isFile()) {
					file.delete();
				}
			}
			System.out.println("All screenshots deleted.");
		} else {
			System.out.println("Screenshot folder does not exist.");
		}

		String AllurReport = System.getProperty("user.dir") + File.separator + "allure-results";
		File folder2 = new File(AllurReport);

		if (folder2.exists() && folder2.isDirectory()) {
			for (File file1 : folder2.listFiles()) {
				if (file1.isFile()) {
					file1.delete();
				}
			}
			System.out.println("All old Report are deleted.");
		} else {
			System.out.println(" Report folder does not exist.");
		}

	}

	@BeforeClass
	public void startBrowser() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		boolean isHeadless = System.getProperty("headless", "false").equalsIgnoreCase("true");

		if (isHeadless) {
			options.addArguments("--headless=new");
			options.addArguments("--disable-gpu");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			log.info("🚀 Running tests in HEADLESS mode");
		} else {
			log.info("🖥 Running tests in NORMAL mode");
		}

		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);

		if (!isHeadless) {
			driver.manage().window().maximize();
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		pom = new BookAppointment(driver);
		config = new ConfigReader();
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		// Load config values
		userName = config.getProperty("Username");
		userPassword = config.getProperty("UserPassword");
		dob = config.getProperty("dob");
		phone = config.getProperty("phone");
		email = config.getProperty("email");
		botUrl = config.getProperty("botUrl");
		maximeyesURL = config.getProperty("maximeyesURL");
		maxUserName = config.getProperty("MaxUserName");
		maxUserPass = config.getProperty("MaxUserPass"); 
		adminURL = config.getProperty("adminURL");
		URL = config.getProperty("URL");

		extent = ExtentManager.getReportInstance();
	}

	@AfterTest
	public void refreshtheBrowserafterTest(Method method) {
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
	}
	@BeforeMethod
	public void setUp(Method method) {
		test = extent.createTest(method.getName());
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
			test.fail(
					"<b>❌ Test Failed:</b> " + result.getName() + "<br><b>💥 Reason:</b> "
							+ result.getThrowable().getMessage(),
					MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			log.error("❌ Test Failed: " + result.getName() + " | Reason: " + result.getThrowable().getMessage());
			attachLogsToExtentReport();

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("<b>✅ Test Passed:</b> " + result.getName());
		} else {
			test.skip("<b>⚠️ Test Skipped:</b> " + result.getName());
		}
	}

	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			driver.manage().deleteAllCookies();
			driver.quit();
		}
		extent.flush();

		try {
			String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
			File reportFile = new File(reportPath);
			Desktop.getDesktop().browse(reportFile.toURI());
		} catch (IOException e) {
			log.error("⚠️ Error opening Extent Report: " + e.getMessage());
		}
	}

	private void attachLogsToExtentReport() {
		try {
			String logContent = new String(Files.readAllBytes(Paths.get(LOG_FILE_PATH)));
			String filteredLogs = logContent.replaceAll("(?m)^((?!ERROR).)*$", "").trim();

			if (!filteredLogs.isEmpty()) {
				test.log(Status.FAIL,
						"<details><summary>📜 View Error Logs</summary><pre>" + filteredLogs + "</pre></details>");
			}
		} catch (IOException e) {
			log.error("⚠️ Error reading log file: " + e.getMessage());
		}
	}
}
