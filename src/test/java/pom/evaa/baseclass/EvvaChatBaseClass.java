package pom.evaa.baseclass;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.evaa.chat.test.pom.EvaaChatPom;
import com.evaa.chat.utils.ConfigReader;
import com.evaa.chat.utils.ExtentManager;
import com.evaa.chat.utils.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EvvaChatBaseClass {
	protected WebDriver driver;
	protected EvaaChatPom pom;
	protected ConfigReader config;
	protected WebDriverWait wait;
	protected static ExtentReports extent;
	protected static ExtentTest test;

	private static final Logger log = LogManager.getLogger(EvvaChatBaseClass.class);
	private static final String LOG_FILE_PATH = "logs/test-execution.log"; // Path to Log4j log file

	// Declare user details variables
	protected String firstName;
	protected String lastName;
	protected String dob;
	protected String phone;
	protected String email;
	protected String botUrl;
	protected String maximeyesURL;
	protected String maxUserName;
	protected String maxUserPass;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public void startBrowser() {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();

		// Corrected headless flag check
		boolean isHeadless = System.getProperty("headless", "false").equalsIgnoreCase("true");

		if (isHeadless) {
			options.addArguments("--headless=new"); // new headless mode for modern Chrome
			options.addArguments("--disable-gpu");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			log.info("🚀 Running tests in HEADLESS mode");
		} else {
			log.info("🖥 Running tests in NORMAL mode");
		}

		// This argument helps resolve websocket connection issues with newer Chrome
		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);

//		driver.get("https://brunetcroma.eyeclinic.ai/");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(60, driver);

		pom = new EvaaChatPom(driver);
		config = new ConfigReader();
		wait = new WebDriverWait(driver, Duration.ofSeconds(70));

		firstName = config.getProperty("firstName");
		lastName = config.getProperty("lastName");
		dob = config.getProperty("dob");
		phone = config.getProperty("phone");
		email = config.getProperty("email");
		botUrl = config.getProperty("botUrl");
		maximeyesURL = config.getProperty("maximeyesURL");
		maxUserName = config.getProperty("MaxUserName");
		maxUserPass = config.getProperty("MaxUserName");

		extent = ExtentManager.getReportInstance();
	}

	@BeforeMethod
	public void setUp(Method method) {
		test = extent.createTest(method.getName()); // Start test in Extent Report
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

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("<b>✅ Test Passed:</b> " + result.getName());
			log.info("✅ Test Passed: " + result.getName());
		} else {
			test.skip("<b>⚠️ Test Skipped:</b> " + result.getName());
			log.warn("⚠️ Test Skipped: " + result.getName());
		}

		attachLogsToExtentReport();
	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
		extent.flush();

		String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
		File reportFile = new File(reportPath);
		try {
			Desktop.getDesktop().browse(reportFile.toURI());
		} catch (IOException e) {
			log.error("⚠️ Error opening Extent Report: " + e.getMessage());
		}
	}

	private void attachLogsToExtentReport() {
		try {
			String logContent = new String(Files.readAllBytes(Paths.get(LOG_FILE_PATH)));
			String filteredLogs = logContent.replaceAll("(?m)^.*INFO.*$", "").trim();

			if (!filteredLogs.isEmpty()) {
				test.log(Status.WARNING,
						"<details><summary>📜 View Logs</summary><pre>" + filteredLogs + "</pre></details>");
			}
		} catch (IOException e) {
			log.error("⚠️ Error reading log file: " + e.getMessage());
//			test.log(Status.WARNING, "⚠️ Could not attach logs due to error: " + e.getMessage());
		}
	}
}
