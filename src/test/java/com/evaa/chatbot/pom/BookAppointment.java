package com.evaa.chatbot.pom;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.evaa.baseclass.EvvaChatBaseClass;

public class BookAppointment extends EvvaChatBaseClass {
	private WebDriver driver; // ✅ WebDriver instance

	// ✅ Constructor to initialize WebElements
	public BookAppointment(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize PageFactory elements
	}

	public void waitForElementVisible(WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.visibilityOf(element));
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
///// login with maximeyes 
	@FindBy(how = How.ID, using = "Username")
	private WebElement enterUsername;
	public WebElement enterUsername() {
		return enterUsername;
	}
	
	@FindBy(how = How.ID, using = "Password")
	private WebElement enterPassword;
	public WebElement enterPassword() {
		return enterPassword;
	}
	
	@FindBy(how = How.ID, using = "MaximEyeURL")
	private WebElement enterURL;
	public WebElement enterURL() {
		return enterURL;
	}
	
	@FindBy(how = How.XPATH, using = "//button[text()='Login']")
	private WebElement clickOnLogin;
	public void clickOnLogin() {
		waitForElementVisible(clickOnLogin);
		clickOnLogin.click();
	}
	
// EVAA Admin DashBoard
	
	@FindBy(how = How.XPATH, using = "//a[@id='settingsLinkOld']")
	private WebElement clickOnSettings;
	public void clickOnSettings() {
		waitForElementVisible(clickOnSettings);
		clickOnSettings.click();
	}	
	@FindBy(how = How.XPATH, using = "//span[text()='Preferences  ']")
	private WebElement clickOnSettingsPreferences;
	public void clickOnSettingsPreferences() {
		waitForElementVisible(clickOnSettingsPreferences);
		clickOnSettingsPreferences.click();
	}	
	
	//preferences
	@FindBy(how = How.ID, using = "AppointmentCancelingId")
	private WebElement AppointmentCancelCheckBox;
	public WebElement AppointmentCancelCheckBox() {
		return AppointmentCancelCheckBox;
	}
	
