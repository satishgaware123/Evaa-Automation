package com.evaa.chatbot;

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
	private String chatMessage;

	@Test(priority = 1)
	public void enableOpticalOrderCheckSettingsFromAdmin() throws Exception {
		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);
		pom.clickOnLogin();

		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(4000);

		if (!pom.opticalOrderCheckBox().isSelected()) {
			pom.opticalOrderCheckBox().click();
		}

		Thread.sleep(5000);
		pom.clickOnUserProfile();
		pom.clickOnLogout();
		Thread.sleep(1000);
		
	}

	@Test(priority = 2)
	public void check_optical_order_status_for_new_user() throws Exception {
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
		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);
		pom.clickOnLogin();

		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);

		if (pom.opticalOrderCheckBox().isSelected()) {
			pom.opticalOrderCheckBox().click();
		}

		Thread.sleep(5000);
		pom.clickOnUserProfile();
		pom.clickOnLogout();
		driver.manage().deleteAllCookies();
	}

	@Test(priority = 4)
	public void checkOpticlOrderShouldNotInitiateAfterDisableFromEvaaAdmin() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("Optical Order status");
		pom.chatSubmit();
		System.out.println(pom.permissionMsg().getText().trim().toLowerCase());
		Assert.assertTrue(pom.permissionMsg().getText().trim().toLowerCase().contains("don't"));
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
}
