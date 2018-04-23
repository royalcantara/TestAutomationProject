package com.braintree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BTFoundTransactionPage {


	WebDriver driver;

	//Initialize
	public BTFoundTransactionPage(WebDriver driver) throws InterruptedException {

		this.driver = driver;
		Thread.sleep(3000);
		PageFactory.initElements(driver, this);

	}


	//Objects


	//Methods    
    public Boolean isTransactionIDFound() throws InterruptedException {
        
    	Boolean flag = false;
    	Thread.sleep(2000);

    	if (driver.findElement(By.xpath("//header[@class='header_group']/h2")).getText().contentEquals("Found 1 Transaction")) {
    		System.out.println("Transaction ID Found");
    		flag = true;
    	}

    	return flag;

    }
    
    public BTTransactionDetailForIDPage clickTransactionIDInTable(String transactionid) throws InterruptedException {
        
    	Thread.sleep(1000);
    	WebElement transactionIDLink = driver.findElement(By.linkText(transactionid));
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", transactionIDLink);
    	
    	Thread.sleep(1000);
    	transactionIDLink.click();
    	
    	return new BTTransactionDetailForIDPage(driver);
    	
    }
    
    

}