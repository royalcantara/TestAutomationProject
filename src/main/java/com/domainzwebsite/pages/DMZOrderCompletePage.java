package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZOrderCompletePage extends TestBase{

	//Objects   
	@FindBy(how=How.XPATH, using = "//div[@class='inner']/div/h1")
	WebElement orderStatus; 
	
	@FindBy(how=How.XPATH, using = "//div[@class='inner']/div/div[2]/p")
	WebElement orderCompleteMessage; 
	
	@FindBy(how=How.XPATH, using = "//table[@id='shoppingCart']/tbody/tr[2]/td[1]")
	WebElement referenceID; 

	//Initializing Page Objects
	public DMZOrderCompletePage(){
		
		PageFactory.initElements(driver, this);
		
	}

	//Methods
    public Boolean isOrderComplete() throws InterruptedException{
    	Boolean flag = false;
    	System.out.println("Now in order complete page");
    	Thread.sleep(5000);
    	if (orderStatus.getText().contentEquals("Order Complete")) {
    		System.out.println("Order Complete Status");
    		if (orderCompleteMessage.getText().contentEquals("Your order has been submitted successfully. Print out this page as a reference for your order confirmation. The reference ID(s) for your domain(s) are also listed below.")) {
    			System.out.println(orderCompleteMessage.getText());
    			flag = true;
    		}
    	}
    	return flag;
    }
    
	public String getReferenceID() throws InterruptedException{
		String referenceid = null;
		Thread.sleep(5000);
		if (referenceID.isDisplayed()) {
    		referenceid = referenceID.getText();
    		System.out.println("Reference ID: "+ referenceid);  
    	}
    	return referenceid;
    }
}