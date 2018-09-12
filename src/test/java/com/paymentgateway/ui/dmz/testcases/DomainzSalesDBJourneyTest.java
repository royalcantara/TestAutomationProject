package com.paymentgateway.ui.dmz.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.braintree.pages.BTFoundTransactionPage;
import com.braintree.pages.BTLoginPage;
import com.braintree.pages.BTMainTabPage;
import com.braintree.pages.BTTransactionsSearchPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
import com.consolesalesdb.pages.CSShowDomainServicesPage;
import com.consolesalesdb.pages.CSWorkflowNotificationPage;
import com.domainzwebsite.pages.DMZAccountPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZLoginPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class DomainzSalesDBJourneyTest extends TestBase{
	
	//Console Sales DB Pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	//Braintree Pages
	BTLoginPage btloginpage;
	BTMainTabPage btmaintabpage;
	BTTransactionsSearchPage bttransactionssearchpage;
	BTFoundTransactionPage btfoundtransactionpage;
	
	//Domainz Cart/Portal Pages
	DMZLoginPage dmzloginpage;
	DMZHeaderPage dmzheaderpage;
	DMZAccountPage dmzaccountpage;
	
	
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public DomainzSalesDBJourneyTest() {
		super();
	}
			
	@Parameters({"environment"})
	@Test(priority=1, enabled = true)
	public void verifySingleDomainOrderInSalesDB (String environment) throws InterruptedException{
		
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = "Domainz";
		String strWorkflowId = null;
		String strTransactionid = null;
		
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
//		strDomainName = "TestPGDomainz" + df.format(d);
		/* For PG-423 testing */
		strDomainName = "TestPG-243-" + df.format(d);
		
		//Initial test data assignment
		if (environment.equals("uat")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "DOM-1302";
//			strPaymentMethod = "Visa: 411111******1111 12/2020";
			strPaymentMethod = "Invoice";
		}
		else if (environment.equals("stagingdomainz")) {
			strTld = "com";
			strRegistrationPeriod = "1";
//			strGreenCode = "PG8-02";
//			strPaymentMethod = "Visa: 401200******7777 02/2020";
			/* For PG-423 testing */
			strGreenCode = "101-28";
			strPaymentMethod = "Visa: 401200******7777 03/2019";
		}
 
		System.out.println("Test: verifySingleDomainOrderInSalesDB");
		initialization(environment, "salesdb");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("stage");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		
//		if ((environment.equals("stagingdomainz")) && (strTld.equals("com"))) {
//			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//			caworkflowadminpage.processMarkAsRegistered(strWorkflowId);
//		}
//		else {
//			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		}
//
//		//Verify if domain registration workflow is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
//		
//		//Get transaction id via pre-auth number in workflow
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
//		strTransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		System.out.println("Transaction ID: " + strTransactionid);
//		driver.close();
//		
//		//Verify if the transaction id status in Braintree is Settling
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage();
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strTransactionid);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strTransactionid), "Settling", btfoundtransactionpage.getTransactionIDStatus(strTransactionid));
//		driver.close();
		
	}
	
	@Parameters({"environment"})
	@Test(priority=2, enabled = false)
	public void verifySingleDomainandSingleProductOrderInSalesDB (String environment) throws InterruptedException{

		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = "Domainz";
		String strWorkflowId = null;
		String strTransactionid = null;
		
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		
		//Initial test data assignment
		if (environment.equals("uat")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "DOM-1302";
			strMajorProduct = "Basic Cloud Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Visa: 411111******1111 12/2020";
		}
		else if (environment.equals("stagingdomainz")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "PG8-02";
			strMajorProduct = "Basic Cloud Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Visa: 401200******7777 02/2020";
		}
 
		System.out.println("Test: verifySingleDomainandSingleProductOrderInSalesDB");
		initialization(environment, "salesdb");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("stage");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName, strTld, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
		
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		
		//Process domain registration
		if ((environment.equals("stagingdomainz")) && (strTld.equals("com"))) {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
			caworkflowadminpage.processMarkAsRegistered(strWorkflowId);
		}
		else {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		}

		//Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
				
		//Process productsetup2
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		caworkflowadminpage.processProductSetup2();
		
		//Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));

		//Get transaction id via pre-auth number in workflow
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
		strTransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
		System.out.println("Transaction ID: " + strTransactionid);
		driver.close();
		
		//Verify if the transaction id status in Braintree is Settling
		initialization(environment, "braintree");
		btloginpage = new BTLoginPage();
		btloginpage.setDefaultLoginDetails("stage");
		btmaintabpage = btloginpage.clickLoginButton();
		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
		bttransactionssearchpage.searchTransactionID(strTransactionid);
		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strTransactionid), "Settling", btfoundtransactionpage.getTransactionIDStatus(strTransactionid));
		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=3, enabled = false)
	public void verifySingleDomainandMultipleProductsOrderInSalesDB (String environment) throws InterruptedException{

		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = "Domainz";
		String strAddOnProduct = null;
		String strWorkflowId = null;
		String strTransactionid = null;
		
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		
		//Initial test data assignment
		if (environment.equals("uat")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "DOM-1302";
			strMajorProduct = "Basic Cloud Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Visa: 411111******1111 12/2020";
		}
		else if (environment.equals("stagingdomainz")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "PG8-02";
			strMajorProduct = "Basic Cloud Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Visa: 401200******7777 02/2020";
			strAddOnProduct = "SEO-ASSESSMENT";
		}
 
		System.out.println("Test: verifySingleDomainandMultipleProductsOrderInSalesDB");
		initialization(environment, "salesdb");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("stage");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName, strTld, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		
		if (environment.equals("stagingdomainz")) {
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
			csshowdomainservicespage.setAddOnProduct(strAddOnProduct);
		}
		else {
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		}
		
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
		
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		
		//Process domain registration
		if ((environment.equals("stagingdomainz")) && (strTld.equals("com"))) {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
			caworkflowadminpage.processMarkAsRegistered(strWorkflowId);
		}
		else {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		}

		//Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
				
		//Process productsetup2
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		caworkflowadminpage.processProductSetup2();
		
		//Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));

		//Get transaction id via pre-auth number in workflow
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
		strTransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
		System.out.println("Transaction ID: " + strTransactionid);
		driver.close();
		
		//Verify if the transaction id status in Braintree is Settling
		initialization(environment, "braintree");
		btloginpage = new BTLoginPage();
		btloginpage.setDefaultLoginDetails("stage");
		btmaintabpage = btloginpage.clickLoginButton();
		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
		bttransactionssearchpage.searchTransactionID(strTransactionid);
		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strTransactionid), "Settling", btfoundtransactionpage.getTransactionIDStatus(strTransactionid));
		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=4, enabled = false)
	public void verifyMultipleProductsOrderInSalesDB (String environment) throws InterruptedException{

		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = "Domainz";
		String strAddOnProduct = null;
		String strWorkflowId = null;
		String strTransactionid = null;
		
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		
		//Initial test data assignment
		if (environment.equals("uat")) {
			strTld = "com";
			strRegistrationPeriod = "Do Not Register";
			strGreenCode = "DOM-1302";
			strMajorProduct = "Basic Cloud Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Visa: 411111******1111 12/2020";
		}
		else if (environment.equals("stagingdomainz")) {
			strTld = "com";
			strRegistrationPeriod = "Do Not Register";
			strGreenCode = "PG8-02";
			strMajorProduct = "Basic Cloud Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Visa: 401200******7777 02/2020";
			strAddOnProduct = "SEO-ASSESSMENT";
		}
 
		System.out.println("Test: verifyMultipleProductsOrderInSalesDB");
		initialization(environment, "salesdb");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("stage");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName, strTld, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		
		if (environment.equals("stagingdomainz")) {
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
			csshowdomainservicespage.setAddOnProduct(strAddOnProduct);
		}
		else {
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		}
		
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
		
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
				
		//Process productsetup2
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId);
		
		//Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));

		//Get transaction id via pre-auth number in workflow
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
		strTransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
		System.out.println("Transaction ID: " + strTransactionid);
		driver.close();
		
		//Verify if the transaction id status in Braintree is Settling
		initialization(environment, "braintree");
		btloginpage = new BTLoginPage();
		btloginpage.setDefaultLoginDetails("stage");
		btmaintabpage = btloginpage.clickLoginButton();
		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
		bttransactionssearchpage.searchTransactionID(strTransactionid);
		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strTransactionid), "Settling", btfoundtransactionpage.getTransactionIDStatus(strTransactionid));
		driver.close();
	}
	
//	@Parameters({"environment"})
//	@Test(priority=2, enabled = false)
//	public void verifyDomainRegistrationInSalesDBForMigratedCustomerNewCard(String environment) throws InterruptedException{
//		
//		String accountreference = "PG9-00";
//		String password = "rENTON11";
//		
//		initialization(environment, "cartlogin");
//		dmzloginpage = new DMZLoginPage();
//		dmzloginpage.setLoginDetails(accountreference, password);
//		dmzheaderpage = dmzloginpage.clickLoginButton();
//		dmzaccountpage = dmzheaderpage.clickAccountTab();
//		driver.close();
//		
//		
//	}
	

	
	
}
