package com.consoleregression.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
import com.domainzwebsite.pages.DMZAccountContactPage;
import com.domainzwebsite.pages.DMZAccountPage;
import com.domainzwebsite.pages.DMZAddDomainPrivacyPage;
import com.domainzwebsite.pages.DMZAddExtrasPage;
import com.domainzwebsite.pages.DMZAddHostingPage;
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZDomainSearchPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZHostingAndExtrasPage;
import com.domainzwebsite.pages.DMZLoginPage;
import com.domainzwebsite.pages.DMZOnlineOrderPage;
import com.domainzwebsite.pages.DMZOrderCompletePage;
import com.domainzwebsite.pages.DMZRegistrantContactPage;
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
	public void verifyDomainRegistrationSingleOrderInSalesDB (String environment) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strTransactionid = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		if (environment.equals("uat1")) {
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "MEL-6007";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Netregistry";
		}
		
		//Test Step 1: Login to sales db and place an order for domain registration
		System.out.println("Start Test: verifyDomainRegistrationSingleOrderInSalesDB");
		initialization(environment, "salesdb");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("uat");
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
		
		//Test Step 2: Process the domain registration order in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		
		//Test Step 3: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		driver.close();
		System.out.println("End Test: verifyDomainRegistrationSingleOrderInSalesDB");
		
	}
	
	@Parameters({"environment"})
	@Test(priority=2, enabled = true)
	public void verifyDomainandSingleProductOrderInSalesDB (String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strTransactionid = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		if (environment.equals("uat1")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6007";
			strMajorProduct = "Done For You Website";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Netregistry";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Done For You Website)
		System.out.println("Start Test: verifyDomainandSingleProductOrderInSalesDB");
		initialization(environment, "salesdb");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("uat");
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
		
		//Test Step 2: Process the domain registration workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);		
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		
		//Test Step 3: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		
		//Test Step 4: Process the productsetup2 workflow in console admin
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		caworkflowadminpage.processProductSetup2();
		
		//Test Step 5: Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "halting current workflow", caworkflowadminpage.getWorkflowStatus("productsetup2"));
		driver.close();
		
		System.out.println("End Test: verifyDomainandSingleProductOrderInSalesDB");
	}
	
}
