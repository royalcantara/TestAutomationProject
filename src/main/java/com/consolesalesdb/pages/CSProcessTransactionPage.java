package com.consolesalesdb.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'11489216')]")
	 WebElement selectInvoice;

	 @FindBy(how= How.XPATH,using ="//*[@id=\'ext-gen2211\']")
	 WebElement TransactionType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'PAYMENT')]")
	 WebElement selectTransactionType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'REFUND')]")
	 WebElement selectTransactionTypeRefund;
	
	 @FindBy(how= How.XPATH,using ="//*[@id=\'ext-gen2223\']")
	 WebElement PaymentType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'Credit Card on file or new card')]")
	 WebElement selectPaymentType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'CC refund of existing transaction')]")
	 WebElement selectPaymentTypeRefund;
	 
	 @FindBy(how= How.XPATH,using ="//input[@name='invoiceItem.amount']")
	 WebElement Amount;

	
	 
	 @FindBy(how= How.XPATH,using ="//*[@id=\'ext-gen2315\']")
	 WebElement existingCreditCard;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'Visa: 4111xxxxxxxx1111')]")
	 WebElement SelectExistingCreditCard;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'Create Transaction')][@type='button']")
	 WebElement CreateTransactionButton;
	 
	 @FindBy(how= How.XPATH,using ="//*[@name='txnRef']")
	 WebElement refundCard;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'NZ$-185.45')]")
	 WebElement selectrefundCardTransaction;
	 
	 @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
	 WebElement okButton;
	 
	
	
	// Initializing Page Objects
	public CSProcessTransactionPage() {
		PageFactory.initElements(driver, this);
	}

	public void clickProcessTransaction() {
		tabProcessTransaction.click();
	}
	
	public void setProcessTransactionDetails(String strgreencode, String strinvoicenumber, String strtransactiontype, String stramount, String strcardnumber) throws InterruptedException, AWTException {
		
		// Enter details on process transaction page
		System.out.println("Navigating to process transaction page");
		Thread.sleep(2000);
		txtGreenCode.sendKeys(strgreencode);

		Thread.sleep(2000);
		Invoice.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(),'"+strinvoicenumber+"')]")).click();

		TransactionType.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(),'"+strtransactiontype+"')]")).click();
		Thread.sleep(2000);

		PaymentType.click();
		Thread.sleep(2000);


		if (strtransactiontype == "PAYMENT") {
			
			driver.findElement(By.xpath("//*[contains(text(),'Credit Card on file or new card')]")).click(); 
			Thread.sleep(2000);
			Amount.sendKeys(stramount);
			Thread.sleep(2000);
			existingCreditCard.click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[contains(text(),'"+strcardnumber+"')]")).click(); 
			Thread.sleep(2000);

		}
		
		else if (strtransactiontype == "REFUND"){
	
			Robot robot = new Robot();
			driver.findElement(By.xpath("//*[contains(text(),'CC refund of existing transaction')]")).click();
			Thread.sleep(2000);
			Amount.sendKeys(stramount);
			Thread.sleep(5000);			
			driver.findElement(By.xpath("//input[@name='txnRef']/parent::div/img")).click();
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_DOWN);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			
		}
		
		CreateTransactionButton.click();
		Thread.sleep(2000);
		
	}
	
    public void clickOKButton() throws InterruptedException {
    	
    	Thread.sleep(2000);
		okButton.click();
	}
	
    public String getConfirmationMessage() throws InterruptedException {
    	
    	String confirmationMessage = driver.findElement(By.xpath("//*[@class='ext-mb-text']")).getText();
		return confirmationMessage;
  	}
	
	
//	
//	public void setProcessTransactionDetailsForRefund(String setGreencode, String domainAmount) throws InterruptedException, AWTException {
//		 Robot robot = new Robot();
//	
//		
//	
//		//Enter details on process transaction page
//				System.out.println("Navigating to process transaction page");
//				Thread.sleep(2000);
//				txtGreenCode.sendKeys(setGreencode);
//				
//				Thread.sleep(2000);
//				Invoice.click();
//				Thread.sleep(2000);
//				selectInvoice.click();
//
//
//				TransactionType.click();
//				Thread.sleep(2000);
//				System.out.println("Selecting payment");
//				selectTransactionTypeRefund.click();
//				//robot.keyPress(KeyEvent.VK_ENTER);
//				Thread.sleep(2000);
//				
//				PaymentType.click();
//				Thread.sleep(2000);
//				selectPaymentTypeRefund.click();
//				Thread.sleep(2000);
//		
//		Amount.sendKeys(domainAmount);
//		Thread.sleep(2000);
//		
//		refundCard.click();
//		Thread.sleep(2000);
//		robot.keyPress(KeyEvent.VK_DOWN);
//		Thread.sleep(2000);
//		robot.keyPress(KeyEvent.VK_ENTER);
//		Thread.sleep(2000);
//		
//		CreateTransactionButton.click();
//		Thread.sleep(2000);
//
//		String confirmationMessage = driver.findElement(By.xpath("//*[@class='ext-mb-text']")).getText();
//		
//		Assert.assertEquals(confirmationMessage, "Item Successfully Added","Domain paid sucessfully");
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//		Thread.sleep(2000);
//
//	}
	
}



