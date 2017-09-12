package com.pearson.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage {
	AndroidDriver<MobileElement> driver;
	public LoginPage(AndroidDriver<MobileElement> driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(id="login_version_txt")
	private WebElement eleVersionTxt;
	public WebElement getEleVersionTxt() {
		return eleVersionTxt;
	}
	@FindBy(id="editTextAccess")
	private WebElement eleEnvironmentTxtFld;
	public WebElement getEleEnvironmentTxtFld() {
		return eleEnvironmentTxtFld;
	}
	@FindBy(id="btnSave")
	private WebElement eleSaveBtn;
	public WebElement getEleSaveBtn() {
		return eleSaveBtn;
	}
	@FindBy(xpath="//android.widget.CheckedTextView[contains(@text,'https://auth.test.pulse.pearson.com')]")
	private WebElement eleEnvironmentRadioBtn;
	public WebElement getEleEnvironmentRadioBtn() {
		return eleEnvironmentRadioBtn;
	}
	@FindBy(id="login_email_etxt")
	private WebElement eleEmailTxtFld;
	public WebElement getEleEmailTxtFld() {
		return eleEmailTxtFld;
	}
	@FindBy(id="login_next_layout")
	private WebElement eleNextBtn;
	public WebElement getEleNextBtn() {
		return eleNextBtn;
	}
	@FindBy(id="login_pwd_etxt")
	private WebElement elePasswordTxtFld;
	public WebElement getElePasswordTxtFld() {
		return elePasswordTxtFld;
	}
	@FindBy(id="login_signin_layout")
	private WebElement eleLoginBtn;
	public WebElement getEleLoginBtn() {
		return eleLoginBtn;
	}
}
