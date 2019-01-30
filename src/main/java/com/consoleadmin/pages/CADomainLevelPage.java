package com.consoleadmin.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CADomainLevelPage extends TestBase{

	
	//Objects
    @FindBy(how=How.ID, using = "addDomainAddon")
    WebElement addOnProductSubmitButton;
    
    @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
    WebElement okButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='domain-level']/table[3]/tbody/*/td[1]/b[contains(text(),'"+"ZONE"+"')]/parent::td/parent::tr/td[5]/a")
    WebElement editZone;
   
    @FindBy(how=How.XPATH, using = "html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[20]/td[5]")
    WebElement txtValue;
    
    @FindBy(how=How.XPATH, using = "/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/div[@id='domain-level']/a[@class='cp'][2]")
    WebElement loginAsClientLink;
    
    @FindBy(how=How.LINK_TEXT, using = "Account Interface")
    WebElement accountInterfaceLink;
	
	//Initializing Page Objects
    public CADomainLevelPage(){
    	PageFactory.initElements(driver, this);
    }
    
    public CAAccountReferencePage clickAccountInterfaceLink() throws InterruptedException{
    	
    	Thread.sleep(3000);
    	accountInterfaceLink.click();
    	Thread.sleep(3000);
    	
    	return new CAAccountReferencePage();
    }
    
}
