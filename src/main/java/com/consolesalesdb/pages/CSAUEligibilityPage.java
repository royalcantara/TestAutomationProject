package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSAUEligibilityPage extends TestBase {

	//Objects  
    @FindBy(how=How.ID, using = "eligibility-form-name-field-1")
    WebElement registrantName;
    
    @FindBy(how=How.XPATH, using = "//table[@id='eligibility-form-update-button-1']/tbody/tr/td[2]/em/button")
    WebElement updateButton;
    
    @FindBy(how=How.XPATH, using = "//div[@id='x-form-el-eligibility-au-typeId-field']/div/img") 
    WebElement registrantTypeDropdownButton;
    
    //Initializing Page Objects
    public CSAUEligibilityPage() throws InterruptedException {
        PageFactory.initElements(driver, this);
    }

    //Methods
    public CSNrCRMPage setContactAndEligibilityDetails(String strregistrantname, String strregistranttype, String strregistrantnumber) throws InterruptedException {
    
    	System.out.println("Method: setContactAndEligibilityDetails");
    	registrantName.clear();
		Thread.sleep(2000);
		registrantName.sendKeys(strregistrantname);
		Thread.sleep(2000);
    	driver.findElement(By.xpath("//div[@id='x-form-el-eligibility-au-number-field']/input")).sendKeys(strregistrantnumber);
		Thread.sleep(2000);
		registrantTypeDropdownButton.click();
		Thread.sleep(2000);
    	driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/*[contains(text(),'"+strregistranttype+"')]")).click();    	
    	Thread.sleep(3000);
    	updateButton.click();
    	Thread.sleep(2000);
		
		return new CSNrCRMPage();
    
    }
       
}
