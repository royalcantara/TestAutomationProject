package com.paymentgateway.testdatacreation.testcases;

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
import com.domainzwebsite.pages.DMZAccountContactPage;
import com.domainzwebsite.pages.DMZAddDomainPrivacyPage;
import com.domainzwebsite.pages.DMZAddExtrasPage;
import com.domainzwebsite.pages.DMZAddHostingPage;
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZDomainSearchPage;
import com.domainzwebsite.pages.DMZHostingAndExtrasPage;
import com.domainzwebsite.pages.DMZOnlineOrderPage;
import com.domainzwebsite.pages.DMZOrderCompletePage;
import com.domainzwebsite.pages.DMZRegistrantContactPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class DomainzDataCreationTest extends TestBase{
	
	DMZOnlineOrderPage dmzonlineorderpage;
	DMZDomainSearchPage dmzdomainsearchpage;
	DMZAddDomainPrivacyPage dmzadddomainprivacypage;
	DMZHostingAndExtrasPage dmzhostingandextraspage;
	DMZAddHostingPage dmzaddhostingpage;
	DMZAddExtrasPage dmzaddextraspage;
	DMZAccountContactPage dmzaccountcontactpage;
	DMZRegistrantContactPage dmzregistrantcontactpage;
	DMZBillingPage dmzbillingpage;
	DMZOrderCompletePage dmzordercompletepage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CAAccountReferencePage caaccountreferencepage;
	CADomainLevelPage cadomainlevelpage;
	
	//Braintree Pages
	BTLoginPage btloginpage;
	BTMainTabPage btmaintabpage;
	BTTransactionsSearchPage bttransactionssearchpage;
	BTFoundTransactionPage btfoundtransactionpage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public DomainzDataCreationTest() {
		super();
	}

	@Parameters({"environment"})
	@Test(priority=1, enabled = true)
	public void generateDomainRegistrationOrderInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		Integer intMaxCount = 2;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestDomainzNewCustomer" + df.format(d1);
		strDomainName_02 = "TestDomainzReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Navigate to Domainz search page and purchase domain name for new customer
		System.out.println("Start Test: generateDomainRegistrationOrderInCustomerPortal");
		initialization(environment, "cart_domainsearchurl_domainz");
		
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Data Creation", "Visa", "4111111111111111", "12", "2020", "123");
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		caworkflowadminpage.processDelegateDomain();
		
		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to Domainz search page and purchase new domain name for returning customer
		initialization(environment, "cart_domainsearchurl_domainz");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		dmzbillingpage.selectNewCreditCardOption();
		dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Data Creation", "MasterCard", "5454545454545454", "12", "2020", "123");
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		driver.close();
		
		//Test Step 9: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		caworkflowadminpage.processDelegateDomain();
		driver.close();
		
		System.out.println("Domainz Test Account Reference [New Customer with domain registration (enable auto-renew)]:" + strAccountReference);
		
		}
	}
}
