package com.paymentgateway.ui.nrg.testcases;

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
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class NetregistrySalesDBJourneyTest extends TestBase{
	
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

		
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public NetregistrySalesDBJourneyTest() {
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
		String strRegistrantDetails = "Netregistry";
		String strWorkflowId = null;
		String strTransactionid = null;
		Integer i = null;
			
	
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGNetregistry" + df.format(d);
		
		//Initial test data assignment
		if (environment.equals("uat")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "DOM-1302";
			strPaymentMethod = "Visa: 411111******1111 12/2020";
		}
		else if (environment.equals("stagingnetregistryold")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "NET-1261";
			strPaymentMethod = "Invoice";
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
		
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		
		if ((environment.equals("stagingnetregistryold")) && (strTld.equals("com"))) {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//			caworkflowadminpage.processMarkAsRegistered(strWorkflowId);
		}
		else {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		}

		//Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		driver.close();
		
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
	@Test(priority=1, enabled = true)
	public void verifySingleDomainOrderInSalesDB02 (String environment) throws InterruptedException{
		
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = "Netregistry";
		String strWorkflowId = null;
		String strTransactionid = null;
		Integer i = null;
			
	
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGNetregistry" + df.format(d);
		
		//Initial test data assignment
		if (environment.equals("uat")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "DOM-1302";
			strPaymentMethod = "Visa: 411111******1111 12/2020";
		}
		else if (environment.equals("stagingnetregistryold")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "NET-1261";
			strPaymentMethod = "Invoice";
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
		
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		
		if ((environment.equals("stagingnetregistryold")) && (strTld.equals("com"))) {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//			caworkflowadminpage.processMarkAsRegistered(strWorkflowId);
		}
		else {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		}

		//Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		driver.close();
		
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
	@Test(priority=1, enabled = true)
	public void verifySingleDomainOrderInSalesDB03 (String environment) throws InterruptedException{
		
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = "Netregistry";
		String strWorkflowId = null;
		String strTransactionid = null;
		Integer i = null;
			
	
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGNetregistry" + df.format(d);
		
		//Initial test data assignment
		if (environment.equals("uat")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "DOM-1302";
			strPaymentMethod = "Visa: 411111******1111 12/2020";
		}
		else if (environment.equals("stagingnetregistryold")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "NET-1261";
			strPaymentMethod = "Invoice";
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
		
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		
		if ((environment.equals("stagingnetregistryold")) && (strTld.equals("com"))) {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//			caworkflowadminpage.processMarkAsRegistered(strWorkflowId);
		}
		else {
			caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		}

		//Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		driver.close();
		
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
}
