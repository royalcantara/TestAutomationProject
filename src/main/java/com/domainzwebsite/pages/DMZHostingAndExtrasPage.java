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
    
    @FindBy(how=How.XPATH, using = "//div[@class='domainAdd']/form/input[3]")
    WebElement addExtrasButton;
    
    //Initializing Page Objects
    public DMZHostingAndExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZAddExtrasPage clickAddExtrasButton(){
    	System.out.println("clicking add extras button");
    	if(addExtrasButton.isDisplayed()||addExtrasButton.isEnabled()) {
    		addExtrasButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new DMZAddExtrasPage();
    }
    
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
