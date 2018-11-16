package com.consolesalesdb.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.base.TestBase;

public class CSProcessTransactionPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "ext-gen2833")
	WebElement tabProcessTransaction;
	
	 @FindBy(how=How.XPATH, using = "//*[@id=\"ext-gen941\"]")
	 WebElement txtGreenCode;
	
	 @FindBy(how= How.XPATH,using ="//input[@name='invoiceId']")
	 WebElement Invoice;
	 
	 @FindBy(how= How.XPATH,using ="//div[@class='x-combo-list-item  x-combo-selected']")
	 WebElement selectInvoice;

	 @FindBy(how= How.XPATH,using ="//*[@id=\'ext-gen2211\']")
	 WebElement TransactionType;
	 
	 @FindBy(how= How.XPATH,using ="//div[@class='x-combo-list-item  x-combo-selected']")
	 WebElement selectTransactionType;
	 
	
	 @FindBy(how= How.XPATH,using ="//*[@id=\'ext-gen2223\']")
	 WebElement PaymentType;
	 
	 @FindBy(how= How.XPATH,using ="//div[@class='x-combo-list-item  x-combo-selected']")
	 WebElement selectPaymentType;
	 
	 @FindBy(how= How.XPATH,using ="//input[@name='invoiceItem.amount']")
	 WebElement Amount;
	
	 
	 @FindBy(how= How.XPATH,using ="//*[@id=\'ext-gen2315\']")
	 WebElement existingCreditCard;
	 
	 @FindBy(how= How.XPATH,using ="//div[@class='x-combo-list-item  x-combo-selected']")
	 WebElement SelectExistingCreditCard;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'Create Transaction')][@type='button']")
	 WebElement CreateTransactionButton;
	 
	
	// Initializing Page Objects
	public CSProcessTransactionPage() {
		PageFactory.initElements(driver, this);
	}

	
	public void clickProcessTransaction() {
		tabProcessTransaction.click();
	}
	
	public void setProcessTransactionDetails(String setGreencode, String domainAmount) throws InterruptedException, AWTException {
		 Robot robot = new Robot();
		 //Enter details on process transaction page
		 System.out.println("Navigating to process transaction page");
		 Thread.sleep(2000);
		 txtGreenCode.sendKeys(setGreencode);
			
		Thread.sleep(2000);
		Invoice.click();
		Thread.sleep(2000);
		//robot.keyPress(KeyEvent.VK_DOWN);
			
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
			
		TransactionType.click();
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
			
		PaymentType.click();
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
			
		Amount.sendKeys(domainAmount);
		Thread.sleep(2000);
			
		existingCreditCard.click();
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
			
		CreateTransactionButton.click();
		Thread.sleep(2000);
	
		String confirmationMessage = driver.findElement(By.xpath("//*[@class='ext-mb-text']")).getText();
			
		Assert.assertEquals(confirmationMessage, "Item Successfully Added","Domain paid sucessfully");
		Thread.sleep(2000);
			
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
		Thread.sleep(2000);
		driver.close();
	}
	
}


