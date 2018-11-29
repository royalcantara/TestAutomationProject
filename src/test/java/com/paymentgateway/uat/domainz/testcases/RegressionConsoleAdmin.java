package com.paymentgateway.uat.domainz.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CAInvoicesPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAViewCreditCardsPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.ExtentTest;

import com.util.TestUtil;

public class RegressionConsoleAdmin extends TestBase {

		// Console Admin Pages
		CALoginPage caloginpage;
		CAHeaderPage caheaderpage;
		CAWorkflowAdminPage caworkflowadminpage;
		CAAccountReferencePage caaccountreferencepage;
		CADomainLevelPage cadomainlevelpage;
		CAViewCreditCardsPage caviewcreditcardspage;
		CAInvoicesPage cainvoicespage;
	
		TestUtil testUtil;
		public static ExtentTest logger;
		
		public String strVirtualization = "DMZ";
		public String strAccountReference = null;
		public String strExpiryMonth = null;
		public String strExpiryYear =  null;
	
		public RegressionConsoleAdmin() {
			super();
		}
		
		
		@Parameters({"environment", "paymentgateway"})
		@Test(priority=7, enabled = true)
		public void testViewBillingInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException, IOException{
		
			if ((environment.equals("uat1"))&&(paymentgateway.equals("quest"))) {
				strAccountReference = "MEL-6021"; 
			}
			else if ((environment.equals("uat1"))&&(paymentgateway.equals("braintree"))) {
				strAccountReference = "DOM-1305"; 
			}
		
			//Test Step 1: Login to console admin, navigate to account reference page then click view billing accounts
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
			caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
			caviewcreditcardspage = caaccountreferencepage.clickViewBillingAccounts();
			
			//Test Step 2: Verify if user can view billing in console admin
			TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest06");
			Assert.assertTrue(caviewcreditcardspage.isViewCreditcardsPageDisplayed(), "View Creditcards Page is not displayed");
		}
		
		
		
		@Parameters({"environment", "paymentgateway"})
		@Test(priority=8, enabled = true)
		public void testUpdateExpiryInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException, IOException{
		
			if ((environment.equals("uat1"))&&(paymentgateway.equals("quest"))) {
				strExpiryMonth = "01";
				strExpiryYear =  "22";
			}
			else if ((environment.equals("uat1"))&&(paymentgateway.equals("braintree"))) {
				strExpiryMonth = "01";
				strExpiryYear =  "24";
			}
			
			//Test Step 1: Update expiry date and verify if a successful confirmation message will be shown.
			caviewcreditcardspage.updateExpiryDate(strExpiryMonth, strExpiryYear);
		
			TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest07");
			if ((environment.equals("uat1"))&&(paymentgateway.equals("quest"))) {
				Assert.assertEquals(caviewcreditcardspage.getUpdateExpiryConfirmation(), "Credit card: Visa: 4111xxxxxxxx1111 "+strExpiryMonth+"/"+strExpiryYear+" expiry date updated.", "Updated Expiry Sucessfully");
			}
			else if ((environment.equals("uat1"))&&(paymentgateway.equals("braintree"))) {
				Assert.assertEquals(caviewcreditcardspage.getUpdateExpiryConfirmation(), "Credit card: MasterCard: 545454******5454 "+strExpiryMonth+"/20"+strExpiryYear+" expiry date updated.", "Updated Expiry Sucessfully");	
			}
			
			driver.close();	
		}
	
		@Parameters({ "environment", "paymentgateway" })
		@Test(priority = 9, enabled = true)
		public void RechargePrepaidInConsoleAdminUsingExistingCard(String environment, String paymentgateway) throws InterruptedException {
			
			String straccountreference = "DOM-1218";
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caviewcreditcardspage = new CAViewCreditCardsPage();
			caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
			caheaderpage = new CAHeaderPage();
			caheaderpage.searchAccountReference(straccountreference);
			cainvoicespage = new CAInvoicesPage();
	
			Thread.sleep(2000);
			driver.findElement(By.linkText("View Invoice & Prepaid detail")).click();
			Thread.sleep(2000);
			driver.findElement(By.linkText("Prepaid Account Details")).click();
			Thread.sleep(2000);
	
			// cainvoicespage.setCreditCardDetails();
	
			driver.findElement(By.xpath("//*[@id=\"useExistingBilling\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"creditCardAmount\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"creditCardAmount\"]")).sendKeys("20");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id=\"purchaseCredit\"]")).click();
			Thread.sleep(2000);
			driver.switchTo().alert().accept();
			Thread.sleep(2000);
	
			String strConfirmationMessage = driver.findElement(By.xpath("//*[@id=\"msg\"]")).getText();
			System.out.println(strConfirmationMessage);
			Assert.assertEquals(strConfirmationMessage, "Credit purchased successfully");
	
			driver.close();
		}

	
}
