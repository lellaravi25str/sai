package com.test.automation.UIAutomation.homepage;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.automation.UIAutomation.testBase.TestBase;
import com.test.automation.UIAutomation.uiActions.HomePage;


public class TC001_VerifyLoginWithInvalidCredentials extends TestBase
{
	
	public static final Logger log=Logger.getLogger(TC001_VerifyLoginWithInvalidCredentials.class.getName());
	HomePage homepage;
	
	@BeforeTest
	public void setUp() throws MalformedURLException
	{
		init();
	}
	

	@Test
	public void verifyLoginWithInvalidCredentials() throws Exception
	{	
		log.info("==================Starting TC001_VerifyLoginWithInvalidCredentials Test================");
		homepage=new HomePage(driver);
		homepage.loginToApplication("test@gmail.com", "password");
		Thread.sleep(4000);
		Assert.assertEquals(homepage.getInvalidLoginText(), "Authentication failed.");
		log.info("==================Ending TC001_VerifyLoginWithInvalidCredentials Test================");
		
	}
	
	
	@AfterTest
	public void endTest()
	{
		closeBrowser();
	}
	

}
