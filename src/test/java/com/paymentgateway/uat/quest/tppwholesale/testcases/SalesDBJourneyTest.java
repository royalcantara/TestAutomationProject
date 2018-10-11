package com.paymentgateway.uat.quest.tppwholesale.testcases;

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
import com.domainzwebsite.pages.DMZAccountContactPage;
import com.domainzwebsite.pages.DMZAccountPage;
import com.domainzwebsite.pages.DMZAddDomainPrivacyPage;
import com.domainzwebsite.pages.DMZAddExtrasPage;
import com.domainzwebsite.pages.DMZAddHostingPage;
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZCreditCardsDetailsPage;
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
		CSAUEligibilityPage csaueligibilitypage;
		
		//Console Admin Pages
		CALoginPage caloginpage;
		CAHeaderPage caheaderpage;
		CAWorkflowAdminPage caworkflowadminpage;
		CAAccountReferencePage caaccountreferencepage;
		CADomainLevelPage cadomainlevelpage;
				
		TestUtil testUtil;
		String clienttoken;
		public static ExtentTest logger;
	
		public SalesDBJourneyTest() {
			super();
		}


		@Parameters({"environment"})
		@Test(priority=1, enabled = false)
		public void QuestTPPWholesale_VerifyCreateOrderPaidViaPrepaidCardInSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strAccountReference = null;
		String strDomainName_01 = null;
		String strTld_01 = null;
		String strRegistrationPeriod = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strWorkflowId_01 = null;
	
		// Generate test name for domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "testpgquesttppws" + df.format(d1);

		if (environment.equals("uat2")) {
			strAccountReference = "570-03";
			strTld_01 = "com";
			strRegistrationPeriod = "1 x Y AU$15.4[ AU$0 setup]";
			strMajorProduct = "Business cPanel Hosting";
			strProductPeriod = "1 x M AU$12.3[ AU$0 setup]";
			strPaymentMethod = "Resellers Default: Prepaid credit: ";
			strRegistrantDetails = "Payment Gateway Test";			
		}
			
		//Test Step 1: Login to Sales DB page, then create an order for domain and product paid via prepaid card
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("uat");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strAccountReference);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName_01, strTld_01, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName_01, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName_01);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId_01 = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
			
		//Test Step 2: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processMarkAsRegistered(strWorkflowId_01);
		
		//Test Step 3: Verify if domain registration workflow status is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
			
		driver.close();
		
		}
		
		@Parameters({"environment"})
		@Test(priority=2, enabled = true)
		public void QuestTPPWholesale_VerifyCreateOrderPaidViaInvoiceInSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strAccountReference = null;
		String strDomainName_01 = null;
		String strTld_01 = null;
		String strRegistrationPeriod = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strWorkflowId_01 = null;
	
		// Generate test name for domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "testpgquesttppws" + df.format(d1);

		if (environment.equals("uat2")) {
			strAccountReference = "570-03";
			strTld_01 = "com";
			strRegistrationPeriod = "1 x Y AU$15.4[ AU$0 setup]";
			strMajorProduct = "Business cPanel Hosting";
			strProductPeriod = "1 x M AU$12.3[ AU$0 setup]";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Payment Gateway Test";			
		}
			
		//Test Step 1: Login to Sales DB page, then create an order for domain and product paid via invoice
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("uat");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strAccountReference);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName_01, strTld_01, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName_01, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName_01);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId_01 = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
			
		//Test Step 2: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processMarkAsRegistered(strWorkflowId_01);
		
		//Test Step 3: Verify if domain registration workflow status is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
			
		driver.close();
		
		}
}