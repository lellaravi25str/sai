package com.test.automation.UIAutomation.testBase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.UIAutomation.excelReader.ExcelReader;

public class TestBase
{
	
	public static final Logger log=Logger.getLogger(TestBase.class.getName());
	
	public static WebDriver driver;
	public ExcelReader excel;
	String url="http://automationpractice.com/index.php";
	//String url="http://seleniumhq.github.io/selenium/docs/api/java/index.html";
	String browser="chrome";
	//String browser="firefox";
	//Listener lis;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	
	
	
	static 
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "/src/main/java/com/test/automation/uiAutomation/report/test" + formater.format(calendar.getTime()) + ".html", false);
	}
	
	
	public void init() throws MalformedURLException
	{
		selectBrowser(browser);
		//lis=new Listener(driver);
		getUrl(url);
		String log4jConfPath="log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
	}
	
	public void selectBrowser(String browser)
	{
		if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\drivers\\chromedriver.exe");
			log.info("Creating a object of"+browser);
			driver=new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("FIREFOX"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+ "\\drivers\\geckodriver.exe");
			log.info("Creating a object of"+browser);
			driver=new FirefoxDriver();
		}
		
	}
	
	
	/*public void selectBrowser(String browser) throws MalformedURLException
	{
		DesiredCapabilities cap=null;
		
		if(browser.equalsIgnoreCase("chrome"))
		{
			//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\drivers\\chromedriver.exe");
			log.info("Creating a object of"+browser);
			cap=new DesiredCapabilities();
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.ANY);
			String Node = "http://localhost:4444/wd/hub";
			driver = new RemoteWebDriver(new URL(Node), cap);
			
		}
		else if(browser.equalsIgnoreCase("FIREFOX"))
		{
			//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+ "\\drivers\\geckodriver.exe");
			log.info("Creating a object of"+browser);
			cap=new DesiredCapabilities();
			cap.setBrowserName("firefox");
			cap.setPlatform(Platform.ANY);
			String Node = "http://localhost:4444/wd/hub";
			driver = new RemoteWebDriver(new URL(Node), cap);
			
		}
		
		
		
	}*/
	
	
	public void getUrl(String url)
	{
		log.info("Navigating to: "+url);
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		
	}
	
	/*public void closeBrowser()
	{
		driver.quit();
	}*/
	
	
	public String[][] getData(String sheetName,String excelName)
	{
		String path=System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\automation\\UIAutomation\\data\\"+excelName;
		excel=new ExcelReader(path);
		String[][] data=excel.getDataFromSheet(sheetName, excelName);
		return data;
	}

	
	public void waitForElement(WebDriver driver, int timeOutInSeconds, WebElement element) 
	{
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	
	
	public void getresult(ITestResult result) 
	{
		if (result.getStatus() == ITestResult.SUCCESS) 
		{
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} 
		else if (result.getStatus() == ITestResult.SKIP) 
		{
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} 
		else if (result.getStatus() == ITestResult.FAILURE) 
		{
			test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} 
		else if (result.getStatus() == ITestResult.STARTED) 
		{
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}
	
	
	
	public String captureScreen(String fileName) 
	{
		if (fileName == "") 
		{
			fileName = "blank";
		}
		
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try 
		{
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/test/automation/UIAutomation/screenshot/";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return destFile.toString();
	}
	
	
	
	@AfterMethod()
	public void afterMethod(ITestResult result) {
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}
	
	

	public void closeBrowser() 
	{
		driver.quit();
		log.info("browser closed");
		extent.endTest(test);
		extent.flush();
	}
	
	
	
	public void getScreenShot(String name) 
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try 
		{
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/test/automation/uiAutomation/screenshot/";
			File destFile = new File((String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}
