	package com.evaa.chatbot;

import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.evaa.baseclass.EvvaChatBaseClass;
import com.github.javafaker.Faker;

public class BookAppointmentSuccessfullyWithInsuranceTest extends EvvaChatBaseClass {
	String chatMessage;
	private static final Faker faker = new Faker();

	// Store the expected names as static or instance variables
	private final String expectedFirstName = faker.name().firstName();
	private final String expectedLastName = faker.name().lastName();
	private final String expectedNumber = faker.number().digits(10);

	public void check_the_response() {

		WebElement msg = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(text(),'Online appointment booking is only for routine exam')]")));
		String txtmsg = msg.getText();

		Assert.assertTrue(
				txtmsg.contains("Online appointment booking is only for routine exam and follow up appointments"));
 
	}

	@Test(priority = 1)
	public void Enable_ensurance_required_from_admin() throws Exception {

		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);;
		pom.clickOnLogin();
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);
		if (!pom.allowInsuranceRequiredCheckBox().isSelected()) {
			pom.allowInsuranceRequiredCheckBox().click();
			
		}
		Thread.sleep(5000);
		logoutAdmin();
	}

	@Test(priority = 2, enabled = true) 
	public void Test_book_appointment_with_insurance() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0); 

		pom.chatMSG.sendKeys("book appointment");
		pom.chatSubmit();
		Thread.sleep(1000);

		verifyRoutineAppointmentMessage();  

		pom.chatMSG.sendKeys("yes");
		pom.chatSubmit();  

		PrimanryInformationPage();

		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();
		System.out.println("i am on otp page");
		System.out.println("Expected: " + expectedFirstName);
		System.out.println("Actual  : " + expectedLastName);

		fillAppointmentDetails();
		selectTomorrowDate();
		selectTimeSlot();
		Thread.sleep(3000);
		fillInsuranceDetails();
		verifyTheDetials();
		Thread.sleep(3000);

	}
	public void logoutAdmin() throws Exception {	
		pom.clickOnUserProfile();
		pom.clickOnLogout();
		pom.loginWithMaximEyes();
		Thread.sleep(2000);
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
	
	
	public void fillInsuranceDetails() {

		WebElement insuranceCN = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("insuranceCN")));
		insuranceCN.sendKeys("Cigna");

		WebElement insuranceID = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("insuranceID")));
		insuranceID.sendKeys("Cigna001");

		WebElement save = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//span[text()='SAVE']"))));
		save.click();

		WebElement finish_Booking = wait
				.until(ExpectedConditions.elementToBeClickable((By.xpath("//span[text()='Finish Booking']"))));
		
		finish_Booking.click();
	}

	public void verifyTheDetials() {

		try {
			WebElement confirmationMsg = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='exampleInputName1']")));

			chatMessage = confirmationMsg.getText();
		} catch (Exception E) {
			System.out.println("Your appointment has been booked: " + E);
		}
		System.out.println("Chat Confirmation Message: " + chatMessage);

		// Assertions
		Assert.assertTrue(chatMessage.contains(expectedFirstName), "First name not found in chat message.");
		Assert.assertTrue(chatMessage.contains(expectedLastName), "Last name not found in chat message.");
//		Assert.assertTrue(chatMessage.contains("appointment successfully"), "'appointment successfully' not found.");
//		Assert.assertTrue(chatMessage.contains("confirmed"), "'confirmed' not found in chat message.");

		System.out.println("✅ Appointment confirmation message verified successfully.");
	}

	@Test(priority = 5, enabled = false, dependsOnMethods = {"Test_book_appointment_with_insurance"})
	public void verify_detail_on_maximeyes_site() throws Exception {
//		driver.get("https://hheyeqainternalmysql.maximeyes.com/Account/Login");
		driver.get(maximeyesURL);
		WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserName")));
		userName.sendKeys("satishG");

		WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password")));
		password.sendKeys("Admin@1234");

		WebElement send_button = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginBtn")));
		send_button.click();

		WebElement findPatient = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@id='imgFindPatient'])[1]")));
		findPatient.click();

		WebElement fname = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='FirstName']")));
		fname.sendKeys("satishG");
		Thread.sleep(5000);
		WebElement findButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='btnSearchPatient']")));
		findButton.click();

		Thread.sleep(5000);

	}

	public void waitForElementVisible2(WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(90)).until(ExpectedConditions.visibilityOf(element));
	}

	public void selectTomorrowDate() throws Exception {
		int tomorrow = LocalDate.now().plusDays(3).getDayOfMonth();
		String xpath = String.format("//button//div[text()='%d']", tomorrow);
		WebElement dateElement = driver.findElement(By.xpath(xpath));
		waitForElementVisible2(dateElement);
		dateElement.click();

	}

	public void fill_insurance_form() {
		pom.enterInsuranceCompanyName("abcd");
		pom.insuranceId("abcd-1");
		pom.saveInsuranceForm();

	}

	public void PrimanryInformationPage() {
		enterText(pom.getFirstNameField(), expectedFirstName);
		enterText(pom.getLastNameField(), expectedLastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), expectedNumber);
		enterText(pom.getEmailField(), "QA" + email);
		pom.next_button_on_primary_page();
	}
	public void PrimanryInformationPage2() {
		enterText(pom.getFirstNameField(), "QA"+expectedFirstName);
		enterText(pom.getLastNameField(), "QA"+expectedLastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), expectedNumber);
		enterText(pom.getEmailField(), "QA" + email);
		pom.next_button_on_primary_page();
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

	private void selectTimeSlot() throws Exception {  
		pom.chooseTimeSlot(); 
		pom.submitTimeSlot(); 
	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

}
