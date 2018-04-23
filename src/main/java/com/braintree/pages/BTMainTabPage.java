package com.braintree.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BTMainTabPage {

	WebDriver driver;
	
    //Initialize
    public BTMainTabPage(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        Thread.sleep(3000);
        PageFactory.initElements(driver, this);

    }
    
    
	//Objects
    @FindBy(how=How.XPATH, using = "//div[@class='nav']/div[2]/ul/li[1]")
    WebElement transactionsLink;
    

    //Methods    
    public BTTransactionsSearchPage clickTransactionsLink() throws InterruptedException {
    	
    	Thread.sleep(3000);
    	transactionsLink.click();
    	
    	return new BTTransactionsSearchPage(driver);
    	
    }
	
	
}
