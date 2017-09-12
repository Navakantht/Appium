/**
* @description		: 		TestNG annotated methods with launching application under test and closing application under test,
*/
package com.pearson.library;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

	public class BaseLib {
	public static AndroidDriver<MobileElement> driver;
	static public String sDirPath = System.getProperty("user.dir");
	public static String sTestDataFile = sDirPath + "/testData.xlsx";
	public static String sConfigFile = sDirPath + "/Config.Properties";
	String appPath=sDirPath+"//src//test//resources//apk//application_under_test.apk";
	DesiredCapabilities capabilities;
	static WebElement wElement;
	static Dimension dSize;
	Wait wait;
	static int sStatusCnt = 0;
	/*
	 * Description:Desired capabilities to launch application under test
	 */
	@BeforeMethod
	public void setUp() throws MalformedURLException {
		capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
				GenericLib.getConfigValue(sConfigFile, "AutomationName"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
				GenericLib.getConfigValue(sConfigFile, "PlatformName"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
				GenericLib.getConfigValue(sConfigFile, "PlatformVersion"));
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
				GenericLib.getConfigValue(sConfigFile, "DeviceName"));
		capabilities.setCapability(MobileCapabilityType.APP,appPath);
		capabilities.setCapability("appPackage", GenericLib.getConfigValue(sConfigFile, "Package"));
		capabilities.setCapability("appActivity", GenericLib.getConfigValue(sConfigFile, "Activity"));
		driver = new AndroidDriver<MobileElement>(new URL(GenericLib.getConfigValue(sConfigFile, "AppiumServer_Url")), capabilities);
		NXGReports.setWebDriver(driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	/*
	 * Description: Fluent wait for element to be visible
	 */
	public static void waitForElement(WebElement ele, String expResult, String actResult) {
		Wait<AndroidDriver> wait = new FluentWait<AndroidDriver>(driver).withTimeout(50, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MICROSECONDS).ignoring(NoSuchElementException.class);
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(ele)) != null, actResult);
		NXGReports.addStep(expResult, LogAs.PASSED, null);
	}
	/*
	 * Description: To perform right to left swipe
	 */
	
	public static void swipeRightToLeft(double startX, double endX) {
		dSize = driver.manage().window().getSize();
		int startx = (int) (dSize.width * startX);
		int endx = (int) (dSize.width * endX);
		int starty = dSize.height / 2;
		driver.swipe(startx, starty, endx, starty, 3000);
	}
	/*
	 * Description: To perform left to right swipe
	 */
	public static void swipeLeftToRight(double startX, double endX) {
		dSize = driver.manage().window().getSize();
		int startx = (int) (dSize.width * startX);
		int endx = (int) (dSize.width * endX);
		int starty = dSize.height / 2;
		driver.swipe(endx, starty, startx, starty, 3000);
	}
	/*
	 * Description: To perform Bottom to Top swipe
	 */
	public static void swipeBottomToTop(double startY, double endY) {
		dSize = driver.manage().window().getSize();
		int starty = (int) (dSize.height * startY);
		int endy = (int) (dSize.height * endY);
		int startx = dSize.width / 2;
		driver.swipe(startx, starty, startx, endy, 3000);
	}
	/*
	 * Description: To perform Top to Bottom swipe
	 */
	public static void swipeTopToBottm(double startY, double endY) {
		dSize = driver.manage().window().getSize();
		int starty = (int) (dSize.height * startY);
		int endy = (int) (dSize.height * endY);
		int startx = dSize.width / 2;
		driver.swipe(startx, starty, startx, endy, 3000);
	}
	/*
	 * Description: To perform element verification
	 */
	public static void elementStatus(WebElement element, String elementName, String checkType)

	{
		switch (checkType) {
		case "displayed":
			try {
				element.isDisplayed();
				NXGReports.addStep(elementName + " is displayed", LogAs.PASSED, null);
			} catch (Exception e) {
				sStatusCnt++;
				NXGReports.addStep(elementName + " is not displayed", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			break;
		case "enabled":
			try {
				element.isEnabled();
				NXGReports.addStep(elementName + " is enabled", LogAs.PASSED, null);
			} catch (Exception e) {
				sStatusCnt++;
				NXGReports.addStep(elementName + " is not enabled", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			break;
		case "selected":
			try {
				element.isSelected();
				NXGReports.addStep(elementName + " is selected", LogAs.PASSED, null);
			} catch (Exception e) {
				sStatusCnt++;
				NXGReports.addStep(elementName + " is not selected", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			break;
		}
	}
	/*
	 * Description: Explicit wait for element to visible
	 */
	public void visibilityOfElementWait(WebElement webElement, String elementName) {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(webElement));
		} catch (Exception e) {
			NXGReports.addStep(elementName + " is not Visible", LogAs.FAILED, null);
		}
	}
	/*
	 * Description: To perform tap operation on the screen
	 */
	public static void tapOnElement(double x, double y) throws InterruptedException {
		Thread.sleep(5000);
		dSize = driver.manage().window().getSize();
		int sx1 = driver.manage().window().getSize().getWidth();
		int sx2 = driver.manage().window().getSize().getHeight();
		int sX = (int) (dSize.width * x);
		int sY = (int) (dSize.height * y);
		driver.tap(1, sX, sY, 1);
	}
	/*
	 * Description: To perform right to left swipe
	 */
	@AfterClass
	public void restapp() {
		driver.resetApp();
	}
	/*
	 * Description: To verify toast messages
	 */
	 public static String verifyToastMessage() throws IOException {
		String result = null;
		File scfile = driver.getScreenshotAs(OutputType.FILE);
		ITesseract instance = new Tesseract();
		try {
			result = instance.doOCR(scfile);
		} catch (TesseractException e) {
			}
		return result;
	 	}
	}