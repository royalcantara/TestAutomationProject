package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSNrCRMPage extends TestBase{

	//Objects
    @FindBy(how=How.ID, using = "Greencode-searchbox")
    WebElement greenCode;

    @FindBy(how=How.XPATH, using = "//div[@id='Domain Details']/div/div[1]/div/table/tbody/tr/td[3]/table/tbody/tr/td[2]/em/button")
    WebElement newDomainNPS;

    @FindBy(how=How.ID, using = "eligibility-form-name-field-0")
    WebElement registrantName;
    
    //Initializing Page Objects
    public CSNrCRMPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setGreenCode(String strgreencode) throws InterruptedException {
    	Thread.sleep(1000);
    	greenCode.sendKeys(strgreencode);
    	Thread.sleep(1000);
    	greenCode.sendKeys(Keys.ENTER);
    }
    
    public CSWorkflowNotificationPage clickConfirmDomain(String strdomainname) throws InterruptedException {
    	Thread.sleep(3000);
    	Actions act = new Actions(driver);
    	act.moveToElement(driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='"+strdomainname+"']"))).doubleClick().build().perform();
    	driver.findElement(By.xpath("//li/a[contains(text(),'(New Pricing Structure) : Confirm Domain (via workflow)')]")).click();
    	Thread.sleep(2000);
    	return new CSWorkflowNotificationPage();
    }
    
    public CSUpgradeServiceWindowPage clickUpgradeHosting(String strdomainname) throws InterruptedException {
    	Thread.sleep(3000);
    	Actions act = new Actions(driver);
    	act.moveToElement(driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='"+strdomainname+"']"))).doubleClick().build().perform();
    	driver.findElement(By.xpath("//li/a[contains(text(),'(New Pricing Structure) : Upgrade Hosting (via workflow)')]")).click();
    	Thread.sleep(2000);
    	return new CSUpgradeServiceWindowPage();
    }
    
    public CSShowDomainServicesPage clickShowDomainServices(String strdomainname) throws InterruptedException {
    	String strdomainnamelowercase = strdomainname.toLowerCase();
    	
    	Thread.sleep(3000);
    	Actions act = new Actions(driver);
    	act.moveToElement(driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='"+strdomainnamelowercase+"']"))).doubleClick().build().perform();
    	driver.findElement(By.xpath("//li/a[contains(text(),'(New Pricing Structure) : Show Domain Services')]")).click();
    	Thread.sleep(2000);
    	return new CSShowDomainServicesPage();
    }
    
    public CSRegistrantDetailsPage clickRegistrantDetails(String strdomainname, String strregistrantdetails) throws InterruptedException {
    	String strdomainnamelowercase = strdomainname.toLowerCase();
    	
    	Thread.sleep(10000);
		driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='"+strdomainnamelowercase+"']/parent::td/parent::tr/td[11]/div/a/b[text()='"+strregistrantdetails+"']")).click();
		Thread.sleep(2000);
    	return new CSRegistrantDetailsPage();
    }
      
    public CSCreateDomainWindowPage clickNewDomainNPSButton() throws InterruptedException {
    	Thread.sleep(3000);
    	System.out.println("clicking new domain (new price system)");
    	if(newDomainNPS.isDisplayed()||newDomainNPS.isEnabled()) {
    		newDomainNPS.click();
    	}
		else {
			System.out.println("element not found");
		}
    	Thread.sleep(2000);
    	return new CSCreateDomainWindowPage();
    }


}
