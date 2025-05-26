package com.evaa.chat.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pom.evaa.baseclass.EvvaChatBaseClass;

public class PrimaryInformationTest extends EvvaChatBaseClass {

	@Test(priority = 1)
	public void verify_fName_error_on_primary_information_page_for_mandatory_fields() {
		PrimanryInformationPage();
		enterText(pom.getLastNameField(), lastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), phone);
		enterText(pom.getEmailField(), email);
		clickOnNextAndVerifyErrorMsg();
		driver.close();
	}

	@Test(priority = 2)
	public void verify_lName_error_on_primary_information_page_for_mandatory_fields() {
		PrimanryInformationPage();
		enterText(pom.getFirstNameField(), firstName);
		pom.getLastNameField().clear();
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), phone);
		enterText(pom.getEmailField(), email);
		clickOnNextAndVerifyErrorMsg();
		driver.close();
	}

	@Test(priority = 3)
	public void verify_dob_error_on_primary_information_page_for_mandatory_fields() {
		PrimanryInformationPage();

		enterText(pom.getFirstNameField(), firstName);
		enterText(pom.getLastNameField(), lastName);
		pom.getDobField().clear();
		enterText(pom.getPhoneNumberField(), phone);
		enterText(pom.getEmailField(), email);
		clickOnNextAndVerifyErrorMsg();
		driver.close();
	}

	@Test(priority = 4)
	public void verify_phoneN_error_on_primary_information_page_for_mandatory_fields() {
		PrimanryInformationPage();
		enterText(pom.getFirstNameField(), firstName);
		enterText(pom.getLastNameField(), lastName);
		pom.getPhoneNumberField().clear();
		enterText(pom.getEmailField(), email);
		clickOnNextAndVerifyErrorMsg();
	}

	@Test(priority = 5)
	public void verifyShowWorningMsgWhenclickOnBackButtonOnPrimaryPage() {
		PrimanryInformationPage();
		enterText(pom.getFirstNameField(), firstName);
		enterText(pom.getLastNameField(), lastName);
		pom.getPhoneNumberField().clear();
		enterText(pom.getEmailField(), email);
		pom.backButton().click();
		Assert.assertEquals(pom.worningMsg().getText().trim(), "Are you sure you want to leave?");
		driver.close();

	}

	@Test(priority = 6)
	public void verify_back_button_navigating_to_again_primary_information_page() {
		PrimanryInformationPage();
		enterText(pom.getFirstNameField(), firstName);
		enterText(pom.getLastNameField(), lastName);
		pom.getPhoneNumberField().clear();
		enterText(pom.getEmailField(), email);
		pom.backButton().click();
		Assert.assertEquals(pom.worningMsg().getText().trim(), "Are you sure you want to leave?");
		pom.click_cancel_button();
//		System.out.println(pom.primary_information_title().getText().trim());
		Assert.assertEquals(pom.primary_information_title().getText().trim(), "Primary Information");
		driver.close();
	}

	@Test(priority = 7)
	public void verify_leave_button_navigating_back_to_chat() {
		PrimanryInformationPage();
		enterText(pom.getFirstNameField(), firstName);
		enterText(pom.getLastNameField(), lastName);
		pom.getPhoneNumberField().clear();
		enterText(pom.getEmailField(), email);
		pom.backButton().click();
		Assert.assertEquals(pom.worningMsg().getText().trim(), "Are you sure you want to leave?");
		pom.leave_button().click();
		Assert.assertNotEquals(pom.primary_information_title().getText().trim(), "Primary Information");
//		System.out.println(pom.primary_information_title().getText().trim());
		driver.close();
	}

	public void PrimanryInformationPage() {

		driver.get(botUrl);

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#chat-widget-push-to-talk > img"))).click();
		driver.switchTo().frame(0);

		WebElement bookAppointment = wait.until(ExpectedConditions.elementToBeClickable(By.id("chatbox")));
		bookAppointment.sendKeys("book appointment");

		WebElement send_button = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='chat_submit']")));
		send_button.click();

//		WebElement bookAppointment = wait
//				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".book-appointment")));
//		bookAppointment.click();

	}

	public void clickOnNextAndVerifyErrorMsg() {
		WebElement next1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()='NEXT'])[1]")));
		next1.click();

		WebElement errorPop = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='validation-message']")));
		String errormsg = errorPop.getText().trim();

		Assert.assertEquals(errormsg, "Please fill all the mandatory fields *");

	}

	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

}
