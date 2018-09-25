package com.consoleregression.testcases.production;

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
import com.consolesalesdb.pages.CSAUEligibilityPage;
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

public class SalesDBJourneyTest extends TestBase{

	
	//Console Sales DB Pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	CSAUEligibilityPage csaueligibilitypage;
	
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

	public SalesDBJourneyTest() {
		super();
	}
			
	@Parameters({"environment"})
	@Test(priority=1, enabled = true)
	public void verify_ComAuDomain_Order_InSalesDB (String environment) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "13328959";
		String strTransactionid = null;
		String strRegistrantType = null;
		String strRegistrantNumber = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleautomation" + df.format(d);
		
		if (environment.equals("prod")) {
			strTld = "com.au";
			strRegistrationPeriod = "2";
			strGreenCode = "MEL-6007";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Netregistry";
			strRegistrantType = "ABN";
			strRegistrantNumber = "13080859721";
		}
		
//		//Test Step 1: Login to sales db and place an order for domain registration
//		System.out.println("Start Test: verify_ComAuDomain_Order_InSalesDB");
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("production");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		csnrcrmpage.setGreenCode(strGreenCode);
//		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
//		cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
//		
//		System.out.println("Method: assign values to aueligibilitypage");
//		csaueligibilitypage = csnrcrmpage.clickUpdateDetails(strDomainName, "Update Details");
//		csnrcrmpage = csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails, strRegistrantType, strRegistrantNumber);
//		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
//		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
//		strWorkflowId = csworkflownotificationpage.getWorkflowID();
//		csworkflownotificationpage.clickOKButton();
//		System.out.println("Workflow ID: " + strWorkflowId);
//		System.out.println("Domain Name: " + strDomainName + "." + strTld);
//		driver.close();
		
		//Test Step 2: Verify if domain registration workflow is completed
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("roy.alcantara", "Stocks005");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		driver.close();
		
		System.out.println("End Test: verify_ComAuDomain_Order_InSalesDB");
		
	}
	
	@Parameters({"environment"})
	@Test(priority=2, enabled = true)
	public void verify_NetDomain_and_DIFM_Order_InSalesDB (String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "13328966";
		String strTransactionid = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleautomation" + df.format(d);

		if (environment.equals("prod")) {
			strTld = "net";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6007";
			strMajorProduct = "Done For You Website";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Netregistry";
		}
	
//		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Done For You Website)
//		System.out.println("Start Test: verify_NetDomain_and_DIFM_Order_InSalesDB");
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("production");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		csnrcrmpage.setGreenCode(strGreenCode);
//		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
//		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName, strTld, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
//		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
//		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
//		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
//		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
//		strWorkflowId = csworkflownotificationpage.getWorkflowID();
//		csworkflownotificationpage.clickOKButton();
//		System.out.println("Workflow ID: " + strWorkflowId);
//		System.out.println("Domain Name: " + strDomainName + "." + strTld);
//		driver.close();
		
		//Test Step 2: Verify if domain registration workflow is completed
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("roy.alcantara", "Stocks005");		
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
				
		//Test Step 3: Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow("testconsoleautomation24092018044231.net");
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
		driver.close();
		
		System.out.println("End Test: verify_NetDomain_and_DIFM_Order_InSalesDB");
	}
	
	
	@Parameters({"environment"})
	@Test(priority=3, enabled = true)
	public void verify_ComDomain_and_Office365_Order_InSalesDB (String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "13329035";
		String strTransactionid = null;
		String strOffice365Product = null;
		
		String strOffice365ProductName = null;
		String strOffice365Quantity = null;
		String strSkykickProduct = null;
		String strSkykickProductName  = null;
		String strSkykickQuantity  = null;
		
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleautomation" + df.format(d);

		if (environment.equals("prod")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "MEL-6007";
			strMajorProduct = "Done For You Website";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Netregistry";
			
			strOffice365Product = "O365-EESEN-QTY";
			strOffice365ProductName = "Office 365 - Email Essentials 1 x Month";
			strOffice365Quantity = "1";
			
		}
	
//		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Done For You Website)
//		System.out.println("Start Test: verify_ComDomain_and_Office365_Order_InSalesDB");
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("production");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		csnrcrmpage.setGreenCode(strGreenCode);
//		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
//		cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
//		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
//		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
//		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
//		csshowdomainservicespage.setAddOnProduct(strOffice365Product);
//		csshowdomainservicespage.setAddOnQuantity(strOffice365ProductName, strOffice365Quantity);
//		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
//		strWorkflowId = csworkflownotificationpage.getWorkflowID();
//		csworkflownotificationpage.clickOKButton();	
//		System.out.println("Workflow ID: " + strWorkflowId);
//		System.out.println("Domain Name: " + strDomainName + "." + strTld);
//		driver.close();
		
		//Test Step 2: Verify if domain registration workflow status is completed
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("roy.alcantara", "Stocks005");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		
		//Test Step 3: Verify if productsetup2 workflow status is approved
		caworkflowadminpage = caheaderpage.searchWorkflow("testconsoleautomation24092018044339.com");
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
		
		//Test Step 4: Verify if o365provisioning workflow status is automatically completed
		caworkflowadminpage = caheaderpage.searchWorkflow("testconsoleautomation24092018044339.com");
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("o365Provisioning"), "automatically completed", caworkflowadminpage.getWorkflowStatus("o365Provisioning"));
		driver.close();
		
		System.out.println("End Test: verify_ComDomain_and_Office365_Order_InSalesDB");
	}
}
