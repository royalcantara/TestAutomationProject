package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZAccountPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = ".//*[@id='sidebar']/div/div[2]/ul/li[5]/a")
    WebElement cancelServicesLink;

	//Initializing Page Objects
	public DMZAccountPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
//    public ConsoleClientCancelServicesPage clickCancelServicesLink() throws InterruptedException {
//
//    	System.out.println("clicking cancel services");
//    	if(cancelServicesLink.isDisplayed()||cancelServicesLink.isEnabled()) {
//    		cancelServicesLink.click();
//    	}
//		else {
//			System.out.println("element not found");
//		}
//
//    	return new ConsoleClientCancelServicesPage(driver);
//    	
//    }

//	public ConsoleClientCancelServicesPage editCreditCardsOnFile() throws InterruptedException {
//
//
//  	
//  }
}
