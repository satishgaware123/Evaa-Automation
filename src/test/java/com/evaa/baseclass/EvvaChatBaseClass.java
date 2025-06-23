package com.evaa.baseclass;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.evaa.chatbot.pom.BookAppointment;
import com.evaa.utils.ConfigReader;
import com.evaa.utils.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EvvaChatBaseClass {

    // Thread-safe WebDriver
    private static final ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<>();

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected BookAppointment pom;
    protected static ExtentReports extent;
    protected static ConfigReader config;
    private static final Logger log = LogManager.getLogger(EvvaChatBaseClass.class);

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

    private static final String LOG_FILE_PATH = "logs/test-execution.log";

    // ✅ Access WebDriver from anywhere (e.g., listeners)
    public static WebDriver getDriver() {
        return threadedDriver.get();
    }

    @BeforeSuite
    public void cleanScreenshotFolder() {
        deleteFolderContent("screenshots");
        deleteFolderContent("allure-results");
    }

    private void deleteFolderContent(String folderName) {
        String path = System.getProperty("user.dir") + File.separator + folderName;
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) file.delete();
            }
            System.out.println("✅ Cleaned: " + folderName);
        } else {
            System.out.println("⚠️ Folder not found: " + folderName);
        }
    }

    @BeforeClass
    public void startBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        boolean isHeadless = System.getProperty("headless", "false").equalsIgnoreCase("true");

        if (isHeadless) {
            options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage");
            log.info("🚀 Running tests in HEADLESS mode");
        } else {
            log.info("🖥 Running tests in NORMAL mode");
        }

        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        threadedDriver.set(driver); // ✅ ThreadLocal set

        if (!isHeadless) {
            driver.manage().window().maximize();
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        pom = new BookAppointment(driver);
        config = new ConfigReader();

        // Load config
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

    // ✅ Use this utility for tab handling
    public void openNewTabAndCloseOld(WebDriver driver) {
        String oldTab = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        String newTab = tabs.stream().filter(tab -> !tab.equals(oldTab)).findFirst().orElse(null);

        if (newTab != null) {
            driver.switchTo().window(newTab);
            driver.switchTo().window(oldTab).close();
            driver.switchTo().window(newTab);
        }
    }

    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            threadedDriver.remove();
        }

        extent.flush();

        try {
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
            Desktop.getDesktop().browse(new File(reportPath).toURI());
        } catch (IOException e) {
            log.error("⚠️ Error opening Extent Report: " + e.getMessage());
        }
    }

    protected void attachLogsToExtentReport() {
        try {
            String logContent = new String(Files.readAllBytes(Paths.get(LOG_FILE_PATH)));
            String filteredLogs = logContent.replaceAll("(?m)^((?!ERROR).)*$", "").trim();

            if (!filteredLogs.isEmpty()) {
                ExtentManager.getTest().log(Status.FAIL,
                        "<details><summary>📜 View Error Logs</summary><pre>" + filteredLogs + "</pre></details>");
            }
        } catch (IOException e) {
            log.error("⚠️ Error reading log file: " + e.getMessage());
        }
    }
}
