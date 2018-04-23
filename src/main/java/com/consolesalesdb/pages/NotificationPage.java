package com.consolesalesdb.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NotificationPage {


	WebDriver driver;
	
    //Initialize
    public NotificationPage(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        Thread.sleep(2000);
        PageFactory.initElements(driver, this);

    }

  
	//Objects  
    @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
    WebElement okButton;
    
    
    //Methods    
    public NrCRMPage clickOK() throws InterruptedException {
        
    	Thread.sleep(3000);
    	okButton.click();
  	
    	return new NrCRMPage(driver);

    }
	
}
