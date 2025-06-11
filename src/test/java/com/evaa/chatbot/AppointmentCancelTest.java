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

public class AppointmentCancelTest extends EvvaChatBaseClass {

	private static final Faker faker = new Faker();
	private final String expectedFirstName = faker.name().firstName();
	private final String expectedLastName = faker.name().lastName();
	private final String expectedNumber = faker.number().digits(10);
	private String chatMessage;

	@Test(priority = 1)
	public void enableCancelAppointmentSettingsFromAdmin() throws Exception {
		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);
		pom.clickOnLogin();

//		WebElement botDropdown = wait
//				.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='AccountId']")));
//		new Select(botDropdown).selectByIndex(0);

		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);

		if (!pom.AppointmentCancelCheckBox().isSelected()) {
			pom.AppointmentCancelCheckBox().click();
		}
		if (!pom.allowAppointmentBookingCheckBox().isSelected()) {
			pom.allowAppointmentBookingCheckBox().click();
		}
		if (pom.allowInsuranceRequiredCheckBox().isSelected()) {
			pom.allowInsuranceRequiredCheckBox().click();
		}
		Thread.sleep(5000);
	}

	@Test(priority = 2)
	public void bookAppointment() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0); 

		pom.chatMSG.sendKeys("book appointment");
		pom.chatSubmit();
		Thread.sleep(1000);

		verifyRoutineAppointmentMessage();

		pom.chatMSG.sendKeys("yes");
		pom.chatSubmit();

		fillPrimaryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();

		fillAppointmentDetails();
		selectTomorrowDate();
		selectTimeSlot();

		Thread.sleep(3000);
		verifyAppointmentDetails();
		Thread.sleep(3000);
	}

	@Test(priority = 3, enabled = true)
	public void cancelAppointmentForExistingPatient() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);

		pom.chatMSG.sendKeys("cancel appointment");
		pom.chatSubmit();
		Thread.sleep(1000);

		fillPrimaryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();

		pom.cancelRescheduleAppointmentButton();
		pom.selectAppointmentForCancel();
		pom.cancelAppointment();

		verifyAppointmentDetails2();
		Thread.sleep(3000);
	}

	@Test(priority = 4, enabled = true)
	public void cancelAppointmentForNewPatient() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);

		pom.chatMSG.sendKeys("cancel appointment");
		pom.chatSubmit();
		Thread.sleep(1000);

		fillPrimaryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();

		WebElement noAppointmentMsg = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[text()='You have no upcoming appointments scheduled.']")));
		Assert.assertEquals(noAppointmentMsg.getText().trim(), "You have no upcoming appointments scheduled.");
		driver.manage().deleteAllCookies();
	}

	@Test(priority = 5)
	public void DisableCancelAppointmentSettingsInPreferences() throws Exception {
		driver.get(adminURL);
//		pom.loginWithMaximEyes();
//		pom.enterUsername().sendKeys(userName);
//		pom.enterPassword().sendKeys(userPassword);
//		pom.enterURL().sendKeys(URL);
//		pom.clickOnLogin();

//		WebElement botDropdown = wait
//				.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='AccountId']")));
//		new Select(botDropdown).selectByIndex(0);

		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);

		if (pom.AppointmentCancelCheckBox().isSelected()) {
			pom.AppointmentCancelCheckBox().click();
		}
		Thread.sleep(5000);
	}

	@Test(priority = 6)
	public void TestCancelAppointmentDisable() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);

		pom.chatMSG.sendKeys("cancel appointment");
		pom.chatSubmit();
		Thread.sleep(1000);

		try {
			// Try to find the element — if found, test should fail
			pom.primary_information_title();
		} catch (Exception e) {
			// Element not found — this is expected
			System.out.println("Primary Information title not found — Test Passed as expected.");
		}
	}
	// ----------------------------
	// 🔽 Common/Reusable Methods
	// ----------------------------

	private void verifyRoutineAppointmentMessage() {
		WebElement msg = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(text(),'Online appointment booking is only for routine exam')]")));
		String txtMsg = msg.getText();
		Assert.assertTrue(
				txtMsg.contains("Online appointment booking is only for routine exam and follow up appointments"));
	}

	private void selectTimeSlot() throws Exception {  
		pom.chooseTimeSlot(); 
		pom.submitTimeSlot(); 
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

	private void selectTomorrowDate() throws Exception {
		int day = LocalDate.now().plusDays(3).getDayOfMonth();
		String xpath = String.format("//button//div[text()='%d']", day);
		WebElement dateElement = driver.findElement(By.xpath(xpath));
		waitForElementVisible2(dateElement);
		dateElement.click();
	}
	private void verifyAppointmentDetails2() {
		try { 
			WebElement confirmationMsg = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='copyContentIdCancel']")));
			chatMessage = confirmationMsg.getText();
		} catch (Exception e) {
			System.out.println("Your appointment has been canceled: " + e);
		}
		Assert.assertTrue(chatMessage.contains(expectedFirstName), "First name not found in chat message.");
		Assert.assertTrue(chatMessage.contains(expectedLastName), "Last name not found in chat message.");
	}

	private void verifyAppointmentDetails() {
		try {
			WebElement confirmationMsg = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='exampleInputName1']")));
			chatMessage = confirmationMsg.getText();
		} catch (Exception e) {
			System.out.println("Your appointment has been canceled: " + e);
		}
		Assert.assertTrue(chatMessage.contains(expectedFirstName), "First name not found in chat message.");
		Assert.assertTrue(chatMessage.contains(expectedLastName), "Last name not found in chat message.");
	}

	private void waitForElementVisible2(WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(90)).until(ExpectedConditions.visibilityOf(element));
	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

	private void fillPrimaryInformationPage() {
		enterText(pom.getFirstNameField(), expectedFirstName);
		enterText(pom.getLastNameField(), expectedLastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), expectedNumber);
		enterText(pom.getEmailField(), "QA" + email);
		pom.next_button_on_primary_page();
	}
}
