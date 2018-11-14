package com.paymentgateway.uat.domainz.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CADomainLevelPage;
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
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class RegressionTest extends TestBase{
			
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
		CAAccountReferencePage caaccountreferencepage;
		CADomainLevelPage cadomainlevelpage;
		
		TestUtil testUtil;
		public static ExtentTest logger;
		
		public String strWorkflowId_01 = null;
		public String strDomainName_01 = null;
		public String strTld_01 = null;
		public String strAccountReference = null;
		public String strRegistrationPeriod = null;
		public String strPaymentMethod = null;
		public String strRegistrantDetails = null;
		public String strRegistrantType = null;
		public String strRegistrantNumber = null;
		public String strMajorProduct = null;
		public String strProductPeriod = null;
		
		
	
		public RegressionTest() {
			super();
		}


		@Parameters({"environment", "paymentgateway"})
		@Test(priority=1, enabled = true)
		public void testCreateDomainAndMajorProductOrderInSalesDB(String environment, String paymentgateway) throws InterruptedException{

		// Generate test name for domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "testpgregression" + df.format(d1);

		if ((environment.equals("uat1"))&&(paymentgateway.equals("quest"))) {
			strAccountReference = "DOM-1218";
			strTld_01 = "com.au";
			strRegistrationPeriod = "2 x Y";
			strMajorProduct = "Basic cPanel Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Payment Gateway Test";	
			strRegistrantType = "ABN";
			strRegistrantNumber = "13080859721";
		}
			
		//Test Step 1: Login to Sales DB page, then create an order for domain and product 
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("uat");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strAccountReference);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName_01, strTld_01, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		
		if(strTld_01=="com"||strTld_01=="org") {
			
			csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName_01, "Update Details");
			csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
			
		}
		else if(strTld_01=="com.au") {
			
			// AU Eligibility code-  Added on: 13-11-2018
			System.out.println("Method: setContactAndEligibilityDetails");
			csaueligibilitypage = csnrcrmpage.clickUpdateDetails(strDomainName_01, "Update Details");
			csnrcrmpage = csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails, strRegistrantType, strRegistrantNumber);
			
		}
		
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName_01);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		
		//Test Step 2: Verify if the services are succesfully confirmed
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed", "Domain purchased successfully");
		strWorkflowId_01 = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		
		driver.close();
			
		}
		
		@Parameters({"environment", "paymentgateway"})
		@Test(priority=2, enabled = false)
		public void testDomainRegistration2WorkflowInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException{

		//Test Step 1: Login to console admin, then process domainregistration2 workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_01, strTld_01);
		
		//Test Step 2: Verify if domain registration workflow status is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));		
		
		//Test Step 3: Verify if domainregistration2 workflow touchpoints are correct		
		}
		
		@Parameters({"environment", "paymentgateway"})
		@Test(priority=3, enabled = false)
		public void testProductSetup2WorkflowInConsoleAdmin(String environment, String paymentgateway) throws InterruptedException{

		//Test Step 1: Process the productsetup2 workflow in console admin
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName_01 + "." + strTld_01);
		caworkflowadminpage.processProductSetup2();
		caworkflowadminpage.processSkipDelegation();
				
		//Test Step 2: Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName_01 + "." + strTld_01);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
		driver.close();
		
				
		}
}