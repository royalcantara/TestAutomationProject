package com.consoleadmin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class CALoginPage extends TestBase{

	static String stageUserName = "erwin.sukarna";
	static String stagePassword = "comein22";
	static String oteUserName = "roy.alcantara";
	static String otePassword = "";
	static String cdev2UserName = "erwin.sukarna";
	static String cdev2Password = "comein22";
	static String uatUserName = "erwin.sukarna";
	static String uatPassword = "comein22";
	static String prodUserName = "roy.alcantara";
	static String prodPassword = "Stocks006";
	
	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement userName;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement submitButton;
	    
    //Initializing Page Objects
    public CALoginPage(){
    	PageFactory.initElements(driver, this);
    }
	    
    //Methods
    public boolean validateSubmitButtonExists(){
    	return submitButton.isDisplayed();
    }
    
    public void setDefaultLoginDetails(String environment) throws InterruptedException {
		if(environment.equalsIgnoreCase("stage")) {
	    	userName.sendKeys(stageUserName);
	    	password.sendKeys(stagePassword);
		}
		else if (environment.equalsIgnoreCase("ote")) {
	    	userName.sendKeys(oteUserName);
	    	password.sendKeys(otePassword);
		}
		else if (environment.equalsIgnoreCase("theconsole-dev-2")) {
	    	userName.sendKeys(cdev2UserName);
	    	password.sendKeys(cdev2Password);
		}
		else if (environment.equalsIgnoreCase("uat")) {
	    	userName.sendKeys(uatUserName);
	    	password.sendKeys(uatPassword);
		}
		else if (environment.equalsIgnoreCase("production")) {
	    	userName.sendKeys(prodUserName);
	    	password.sendKeys(prodPassword);
		}
    }
    
    public CAHeaderPage login(String strusername, String strpassword){
    	userName.sendKeys(strusername);
    	password.sendKeys(strpassword);
    	submitButton.click();
    	return new CAHeaderPage();
    }
}
