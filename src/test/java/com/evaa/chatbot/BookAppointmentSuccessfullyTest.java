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
import com.github.javafaker.Faker;

public class BookAppointmentSuccessfullyTest extends EvvaChatBaseClass {
	String chatMessage;
	private static final Faker faker = new Faker();

	// Store the expected names as static or instance variables
	final String expectedFirstName = faker.name().firstName();
	final String expectedLastName = faker.name().lastName();
	final String expectedNumber = faker.number().digits(10);

	@Test(priority = 1)
	public void Disable_ensurance_required_from_admin() throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys("burneteyecarepinecone");
		;
		pom.clickOnLogin();
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);
		if (pom.allowInsuranceRequiredCheckBox().isSelected()) {
			pom.allowInsuranceRequiredCheckBox().click();
		}

		if (pom.allowAcceptInsuranceCheckBox().isSelected()) {
			pom.allowAcceptInsuranceCheckBox().click();
		}
		Thread.sleep(5000);
		logoutAdmin();
	}

	@Test(priority = 2)
	public void Test_book_appointment_without_insurance() throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("book appointment");
		Thread.sleep(1000);
		pom.chatSubmit();
		verifyRoutineAppointmentMessage();
		pom.chatMSG.sendKeys("yes");
		Thread.sleep(1000);
		pom.chatSubmit();
		PrimaryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();

		fillAppointmentDetails();
		selectTomorrowDate();
		selectTimeSlot();
		Thread.sleep(3000);
		verifyTheDetials();
		Thread.sleep(3000);

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

	@Test(priority = 3, enabled = false, dependsOnMethods = { "Test_book_appointment_without_insurance" })
	public void verify_detail_on_maximeyes_site() throws Exception {
		openNewTabAndCloseOld(driver);
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

	@Test(priority = 4)
	public void While_booking_an_appointment_clicking_No_should_display_a_message_with_a_Contact_Us_link()
			throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("book appointment");
		pom.chatSubmit();
		verifyRoutineAppointmentMessage();
		pom.chatMSG.sendKeys("No");
		pom.chatSubmit();
		String contactMSG = pom.contactMsg().getText().trim();
		try {
			Assert.assertEquals(contactMSG,
					"You can find our location and contact details here: https://brunetpinecone.eyeclinic.ai/contact-us/ . We’re happy to help!");
		} catch (Exception e) {
			System.out.println("Actual and Expected MSG is Different: " + e);
		}
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

	public void PrimaryInformationPage() {
		enterText(pom.getFirstNameField(), expectedFirstName);
		enterText(pom.getLastNameField(), expectedLastName);
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

	public void selectTimeSlot() throws Exception {
		WebElement timeSlot = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//div[@class='confirmtime']//following::div//div//div/div)[1]")));
		timeSlot.click();

		WebElement clickOnsubmit = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='confirmtime']//following::span[contains(text(),'Finish Booking')]")));
		clickOnsubmit.click();
	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

}
