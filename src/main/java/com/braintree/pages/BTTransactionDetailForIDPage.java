package com.braintree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BTTransactionDetailForIDPage {

	WebDriver driver;
	
    //Initialize
    public BTTransactionDetailForIDPage(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        Thread.sleep(3000);
        PageFactory.initElements(driver, this);

    }
    
    
	//Objects

    
    //Methods    
    public String getCustomerInformation(String parametername) throws InterruptedException {
    	
    	String parametertextvalue = null;
    	Thread.sleep(1000);
    	
    	if (parametername.contentEquals("Company")) {
    		
    		WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[4]/dl/dd[2]"));
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    		parametertextvalue = parametervalue.getText();
    		
    	}
    	
    	return parametertextvalue;
    		
    		
    }
    
    public String getPaymentInformation(String parametername) throws InterruptedException {
    	
    	String parametertextvalue = null;
    	Thread.sleep(1000);
    	
    	if (parametername.contentEquals("Card Type")) {
    		
    		WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[5]/dl/dd[3]"));
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    		parametertextvalue = parametervalue.getText();
    		
    	}
    	else if (parametername.contentEquals("Credit Card Number")) {
    		
    		WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[5]/dl/dd[5]/span"));
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    		parametertextvalue = parametervalue.getText();
    		
    	}	
    	else if (parametername.contentEquals("Expiration Date")) {
    		
    		WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[5]/dl/dd[7]"));
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    		parametertextvalue = parametervalue.getText();
    		
    	}
    	
    	return parametertextvalue;
    		
    		
    }
	
	
}
