package com.evaa.chatbot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.evaa.baseclass.EvvaChatBaseClass;
import com.github.javafaker.Faker;

public class SendTranscriptionTest extends EvvaChatBaseClass {
	private static final Faker faker = new Faker();
	private final String expectedFirstName = faker.name().firstName();
	private final String expectedLastName = faker.name().lastName();
	private final String expectedNumber = faker.number().digits(10);

	@Test(priority = 1)
	public void enableCancelAppointmentSettingsFromAdmin() throws Exception {
		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);
		pom.clickOnLogin();
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(4000);

		if (!pom.AppointmentStatusCheckBox().isSelected()) {
			pom.AppointmentStatusCheckBox().click();
			System.out.println("Enable the Appointment Status Checking");
		} 
		Thread.sleep(5000);
		logoutAdmin();
	}

	@Test(priority = 2, enabled = true)
	public void Test_ideal_time_out_msg_after_2_minutes() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0); 
		pom.chatMSG.sendKeys("appointment status");
		pom.chatSubmit();
		Thread.sleep(1000);
		fillPrimaryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();
		Thread.sleep(130000);
		String msgAfter2minutes =  pom.areYouStillThereMSG().getText().trim();
		Assert.assertTrue(msgAfter2minutes.toLowerCase().contains("still"));
		
	}
	
	
	@Test(priority = 3, enabled = true)
	public void Test_send_transcription_is_working() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0); 
		pom.clickSortMenu();
		pom.clickEmailTranscription();
		pom.enterEmail().sendKeys("satishg@first-insight.com");
		pom.sendTranscription();
		String successMsg =  pom.sentTranscription().getText().trim();
		Assert.assertTrue(successMsg.toLowerCase().contains("successfully"));
		
	}
	
	@Test(priority = 4, enabled = true)
	public void Test_show_error_when_sending_transcription_with_empty_email() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0); 
		pom.clickSortMenu();
		pom.clickEmailTranscription();
		pom.enterEmail().sendKeys("");
		pom.sendTranscription();
		String errorMSG =  pom.emailError().getText().trim();
		Assert.assertEquals(errorMSG, "Please enter an email address");
			
	}
	
	@Test(priority = 5, enabled = true)
	public void Test_show_error_when_transcription_sent_with_invalid_email() throws Exception {
		driver.navigate().refresh();
		pom.openChatBot();
		driver.switchTo().frame(0); 
		pom.clickSortMenu();
		pom.clickEmailTranscription();
		pom.enterEmail().sendKeys("343");
		pom.sendTranscription();
		String errorMSG =  pom.emailError().getText().trim();
		Assert.assertEquals(errorMSG, "Please enter a valid email format");
			
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
