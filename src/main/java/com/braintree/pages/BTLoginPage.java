package com.braintree.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BTLoginPage {

	WebDriver driver;
	static String stageUserName = "makeyanvesh";
	static String stagePassword = "Me!bourne1t";

	
    //Initialize
    public BTLoginPage(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        Thread.sleep(3000);
        PageFactory.initElements(driver, this);

    }

    
	//Objects 
    @FindBy(how=How.ID, using = "login")
    WebElement userName;
    
    @FindBy(how=How.ID, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "commit")
    WebElement loginButton;
    

    //Methods
    public void setDefaultLoginDetails(String environment) throws InterruptedException {
	    
		if(environment.equalsIgnoreCase("stage")) {
			
	    	userName.sendKeys(stageUserName);
	    	password.sendKeys(stagePassword);
			
		}
		else if (environment.equalsIgnoreCase("production")) {
			
			//Param to be defined
			
		}

    }
    
    public BTMainTabPage clickLoginButton() throws InterruptedException {

    	System.out.println("clicking login button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new BTMainTabPage(driver);
    	
    }
	
}
