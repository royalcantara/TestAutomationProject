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

public class DomainzDataCreationTest extends TestBase{
	
	//Domainz Customer Portal Pages
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
	DMZLoginPage dmzloginpage;
	DMZHeaderPage dmzheaderpage;
	DMZAccountPage dmzaccountpage;
	DMZCreditCardsDetailsPage dmzcreditcardsdetailspage;
	
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
	@Test(priority=1, enabled = false)
	public void generateCustomerDataWithDomainRegistrationAndEnableAutoRenew(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		Integer intMaxCount = 50;
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
		System.out.println("Start Test: generateCustomerDataWithDomainRegistrationAndEnableAutoRenew");
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
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer - Domain Reg", "Visa", "4111111111111111", "01", "2021", "123");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer - Domain Reg", "Visa", "4005519200000004", "02", "2022", "456");
		}
		
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
		
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Domain Reg", "MasterCard", "2223000048400011", "11", "2023", "678");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Domain Reg", "MasterCard", "5555555555554444", "12", "2024", "990");
		}
		
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
		
		System.out.println("Domainz Test Account Reference [Customer with domain registration (enable auto-renew)]:" + strAccountReference);
		System.out.println("End Test: generateCustomerDataWithDomainRegistrationAndEnableAutoRenew");
		
		}
	}
	
	@Parameters({"environment"})
	@Test(priority=2, enabled = false)
	public void generateCustomerDataWithMonthlyBillingProduct(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId_01 = null;
		String strWorkflowId_02 = null;
		String strWorkflowId_03 = null;
		String strWorkflowId_04 = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		String strProduct = "Basic cPanel Hosting";
		
		Integer intMaxCount = 50;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestDomainzNewCustomer" + df.format(d1);
		strDomainName_02 = "TestDomainzReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".net";
		}
			
		//Test Step 1: Navigate to Domainz search page, then purchase domain and monthly billing product as a new customer
		System.out.println("Start Test: generateCustomerDataWithMonthlyBillingProduct");
		initialization(environment, "cart_domainsearchurl_domainz");
		
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();		
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();		
		dmzaddhostingpage = dmzhostingandextraspage.clickAddHostingButton();
		dmzhostingandextraspage = dmzaddhostingpage.clickAddProduct(strProduct);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer - Monthly Product", "Visa", "4009348888881881", "03", "2025", "777");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer - Monthly Product", "Visa", "4012000033330026", "04", "2026", "234");
		}
			
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
		caworkflowadminpage.processDelegateDomain();
				
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);
		caworkflowadminpage.processSkipDelegation();

		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to Domainz search page, then purchase domain and monthly billing product for returning customer
		initialization(environment, "cart_domainsearchurl_domainz");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddhostingpage = dmzhostingandextraspage.clickAddHostingButton();
		dmzhostingandextraspage = dmzaddhostingpage.clickAddProduct(strProduct);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		dmzbillingpage.selectNewCreditCardOption();
		
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Monthly Product", "MasterCard", "5555555555554444", "09", "2019", "543");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Monthly Product", "MasterCard", "2223000048400011", "12", "2018", "102");
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);	
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_03);
		caworkflowadminpage.processFraudCheck();
		caworkflowadminpage.processDelegateDomain();
		
		strWorkflowId_04 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_04);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_04);
		caworkflowadminpage.processSkipDelegation();
		driver.close();
		
		System.out.println("Domainz Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		System.out.println("End Test: generateCustomerDataWithMonthlyBillingProduct");
		}
	}
	
	@Parameters({"environment"})
	@Test(priority=3, enabled = false)
	public void generateCustomerDataWithYearlyBillingProduct(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId_01 = null;
		String strWorkflowId_02 = null;
		String strWorkflowId_03 = null;
		String strWorkflowId_04 = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		String strProduct = "Business Cloud Hosting";
		
		Integer intMaxCount = 50;
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
			
		//Test Step 1: Navigate to Domainz search page, then purchase domain and yearly billing product as a new customer
		System.out.println("Start Test: generateCustomerDataWithYearlyBillingProduct");
		initialization(environment, "cart_domainsearchurl_domainz");
		
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddhostingpage = dmzhostingandextraspage.clickAddHostingButton();
		dmzhostingandextraspage = dmzaddhostingpage.clickAddProduct(strProduct);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer - Yearly Product", "Visa", "4012000077777777", "05", "2020", "945");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer - Yearly Product", "Visa", "4012888888881881", "06", "2021", "186");
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01  = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
		caworkflowadminpage.processDelegateDomain();
		
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);

		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to Domainz search page, then purchase domain and yearly billing product for returning customer
		initialization(environment, "cart_domainsearchurl_domainz");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddhostingpage = dmzhostingandextraspage.clickAddHostingButton();
		dmzhostingandextraspage = dmzaddhostingpage.clickAddProduct(strProduct);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		dmzbillingpage.selectNewCreditCardOption();
		
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Yearly Product", "MasterCard", "2223000048400011", "07", "2019", "448");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Yearly Product", "MasterCard", "5454545454545454", "08", "2027", "763");
		}

		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);	
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_03);
		caworkflowadminpage.processFraudCheck();
		caworkflowadminpage.processDelegateDomain();
		
		strWorkflowId_04 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_04);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_04);
		driver.close();
		
		System.out.println("Domainz Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		System.out.println("End Test: generateCustomerDataWithYearlyBillingProduct");
		}
	}
	
	@Parameters({"environment"})
	@Test(priority=4, enabled = false)
	public void generateCustomerDataWithOutstandingInvoice(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld_01 = null;
		String strWorkflowId_01 = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";

		String strTld_02 = null;
		String strRegistrationPeriod = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strWorkflowId_02 = null;
		String strWorkflowId_03 = null;
		
		Integer intMaxCount = 2;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestDomainzNewCustomer" + df.format(d1);
		strDomainName_02 = "TestDomainzReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld_01 = ".com";
			
			//For Sales DB
			strTld_02 = "nz";
			strRegistrationPeriod = "1 x Y NZ$74.95[ NZ$0 setup]";
			
			if ((intMinCount % 2)==0) {
				strMajorProduct = "Basic cPanel Hosting";
				strProductPeriod = "1 x M NZ$12.95[ NZ$0 setup]";
			}
			else {
				strMajorProduct = "Business Cloud Hosting";
				strProductPeriod = "1 x Y NZ$329.45[ NZ$0 setup]";
			}
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Payment Gateway Test";			
		}
			
		//Test Step 1: Navigate to Domainz search page and purchase domain name for new customer
		System.out.println("Start Test: generateCustomerDataWithOutstandingInvoice");
		initialization(environment, "cart_domainsearchurl_domainz");
		
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer", "Visa", "4217651111111119", "09", "2022", "480");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer", "Visa", "4500600000000061", "10", "2023", "739");
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
		caworkflowadminpage.processDelegateDomain();

		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Login to Sales DB page, then purchase a domain with monthly or yearly product and pay through invoice
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("stage");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strAccountReference);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName_02, strTld_02, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName_02, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName_02);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId_02 = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
			
		//Test Step 7: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_02);
		
		strWorkflowId_03 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_03);
		driver.close();
	
		System.out.println("Domainz Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		System.out.println("End Test: generateCustomerDataWithOutstandingInvoice");
		
		}
	}
	
	@Parameters({"environment"})
	@Test(priority=5, enabled = true)
	public void generateCustomerDataWithDefaultCreditCard(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		Integer intMaxCount = 50;
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
		System.out.println("Start Test: generateCustomerDataWithDefaultCreditCard");
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
		if ((intMinCount % 2)==0) {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer", "Visa", "4111111111111111", "10", "2020", "715");
		}
		else {
			dmzbillingpage.setQuestFormCreditCardDetails("Domainz Test New Customer", "MasterCard", "2223000048400011", "11", "2019", "994");
		}
		
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
		initialization(environment, "customerportalurl_domainz");
		dmzloginpage = new DMZLoginPage();
		dmzloginpage.setLoginDetails(strAccountReference, strAccountReferenceNewPassword);
		dmzheaderpage = dmzloginpage.clickLoginButton();
		dmzaccountpage = dmzheaderpage.clickAccountTab();
		dmzcreditcardsdetailspage = dmzaccountpage.clickEditCreditCardsOnFile();
		
		if ((intMinCount % 2)==0) {
			dmzcreditcardsdetailspage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Default Credit Card", "MasterCard", "5555555555554444", "05", "2026", "121");
		}
		else {	
			dmzcreditcardsdetailspage.setQuestFormCreditCardDetails("Domainz Test Returning Customer - Default Credit Card", "Visa", "4005519200000004", "06", "2025", "836");
		}
		
		dmzcreditcardsdetailspage.tickMakeCreditCardAsDefaultPayment();
		dmzcreditcardsdetailspage.clickAddCreditCard();
		
		//Test Step 7: Verify if adding new card is successful
		Assert.assertTrue(dmzcreditcardsdetailspage.isNewCreditCardAdded(), "New Credit Card is not added");
		
		
		System.out.println("Domainz Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		System.out.println("End Test: generateCustomerDataWithDefaultCreditCard");
		
		}
	}
	
}


