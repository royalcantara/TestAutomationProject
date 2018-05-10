package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZHeaderPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[1]/a/span")
    WebElement overviewTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[2]/a/span")
    WebElement orderTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[3]/a/span")
    WebElement billingTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[4]/a/span")
    WebElement supportTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[5]/a/span")
    WebElement notificationsTab;
    
    @FindBy(how=How.LINK_TEXT, using = "Account")
    WebElement accountTab;
    
	//Initializing Page Objects
	public DMZHeaderPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZAccountPage clickAccountTab() throws InterruptedException {

    	System.out.println("clicking account tab");
    	if(accountTab.isDisplayed()||accountTab.isEnabled()) {
    		accountTab.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new DMZAccountPage();
    	
    }
//    
//    public ConsoleClientOrderPage clickOrderTab() throws InterruptedException {
//
//    	System.out.println("clicking order tab");
//    	if(orderTab.isDisplayed()||orderTab.isEnabled()) {
//    		orderTab.click();
//    	}
//		else {
//			System.out.println("element not found");
//		}
//
//    	return new ConsoleClientOrderPage(driver);
//    	
//    }
	
}
