package com.test.automation.UIAutomation.uiActions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage 
{

	public static final Logger log=Logger.getLogger(HomePage.class.getName());
	WebDriver driver;
	
	@FindBy(xpath=".//*[@id='header']/div[2]/div/div/nav/div[1]/a")
	WebElement signIn;
	
	@FindBy(xpath=".//*[@id='email']")
	WebElement loginEmailAddress;
	
	@FindBy(xpath=".//*[@id='passwd']")
	WebElement loginPassword;

	@FindBy(xpath=".//*[@id='SubmitLogin']")
	WebElement submitButton;
	
	@FindBy(xpath=".//*[@id='center_column']/div[1]/ol/li")
	WebElement authenticationFailed;
	
	@FindBy(name="classFrame")
	WebElement thirdframe ;
	
	@FindBy(name="packageFrame")
	WebElement secondframe ;
	
	@FindBy(name="packageListFrame")
	WebElement firstframe ;
	
	@FindBy(linkText="com.thoughtworks.selenium")
	WebElement link1;
	
	@FindBy(linkText="Selenium")
	WebElement link2;
	
	@FindBy(linkText="DefaultSelenium")
	WebElement link3;
	
	
	public HomePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);	
	}
	
	
	public void loginToApplication(String emailAddress,String password)
	{
		signIn.click();
		log.info("Clicked on Sign in and object is:- "+signIn.toString());
		loginEmailAddress.sendKeys(emailAddress);
		log.info("Entered email adress :-"+emailAddress+ "and object is:- "+loginEmailAddress.toString());
		loginPassword.sendKeys(password);
		log.info("Entered password :-"+password+ "and object is:- "+loginPassword.toString());
		submitButton.click();
		log.info("Clicked on Submit button and object is :-"+submitButton.toString());	
	}

	public String getInvalidLoginText()
	{
		log.info("Error message is :-" +authenticationFailed.getText());
		return authenticationFailed.getText();
	}
	
	
	public void seleniumFrames()
	{
		driver.switchTo().frame(firstframe);
		link1.click();
		log.info("Clicked on firstframe and object is:- "+link1.toString());
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame(secondframe);
		link2.click();
		log.info("Clicked on secondframe and object is:- "+link2.toString());
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame(thirdframe);
		link3.click();
		log.info("Clicked on thirdframe and object is:- "+link3.toString());
	}
	
	
	
	
	
}
