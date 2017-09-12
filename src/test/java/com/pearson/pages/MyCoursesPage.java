package com.pearson.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class MyCoursesPage {
	AndroidDriver <MobileElement>driver;
	public MyCoursesPage(AndroidDriver<MobileElement> driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Grade 7 Mathematics Batch 1')]")
	private WebElement eleGradeSevenMathematicsLnk;
	public WebElement getEleGradeSevenMathematicsLnk() {
		return eleGradeSevenMathematicsLnk;
	}
	@FindBy(xpath="//android.widget.TextView[@text='Lesson Material']")
	private WebElement eleLessonMaterialLnk;
	public WebElement getEleLessonMaterialLnk() {
		return eleLessonMaterialLnk;
	}
	@FindBy(xpath="//android.widget.TextView[@text='01 Integers']")
	private WebElement eleIntegersLnk;
	public WebElement getEleIntegersLnk() {
		return eleIntegersLnk;
	}
	@FindBy(xpath="//android.widget.TextView[@text='1.1.4 Integers - An Activity']")
	private WebElement eleIntegersActivityLnk;
	public WebElement getEleIntegersActivityLnk(){
		return eleIntegersActivityLnk;
	}
}
