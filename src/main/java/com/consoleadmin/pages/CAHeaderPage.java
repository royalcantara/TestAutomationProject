package com.consoleadmin.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class CAHeaderPage extends TestBase{

	
	//Objects
    @FindBy(how=How.ID, using = "domainInput")
    WebElement domainInput;

    @FindBy(how=How.XPATH, using = "//table[@class='headerbar']/tbody/tr[1]/td[2]/form/input[@class='cp'][2]")
    WebElement searchButton;
    
    @FindBy(how=How.XPATH, using = "//table[@class='headerbar']/tbody/tr[1]/td[3]/form/input[@name='submit']")
    WebElement submitButton;
    
    @FindBy(how=How.XPATH, using = "//table[@class='headerbar']/tbody/tr[1]/td[4]/form/input[@class='cp'][2]")
    WebElement getButton;
    
    @FindBy(how=How.NAME, using = "greencode")
    WebElement accountReferenceInput;
    
    @FindBy(how=How.NAME, using = "domain")
    WebElement workflowInput;
    
    //Initializing Page Objects
    public CAHeaderPage(){
    	PageFactory.initElements(driver, this);
    }
	
    //Methods
    public CADomainLevelPage searchDomain(String strdomainname) throws InterruptedException{
    	
    	Thread.sleep(3000);
    	domainInput.sendKeys(strdomainname);
    	Thread.sleep(3000);
    	searchButton.click();
    	Thread.sleep(5000);
    	
    	return new CADomainLevelPage();
    }
    
    public CAAccountReferencePage searchAccountReference(String straccountreference) throws InterruptedException, AWTException{
    	
    	Thread.sleep(3000);
    	accountReferenceInput.sendKeys(straccountreference);
    	Thread.sleep(3000);
    	System.out.println ("Searching for submit button");
    	Thread.sleep(3000);
    	submitButton.click();
    	System.out.println ("Submit button clicked");
		
    	Thread.sleep(8000);
    	
    	return new CAAccountReferencePage();
    }
    
    public CAWorkflowAdminPage searchWorkflow(String strworkflow) throws InterruptedException{
    	
    	Thread.sleep(3000);
    	workflowInput.sendKeys(strworkflow);
    	Thread.sleep(3000);
    	getButton.click();
    	Thread.sleep(5000);
    	
    	return new CAWorkflowAdminPage();
    }	
    
    public String verifyHeaderPageTitle(){
    	return driver.getTitle();
    }
    
    public boolean verifyDomainSearchButtonExists(){
    	return searchButton.isDisplayed();
    }
}
