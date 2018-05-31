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
import com.domainzwebsite.pages.DMZAccountPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZLoginPage;
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
			
//			//Domainz Cart/Portal Pages
//			DMZLoginPage dmzloginpage;
//			DMZHeaderPage dmzheaderpage;
//			DMZAccountPage dmzaccountpage;
			
			TestUtil testUtil;
			String clienttoken;
			public static ExtentTest logger;

			public NetregistrySalesDBJourneyTest() {
				super();
			}
					
			@Parameters({"environment"})
			@Test(priority=1, enabled = true)
			public void verifyDomainRegistrationInSalesDBForExistingCustomerExistingCard(String environment) throws InterruptedException{
				
				DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
				Date d = new Date();
				String strDomainName = "TestPGDomainz" + df.format(d);
				String strTld = "com";
				String greenCode = "PAY-188";
				String paymentMethod = "Visa: 411111******1111 11/2019";
				String registrantDetails = "Netregistry";
				String workflowid;
				String transactionid;
		 
				System.out.println("Test01: Sales DB");
				initialization(environment, "salesdb");
				csloginpage = new CSLoginPage();
				csloginpage.setDefaultLoginDetails("stage");
				csnrcrmpage = csloginpage.clickLoginButton();
				csnrcrmpage.setGreenCode(greenCode);
				cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
				cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, "1", paymentMethod);
				csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
				csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(registrantDetails);
				csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
				csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
				workflowid = csworkflownotificationpage.getWorkflowID();
				csworkflownotificationpage.clickOKButton();
				driver.close();
				
				initialization(environment, "consoleadmin");
				caloginpage = new CALoginPage();
				caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
				caworkflowadminpage = caheaderpage.searchWorkflow(workflowid);
				caworkflowadminpage.processDomainRegistrationWF(workflowid);
				
				//Verify if domain registration workflow is completed
				caworkflowadminpage = caheaderpage.searchWorkflow(workflowid);
				Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
				
				//Get transaction id via pre-auth number in workflow
				caworkflowadminpage = caheaderpage.searchWorkflow(workflowid);
				Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(workflowid), "Workflow ID not found");
				transactionid = caworkflowadminpage.getPreAuthNumber(workflowid);
				System.out.println("Transaction ID: " + transactionid);
				driver.close();
				
				//Verify if the transaction id status in Braintree is Settling
				initialization(environment, "braintree");
				btloginpage = new BTLoginPage();
				btloginpage.setDefaultLoginDetails("stage");
				btmaintabpage = btloginpage.clickLoginButton();
				bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
				bttransactionssearchpage.searchTransactionID(transactionid);
				btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
				Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
				Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(transactionid), "Settling", btfoundtransactionpage.getTransactionIDStatus(transactionid));
				driver.close();
				
			}	
	
	
}
