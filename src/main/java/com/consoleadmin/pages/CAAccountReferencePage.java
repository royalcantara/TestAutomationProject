package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CAAccountReferencePage extends TestBase{

	//Objects
    @FindBy(how=How.LINK_TEXT, using = "View Billing Accounts")
    WebElement viewBillingAccountsLink;
		
	//Initializing Page Objects
	public CAAccountReferencePage(){
    	PageFactory.initElements(driver, this);
    }
	
    //Methods
    public void updatePassword(String strnewpassword) throws InterruptedException {
    	
    	//Enter New Password
    	driver.findElement(By.xpath("//div[@id='clientContactDetails']/table/tbody/tr[16]/td[2]/form/table/tbody/tr[1]/td[2]/input")).sendKeys(strnewpassword);
    	Thread.sleep(3000);
    	
    	//Reenter to Verify
    	driver.findElement(By.xpath("//div[@id='clientContactDetails']/table/tbody/tr[16]/td[2]/form/table/tbody/tr[2]/td[2]/input")).sendKeys(strnewpassword);
    	Thread.sleep(3000);
    		
    	//Click update password 
       	driver.findElement(By.xpath("//div[@id='clientContactDetails']/table/tbody/tr[16]/td[2]/form/input[1]")).click();
    	Thread.sleep(3000);

   	
    }
    
    public CAViewCreditCardsPage clickViewBillingAccounts() {
    	
    	viewBillingAccountsLink.click();
    	return new CAViewCreditCardsPage();
    	
    }
}
