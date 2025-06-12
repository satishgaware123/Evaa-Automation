package com.evaa.chatbot;
import com.evaa.baseclass.EvvaChatBaseClass;
import com.github.javafaker.Faker;

import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RescheduleAppointmentTest extends EvvaChatBaseClass {
    private static final Faker faker = new Faker();

    // Store the expected names as static or instance variables
    final String expectedFirstName = faker.name().firstName() ;
    final String expectedLastName = faker.name().lastName();
	final String expectedNumber = faker.number().digits(10); 
	private String chatMessage;
    
    @Test(priority = 1)
	public void Enable_Reschedule_from_admin() throws Exception {
    	driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);;
		pom.clickOnLogin();
//		WebElement botDropdown = wait
//				.until(ExpectedConditions.elementToBeClickable((By.xpath("//select[@id='AccountId']"))));
//		Select dropdown = new Select(botDropdown);
//		dropdown.selectByIndex(0);
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(4000);
		
		WebElement AppointmentRescheduling = wait.until(ExpectedConditions.elementToBeClickable(By.id("AppointmentReschedulingId")));
		if (AppointmentRescheduling.isSelected()) {
			AppointmentRescheduling.click();
		}
		Thread.sleep(3000);
		pom.clickOnUserProfile();
		pom.clickOnLogout();
		Thread.sleep(1000);
	}   
    
    @Test(priority = 2)
    public void Test_Reschedule_an_appointment_for_new_user() throws Exception {
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("reschedule appointment");
		pom.chatSubmit();
        Thread.sleep(1000);
		PrimaryInformationPage();
        driver.findElement(By.id("otp1")).sendKeys("9753");
        pom.next_button_on_otp_page().click();      
        Assert.assertEquals(pom.noUpcommingApptMsg().getText(), "You have no upcoming appointments scheduled.");
        Thread.sleep(5000);
    }

    @Test(priority = 3)
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
    ///
    @Test(priority = 4)
   	public void RescheduleAppointmentforExistingUser() throws Exception {
   		driver.get(botUrl);
   		pom.openChatBot();
   		driver.switchTo().frame(0); 

   		pom.chatMSG.sendKeys("reschedule an appointment");
   		pom.chatSubmit();
   		Thread.sleep(1000);

   		fillPrimaryInformationPage();
   		driver.findElement(By.id("otp1")).sendKeys("9753");
   		pom.next_button_on_otp_page().click();
   		pom.clickOnRescheduleButton();
   		pom.selectAppointment();
   		pom.rescheduleAppt();
   		
   		fillAppointmentDetails();
   		selectTomorrowDate();
   		selectTimeSlot();

   		Thread.sleep(3000);
   		verifyAppointmentDetails();
   		Thread.sleep(3000);
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

    private void fillPrimaryInformationPage() {
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
	private void waitForElementVisible2(WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(90)).until(ExpectedConditions.visibilityOf(element));
	}
	@Test(priority = 5)
	public void Disable_Reschedule_from_admin() throws Exception {

		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);
		pom.clickOnLogin();
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(5000);

		WebElement AppointmentRescheduling = wait.until(ExpectedConditions.elementToBeClickable(By.id("AppointmentReschedulingId")));
		if (AppointmentRescheduling.isSelected()) {
			AppointmentRescheduling.click();
			System.out.println("insurance Req Appt checkbox was selected. Now Unchecked.");
		}
		Thread.sleep(3000);
		pom.clickOnUserProfile();
		pom.clickOnLogout();
	}


	@Test(priority = 6)
	public void TestRescheduleAppointmentDisabled() throws Exception {

   		driver.get(botUrl);
   		pom.openChatBot();
   		driver.switchTo().frame(0); 
   		pom.chatMSG.sendKeys("reschedule an appointment");
   		pom.chatSubmit();
		Thread.sleep(1000);
 
		WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(text(),'do not have') or contains(text(),'capability')]")));

		String actualMessage = message.getText().trim();
		System.out.println("actualMessage: "+actualMessage);
		Assert.assertTrue(
				actualMessage.contains("You do not have permission") ||
						actualMessage.contains("sorry, but I don't have the capability to reschedule appointments"),
				"Expected error message not found");
		Thread.sleep(5000);
	}
	public void PrimaryInformationPage() {
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
