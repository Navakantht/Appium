package com.pearson.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pearson.library.BaseLib;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class ActivityPage {
	AndroidDriver<MobileElement> driver;
	public ActivityPage(AndroidDriver<MobileElement> driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath="//android.view.View[@content-desc='Instructions']")
	private List<WebElement> eleInstructionTitleTxt;
	public List<WebElement> getEleInstructionTitleTxt() {
		return eleInstructionTitleTxt;
	}
	@FindBy(xpath="//android.view.View[@resource-id='wa11-text1']")
	private List<WebElement> eleInstr1;
	public List<WebElement> getEleInstr1() {
		return eleInstr1;
	}
	@FindBy(xpath="//android.view.View[@resource-id='wa11-text2']")
	private List<WebElement> eleInstr2;
	public List<WebElement> getEleInstr2() {
		return eleInstr2;
	}
	@FindBy(xpath="//android.view.View[@resource-id='wa11-text3']")
	private List<WebElement> eleInstr3;
	public List<WebElement> getEleInstr3() {
		return eleInstr3;
	}
	@FindBy(xpath="//android.view.View[@resource-id='wa11-text4']")
	private List<WebElement> eleInstr4;
	public List<WebElement> getEleInstr4() {
		return eleInstr4;
	}
	@FindBy(xpath="//android.view.View[@resource-id='wa11-startBtn']")
	private WebElement eleStartBtn;
	public WebElement getEleStartBtn() {
		return eleStartBtn;
	}
	@FindBy(xpath="//android.view.View[contains(@resource-id,'wa21-optionTxt')]")
	private List<WebElement> eleOptionFld;
	public List<WebElement> getEleOptionFld() {
		return eleOptionFld;
	}
	@FindBy(id="toolbar_assn_details_close_img")
	private WebElement eleCloseIcn;
	public WebElement getEleCloseIcn() {
		return eleCloseIcn;
	}
	@FindBy(xpath="//android.view.View[contains(@resource-id,'wa21-qstTxt')]")
	private WebElement eleInstructText;
	public WebElement getEleInstructText() {
		return eleInstructText;
	}
	@FindBy(xpath="//android.view.View[contains(@resource-id,'wa21-qstInst')]")
	private WebElement eleQuestionInfo;
	public WebElement getEleQuestionInfo() {
		return eleQuestionInfo;
	}
	@FindBy(xpath="//android.view.View[contains(@content-desc,'Your Position : ')]")
	private WebElement elePositionInfo;
	public WebElement getElePositionInfo() {
		return elePositionInfo;
	}
	public void elementsPresence(){
		BaseLib.elementStatus(getEleInstructText(), "Question", "displayed");
		BaseLib.elementStatus(getEleQuestionInfo(), "Question Number", "displayed");
		BaseLib.elementStatus(getElePositionInfo(), "Position Info", "displayed");
	}
}