package com.evaa.chatbot;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.evaa.baseclass.EvvaChatBaseClass;
import com.github.javafaker.Faker;

public class OpticalOrderCheckTest extends EvvaChatBaseClass {

	private static final Faker faker = new Faker();
	private final String expectedFirstName = faker.name().firstName();
	private final String expectedLastName = faker.name().lastName();
	private final String expectedNumber = faker.number().digits(10);

	@Test(priority = 1)
	public void enable_optical_order_check_settings_from_admin() throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(adminURL);
		try {
			pom.loginWithMaximEyes();
			pom.enterUsername().sendKeys(userName);
			pom.enterPassword().sendKeys(userPassword);
			pom.enterURL().sendKeys(URL);
			pom.clickOnLogin();
		} catch (Exception e) {
			System.out.println("Login Error: " + e);
		}
		try {
			pom.clickOnSettings();
			pom.clickOnSettingsPreferences();
			Thread.sleep(4000);
			if (!pom.opticalOrderCheckBox().isSelected()) {
				pom.opticalOrderCheckBox().click();
			}
		} catch (Exception e) {
			System.out.println("Update preference Error: " + e);
		} finally {
			Thread.sleep(4000);
			logoutAdmin();
		}
	}

	@Test(priority = 2)
	public void check_optical_order_status_for_new_user() throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("Optical Order status");
		pom.chatSubmit();
		fillPrimaryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();
		String msg = pom.noOpticalOrderMsg().getText().trim().toLowerCase();
//		System.out.println(msg);
		Assert.assertEquals(msg, "no optical order status data found for this user.");
	}

	@Test(priority = 3)
	public void DisableOpticalOrderCheckSettingsFromAdmin() throws Exception {
		try {
			openNewTabAndCloseOld(driver);
			driver.get(adminURL);
			pom.loginWithMaximEyes();
			pom.enterUsername().sendKeys(userName);
			pom.enterPassword().sendKeys(userPassword);
			pom.enterURL().sendKeys(URL);
			pom.clickOnLogin();
		} catch (Exception e) {
			System.out.println("Login Error: " + e);
		} finally {
			pom.clickOnSettings();
			pom.clickOnSettingsPreferences();
			Thread.sleep(5000);
			if (pom.opticalOrderCheckBox().isSelected()) {
				pom.opticalOrderCheckBox().click();
				Thread.sleep(5000);
			}
		}
		logoutAdmin();
	}

	@Test(priority = 4)
	public void checkOpticlOrderShouldNotInitiateAfterDisableFromEvaaAdmin() throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("Optical Order status");
		pom.chatSubmit();
		System.out.println(pom.permissionMsg().getText().trim().toLowerCase());
		Assert.assertTrue(pom.permissionMsg().getText().trim().toLowerCase().contains("don't"));
		
	    // Step 4: Check if Primary Information page is shown
	    try {
	        if (pom.primary_information_title().isDisplayed()) {
	            Assert.fail("Primary Information page opened even though Optical Order is disabled.");
	        }
	    } catch (NoSuchElementException e) {
	        System.out.println("Primary Information page not shown. Test passed.");
	        Assert.assertTrue(true);
	    }
		
	}

	private void fillPrimaryInformationPage() {
		enterText(pom.getFirstNameField(), expectedFirstName);
		enterText(pom.getLastNameField(), expectedLastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), expectedNumber);
		enterText(pom.getEmailField(), "QA" + email);
		pom.next_button_on_primary_page();
	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

	public void logoutAdmin() throws Exception {
		pom.clickOnUserProfile();
		pom.clickOnLogout();
		pom.loginWithMaximEyes();
		Thread.sleep(2000);
	}
}
