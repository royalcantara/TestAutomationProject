package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZHostingAndExtrasPage extends TestBase{

	//Objects         
    @FindBy(how=How.XPATH, using = "//div[@class='next']/form/input")
    WebElement continueButton;
    
    //Initializing Page Objects
    public DMZHostingAndExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZAccountContactPage clickContinueButton(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new DMZAccountContactPage();
    }

}
