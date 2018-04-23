package com.consoleadmin.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class CAHeaderPage extends TestBase{

	
	//Objects
    @FindBy(how=How.ID, using = "domainInput")
    WebElement domainInput;

    @FindBy(how=How.XPATH, using = "html/body/table/tbody/tr[1]/td/table/tbody/tr[1]/td[2]/form/input[2]")
    WebElement domainSearchButton;
    
    @FindBy(how=How.NAME, using = "greencode")
    WebElement accountReferenceInput;
    
    @FindBy(how=How.NAME, using = "domain")
    WebElement workflowInput;
    
    //Initializing Page Objects
    public CAHeaderPage(){
    	PageFactory.initElements(driver, this);
    }
	
    //Methods
    public CADomainLevelPage searchDomain(String strdomainname){
    	domainInput.sendKeys(strdomainname);
    	domainSearchButton.click();
    	return new CADomainLevelPage();
    }
    
    public CAAccountReferencePage searchAccountReference(String straccountreference){
    	accountReferenceInput.sendKeys(straccountreference);
    	accountReferenceInput.sendKeys(Keys.ENTER);
    	return new CAAccountReferencePage();
    }
    
    public CAWorkflowAdminPage searchWorkflow(String strworkflow){
    	workflowInput.sendKeys(strworkflow);
    	workflowInput.sendKeys(Keys.ENTER);
    	return new CAWorkflowAdminPage();
    }	
    
    public String verifyHeaderPageTitle(){
    	return driver.getTitle();
    }
    
    public boolean verifyDomainSearchButtonExists(){
    	return domainSearchButton.isDisplayed();
    }
}
