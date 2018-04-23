package com.consoleadmin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class CALoginPage extends TestBase{

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
    
    public CAHeaderPage login(String strusername, String strpassword){
    	userName.sendKeys(strusername);
    	password.sendKeys(strpassword);
    	submitButton.click();
    	return new CAHeaderPage();
    }
}
