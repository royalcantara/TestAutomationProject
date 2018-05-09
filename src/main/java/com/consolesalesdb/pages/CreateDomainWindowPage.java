package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CreateDomainWindowPage extends TestBase{
	
	//Objects
    @FindBy(how=How.ID, using = "create-domain-domainName-field-0.2")
    WebElement domainName;
    
    @FindBy(how=How.XPATH, using = "//div[2]/div/div/img")
    WebElement tldDropdownButton;
    
    @FindBy(how=How.XPATH, using = "//div[3]/div/div/img")
    WebElement regPeriodDropdownButton;
    
    @FindBy(how=How.XPATH, using = "//div[4]/div/div/img")
    WebElement majorProductButton;
    
    @FindBy(how=How.XPATH, using = "//div[5]/div/div/img")
    WebElement productPeriodButton;
  
    @FindBy(how=How.XPATH, using = "//div/div/div/div/div/div/input[2]")
    WebElement paymentMethodButton;
    
    @FindBy(how=How.XPATH, using = "//div/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button")
    WebElement createDomainButton;
   
    @FindBy(how=How.ID, using = "ext-gen2311")
    WebElement updateButton;
	
    //Initializing Page Objects
    public CreateDomainWindowPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods
    public void setDomainDetails(String strdomainame, String strtld, String strperiod, String strpaymentMethod) throws InterruptedException {
    	domainName.sendKeys(strdomainame);
    	tldDropdownButton.click();
    	Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strtld+"']")).click();
        regPeriodDropdownButton.click();
        Thread.sleep(2000);
//      driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strperiod+" x Y NZ$')]")).click(); 
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strperiod+" x Y ')]")).click();
        paymentMethodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strpaymentMethod+"')]")).click();
        Thread.sleep(2000);
        this.clickCreateDomainButton();
    }
 
    public void setDomainDetailswithoutRegister(String strdomainame, String strtld, String strpaymentMethod) throws InterruptedException {
    	domainName.sendKeys(strdomainame);
    	tldDropdownButton.click();
    	Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strtld+"']")).click();
        paymentMethodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strpaymentMethod+"')]")).click();
        Thread.sleep(2000);
        this.clickCreateDomainButton();
    }
    
    public void setDomainandMajorProductDetails(String strdomainame, String strtld, String strperiod, String strmajorproduct, String strproductperiod, String strpaymentMethod) throws InterruptedException {
    	domainName.sendKeys(strdomainame);
    	tldDropdownButton.click();
    	Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strtld+"']")).click();
        regPeriodDropdownButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strperiod+" x Y AU$')]")).click();
        Thread.sleep(2000);
        majorProductButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+strmajorproduct+"']")).click();      
        productPeriodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strproductperiod+" AU$')]")).click();
        paymentMethodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strpaymentMethod+"')]")).click();
        Thread.sleep(2000);
        this.clickCreateDomainButton();
    }
    
    public void setDomainandMajorProductwithoutRegistration(String strdomainame, String strtld, String strmajorproduct, String strproductperiod, String strpaymentMethod) throws InterruptedException {
    	domainName.sendKeys(strdomainame);
    	tldDropdownButton.click();
    	Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strtld+"']")).click();
        Thread.sleep(2000);
        majorProductButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+strmajorproduct+"']")).click();      
        productPeriodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strproductperiod+" AU$')]")).click();
        paymentMethodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strpaymentMethod+"')]")).click();
        Thread.sleep(2000);
        this.clickCreateDomainButton();
    }

    public NrCRMPage clickCreateDomainButton() throws InterruptedException {
    	Thread.sleep(2000);
    	System.out.println("clicking new domain (new price system)");
    	if(createDomainButton.isDisplayed()||createDomainButton.isEnabled()) {
    		createDomainButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new NrCRMPage();
    }

}
