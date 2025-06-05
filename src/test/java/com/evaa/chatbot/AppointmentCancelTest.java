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
	public void Enable_cancel_appointment_settings_from_admin() throws Exception {

		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);;
		pom.clickOnLogin();
		WebElement botDropdown = wait
				.until(ExpectedConditions.elementToBeClickable((By.xpath("//select[@id='AccountId']"))));
		Select dropdown = new Select(botDropdown);
		dropdown.selectByIndex(0);
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);
		if (!pom.AppointmentCancelCheckBox().isSelected()) {
			pom.AppointmentCancelCheckBox().click();
//			System.out.println("Enable the Appointment cancel settings from Admin");
		} else {
//			System.out.println("Allready Appointment cancel settings from Admin");
		}

		if (!pom.allowAppointmentBookingCheckBox().isSelected()) {
			pom.allowAppointmentBookingCheckBox().click();
//			System.out.println("Enable the Appointment Booking settings from Admin");
		} else {
//			System.out.println("Allready Enable the Appointment Booking settings");
		}
		if (pom.allowInsuranceRequiredCheckBox().isSelected()) {
			pom.allowInsuranceRequiredCheckBox().click();
//			System.out.println("disable the insurance required Appointment");
		} else {
//			System.out.println("Allready disabled insurance required Appointment");
		}

		Thread.sleep(5000);
	}

	@Test(priority = 2)
	public void Test_book_appointment_without_insurance() throws Exception {

		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("book appointment");
		pom.chatSubmit();
		Thread.sleep(1000);
		check_the_response();
		pom.chatMSG.sendKeys("yes");
		pom.chatSubmit();
		PrimanryInformationPage();

		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();
		fillAppointmentDetails();
		selectTomorrowDate();
		selectTimeSlot();
		Thread.sleep(3000);
		verifyTheDetials();
		Thread.sleep(3000);

	}

	@Test(priority = 3, enabled = true)
	public void Test_appointment_cancel_for_existing_patient() throws Exception {

		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("cancel appointment");
		pom.chatSubmit();
		Thread.sleep(1000);
		PrimanryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();

		WebElement cancelAppointmentButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//div[@id='appointmentsContainer']//following::span[contains(text(),'Cancel')])[1]")));
		cancelAppointmentButton.click();

		WebElement selectAppointment = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//div[@id='apptContainer']//input[@name='appointment'])[1]")));
		selectAppointment.click();

		WebElement clickOnCancelButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//div[@id='apptContainer']//following::span[contains(text(),'Cancel')])[1]")));
		clickOnCancelButton.click();

		verifyTheDetials();
		Thread.sleep(3000);

	}

	@Test(priority = 4, enabled = true)
	public void Test_appointment_cancel_for_new_patient() throws Exception {

		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("cancel appointment");
		pom.chatSubmit();
		Thread.sleep(1000);
		PrimanryInformationPage();
		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();

		WebElement NoAppoitmentMsg = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[text()='You have no upcoming appointments scheduled.']")));
		NoAppoitmentMsg.getText();

		Assert.assertEquals(NoAppoitmentMsg.getText().trim(), "You have no upcoming appointments scheduled.");

	}

	public void selectTimeSlot() throws Exception {
		WebElement timeSlot = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//div[@class='confirmtime']//following::div//div//div/div)[1]")));
		timeSlot.click();
		WebElement clickOnsubmit = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='confirmtime']//following::span[contains(text(),'Submit')]")));
		clickOnsubmit.click();
//		WebElement clickOnsubmit = wait.until(ExpectedConditions
//				.elementToBeClickable(By.xpath("//div[@data-index='0']//following::span[contains(text(),'Finish Booking')]")));
//		clickOnsubmit.click();
	}

	public void fillAppointmentDetails() throws Exception {

		WebElement locationdropdown = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//div[@class='pr-4 pl-4']//div[@class='v-select__slot'])[1]")));

		locationdropdown.click();
		WebElement location = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Pune']")));
		location.click();
		WebElement ProviderDropdown = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//div[@class='pr-4 pl-4']//div[@class='v-select__slot'])[2]")));

		ProviderDropdown.click();
		WebElement Provider = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[text()='Dr Smith'])[2]")));
		Provider.click();
		WebElement resonDropdown = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//div[@class='pr-4 pl-4']//div[@class='v-select__slot'])[3]")));

		resonDropdown.click();
		WebElement reason = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[text()='Vision Exam -Comprehensive Eye Exam']")));
		reason.click();
		WebElement next = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='pr-4 pl-4']//div[@class='v-select__slot']//following::span[text()='NEXT']")));
		next.click();
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

	public void verifyTheDetials() {
		try {
			WebElement confirmationMsg = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='exampleInputName1']")));

			chatMessage = confirmationMsg.getText();
		} catch (Exception E) {
			System.out.println("Your appointment has been cnaceled: " + E);
		}
//		System.out.println("Chat Confirmation Message: " + chatMessage);
		Assert.assertTrue(chatMessage.contains(expectedFirstName), "First name not found in chat message.");
		Assert.assertTrue(chatMessage.contains(expectedLastName), "Last name not found in chat message.");
	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

	public void PrimanryInformationPage() {
		enterText(pom.getFirstNameField(), expectedFirstName);
		enterText(pom.getLastNameField(), expectedLastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), expectedNumber);
		enterText(pom.getEmailField(), "QA" + email);
		pom.next_button_on_primary_page();
	}
}
