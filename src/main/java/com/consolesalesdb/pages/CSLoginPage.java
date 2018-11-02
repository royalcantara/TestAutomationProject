package com.consolesalesdb.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSLoginPage extends TestBase{

	static String stageUserName = "erwin.sukarna";
	static String stagePassword = "comein22";
	static String oteUserName = "roy.alcantara";
	static String otePassword = "";
	static String uatUserName = "erwin.sukarna";
	static String uatPassword = "comein22";
	static String prodUserName = "roy.alcantara";
	static String prodPassword = "";
	
	//Objects
    @FindBy(how=How.ID, using = "logon-username-field")
    WebElement userName;

    @FindBy(how=How.ID, using = "logon-password-field")
    WebElement password;
    
    @FindBy(how=How.ID, using = "ext-gen14")
    WebElement loginButton;
	
    //Initializing Page Objects
    public CSLoginPage(){
    	PageFactory.initElements(driver, this);
    }

    //Methods
    public void setDefaultLoginDetails(String environment){
		if(environment.equalsIgnoreCase("stage")) {
	    	userName.sendKeys(stageUserName);
	    	password.sendKeys(stagePassword);
		}
		else if (environment.equalsIgnoreCase("ote")) {
	    	userName.sendKeys(oteUserName);
	    	password.sendKeys(otePassword);
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
    
    public CSNrCRMPage clickLoginButton(){
    	System.out.println("clicking submit button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new CSNrCRMPage();
    }
    
}
