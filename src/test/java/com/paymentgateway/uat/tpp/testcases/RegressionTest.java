//package com.paymentgateway.uat.tpp.testcases;
//
//import java.awt.AWTException;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.testng.Assert;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.base.TestBase;
//import com.consoleadmin.pages.CAAccountReferencePage;
//import com.consoleadmin.pages.CADomainLevelPage;
//import com.consoleadmin.pages.CAHeaderPage;
//import com.consoleadmin.pages.CAInvoicesPage;
//import com.consoleadmin.pages.CALoginPage;
//import com.consoleadmin.pages.CATaxInvoicePage;
//import com.consoleadmin.pages.CAViewCreditCardsPage;
//import com.consoleadmin.pages.CAWorkflowAdminPage;
//import com.consolesalesdb.pages.CSAUEligibilityPage;
//import com.consolesalesdb.pages.CSAccountPage;
//import com.consolesalesdb.pages.CSCreateDomainWindowPage;
//import com.consolesalesdb.pages.CSLoginPage;
//import com.consolesalesdb.pages.CSNrCRMPage;
//import com.consolesalesdb.pages.CSProcessTransactionPage;
//import com.consolesalesdb.pages.CSRegistrantDetailsPage;
//import com.consolesalesdb.pages.CSShowDomainServicesPage;
//import com.consolesalesdb.pages.CSWorkflowNotificationPage;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.util.TestUtil;
//
//public class RegressionTest extends TestBase{
//			
//		//Console Sales DB Pages
//		CSLoginPage csloginpage;
//		CSNrCRMPage csnrcrmpage;
//		CSCreateDomainWindowPage cscreatedomainwindowpage;
//		CSRegistrantDetailsPage csregistrantdetailspage;
//		CSShowDomainServicesPage csshowdomainservicespage;
//		CSWorkflowNotificationPage csworkflownotificationpage;
//		CSAUEligibilityPage csaueligibilitypage;
//		CSAccountPage csaccountpage;
//		CSProcessTransactionPage csprocesstransactionpage;
//		
//		//Console Admin Pages
//		CALoginPage caloginpage;
//		CAHeaderPage caheaderpage;
//		CAWorkflowAdminPage caworkflowadminpage;
//		CAAccountReferencePage caaccountreferencepage;
//		CADomainLevelPage cadomainlevelpage;
//		CAViewCreditCardsPage caviewcreditcardspage;
//		CAInvoicesPage cainvoicespage;
//		CATaxInvoicePage cataxinvoicepage;
//		
//		TestUtil testUtil;
//		public static ExtentTest logger;
//		
//		public String strWorkflowId_01 = null;
//		public String strDomainName_01 = null;
//		public String strTld_01 = null;
//		public String strAccountReference = null;
//		public String strRegistrationPeriod = null;
//		public String strPaymentMethod = null;
//		public String strRegistrantDetails = null;
//		public String strRegistrantType = null;
//		public String strRegistrantNumber = null;
//		public String strMajorProduct = null;
//		public String strProductPeriod = null;
//		public String strVirtualization = "TPP";
//		
//		
//	
//		public RegressionTest() {
//			super();
//		}
//
//
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=1, enabled = false)
//		public void testCreateDomainAndMajorProductOrderInSalesDB(String environment, String paymentgateway) throws InterruptedException, IOException{
//
//		// Generate test name for domain
//		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
//		Date d1 = new Date();
//		strDomainName_01 = "testpgregression" + df.format(d1);
//
//		if ((environment.equals("uat1"))&&(paymentgateway.equals("quest"))) {
//			strAccountReference = "MEL-6038";
//			strTld_01 = "org";
//			strRegistrationPeriod = "1 x Y";
//			strMajorProduct = "Domain Manager";
//			strProductPeriod = "1 x M";
//			strPaymentMethod = "Invoice";
//			//strPaymentMethod = "Resellers Default: Visa: 4111xxxxxxxx1111";
//			strRegistrantDetails = "Payment Gateway Test";	
//			strRegistrantType = "ABN";
//			strRegistrantNumber = "13080859721";
//		}
//		else if ((environment.equals("uat1"))&&(paymentgateway.equals("braintree"))) {
//			
//			//param values to be defined 
//		}
//			
//		//Test Step 1: Login to Sales DB page, then create an order for domain and product 
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("uat");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		csnrcrmpage.setGreenCode(strAccountReference);
//		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
//		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName_01, strTld_01, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
//		
//        if (strTld_01=="com.au") {
//    		
//			// AU Eligibility code-  Added on: 13-11-2018
//			System.out.println("Method: setContactAndEligibilityDetails");
//			csaueligibilitypage = csnrcrmpage.clickUpdateDetails(strDomainName_01, "Update Details");
//			csnrcrmpage = csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails, strRegistrantType, strRegistrantNumber);
//		}
//        else {
//        	
//			//For com, net, nz, org, info tlds
//			csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName_01, "Update Details");
//			csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
//        }
//		
//		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName_01);
//		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
//		
//		//Test Step 2: Verify if the services are successfully confirmed
//		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed", "Domain purchased successfully");
//		strWorkflowId_01 = csworkflownotificationpage.getWorkflowID();
//		
//		TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest01");
//		csworkflownotificationpage.clickOKButton();
//		driver.close();
//			
//		}
//		
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=2, enabled = false)
//		public void testDomainRegistration2WorkflowInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException, IOException{
//
//		//Test Step 1: Login to console admin, then process domainregistration2 workflow		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
//		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_01, strTld_01);
//		
//		//Test Step 2: Verify if domainregistration2 workflow touchpoints are correct	
//		if (paymentgateway.equals("quest")) {
//
////			Assert.assertEquals(caworkflowadminpage.getDomainRegistration2WorkflowParameterValue("payment.accounting.productCode"), "PAYMT-CC");
////			Assert.assertEquals(caworkflowadminpage.getDomainRegistration2WorkflowParameterValue("payment.preauth.txnref"), "11111111");
////			Assert.assertEquals(caworkflowadminpage.getDomainRegistration2WorkflowParameterValue("payment.completion.rrn"), "999999");
////			Assert.assertEquals(caworkflowadminpage.getDomainRegistration2WorkflowParameterValue("payment.accounting.description"), "Online Credit Card Payment Amex Card# 4111xxxxxxxx1111");
////			Assert.assertEquals(caworkflowadminpage.getDomainRegistration2WorkflowParameterValue("payment.preauth.response"), "approved");
////			Assert.assertEquals(caworkflowadminpage.getDomainRegistration2WorkflowParameterValue("payment.preauth.preauth"), "11111111");
//		
//		}
//		else if (paymentgateway.equals("braintree")) {
//			
//			/* will include assertion */
//		}
//		
//		//Test Step 3: Verify if domain registration workflow status is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));		
//
//		TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest02");
//		}
//		
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=3, enabled = false)
//		public void testProductSetup2WorkflowInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException, IOException{
//
//		//Test Step 1: Process the productsetup2 workflow in console admin
//		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName_01 + "." + strTld_01);
//		caworkflowadminpage.processProductSetup2();
//		//caworkflowadminpage.processSkipDelegation();
//				
//		//Test Step 2: Verify if productsetup2 workflow is approved
//		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName_01 + "." + strTld_01);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
//				
//		TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest03");
//		driver.close();
//		}
//		
//		
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=4, enabled = false)
//		public void testPayInvoiceUsingExistingCardFromSalesDB(String environment, String paymentgateway) throws InterruptedException, AWTException{
//		
//		// Initialization (Test Data Creation and Assignment)
//		strAccountReference = "DOM-1218";
//		String strInvoiceNumber = "11489233";
//		String strAmount = "118.73";
//		String strTransactionType= "PAYMENT";
//		
//		//Test Step 1: Login to Sales DB page, then pay for an existing invoice for domain and product via existing credit card
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("uat");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		
//		csaccountpage = new CSAccountPage();
//		csaccountpage.clickAccountTab();
//		csprocesstransactionpage = new CSProcessTransactionPage();
//		csprocesstransactionpage.setProcessTransactionDetails(strAccountReference, strInvoiceNumber, strTransactionType,  strAmount);
//		//driver.close();
//		}
//		
//		
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=5, enabled = false)
//		public void testRefundPaymentInSalesDB(String environment, String paymentgateway) throws InterruptedException, AWTException{
//		
//		// Initialization (Test Data Creation and Assignment)
//		strAccountReference = "DOM-1218";
//		String strInvoiceNumber = "11489233";
//		String strAmount = "118.73";
//		String strTransactionType= "REFUND";
//		
//		
//		//Test Step 1: Login to Sales DB page, then refund an invoice
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("uat");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		
//		csaccountpage = new CSAccountPage();
//		csaccountpage.clickAccountTab();
//		
//		csprocesstransactionpage = new CSProcessTransactionPage();
//		csprocesstransactionpage.setProcessTransactionDetails(strAccountReference, strInvoiceNumber, strTransactionType,  strAmount);
//			
//		driver.close();
//		}
//		
//		
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=6, enabled = true)
//		public void testViewBillingInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException, IOException{
//		
//		strAccountReference = "MEL-6038"; 
//
//		//Test Step 1: Login to console admin, then verify if user can view billing in console admin
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
//		caviewcreditcardspage = caaccountreferencepage.clickViewBillingAccounts();
//		
//		TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest06");
//		Assert.assertTrue(caviewcreditcardspage.isViewCreditcardsPageDisplayed(), "View Creditcards Page is not displayed");
//		}
//		
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=7, enabled = true)
//		public void testUpdateExpiryInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException, IOException{
//		
//		String strExpiryMonth = "01";
//		String strExpiryYear =  "32";
//
//		//Test Step 1: Update expiry date and verify if a successful confirmation message will be shown.
//		caviewcreditcardspage.updateExpiryDate(strExpiryMonth, strExpiryYear);
//		
//		TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest07");
//		Assert.assertEquals(caviewcreditcardspage.getUpdateExpiryConfirmation(), "Credit card: Visa: 4111xxxxxxxx1111 "+strExpiryMonth+"/"+strExpiryYear+" expiry date updated.", "Updated Expiry Sucessfully");		
//		driver.close();	
//		}
//		
//		@Parameters({"environment", "paymentgateway"})
//		@Test(priority=8, enabled = false)
//		public void testPayingSingleInvoiceInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException{
//		
//		strAccountReference = "DOM-1218";
//		String strinvoicenumber = "11489218";
//		
//		String strCardOwner = "Test Master";
//		String strCardNumber = "5454545454545454";
//		String strCardExpiryMonth = "02";
//		String strCardExpiryYear = "20";
//
//		// Test Step 1: Login to console admin and pay an existing invoice using a new card
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
//		cainvoicespage = caaccountreferencepage.clickPayOutstandingInvoices();
//		cataxinvoicepage = cainvoicespage.selectInvoiceNumber(strinvoicenumber);
//		cataxinvoicepage.setCreditCardDetails(strCardOwner, strCardNumber, strCardExpiryMonth, strCardExpiryYear);
//		cataxinvoicepage.payInvoice();
//		Assert.assertEquals(cataxinvoicepage.getInvoicePaymentConfirmation(), "The payment of NZ$100.00 for invoice 11489218 has been accepted");
//		Thread.sleep(1000);
////
////			cainvoicespage.setCreditCardDetails();
////
////			cainvoicespage.payInvoice();
////
////			driver.findElement(By.linkText("Invoices")).click();
////			Thread.sleep(2000);
////
////			String strOutstanding = driver
////					.findElement(By.xpath("//*[@class='cp'][text()='11489218']/parent::td/parent::tr/td[7]")).getText();
////			String strOutstandingAmount = strOutstanding.trim();
////			System.out.println(strOutstandingAmount);
////
////			Assert.assertEquals(strOutstandingAmount, "NZ$0.00");
////
////			driver.close();
//		}
//		
//		
//		
//}