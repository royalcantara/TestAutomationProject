package com.braintree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BTTransactionsSearchPage {

	WebDriver driver;
	
    //Initialize
    public BTTransactionsSearchPage(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        Thread.sleep(3000);
        PageFactory.initElements(driver, this);

    }
    
    
	//Objects
    @FindBy(how=How.XPATH, using = "//form[@id='advanced_search_form']/p/input")
    WebElement searchButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='text_field_inputs']/div[1]/select/optgroup[1]/option[1]")
    WebElement searchFieldForTransactionID;

    
    //Methods    
    public void searchTransactionID(String transactionid) throws InterruptedException {
    	
    	Thread.sleep(3000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchFieldForTransactionID);
    	
    	searchFieldForTransactionID.click();
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[@class='text_field_inputs']/input")).sendKeys(transactionid);
    	Thread.sleep(3000);
    		
    }
    
    public BTFoundTransactionPage clickSearchButton() throws InterruptedException {

    	
    	Thread.sleep(2000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButton);
    	System.out.println("clicking search button");
    	
    	if(searchButton.isDisplayed()||searchButton.isEnabled()) {
    		searchButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new BTFoundTransactionPage(driver);
    	
    }
    
    
	
}
