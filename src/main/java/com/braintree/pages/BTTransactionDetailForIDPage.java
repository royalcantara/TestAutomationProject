package com.braintree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class BTTransactionDetailForIDPage extends TestBase{

	//Objects
	
    //Initializing Page Objects
    public BTTransactionDetailForIDPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods    
    public String getCustomerInformation(String parametername) throws InterruptedException{
    	
    	String parametertextvalue = null;
    	Thread.sleep(1000);
    	
    	if (parametername.contentEquals("Company")) {
    		WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[4]/dl/dd[2]"));
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    		parametertextvalue = parametervalue.getText();	
    	}
    	return parametertextvalue;		
    }
    
    public String getPaymentInformation(String parametername) throws InterruptedException{
    	
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
