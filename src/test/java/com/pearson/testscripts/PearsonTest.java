package com.pearson.testscripts;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.pearson.library.BaseLib;
import com.pearson.pages.ActivityPage;
import com.pearson.pages.LoginPage;
import com.pearson.pages.MyCoursesPage;

public class PearsonTest extends BaseLib{
	LoginPage loginPage=null;
	MyCoursesPage myCoursesPage=null;
	ActivityPage activityPage=null;
	@BeforeMethod
	public void init() {
		loginPage=new LoginPage(driver);
		myCoursesPage=new MyCoursesPage(driver);
		activityPage=new ActivityPage(driver);
	}
	@Parameters("device")
	@Test(enabled=true,description=" Setting Pulse Environment Test, Login into Application, select 'Grade 7 Mathematics Batch 1', and verify the functionality of '01 Integers' activity.")
	public void testIntegerActivity(String device) throws InterruptedException{
		init();
		elementStatus(loginPage.getEleVersionTxt(), "Version", "displayed");
		try {
		  for(int i=1;i<=5;i++){
			  loginPage.getEleVersionTxt().click();
		  }
		  loginPage.getEleEnvironmentTxtFld().sendKeys("pulse");
		  driver.hideKeyboard();
		  loginPage.getEleSaveBtn().click();
		  loginPage.getEleEnvironmentRadioBtn().click();
		  loginPage.getEleSaveBtn().click();
		  elementStatus(loginPage.getEleEmailTxtFld(), "User Name Field", "displayed");
		  loginPage.getEleEmailTxtFld().sendKeys("username");
		  driver.hideKeyboard();
		  loginPage.getEleNextBtn().click();
		  elementStatus(loginPage.getElePasswordTxtFld(), "Password Field", "displayed");
		  loginPage.getElePasswordTxtFld().sendKeys("password");
		  driver.hideKeyboard();
		  loginPage.getEleLoginBtn().click();
		  swipeTopToBottm(0.70, 0.20);
		  elementStatus(myCoursesPage.getEleGradeSevenMathematicsLnk(), "Grade 7 mathematics Link ", "displayed");
		  myCoursesPage.getEleGradeSevenMathematicsLnk().click();
		  elementStatus(myCoursesPage.getEleLessonMaterialLnk(), "Lesson Material", "displayed");
		  myCoursesPage.getEleLessonMaterialLnk().click();
		  elementStatus(myCoursesPage.getEleIntegersLnk(), "Integers Title ", "displayed");
		  myCoursesPage.getEleIntegersLnk().click();
		  swipeTopToBottm(0.70, 0.20);
		  elementStatus(myCoursesPage.getEleIntegersActivityLnk(), "Integers Activity Link ", "displayed");
		  myCoursesPage.getEleIntegersActivityLnk().click();
		  tapOnElement(0.50, 0.50);
		  Boolean InstructionsPresence=activityPage.getEleInstructionTitleTxt().size()==1;
		  Assert.assertTrue(InstructionsPresence,"Instructions Title");
		  Boolean instructionsOne=activityPage.getEleInstr1().size()==1;
		  Assert.assertTrue(instructionsOne,"Instructions One");
		  Boolean instructionsTwo=activityPage.getEleInstr2().size()==1;
		  Assert.assertTrue(instructionsTwo,"Instructions Two");
		  Boolean instructionsThree=activityPage.getEleInstr3().size()==1;
		  Assert.assertTrue(instructionsThree,"Instructions Three");
		  Boolean instructionsFour=activityPage.getEleInstr4().size()==1;
		  Assert.assertTrue(instructionsFour,"Instructions Four");
		  elementStatus(activityPage.getEleStartBtn(), "Play button", "displayed");
		  activityPage.getEleStartBtn().click();
		  activityPage.elementsPresence();
		  activityPage.getEleOptionFld().get(0).click();
	 	  String optionText=verifyToastMessage();
	 	  System.out.println(optionText);
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(1).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(2).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(1).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(2).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(2).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(0).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(1).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(2).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleOptionFld().get(2).click();
	 	  activityPage.elementsPresence();
	 	  activityPage.getEleCloseIcn().click();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
}