package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CAAccountReferencePage extends TestBase{

	//Objects
    @FindBy(how=How.LINK_TEXT, using = "View Billing Accounts")
    WebElement viewBillingAccountsLink;
    
    @FindBy(how=How.LINK_TEXT, using = "Pay outstanding invoices")
    WebElement payOutstandingInvoicesLink;
		
    @FindBy(how=How.XPATH, using = "/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/div[@id='container']/div[@id='left']/div[@id='clientContactDetails']/table/tbody/tr[16]/td[@class='cp'][2]/form/input")
    WebElement updatePasswordButton;
    
    @FindBy(how=How.XPATH, using = "//div[@id='clientContactDetails']/table/tbody/tr[16]/td[2]/form/table/tbody/tr[1]/td[2]/input")
    WebElement enterNewPasswordTextField;
    
    @FindBy(how=How.XPATH, using = "//div[@id='clientContactDetails']/table/tbody/tr[16]/td[2]/form/table/tbody/tr[2]/td[2]/input")
    WebElement reenterToVerifyTextField;
    
	//Initializing Page Objects
	public CAAccountReferencePage(){
    	PageFactory.initElements(driver, this);
    }
	
    //Methods
    public void updatePassword(String strnewpassword) throws InterruptedException {
    	
    	//Enter New Password
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", enterNewPasswordTextField);
    	enterNewPasswordTextField.sendKeys(strnewpassword);
    	Thread.sleep(3000);
    	
    	//Reenter to Verify
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", reenterToVerifyTextField);
    	reenterToVerifyTextField.sendKeys(strnewpassword);
    	Thread.sleep(3000);
    		
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", updatePasswordButton);
    	System.out.println ("Update Password button found");
    	Thread.sleep(20000);
    	updatePasswordButton.click();
    	System.out.println ("Update Password button clicked");
    	
    	//Click update password 
        Thread.sleep(3000);
    }
    
    public CAViewCreditCardsPage clickViewBillingAccounts() {
    	
    	viewBillingAccountsLink.click();
    	return new CAViewCreditCardsPage();
    	
    }
    
    public CAInvoicesPage clickPayOutstandingInvoices() {
    	
    	payOutstandingInvoicesLink.click();
    	return new CAInvoicesPage();
    	
    }
}
