package com.evaa.chatbot.bookappointment;

import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import pom.evaa.baseclass.EvvaChatBaseClass;

public class book_appointment_suceessfuly extends EvvaChatBaseClass {
	String chatMessage;
	private static final Faker faker = new Faker();

	// Store the expected names as static or instance variables
	private String expectedFirstName = faker.name().firstName();
	private String expectedLastName = faker.name().lastName();
	private String expectedNumber = faker.number().digits(10);

	public void check_the_response() {

		WebElement msg = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(text(),'Online appointment booking is only for routine exam')]")));
		String txtmsg = msg.getText();

		Assert.assertTrue(
				txtmsg.contains("Online appointment booking is only for routine exam and follow up appointments"));

	}

	@Test(priority = 1)
	public void Disable_ensurance_required_from_admin() throws Exception {

		driver.get("https://assistant2-pinecone-admin.evaa.ai/");
		driver.findElement(By.xpath("//button[text()=' MaximEyes']")).click();
		driver.findElement(By.id("Username")).sendKeys("satishG");
		driver.findElement(By.id("Password")).sendKeys("Admin@1234");
		driver.findElement(By.id("MaximEyeURL")).sendKeys("burneteyecarepinecone");
		driver.findElement(By.xpath("//button[text()='Login']")).click();

		WebElement botDropdown = wait
				.until(ExpectedConditions.elementToBeClickable((By.xpath("//select[@id='AccountId']"))));
		Select dropdown = new Select(botDropdown);
		dropdown.selectByIndex(0);
		WebElement settings = wait
				.until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@id='settingsLinkOld']"))));
		settings.click();

		WebElement preferences = wait
				.until(ExpectedConditions.elementToBeClickable((By.xpath("//span[text()='Preferences  ']"))));
		preferences.click();
		Thread.sleep(5000);
		WebElement checkbox1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("UploadInsCardId")));
		if (checkbox1.isSelected()) {
			checkbox1.click();
			System.out.println("UploadInsCardId checkbox was selected. Now unchecked.");
		} else {
			System.out.println("UploadInsCardId checkbox was already unselected.");
		}

		WebElement checkbox2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("insuranceReqApptId")));
		if (checkbox2.isSelected()) {
			checkbox2.click();
			System.out.println("insuranceReqApptId checkbox was selected. Now unchecked.");
		} else {
			System.out.println("insuranceReqApptId checkbox was already unselected.");
		}
		Thread.sleep(5000);
	}

	@Test(priority = 2)
	public void Test_book_appointment_without_insurance() throws Exception {

		driver.get(botUrl);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#chat-widget-push-to-talk > img"))).click();
		driver.switchTo().frame(0);
		WebElement bookAppointment = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//textarea[@id='chatbox']")));
		bookAppointment.sendKeys("book appointment");
		WebElement send_button = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='chat_submit']")));
		send_button.click();
		Thread.sleep(1000);
		check_the_response();
		bookAppointment.sendKeys("yes");
		send_button.click();
		PrimanryInformationPage();

		driver.findElement(By.id("otp1")).sendKeys("9753");
		pom.next_button_on_otp_page().click();
		System.out.println("i am on otp page");
		System.out.println("Expected: " + expectedFirstName);
		System.out.println("Actual  : " + expectedLastName);

		// appoitment detail page - select location, provider, reason
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

	@Test(priority = 3, enabled = false)
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

	public void selectTimeSlot() throws Exception {
		WebElement timeSlot = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//div[@class='confirmtime']//following::div//div//div/div)[1]")));
		timeSlot.click();

		WebElement clickOnsubmit = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='confirmtime']//following::span[text()='SUBMIT']")));
		clickOnsubmit.click();
	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

}
