package com.evaa.chatbot;

import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.evaa.baseclass.EvvaChatBaseClass;
import com.evaa.chatbot.pom.AppText;
import com.github.javafaker.Faker;

public class AfterBookAppointmentTestMsgTest extends EvvaChatBaseClass {

	
	String chatMessage;
	private static final Faker faker = new Faker();
	private final String expectedFirstName = faker.name().firstName();
	private final String expectedLastName = faker.name().lastName();
	private final String expectedNumber = faker.number().digits(10);

	public void check_the_response() {
		String txtmsg = pom.firstMsg().getText();
		Assert.assertTrue(txtmsg.contains(AppText.APPOINTMENT_MSG));
	}

	@Test(priority = 1)
	public void Disable_ensurance_required_from_admin() throws Exception {

		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);;
		pom.clickOnLogin();
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);
		if (pom.allowInsuranceRequiredCheckBox().isSelected()) {
			pom.allowInsuranceRequiredCheckBox().click();			
		}
		Thread.sleep(5000);
		logoutAdmin();
	}

	@Test(priority = 2, enabled = true) 
	public void Test_ideal_msgs_after_successull_appointments_click_on_No() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0); 
		pom.chatMSG.sendKeys("book appointment");
		pom.chatSubmit();
		Thread.sleep(1000);
		verifyRoutineAppointmentMessage();
		pom.chatMSG.sendKeys("yes");
		pom.chatSubmit();
		fillPrimaryInformation();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();
		fillAppointmentDetails();
		selectTomorrowDate();
		selectTimeSlot();
		pom.click_On_No_do_you_need_futher_assistance();
		System.out.println("===== "+pom.wasThisConverationHelpfull().getText().trim());
//		Assert.assertEquals(pom.wasThisConverationHelpfull().getText().trim(), "Was this conversation helpful to you?\r\n"+ "(Like or Dislike)");
		Assert.assertTrue(pom.wasThisConverationHelpfull().getText().trim().contains("Was this conversation helpful to you"));
		pom.clickOnLikeButton();
		Assert.assertEquals(pom.restartButton().getText().trim(), "Restart Chat");
		
		

	}
	@Test(priority = 3, enabled = true) 
	public void Test_ideal_msgs_after_successull_appointments_and_click_on_yes() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0); 
		pom.chatMSG.sendKeys("book appointment");
		pom.chatSubmit();
		Thread.sleep(1000);
		verifyRoutineAppointmentMessage();
		pom.chatMSG.sendKeys("yes");
		pom.chatSubmit();
		fillPrimaryInformation();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();
		fillAppointmentDetails();
		selectTomorrowDate();
		selectTimeSlot();
		pom.Click_On_Yes_do_you_need_futher_assistance();
		
		String actualText = pom.howCanAshowCanAssistYousistYou().getText().trim().toLowerCase();
		System.out.println(pom.howCanAshowCanAssistYousistYou().getText().trim().toLowerCase());
		Assert.assertTrue(
		    actualText.contains("how can i assist you further") || actualText.contains("how can i help you" ) || actualText.contains( "Please let me know what you need assistance"),
		    "Expected text not found: " + actualText
		);

	}
	public void selectTomorrowDate() throws Exception {
		int tomorrow = LocalDate.now().plusDays(3).getDayOfMonth();
		String xpath = String.format("//button//div[text()='%d']", tomorrow);
		WebElement dateElement = driver.findElement(By.xpath(xpath));
		waitForElementVisible2(dateElement);
		dateElement.click();

	}
	public void waitForElementVisible2(WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(90)).until(ExpectedConditions.visibilityOf(element));
	}
	private void selectTimeSlot() throws Exception {  
		pom.chooseTimeSlot(); 
		pom.submitTimeSlot(); 
	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}
	private void fillAppointmentDetails() throws Exception {
		pom.openLocationDropdown();
		pom.selectLocation();
		pom.openProviderDropdown();
		pom.selectProvider();
		pom.openReasonDropdown();
		pom.selectReasonForBooking();
		pom.saveAppointmentDetails();
	}

	public void logoutAdmin() throws Exception {	
		pom.clickOnUserProfile();
		pom.clickOnLogout();
		pom.loginWithMaximEyes();
		Thread.sleep(2000);
	}
	public void fillPrimaryInformation() {
		enterText(pom.getFirstNameField(), expectedFirstName);
		enterText(pom.getLastNameField(), expectedLastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), expectedNumber);
		enterText(pom.getEmailField(), "QA" + email);
		pom.next_button_on_primary_page();
	}

	private void verifyRoutineAppointmentMessage() {
		WebElement msg = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(text(),'Online appointment booking is only for routine exam')]")));
		String txtMsg = msg.getText();
		Assert.assertTrue(
				txtMsg.contains("Online appointment booking is only for routine exam and follow up appointments"));
	}  
	
	public void verifyDetalsForNewUser() {
		
		WebElement noAppointmentElement = driver.findElement(By.xpath("//div[contains(text(),'No appointments')]"));
		String extractedText = noAppointmentElement.getText().trim();

		Assert.assertTrue(
		    extractedText.toLowerCase().contains("no appointment"),
		    "Expected text to contain 'no appointment' but found: " + extractedText
		);	
	}
	
	

	
	
	
	
}
