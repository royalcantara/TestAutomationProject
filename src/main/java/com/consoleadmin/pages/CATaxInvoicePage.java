package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.base.TestBase;

public class CATaxInvoicePage extends TestBase{
	
	// Objects
	@FindBy(how = How.NAME, using = "cardOwner")
	WebElement cardOwner;

	@FindBy(how = How.NAME, using = "cardType")
	WebElement cardType;

	@FindBy(how = How.XPATH, using = "//*[@name='cardType']/*[contains(text(),'Master')]")
	WebElement selectCardType;

	@FindBy(how = How.NAME, using = "cardNumber")
	WebElement cardnumber;

	@FindBy(how = How.NAME, using = "expiryMonth")
	WebElement cardExpiryMonth;

	@FindBy(how = How.NAME, using = "expiryYear")
	WebElement cardExpiryYear;

	@FindBy(how = How.XPATH, using = "//*[@type='submit'][@value='Pay Invoice']")
	WebElement btnPayInvoice;
	
    @FindBy(how=How.LINK_TEXT, using = "Invoices")
    WebElement invoicesLink;



	public CATaxInvoicePage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setCreditCardDetails(String strcardowner, String strcardnumber, String strcardexpirymonth, String strcardexpiryyear) throws InterruptedException {
		
		cardOwner.sendKeys(strcardowner);
		Thread.sleep(2000);

		cardType.click();
		Thread.sleep(1000);

		selectCardType.click();
		Thread.sleep(1000);
		cardnumber.sendKeys(strcardnumber);
		Thread.sleep(1000);

		cardExpiryMonth.sendKeys(strcardexpirymonth);
		Thread.sleep(1000);
		cardExpiryYear.sendKeys(strcardexpiryyear);
		Thread.sleep(1000);
	}

	public void payInvoice() throws InterruptedException {

		btnPayInvoice.click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
	}
	
	public String getInvoicePaymentConfirmation() throws InterruptedException {

		String confirmationmessage = driver.findElement(By.xpath("//*[@id=\"msg\"]")).getText();
		System.out.println(confirmationmessage);
		
		return confirmationmessage;
	}
	
    public CAInvoicesPage clickInvoicesLink() {
    	
    	invoicesLink.click();
    	return new CAInvoicesPage();
    	
    }
	
	

}
