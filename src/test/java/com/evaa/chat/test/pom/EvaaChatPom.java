package com.evaa.chat.test.pom;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pom.evaa.baseclass.EvvaChatBaseClass;

public class EvaaChatPom extends EvvaChatBaseClass {
	private WebDriver driver; // ✅ WebDriver instance

	// ✅ Constructor to initialize WebElements
	public EvaaChatPom(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize PageFactory elements
	}

	public void waitForElementVisible(WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOf(element));
	}

	// ✅ Using @FindBy for cleaner locator management
	@FindBy(how = How.ID, using = "idFirstName")
	private WebElement firstNameField;

	public WebElement getFirstNameField() {
		return firstNameField;
	}

	@FindBy(how = How.ID, using = "idLastName")
	private WebElement lastNameField;

	@FindBy(how = How.ID, using = "idDOB")
	private WebElement dobField;

	@FindBy(how = How.ID, using = "idPhoneNumber")
	private WebElement phoneNumberField;

	@FindBy(how = How.ID, using = "idEmail")
	private WebElement emailField;

	@FindBy(how = How.XPATH, using = "(//span[text()='BACK'])[1]")
	private WebElement backButton;

	public WebElement backButton() {
		return backButton;
	}

	@FindBy(how = How.XPATH, using = "//div[text()='Are you sure you want to leave? ']")
	private WebElement popupmsg;

	public WebElement worningMsg() {
		return popupmsg;
	}

	@FindBy(how = How.XPATH, using = "(//button[text()='Cancel'])[2]")
	private WebElement cancel_button;

	public WebElement click_cancel_button() {
		return cancel_button;
	}

	@FindBy(how = How.XPATH, using = "//div[@class='title']/div")
	private WebElement primary_information_title;

	public WebElement primary_information_title() {
		return primary_information_title;
	}

	@FindBy(how = How.XPATH, using = "//button[text()='Leave']")
	private WebElement leave_button;

	public WebElement leave_button() {
		return leave_button;
	}

	@FindBy(how = How.XPATH, using = "(//span[text()='NEXT'])[1]")
	private WebElement next_button_on_primary_page;

	public void next_button_on_primary_page() {
		waitForElementVisible(next_button_on_primary_page);
		next_button_on_primary_page.click();

	}

	@FindBy(how = How.ID, using = "insuranceCN")
	private WebElement insuranceCompanyName;

	public void enterInsuranceCompanyName(String companyName) {
		insuranceCompanyName.sendKeys("Cigna");
	}

	@FindBy(how = How.ID, using = "insuranceID")
	private WebElement insuranceId;

	public void insuranceId(String companyName) {
		insuranceId.sendKeys("Cigna-001");
	}

	@FindBy(how = How.XPATH, using = "(//span[text()='Book Appointment'])[2]")
	private WebElement clickOnBookAppointment;

	public void clickOnBookAppointment() {
		waitForElementVisible(clickOnBookAppointment);
		clickOnBookAppointment.click();
	}

	@FindBy(how = How.XPATH, using = "//div[@id='insuranceNameSectionId']//following::button//span[text()='Book Appointment']")
	private WebElement clickOnBookAppointmentOnAvilableInsurance;

	public void clickOnBookAppointmentOnAvilableInsurance() {
		waitForElementVisible(clickOnBookAppointmentOnAvilableInsurance);
		clickOnBookAppointmentOnAvilableInsurance.click();
	}

	@FindBy(how = How.XPATH, using = "(//div[text()='Available insurance'])[1]")
	private WebElement appointmentDetails;

	public WebElement appointmentDetails() {
		waitForElementVisible(appointmentDetails);
		return appointmentDetails();
	}

	@FindBy(how = How.XPATH, using = "(//div[text()='Available insurance'])[1]")
	private WebElement availableInsurance;

	public WebElement availableInsurance() {
		waitForElementVisible(availableInsurance);
		return availableInsurance();
	}

	@FindBy(how = How.XPATH, using = "(//span[text()='NEXT'])[2]")
	private WebElement next_button_on_otp_page;

	public WebElement next_button_on_otp_page() {
		waitForElementVisible(next_button_on_otp_page);
		return next_button_on_otp_page;
	}

	@FindBy(how = How.XPATH, using = "//input[@id='ddlReasons']")
	private WebElement selectReason;

	public void selectReason() {
		waitForElementVisible(selectReason);
		selectReason.click();
	}

	@FindBy(how = How.XPATH, using = "//div[@id='list-item-53-0']")
	private WebElement select_contract_check;

	public void select_contract_check() {
		waitForElementVisible(select_contract_check);
		select_contract_check.click();
	}

	@FindBy(how = How.XPATH, using = "(//span[text()='NEXT'])[3]")
	private WebElement clickNextOnAppointmentDetails;

	public void clickNextOnAppointmentDetails() {
		waitForElementVisible(clickNextOnAppointmentDetails);
		clickNextOnAppointmentDetails.click();
	}

	@FindBy(how = How.XPATH, using = "//button[@value='263150']")
	private WebElement select_time;

	public void select_time() {
		waitForElementVisible(select_time);
		select_time.click();
	}

	@FindBy(how = How.XPATH, using = "//button[text()='SUBMIT']")
	private WebElement submit;

	public void submit() {
		waitForElementVisible(submit);
		submit.click();
	}

	@FindBy(how = How.XPATH, using = "(//span[text()='NEXT'])[4]")
	private WebElement clickNextOncalander;

	public void clickNextOncalander() {
		waitForElementVisible(clickNextOncalander);
		clickNextOncalander.click();
	}

	@FindBy(how = How.XPATH, using = "//button//span[text()='SAVE']")
	private WebElement saveInsuranceForm;

	public void saveInsuranceForm() {
		waitForElementVisible(saveInsuranceForm);
		saveInsuranceForm.click();
	}

	@FindBy(how = How.XPATH, using = "(//div[text()='Available insurance'])[1]")
	private WebElement insurancePageTitle;

	public WebElement insurancePageTitle() {
		return insurancePageTitle;
	}

	public WebElement getLastNameField() {
		return lastNameField;
	}

	public WebElement getDobField() {
		return dobField;
	}

	public WebElement getPhoneNumberField() {
		return phoneNumberField;
	}

	public WebElement getEmailField() {
		return emailField;
	}
}
