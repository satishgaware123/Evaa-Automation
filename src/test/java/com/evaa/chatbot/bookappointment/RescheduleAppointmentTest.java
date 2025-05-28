package com.evaa.chatbot.bookappointment;
import com.evaa.baseclass.EvvaChatBaseClass;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RescheduleAppointmentTest extends EvvaChatBaseClass {
    private static final Faker faker = new Faker();

    // Store the expected names as static or instance variables
    final String expectedFirstName = faker.name().firstName();
    final String expectedLastName = faker.name().lastName();
	final String expectedNumber = faker.number().digits(10);

    
    @Test(priority = 1)
	public void Enable_Reschedule_from_admin() throws Exception {

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
		

		WebElement AppointmentRescheduling = wait.until(ExpectedConditions.elementToBeClickable(By.id("AppointmentReschedulingId")));
		if (!AppointmentRescheduling.isSelected()) {
			AppointmentRescheduling.click();
			System.out.println("insuranceReqApptId checkbox was Unselected. Now checked.");
		} else {
			System.out.println("insuranceReqApptId checkbox was already selected.");
		}
		Thread.sleep(3000);
	}
    
    
    @Test(priority = 2)
    public void Test_Reschedule_an_appointment_for_new_user() throws Exception {

        driver.get(botUrl);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#chat-widget-push-to-talk > img"))).click();
        driver.switchTo().frame(0);
        WebElement bookAppointment = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//textarea[@id='chatbox']")));
        bookAppointment.sendKeys("reschedule appointment");
        WebElement send_button = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='chat_submit']")));
        send_button.click();
        Thread.sleep(1000);
//        bookAppointment.sendKeys("yes");
//        send_button.click();
		PrimaryInformationPage();

        System.out.println("i am on otp page");
        driver.findElement(By.id("otp1")).sendKeys("9753");
        pom.next_button_on_otp_page().click();
        
        
        WebElement upcomingAppointment = driver.findElement(By.xpath("//div[text()='Upcoming Appointment']"));
        
        String title = upcomingAppointment.getText();
        Assert.assertEquals(title, "upcoming Appointment ");
        Thread.sleep(5000);
    }


	@Test(priority = 3)
	public void Disable_Reschedule_from_admin() throws Exception {

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


		WebElement AppointmentRescheduling = wait.until(ExpectedConditions.elementToBeClickable(By.id("AppointmentReschedulingId")));
		if (AppointmentRescheduling.isSelected()) {
			AppointmentRescheduling.click();
			System.out.println("insurance Req Appt checkbox was selected. Now Unchecked.");
		} else {
			System.out.println("insurance Req Appt  checkbox was already Unselected.");
		}
		Thread.sleep(3000);
	}


	@Test(priority = 4)
	public void TestRescheduleAppointmentDisabled() throws Exception {

		driver.get(botUrl);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#chat-widget-push-to-talk > img"))).click();
		driver.switchTo().frame(0);
		WebElement bookAppointment = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//textarea[@id='chatbox']")));
		bookAppointment.sendKeys("reschedule appointment");
		WebElement send_button = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='chat_submit']")));
		send_button.click();
		Thread.sleep(1000);
//        bookAppointment.sendKeys("yes");
//        send_button.click();
//		WebElement dontHavePermissionMsg = wait
//				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'don\\'t have')]")));
//		dontHavePermissionMsg.getText();
//		Assert.assertTrue(dontHavePermissionMsg.getText().contains("don't have"));

		// Wait for either message to appear
		WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(text(),'do not have') or contains(text(),'capability')]")));

		String actualMessage = message.getText().trim();
		System.out.println("actualMessage: "+actualMessage);
		Assert.assertTrue(
				actualMessage.contains("You do not have permission") ||
						actualMessage.contains("sorry, but I don't have the capability to reschedule appointments"),
				"Expected error message not found"
		);



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
