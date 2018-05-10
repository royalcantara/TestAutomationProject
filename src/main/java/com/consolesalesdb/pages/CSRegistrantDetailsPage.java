package com.consolesalesdb.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSRegistrantDetailsPage extends TestBase{

	//Objects
    @FindBy(how=How.ID, using = "eligibility-form-name-field-0")
    WebElement registrantName;
    
    @FindBy(how=How.XPATH, using = "//div/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[2]/table/tbody/tr/td[2]/em/button")
    WebElement updateButton;
    
    //Initializing Page Objects
    public CSRegistrantDetailsPage() throws InterruptedException {
        PageFactory.initElements(driver, this);
    }

    //Methods
    public CSNrCRMPage setRegistrantDetails(String strregistrantname) throws InterruptedException {
		Thread.sleep(2000);
		registrantName.clear();
		Thread.sleep(2000);
		registrantName.sendKeys(strregistrantname);
		Thread.sleep(3000);
		updateButton.click();
		return new CSNrCRMPage();
    }
    
    
    
    
    
}
