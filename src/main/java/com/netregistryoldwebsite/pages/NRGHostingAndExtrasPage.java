package com.netregistryoldwebsite.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class NRGHostingAndExtrasPage extends TestBase{

	//Objects         
    @FindBy(how=How.XPATH, using = "//div[@class='next']/form/input[2]")
    WebElement continueButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[1]/input")
    WebElement webHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[2]/input")
    WebElement websiteRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[3]/input")
    WebElement emailHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[4]/input")
    WebElement domainManagerRadioButton;
    
    //Initializing Page Objects
    public NRGHostingAndExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickWebHostingRadioButton(){
    	System.out.println("clicking web hosting radio button");
    	if(webHostingRadioButton.isDisplayed()||webHostingRadioButton.isEnabled()) {
    		webHostingRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickWebsiteRadioButton(){
    	System.out.println("clicking website radio button");
    	if(websiteRadioButton.isDisplayed()||websiteRadioButton.isEnabled()) {
    		websiteRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickEmailHostingRadioButton(){
    	System.out.println("clicking email hosting radio button");
    	if(emailHostingRadioButton.isDisplayed()||emailHostingRadioButton.isEnabled()) {
    		emailHostingRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickDomainManagerRadioButton(){
    	System.out.println("clicking domain manager radio button");
    	if(domainManagerRadioButton.isDisplayed()||domainManagerRadioButton.isEnabled()) {
    		domainManagerRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void addCPanelStarter (String subscription){
    	System.out.println("adding cpanel starter in the cart");
    	
    	
    	WebElement productDropdown = driver.findElement(By.xpath("//div[@id='CPANEL-STARTER']/div/form/div/span"));
//    	if(productDropdown.isDisplayed()||productDropdown.isEnabled()) {
//    		productDropdown.click();
//    	}
//		else {
//			System.out.println("element not found");
//		}    
 //   	new Select(productDropdown).selectByVisibleText("1 Month");
    	productDropdown.click(); 	
    	
    	driver.findElement(By.xpath("//div[@class='x-combo-list-item']/*[contains(text(),'"+straddonproduct+"')]")).click();
    }
       
    public NRGAccountContactPage clickContinueButton() {
    	
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new NRGAccountContactPage();
    }

}
