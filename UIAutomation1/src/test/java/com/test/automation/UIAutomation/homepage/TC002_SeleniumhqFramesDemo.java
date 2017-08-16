package com.test.automation.UIAutomation.homepage;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.automation.UIAutomation.testBase.TestBase;
import com.test.automation.UIAutomation.uiActions.HomePage;

public class TC002_SeleniumhqFramesDemo extends TestBase
{
	public static final Logger log=Logger.getLogger(TC002_SeleniumhqFramesDemo.class.getName());
	HomePage homepage;
	
	@BeforeTest
	public void setUp() throws MalformedURLException
	{
		init();
	}
	

	@Test
	public void linkClickinginFrames() throws Exception
	{	
		log.info("==================Starting TC002_SeleniumhqFramesDemo Test================");
		homepage=new HomePage(driver);
		homepage.seleniumFrames();
		log.info("==================Ending TC002_SeleniumhqFramesDemo Test================");
	}
	
	
	@AfterTest
	public void endTest()
	{
		closeBrowser();
	}

}
