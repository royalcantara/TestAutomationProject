package com.netregistryoldwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;


public class NRGDomainSearchPage extends TestBase{

	//Objects     
    @FindBy(how=How.ID, using = "continueCart")
    WebElement continueToCheckoutButton;
	
	//Initializing Page Objects
    public NRGDomainSearchPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public NRGAddDomainPrivacyPage clickContinueToCheckout(){
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new NRGAddDomainPrivacyPage();
    }
    
    public NRGHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy(){
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new NRGHostingAndExtrasPage();
    }
    
}
