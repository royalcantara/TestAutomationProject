package com.paymentgateway.testdatacreation.testcases;

import java.awt.AWTException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
	public Integer countDataMigrationGC = 0;
	public List<String> arrDataMigrationGC = new ArrayList<String>();
	public List<String> arrDataMigrationGCAndCCDetails = new ArrayList<String>();
	
	
	public DomainzDataCreationTest() {
		super();
	}

	public void saveGreenCode(String strgreencode) {
		System.out.println("preparing to save greencode");
		arrDataMigrationGC.add(strgreencode) ;
		System.out.println("done saving greencode");
	}
	
	public void saveGreenCodeAndCreditCardDetails(String strgreencode, String strcardnumber, String strcardowner, String strcardexpirymonth, String strcardexpiryyear) {
		System.out.println("preparing to save greencode and card details");
		arrDataMigrationGCAndCCDetails.add(strgreencode+","+strcardnumber+","+'"'+strcardowner+'"'+","+strcardexpirymonth+","+strcardexpiryyear);
		System.out.println("done saving greencode and card details");
	}
	
	@Parameters({"environment"})
	@Test
	public void generateCustomerDataWithDomainRegistrationAndEnableAutoRenew(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = 4;
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
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test New Customer - Domain Reg";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "125";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test New Customer - Domain Reg";
			strCardType = "Visa";
			strCardNumber = "4009348888881881";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "457";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test New Customer - Domain Reg";
			strCardType = "Visa";
			strCardNumber = "4012000033330026";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "781";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else {
			strCardOwnerName = "Domainz Test New Customer - Domain Reg";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "569";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("Domainz Test Migration Data [Customer with domain registration (enable auto-renew)]:" + strAccountReference + "," + strCardNumber + "," + strCardExpiryMonth + "," + strCardExpiryYear);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		
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
		
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test Returning Customer - Domain Reg";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "112";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test Returning Customer - Domain Reg";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2026";
		    strCardSecurityCode = "883";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test Returning Customer - Domain Reg";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "670";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else {
			strCardOwnerName = "Domainz Test Returning Customer - Domain Reg";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "12";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "991";
			dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("Domainz Test Migration Data [Customer with domain registration (enable auto-renew)]:" + strAccountReference + "," + strCardNumber + "," + strCardExpiryMonth + "," + strCardExpiryYear);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithDomainRegistrationAndEnableAutoRenew");
		
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void generateCustomerDataWithMonthlyBillingProduct(String environment) throws InterruptedException, AWTException{
	
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
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = 4;
		Integer intMinCount = null;
		for(intMinCount = 4; intMinCount<=intMaxCount; intMinCount++) {

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
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test New Customer - Monthly Product";
			strCardType = "Visa";
			strCardNumber = "4009348888881881";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "776";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test New Customer - Monthly Product";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
		    strCardExpiryMonth = "10";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "443";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test New Customer - Monthly Product";
			strCardType = "Visa";
			strCardNumber = "4500600000000061";
		    strCardExpiryMonth = "12";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "992";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "Domainz Test New Customer - Monthly Product";
			strCardType = "Visa";
			strCardNumber = "4012000033330026";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2027";
		    strCardSecurityCode = "238";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
			
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("Domainz Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
				
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);

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
		
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test Returning Customer - Monthly Product";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "09";
		    strCardExpiryYear = "2020";
		    strCardSecurityCode = "545";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test Returning Customer - Monthly Product";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "108";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test Returning Customer - Monthly Product";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "731";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "Domainz Test Returning Customer - Monthly Product";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "10";
		    strCardExpiryYear = "2019";
		    strCardSecurityCode = "106";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);
		System.out.println("Domainz Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_03);
		caworkflowadminpage.processFraudCheck();
		
		strWorkflowId_04 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_04);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_04);
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithMonthlyBillingProduct");
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void generateCustomerDataWithYearlyBillingProduct(String environment) throws InterruptedException, AWTException{
	
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
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = 4;
		Integer intMinCount = null;
		for(intMinCount = 3; intMinCount<=intMaxCount; intMinCount++) {

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
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test New Customer - Yearly Product";
			strCardType = "Visa";
			strCardNumber = "4012000077777777";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "943";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test New Customer - Yearly Product";
			strCardType = "Visa";
			strCardNumber = "4217651111111119";
		    strCardExpiryMonth = "11";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "668";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test New Customer - Yearly Product";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "705";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "Domainz Test New Customer - Yearly Product";
			strCardType = "Visa";
			strCardNumber = "4012888888881881";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "185";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01  = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("Domainz Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
		
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);
		driver.close();

		//Test Step 5: Set the new password for the account reference
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
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
		
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test Returning Customer - Yearly Product";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "433";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test Returning Customer - Yearly Product";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "09";
		    strCardExpiryYear = "2020";
		    strCardSecurityCode = "331";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test Returning Customer - Yearly Product";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2019";
		    strCardSecurityCode = "400";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "Domainz Test Returning Customer - Yearly Product";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "755";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}

		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);	
		System.out.println("Domainz Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_03);
		caworkflowadminpage.processFraudCheck();
		
		strWorkflowId_04 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_04);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_04);
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithYearlyBillingProduct");
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void generateCustomerDataWithOutstandingInvoice(String environment) throws InterruptedException, AWTException{
	
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
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = 4;
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
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "Visa";
			strCardNumber = "4217651111111119";
		    strCardExpiryMonth = "09";
		    strCardExpiryYear = "2020";
		    strCardSecurityCode = "443";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "Visa";
			strCardNumber = "4009348888881881";
		    strCardExpiryMonth = "10";
		    strCardExpiryYear = "2020";
		    strCardSecurityCode = "762";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "Visa";
			strCardNumber = "4012888888881881";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2019";
		    strCardSecurityCode = "730";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "Visa";
			strCardNumber = "4500600000000061";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "782";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("Domainz Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
		//caworkflowadminpage.processDelegateDomain();

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
		System.out.println("Domainz Test Payment Method: " + strPaymentMethod);
		System.out.println("Domainz Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		
		
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
	
		System.out.println("End Test: generateCustomerDataWithOutstandingInvoice");
		
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void generateCustomerDataWithDefaultCreditCard(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		
		Integer intMaxCount = 4;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestDomainzNewCustomer" + df.format(d1);
					
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
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "10";
		    strCardExpiryYear = "2019";
		    strCardSecurityCode = "714";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "Visa";
			strCardNumber = "4009348888881881";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2026";
		    strCardSecurityCode = "216";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "443";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "Domainz Test New Customer";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "11";
		    strCardExpiryYear = "2020";
		    strCardSecurityCode = "966";
		    dmzbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("Domainz Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		
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
		
		if (intMinCount == 4) {
			strCardOwnerName = "Domainz Test Returning Customer - Default Credit Card";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "122";	
			dmzcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "Domainz Test Returning Customer - Default Credit Card";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "178";	
			dmzcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "Domainz Test Returning Customer - Default Credit Card";
			strCardType = "Visa";
			strCardNumber = "4012000077777777";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "144";	
			dmzcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else {	
			strCardOwnerName = "Domainz Test Returning Customer - Default Credit Card";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2019";
		    strCardSecurityCode = "811";	
		    dmzcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		System.out.println("Domainz Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		dmzcreditcardsdetailspage.tickMakeCreditCardAsDefaultPayment();
		dmzcreditcardsdetailspage.clickAddCreditCard();
		
		//Test Step 7: Verify if adding new card is successful
		Assert.assertTrue(dmzcreditcardsdetailspage.isNewCreditCardAdded(), "New Credit Card is not added");
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithDefaultCreditCard");
		
		}
	}
	
	@Parameters({"environment"})
	@Test(priority=6, enabled = true)
	public void createTextFileForGreencodeAndCreditCardDetails (String environment) throws InterruptedException, IOException{
		
		Integer intMaxCount = arrDataMigrationGCAndCCDetails.size();
		Integer intMinCount = null;
		PrintWriter writer = new PrintWriter("GreencodeAndCreditCardDetails.txt", "UTF-8");
		writer.println("green_code,card_digits,card_owner,card_expire_month,card_expire_year");
		
		
		for(intMinCount = 0; intMinCount<intMaxCount; intMinCount++) {
			writer.println(arrDataMigrationGCAndCCDetails.get(intMinCount));
		}	
		writer.close();
		System.out.println("Done with Text File Creation");
		
		}
	
	@Parameters({"environment"})
	@Test(priority=7, enabled = true)
	public void createPropertyFileForDecryptedDataVerification (String environment) throws InterruptedException, IOException{
		
		Integer intMaxCount = arrDataMigrationGC.size();
		Integer intMinCount = null;

		StringBuffer strDataMigrationGC = new StringBuffer("'"+arrDataMigrationGC.get(0)+"'");
		
		for(intMinCount = 1; intMinCount<intMaxCount; intMinCount++) {
		
		
			
			strDataMigrationGC.append(","+"'"+ arrDataMigrationGC.get(intMinCount)+"'"); 
			
		}		
		System.out.println(strDataMigrationGC);
		
		Properties properties = new Properties();
		properties.setProperty("db.server.name", "console-db-1.staging.netregistry.net");
		properties.setProperty("db.port.number", "20005");
		properties.setProperty("db.database.name", "automation");
		properties.setProperty("db.user", "automation");
		properties.setProperty("db.password", "{n0P41nN0G41n}");
		properties.setProperty("sql", "SELECT * FROM public.v_companybilling_bt WHERE cm_greencode IN ("+ strDataMigrationGC +") AND card_digits IS NOT NULL AND braintree_migrated = false;");
		properties.setProperty("output.file", "output.csv");	
	
		File file = new File("migration.properties");
		FileOutputStream fileOut = new FileOutputStream(file);
		properties.store(fileOut, "DB");
		fileOut.close();
		System.out.println("Done File Creation");
		
		}
	
	
//	@Parameters({"environment"})
//	@Test(priority=8, enabled = false)
//	public void getDetailsForCustomerWithDefaultCreditCard(String environment) throws InterruptedException{
//	
//		// Initialization (Test Data Creation and Assignment)
//		String strAccountReference = null;
//		String strbillingaccountdetails = null;
//		//[] arrAccountReference = {"PAY-501", "PAY-502"};
//		
//		//Integer intMaxCount = arrAccountReference.length;
//		Integer intMaxCount = 502;
//		Integer intMinCount = null;
//		for(intMinCount = 501; intMinCount<=intMaxCount; intMinCount++) {
//			
//		strAccountReference = "PAY-"+intMinCount.toString();
//			
//		//Test Step 6: Login to Sales DB page, then purchase a domain with monthly or yearly product and pay through invoice
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("stage");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		
//		System.out.println("Domainz Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
//		
//		csnrcrmpage.setGreenCode(strAccountReference);
//		strbillingaccountdetails = csnrcrmpage.getBillingAccount();
//		System.out.println("Billing Account Details: " + strbillingaccountdetails);
//		
//		if (strbillingaccountdetails.equals("Visa: 4005xxxxxxxx0004 06/25")) {
//			System.out.println("Domainz Test Credit Card Details: Visa, 4005519200000004, 06, 2025, 836");
//			
//		}
//		else if (strbillingaccountdetails.equals("MasterCard: 5555xxxxxxxx4444 05/26")) {
//			System.out.println("Domainz Test Credit Card Details: MasterCard, 5555555555554444, 05, 2026, 121");
//		}
//		
//		driver.close();
//		
//		}
//	}
//	
//	@Parameters({"environment"})
//	@Test(priority=9, enabled = false)
//	public void getDetailsForCustomerWithOutstandingInvoice (String environment) throws InterruptedException{
//	
//		// Initialization (Test Data Creation and Assignment)
//		String strAccountReference = null;
//		String strbillingaccountdetails = null;
//		//[] arrAccountReference = {"PAY-501", "PAY-502"};
//		
//		//Integer intMaxCount = arrAccountReference.length;
//		Integer intMaxCount = 502;
//		Integer intMinCount = null;
//		for(intMinCount = 501; intMinCount<=intMaxCount; intMinCount++) {
//			
//		strAccountReference = "PAY-"+intMinCount.toString();
//			
//		//Test Step 6: Login to Sales DB page, then purchase a domain with monthly or yearly product and pay through invoice
//		initialization(environment, "salesdburl");
//		csloginpage = new CSLoginPage();
//		csloginpage.setDefaultLoginDetails("stage");
//		csnrcrmpage = csloginpage.clickLoginButton();
//		
//		System.out.println("Domainz Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
//		
//		csnrcrmpage.setGreenCode(strAccountReference);
//		strbillingaccountdetails = csnrcrmpage.getBillingAccount();
//		System.out.println("Billing Account Details: " + strbillingaccountdetails);
//		
//		if (strbillingaccountdetails.equals("Visa: 4005xxxxxxxx0004 06/25")) {
//			System.out.println("Domainz Test Credit Card Details: Visa, 4005519200000004, 06, 2025, 836");
//			
//		}
//		else if (strbillingaccountdetails.equals("MasterCard: 5555xxxxxxxx4444 05/26")) {
//			System.out.println("Domainz Test Credit Card Details: MasterCard, 5555555555554444, 05, 2026, 121");
//		}
//		
//		driver.close();
//		
//		}
//	}
	

}


