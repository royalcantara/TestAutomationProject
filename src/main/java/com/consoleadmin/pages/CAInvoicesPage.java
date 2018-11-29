package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.base.TestBase;

public class CAInvoicesPage extends TestBase {
	
	// Objects

	
	public CAInvoicesPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public CATaxInvoicePage selectInvoiceNumber(String strinvoicenumber) throws InterruptedException {
		
		driver.findElement(By.xpath("//*[@class='cp'][text()='"+strinvoicenumber+"']/parent::td/parent::tr/td[9]/a[2]")).click();
		Thread.sleep(2000);
		
		return new CATaxInvoicePage();
		
	}
	
	public String getInvoiceNumber() throws InterruptedException {
		
		String invoicenumber;
		
		invoicenumber = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[4]/tbody/tr[2]/td[1]/a")).getText();
		Thread.sleep(2000);
		
		return invoicenumber;
		
	}
	

	


}
