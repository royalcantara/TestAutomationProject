package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

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
	


}