	@FindBy(how = How.ID, using = "OpticalOrderChecksId")
	private WebElement opticalOrderCheckBox;
	public WebElement opticalOrderCheckBox() {
		return opticalOrderCheckBox;
	}
	
	
	@FindBy(how = How.ID, using = "//div//p[contains(text(),'don')]")
	private WebElement permissionMsg;
	public WebElement permissionMsg() {
		return permissionMsg;
	}
	
		
	@FindBy(how = How.ID, using = "AppointmentStatusCheckingId")
	private WebElement AppointmentStatusCheckBox;
	public WebElement AppointmentStatusCheckBox() {
		return AppointmentStatusCheckBox;
	}
	
	
	@FindBy(how = How.ID, using = "ShowAppointmentBookingId")
	private WebElement allowAppointmentBookingCheckBox;
	public WebElement allowAppointmentBookingCheckBox() {
		return allowAppointmentBookingCheckBox;
	}
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Still')]")
	private WebElement areYouStillThereMSG;
	public WebElement areYouStillThereMSG() {
		return areYouStillThereMSG;
	}
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'transcription')]")
	private WebElement sentTranscription;
	public WebElement sentTranscription() {
		waitForElementVisible(sentTranscription);
		return sentTranscription;
	}
	// user profile 
	
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'contact') and contains(text(),'location')]")
	private WebElement contactMsg;
	public WebElement contactMsg() {
		waitForElementVisible(contactMsg);
		return contactMsg;
	}
	
	@FindBy(how = How.XPATH, using = "//div[@id='emailError']")
	private WebElement emailError;
	public WebElement emailError() {
		return emailError;
	}
	
	
	@FindBy(how = How.ID, using = "UploadInsCardId")
	private WebElement allowInsuranceRequiredCheckBox;
	public WebElement allowInsuranceRequiredCheckBox() {
		return allowInsuranceRequiredCheckBox;
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

	@FindBy(how = How.XPATH, using = "//div//textarea[@id='chatbox']")
	public WebElement chatMSG;
	public WebElement chatMSG() {
		waitForElementVisible(chatMSG);
		return chatMSG();
	}
	
	
	@FindBy(how = How.XPATH, using = "//div//textarea[@id='chatbox']")
	private WebElement availableInsurance;
	public WebElement availableInsurance() {
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
	@FindBy(how = How.XPATH, using = "//button[text()=' MaximEyes']")
	private WebElement loginWithMaximEyes;
	public void loginWithMaximEyes() {
		waitForElementVisible(loginWithMaximEyes);
		loginWithMaximEyes.click();
	}
	
	
	@FindBy(how = How.XPATH, using = "//a[@id='menuToggle']")
	private WebElement clickSortMenu;
	public void clickSortMenu() {
		waitForElementVisible(clickSortMenu);
		clickSortMenu.click();
	}

	@FindBy(how = How.XPATH, using = "//a[text()='Email Transcription']")
	private WebElement clickEmailTranscription;
	public void clickEmailTranscription() {
		waitForElementVisible(clickEmailTranscription);
		clickEmailTranscription.click();
	}
	
	
	@FindBy(how = How.ID, using = "recipientEmail")
	private WebElement enterEmail;
	public WebElement enterEmail() {
		waitForElementVisible(enterEmail);
		return enterEmail;
	}
	
	@FindBy(how = How.XPATH, using = "//button[@id='emailSendBtn']")
	private WebElement sendTranscription;
	public void sendTranscription() {
		waitForElementVisible(sendTranscription);
		sendTranscription.click();
	}
	
	@FindBy(how = How.XPATH, using = "(//div[@id='appointmentsContainer']//following::span[contains(text(),'Cancel')])[1]")
	private WebElement cancelRescheduleAppointmentButton;
	public void cancelRescheduleAppointmentButton() {
		waitForElementVisible(cancelRescheduleAppointmentButton);
		cancelRescheduleAppointmentButton.click();
	}
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Confirm / Cancel / Reschedule Appointment')]")
	private WebElement clickOnRescheduleButton;
	public void clickOnRescheduleButton() {
		waitForElementVisible(clickOnRescheduleButton);
		clickOnRescheduleButton.click();
	}
	
	@FindBy(how = How.XPATH, using = "//input[@name='appointment']")
	private WebElement selectAppointment;
	public void selectAppointment() {
		waitForElementVisible(selectAppointment);
		selectAppointment.click();
	}
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Reschedule Appt')]")
	private WebElement rescheduleAppt;
	public void rescheduleAppt() {
		waitForElementVisible(rescheduleAppt);
		rescheduleAppt.click();
	}
	
	
	@FindBy(how = How.XPATH, using = "(//div[@id='apptContainer']//input[@name='appointment'])[1]")
	private WebElement selectAppointment1;
	public void selectAppointmentForCancel() {
		waitForElementVisible(selectAppointment1);
		selectAppointment1.click();
	}
	
	@FindBy(how = How.XPATH, using = "(//div[@id='apptContainer']//following::span[contains(text(),'Cancel')])[1]")
	private WebElement cancelAppointment;
	public void cancelAppointment() {
		waitForElementVisible(cancelAppointment);
		cancelAppointment.click();
	}
	// chatbot
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Online appointment booking is only for routine exam')]")
	private WebElement firstMsg;
	public WebElement firstMsg() {
		return firstMsg;
	}
	
	
	
	//choose Time Slot
	@FindBy(how = How.XPATH, using = "(//div[@class='confirmtime']//following::div//div//div/div)[1]")
	private WebElement chooseTimeSlot;
	public void chooseTimeSlot() {
		waitForElementVisible(chooseTimeSlot);
		chooseTimeSlot.click();
	}
	
	@FindBy(how = How.XPATH, using = "//div[@class='confirmtime']//following::span[contains(text(),'Finish Booking') or contains(text(),'Next')]")
	private WebElement submitTimeSlot;
	public void submitTimeSlot() {
		waitForElementVisible(submitTimeSlot);
		submitTimeSlot.click();
	}
	
	// appointment details - location, provider, reason
	
	@FindBy(how = How.XPATH, using = "(//div[@class='pr-4 pl-4']//div[@class='v-select__slot'])[1]")
	private WebElement openLocationDropdown;
	public void openLocationDropdown() {
		waitForElementVisible(openLocationDropdown);
		openLocationDropdown.click();
	}
	@FindBy(how = How.XPATH, using = "//div[text()='Pune']")
	private WebElement selectLocation;
	public void selectLocation() {
		waitForElementVisible(selectLocation);
		selectLocation.click();
	}
	
	
	@FindBy(how = How.XPATH, using = "(//div[@class='pr-4 pl-4']//div[@class='v-select__slot'])[2]")
	private WebElement openProviderDropdown;
	public void openProviderDropdown() {
		waitForElementVisible(openProviderDropdown);
		openProviderDropdown.click();
	}
	
	@FindBy(how = How.XPATH, using = "(//div[text()='Dr Smith'])[2]")
	private WebElement selectProvider;
	public void selectProvider() {
		waitForElementVisible(selectProvider);
		selectProvider.click();
	}
	
	@FindBy(how = How.XPATH, using = "(//div[@class='pr-4 pl-4']//div[@class='v-select__slot'])[3]")
	private WebElement openReasonDropdown;
	public void openReasonDropdown() {
		waitForElementVisible(openReasonDropdown);
		openReasonDropdown.click();
	}
	@FindBy(how = How.XPATH, using = "//div[text()='Vision Exam -Comprehensive Eye Exam']")
	private WebElement selectReasonForBooking;
	public void selectReasonForBooking() {
		waitForElementVisible(selectReasonForBooking);
		selectReasonForBooking.click();
	}
	
	@FindBy(how = How.XPATH, using = "//div[@class='pr-4 pl-4']//div[@class='v-select__slot']//following::span[text()='NEXT']")
	private WebElement saveAppointmentDetails;
	public void saveAppointmentDetails() {
		waitForElementVisible(saveAppointmentDetails);
		saveAppointmentDetails.click();
	}
	//@FindBy(how = How.XPATH, using = "//div[@class='confirmtime']//following::span[contains(text(),'Finish Booking')]")	
	
	
	@FindBy(how = How.CSS, using = "#chat-widget-push-to-talk > img")
	private WebElement openChatBot;
	public void openChatBot() {
		waitForElementVisible(openChatBot);
		openChatBot.click();
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
	
	@FindBy(how = How.XPATH, using = "//button[@id='chat_submit']")
	private WebElement chatSubmit;
	public void chatSubmit() {
		waitForElementVisible(chatSubmit);
		chatSubmit.click();
	}
	//user profile
	@FindBy(how = How.ID, using = "UserDropdown")
	private WebElement userProfile;
	public void clickOnUserProfile() {
		waitForElementVisible(userProfile);
		userProfile.click();
	}
	
	@FindBy(how = How.XPATH ,using = "//a[@onclick='logout();']")
	private WebElement clickOnLogout;
	public void clickOnLogout() {
		waitForElementVisible(clickOnLogout);
		clickOnLogout.click();
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
		waitForElementVisible(insurancePageTitle);
		return insurancePageTitle;
	}
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'No Optical')]")
	private WebElement noOpticalOrderMsg;
	public WebElement noOpticalOrderMsg() {
		waitForElementVisible(noOpticalOrderMsg);
		return noOpticalOrderMsg;
	}
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'upcoming appointment')]")
	private WebElement noUpcommingMsg;
	public WebElement noUpcommingApptMsg() {
		waitForElementVisible(noUpcommingMsg);
		return noUpcommingMsg;
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
