package com.evaa.chatbot;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.evaa.baseclass.EvvaChatBaseClass;
import com.github.javafaker.Faker;

public class PrimaryInformationValidationTest  extends EvvaChatBaseClass  {
	

	private static final Faker faker = new Faker();
	private final String FirstName = faker.name().firstName();
	private final String LastName = faker.name().lastName();
	private final String Number = faker.number().digits(10);

	@Test(priority = 1)
	public void enableCancelAppointmentSettingsFromAdmin() throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(adminURL);
		pom.loginWithMaximEyes();
		pom.enterUsername().sendKeys(userName);
		pom.enterPassword().sendKeys(userPassword);
		pom.enterURL().sendKeys(URL);
		pom.clickOnLogin();
		pom.clickOnSettings();
		pom.clickOnSettingsPreferences();
		Thread.sleep(4000);

		if (!pom.AppointmentCancelCheckBox().isSelected()) {
			pom.AppointmentCancelCheckBox().click();
		}
		Thread.sleep(5000);
	
	}
	@Test(priority = 2, enabled = true)
	public void validating_error_msg_on_primary_information_page_for_all_mandatory_fields() throws Exception {
		openNewTabAndCloseOld(driver);
		driver.get(botUrl);
		pom.openChatBot();
		driver.switchTo().frame(0);
		pom.chatMSG.sendKeys("cancel appointment");
		pom.chatSubmit();
		
		fillPrimaryInformationPage();
		pom.getFirstNameField().clear();
		verifyErrorMsg();
		
		fillPrimaryInformationPage();
		pom.getLastNameField().clear();
		verifyErrorMsg();
		
		fillPrimaryInformationPage();
		pom.getDobField().clear();
		verifyErrorMsg();
		
		fillPrimaryInformationPage();
		pom.getPhoneNumberField().clear();
		verifyErrorMsg();
		
	}
	
	public void verifyErrorMsg() {	
		pom.next_button_on_primary_page();
		Assert.assertEquals(pom.validationMsgOnPrimaryInfo().getText().trim(), "Please fill all the mandatory fields *");
	}
	
	private void enterText(WebElement webElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(webElement)).clear();
		webElement.sendKeys(text);
	}

	private void fillPrimaryInformationPage() {
		enterText(pom.getFirstNameField(), FirstName);
		enterText(pom.getLastNameField(), LastName);
		enterText(pom.getDobField(), dob);
		enterText(pom.getPhoneNumberField(), Number);
		enterText(pom.getEmailField(), "QA" + email);
	}

}
