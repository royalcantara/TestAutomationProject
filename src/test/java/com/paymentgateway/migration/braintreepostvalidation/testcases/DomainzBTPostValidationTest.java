package com.paymentgateway.migration.braintreepostvalidation.testcases;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.braintree.pages.BTFoundTransactionPage;
import com.braintree.pages.BTLoginPage;
import com.braintree.pages.BTMainTabPage;
import com.braintree.pages.BTTransactionDetailForIDPage;
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

public class DomainzBTPostValidationTest extends TestBase{
	
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
	BTTransactionDetailForIDPage btransactiondetailforidpage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	public Integer countDataMigrationGC = 0;
	public List<String> arrDataMigrationGC = new ArrayList<String>();
	public List<String> arrDataMigrationGCAndCCDetails = new ArrayList<String>();
	
	
	public DomainzBTPostValidationTest() {
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
	public void testMigratedCustomerInDomainRegistrationAndEnableAutoRenew(String environment) throws InterruptedException, AWTException{
	
		// Initialization 
		String strDomainName= null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = "DEV-443";
		String strAccountReferenceNewPassword = "comein22";
		
		String[] arrExistingCardDetails = new String[6];
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
		String strMaskedCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
	    String strBraintreeTransactionID = null;
	    String strBraintreeTransactionIDStatus = null;
	    
		Integer intMaxCount = 1;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for a domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName = "TestDomainzMigratedCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
	
		
		//Test Step 1: Modify the login password of the account
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 2: Navigate to Domainz search page and purchase new domain name for migrated customer
		initialization(environment, "cart_domainsearchurl_domainz");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 3: Input credit card details and submit the order 
		if (intMinCount == 4) {
			
			arrExistingCardDetails = dmzbillingpage.getExistingCardDetails("Visa");
			dmzbillingpage.selectExistingCreditCardOption(arrExistingCardDetails[0]);
			strCardOwnerName = arrExistingCardDetails[2];
			strCardType = arrExistingCardDetails[1];
			strMaskedCardNumber = arrExistingCardDetails[3];
			strCardExpiryMonth = arrExistingCardDetails[4];
			strCardExpiryYear = arrExistingCardDetails[5];
			
			System.out.println("strCardOwnerName: " + strCardOwnerName);
			System.out.println("strCardType: " + strCardType);
			System.out.println("strMaskedCardNumber: " + strMaskedCardNumber);
			System.out.println("strCardExpiryMonth: " + strCardExpiryMonth);
			System.out.println("strCardExpiryYear: " + strCardExpiryYear);
			
		}
		else if (intMinCount == 3) {
			
			dmzbillingpage.selectNewCreditCardOption();
			
			strCardOwnerName = "Domainz Test Migrated Customer - Domain Reg";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strMaskedCardNumber = "5454********5454"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2026";
		    strCardSecurityCode = "883";
		    dmzbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
	
			arrExistingCardDetails = dmzbillingpage.getExistingCardDetails("MasterCard");
			dmzbillingpage.selectExistingCreditCardOption(arrExistingCardDetails[0]);
			strCardOwnerName = arrExistingCardDetails[2];
			strCardType = arrExistingCardDetails[1];
			strMaskedCardNumber = arrExistingCardDetails[3];
			strCardExpiryMonth = arrExistingCardDetails[4];
			strCardExpiryYear = arrExistingCardDetails[5];
			
			System.out.println("strCardOwnerName: " + strCardOwnerName);
			System.out.println("strCardType: " + strCardType);
			System.out.println("strMaskedCardNumber: " + strMaskedCardNumber);
			System.out.println("strCardExpiryMonth: " + strCardExpiryMonth);
			System.out.println("strCardExpiryYear: " + strCardExpiryYear);
		}
		else {
			
			dmzbillingpage.selectNewCreditCardOption();
			
			strCardOwnerName = "Domainz Test Migrated Customer - Domain Reg";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
			strMaskedCardNumber = "5555********4444"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "12";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "991";
		    dmzbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 4: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("Domainz Test Migration Data [Customer with domain registration (enable auto-renew)]:" + strAccountReference + "," + strCardNumber + "," + strCardExpiryMonth + "," + strCardExpiryYear);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 5: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		strBraintreeTransactionID = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		caworkflowadminpage.processFraudCheck();
//		driver.close();
//		
//		//Test Step 6: Login to Braintree sandbox portal and search for transaction id
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage(); 
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strBraintreeTransactionID);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();	
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");
//		
//		//Test Step 7: Verify if the transaction id status is settling
//		strBraintreeTransactionIDStatus = "Settling";
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID), strBraintreeTransactionIDStatus, 
//							btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID));	
//		
//		//Test Step 8: Verify if the transaction id values are correct
//		btransactiondetailforidpage = btfoundtransactionpage.clickTransactionIDInTable(strBraintreeTransactionID);
//		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Cardholder Name", strBraintreeTransactionIDStatus), strCardOwnerName);
//		System.out.println("Company Name is verified saved");
//		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Card Type", strBraintreeTransactionIDStatus), strCardType);
//		System.out.println("Card Type is verified saved");
//		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Credit Card Number", strBraintreeTransactionIDStatus), strMaskedCardNumber);
//		System.out.println("Credit Card Numbers are verified saved and shown in Masked Details (Bin and LastBin)");
//		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Expiration Date", strBraintreeTransactionIDStatus), strCardExpiryMonth+"/"+strCardExpiryYear);
//		System.out.println("Expiration Date (ExpiryMonth/ExpiryYear) is verified saved");
//		driver.close();
//		
//		System.out.println("End Test: generateCustomerDataWithDomainRegistrationAndEnableAutoRenew");
		
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void testMigratedCustomerWithMonthlyBillingProduct(String environment) throws InterruptedException, AWTException{
	
		// Initialization 
		String strDomainName = null;
		String strTld = null;
		String strWorkflowId_01 = null;
		String strWorkflowId_02 = null;
		String strAccountReference = "DEV-443";
		String strAccountReferenceNewPassword = "comein22";
		String strProduct = "Basic cPanel Hosting";
		
		String[] arrExistingCardDetails = new String[6];
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
		String strMaskedCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
	    
	    String strBraintreeTransactionID = null;
	    String strBraintreeTransactionIDStatus = null;
		
		Integer intMaxCount = 1;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for a domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName = "TestDomainzMigratedCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".net";
		}
			
		//Test Step 1: Modify the login password of the account
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 2: Navigate to Domainz search page, then purchase domain and monthly billing product for migrated customer
		initialization(environment, "cart_domainsearchurl_domainz");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddhostingpage = dmzhostingandextraspage.clickAddHostingButton();
		dmzhostingandextraspage = dmzaddhostingpage.clickAddProduct(strProduct);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		//Test Step 3: Input credit card details and submit the order 		
		if (intMinCount == 4) {
			
			arrExistingCardDetails = dmzbillingpage.getExistingCardDetails("Visa");
			dmzbillingpage.selectExistingCreditCardOption(arrExistingCardDetails[0]);
			strCardOwnerName = arrExistingCardDetails[2];
			strCardType = arrExistingCardDetails[1];
			strMaskedCardNumber = arrExistingCardDetails[3];
			strCardExpiryMonth = arrExistingCardDetails[4];
			strCardExpiryYear = arrExistingCardDetails[5];
			
			System.out.println("strCardOwnerName: " + strCardOwnerName);
			System.out.println("strCardType: " + strCardType);
			System.out.println("strMaskedCardNumber: " + strMaskedCardNumber);
			System.out.println("strCardExpiryMonth: " + strCardExpiryMonth);
			System.out.println("strCardExpiryYear: " + strCardExpiryYear);
			
		}
		else if (intMinCount == 3) {
			
			dmzbillingpage.selectNewCreditCardOption();
			
			strCardOwnerName = "Domainz Test Migrated Customer - Monthly Product";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
			strMaskedCardNumber = "5555********4444"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "108";
		    dmzbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			
		}
		else if (intMinCount == 2) {
			
			arrExistingCardDetails = dmzbillingpage.getExistingCardDetails("MasterCard");
			dmzbillingpage.selectExistingCreditCardOption(arrExistingCardDetails[0]);
			strCardOwnerName = arrExistingCardDetails[2];
			strCardType = arrExistingCardDetails[1];
			strMaskedCardNumber = arrExistingCardDetails[3];
			strCardExpiryMonth = arrExistingCardDetails[4];
			strCardExpiryYear = arrExistingCardDetails[5];
			
			System.out.println("strCardOwnerName: " + strCardOwnerName);
			System.out.println("strCardType: " + strCardType);
			System.out.println("strMaskedCardNumber: " + strMaskedCardNumber);
			System.out.println("strCardExpiryMonth: " + strCardExpiryMonth);
			System.out.println("strCardExpiryYear: " + strCardExpiryYear);
			
		}
		else {
			
			dmzbillingpage.selectNewCreditCardOption();
			
			strCardOwnerName = "Domainz Test Migrated Customer - Monthly Product";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
			strMaskedCardNumber = "2223********0011"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "10";
		    strCardExpiryYear = "2019";
		    strCardSecurityCode = "106";
		    dmzbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 4: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);
		System.out.println("Domainz Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 5: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		strBraintreeTransactionID = caworkflowadminpage.getPreAuthNumber(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();

		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);
		driver.close();
		
		//Test Step 6: Login to Braintree sandbox portal and search for transaction id
		initialization(environment, "braintree");
		btloginpage = new BTLoginPage(); 
		btloginpage.setDefaultLoginDetails("stage");
		btmaintabpage = btloginpage.clickLoginButton();
		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
		bttransactionssearchpage.searchTransactionID(strBraintreeTransactionID);
		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");
		
		//Test Step 7: Verify if the transaction id status is settling
		strBraintreeTransactionIDStatus = "Settling";
		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID), strBraintreeTransactionIDStatus, 
							btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID));	
		
		//Test Step 8: Verify if the transaction id values are correct
		btransactiondetailforidpage = btfoundtransactionpage.clickTransactionIDInTable(strBraintreeTransactionID);
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Cardholder Name", strBraintreeTransactionIDStatus), strCardOwnerName);
		System.out.println("Company Name is verified saved");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Card Type", strBraintreeTransactionIDStatus), strCardType);
		System.out.println("Card Type is verified saved");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Credit Card Number", strBraintreeTransactionIDStatus), strMaskedCardNumber);
		System.out.println("Credit Card Numbers are verified saved and shown in Masked Details (Bin and LastBin)");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Expiration Date", strBraintreeTransactionIDStatus), strCardExpiryMonth+"/"+strCardExpiryYear);
		System.out.println("Expiration Date (ExpiryMonth/ExpiryYear) is verified saved");
		driver.close();
				
		System.out.println("End Test: generateCustomerDataWithMonthlyBillingProduct");
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void testMigratedCustomerWithYearlyBillingProduct(String environment) throws InterruptedException, AWTException{
	
		// Initialization 
		String strDomainName = null;
		String strTld = null;
		String strWorkflowId_01 = null;
		String strWorkflowId_02 = null;
		String strAccountReference = "DEV-443";
		String strAccountReferenceNewPassword = "comein22";
		String strProduct = "Business Cloud Hosting";
		
		String[] arrExistingCardDetails = new String[6];
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
		String strMaskedCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
	    
	    String strBraintreeTransactionID = null;
	    String strBraintreeTransactionIDStatus = null;
		
		Integer intMaxCount = 1;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for a domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName = "TestDomainzMigratedCustomer" + df.format(d1);

					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Modify the login password of the account
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 2: Navigate to Domainz search page, then purchase domain and yearly billing product for migrated customer
		initialization(environment, "cart_domainsearchurl_domainz");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
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
		if (intMinCount == 4) {
			
			arrExistingCardDetails = dmzbillingpage.getExistingCardDetails("Visa");
			dmzbillingpage.selectExistingCreditCardOption(arrExistingCardDetails[0]);
			strCardOwnerName = arrExistingCardDetails[2];
			strCardType = arrExistingCardDetails[1];
			strMaskedCardNumber = arrExistingCardDetails[3];
			strCardExpiryMonth = arrExistingCardDetails[4];
			strCardExpiryYear = arrExistingCardDetails[5];
			
			System.out.println("strCardOwnerName: " + strCardOwnerName);
			System.out.println("strCardType: " + strCardType);
			System.out.println("strMaskedCardNumber: " + strMaskedCardNumber);
			System.out.println("strCardExpiryMonth: " + strCardExpiryMonth);
			System.out.println("strCardExpiryYear: " + strCardExpiryYear);
			
		}
		else if (intMinCount == 3) {
			
			dmzbillingpage.selectNewCreditCardOption();
			
			strCardOwnerName = "Domainz Test Migrated Customer - Yearly Product";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
			strMaskedCardNumber = "5555********4444"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "09";
		    strCardExpiryYear = "2020";
		    strCardSecurityCode = "331";
		    dmzbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			
		}
		else if (intMinCount == 2) {
			
			arrExistingCardDetails = dmzbillingpage.getExistingCardDetails("MasterCard");
			dmzbillingpage.selectExistingCreditCardOption(arrExistingCardDetails[0]);
			strCardOwnerName = arrExistingCardDetails[2];
			strCardType = arrExistingCardDetails[1];
			strMaskedCardNumber = arrExistingCardDetails[3];
			strCardExpiryMonth = arrExistingCardDetails[4];
			strCardExpiryYear = arrExistingCardDetails[5];
			
			System.out.println("strCardOwnerName: " + strCardOwnerName);
			System.out.println("strCardType: " + strCardType);
			System.out.println("strMaskedCardNumber: " + strMaskedCardNumber);
			System.out.println("strCardExpiryMonth: " + strCardExpiryMonth);
			System.out.println("strCardExpiryYear: " + strCardExpiryYear);
			
		}
		else {
			
			dmzbillingpage.selectNewCreditCardOption();
			
			strCardOwnerName = "Domainz Test Migrated Customer - Yearly Product";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strMaskedCardNumber = "5454********5454"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "755";
		    dmzbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}

		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
		
		//Test Step 4: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = dmzordercompletepage.getSingleReferenceID();
		strAccountReference = dmzordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("Domainz Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 5: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		strBraintreeTransactionID = caworkflowadminpage.getPreAuthNumber(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
	
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);
		driver.close();
		
		//Test Step 6: Login to Braintree sandbox portal and search for transaction id
		initialization(environment, "braintree");
		btloginpage = new BTLoginPage(); 
		btloginpage.setDefaultLoginDetails("stage");
		btmaintabpage = btloginpage.clickLoginButton();
		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
		bttransactionssearchpage.searchTransactionID(strBraintreeTransactionID);
		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");
		
		//Test Step 7: Verify if the transaction id status is settling
		strBraintreeTransactionIDStatus = "Settling";
		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID), strBraintreeTransactionIDStatus, 
							btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID));	
		
		//Test Step 7: Verify if the Transaction ID values are correct
		btransactiondetailforidpage = btfoundtransactionpage.clickTransactionIDInTable(strBraintreeTransactionID);
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Cardholder Name", strBraintreeTransactionIDStatus), strCardOwnerName);
		System.out.println("Company Name is verified saved");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Card Type", strBraintreeTransactionIDStatus), strCardType);
		System.out.println("Card Type is verified saved");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Credit Card Number", strBraintreeTransactionIDStatus), strMaskedCardNumber);
		System.out.println("Credit Card Numbers are verified saved and shown in Masked Details (Bin and LastBin)");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Expiration Date", strBraintreeTransactionIDStatus), strCardExpiryMonth+"/"+strCardExpiryYear);
		System.out.println("Expiration Date (ExpiryMonth/ExpiryYear) is verified saved");
		driver.close();
	
		System.out.println("End Test: generateCustomerDataWithYearlyBillingProduct");
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void testMigratedCustomerWithOutstandingInvoice(String environment) throws InterruptedException{
	
		// Initialization 
		String strDomainName = null;
		String strTld = null;
		String strWorkflowId_01 = null;
		String strWorkflowId_02 = null;
		String strAccountReference = "DEV-443";
		String strRegistrationPeriod = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		
		Integer intMaxCount = 1;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for a domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName = "TestDomainzMigratedCustomer" + df.format(d1);
				
		if (environment.equals("stagingdev-5")) {

			//For Sales DB
			strTld = "nz";
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
				
		//Test Step 1: Login to Sales DB page, then purchase a domain with monthly or yearly product and pay through invoice
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails("stage");
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strAccountReference);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName, strTld, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		System.out.println("Domainz Test Payment Method: " + strPaymentMethod);
		System.out.println("Domainz Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		
		
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
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
		
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);
		driver.close();
	
		System.out.println("End Test: generateCustomerDataWithOutstandingInvoice");
		
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void testMigratedCustomerWithDefaultCreditCard(String environment) throws InterruptedException, AWTException{
	
		// Initialization
		String strDomainName = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = "DEV-443";
		String strAccountReferenceNewPassword = "comein22";
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
		String strMaskedCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
	    
	    String strBraintreeTransactionID = null;
	    String strBraintreeTransactionIDStatus = null;
	    String strBraintreeTransactionIDAmount = null;
		
		
		Integer intMaxCount = 1;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for a domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName = "TestDomainzMigratedCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Modify the login password of the account
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 2: Login to customer portal account and add new credit card
		initialization(environment, "customerportalurl_domainz");
		dmzloginpage = new DMZLoginPage();
		dmzloginpage.setLoginDetails(strAccountReference, strAccountReferenceNewPassword);
		dmzheaderpage = dmzloginpage.clickLoginButton();
		dmzaccountpage = dmzheaderpage.clickAccountTab();
		dmzcreditcardsdetailspage = dmzaccountpage.clickEditCreditCardsOnFile();
		
		if (intMinCount == 4) {
			
			strCardOwnerName = "Domainz Test Migrated Customer - Default Credit Card";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
			strMaskedCardNumber = "5555********4444"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "122";
		    dmzcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		   
		}
		else if (intMinCount == 3) {
			
			strCardOwnerName = "Domainz Test Migrated Customer - Default Credit Card";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
			strMaskedCardNumber = "2223********0011"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "178";	
		    dmzcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			
		}
		else if (intMinCount == 2) {
			
			strCardOwnerName = "Domainz Test Migrated Customer - Default Credit Card";
			strCardType = "Visa";
			strCardNumber = "4012000077777777";
			strMaskedCardNumber = "4012********7777"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "144";	
		    dmzcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			
		}
		else {	
			
			strCardOwnerName = "Domainz Test Migrated Customer - Default Credit Card";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
			strMaskedCardNumber = "4005********0004"; //Masked Number in Console Admin Format
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2019";
		    strCardSecurityCode = "811";	
		    dmzcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			
		}
		
		System.out.println("Domainz Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		dmzcreditcardsdetailspage.tickMakeCreditCardAsDefaultPayment();
		dmzcreditcardsdetailspage.clickAddCreditCard();
		
		//Test Step 7: Verify if adding new card is successful
		Assert.assertTrue(dmzcreditcardsdetailspage.isNewCreditCardAdded(), "New Credit Card is not added");
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		
		//Test Step 8: Login to Braintree sandbox portal, search for transaction id record via card number.  
		initialization(environment, "braintree");
		btloginpage = new BTLoginPage(); 
		btloginpage.setDefaultLoginDetails("stage");
		btmaintabpage = btloginpage.clickLoginButton();		
		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
		bttransactionssearchpage.searchCreditCardNumber(strCardNumber);
		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
		
		Assert.assertTrue(btfoundtransactionpage.isCreditCardFound(), "Credit Card Record not found");
		strBraintreeTransactionID = btfoundtransactionpage.getLatestTransactionIDInTable();
		
		btmaintabpage = new BTMainTabPage();
		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
		bttransactionssearchpage.searchTransactionID(strBraintreeTransactionID);
		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");
		
		//Test Step 9: Verify if the transaction id status is authorized
		strBraintreeTransactionIDStatus = "Authorized";
		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID), strBraintreeTransactionIDStatus, 
							btfoundtransactionpage.getTransactionIDStatus(strBraintreeTransactionID));
		
		//Test Step 10: Verify if the transaction id amount is $1.00 NZD
		strBraintreeTransactionIDAmount = "$1.00 NZD";
		Assert.assertEquals(btfoundtransactionpage.getTransactionIDAmount(strBraintreeTransactionID), strBraintreeTransactionIDAmount, 
							btfoundtransactionpage.getTransactionIDAmount(strBraintreeTransactionID));
		
		//Test Step 11: Verify if the transaction id values are correct
		btransactiondetailforidpage = btfoundtransactionpage.clickTransactionIDInTable(strBraintreeTransactionID);
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Cardholder Name", strBraintreeTransactionIDStatus), strCardOwnerName);
		System.out.println("Company Name is verified saved");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Card Type", strBraintreeTransactionIDStatus), strCardType);
		System.out.println("Card Type is verified saved");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Credit Card Number", strBraintreeTransactionIDStatus), strMaskedCardNumber);
		System.out.println("Credit Card Numbers are verified saved and shown in Masked Details (Bin and LastBin)");
		Assert.assertEquals(btransactiondetailforidpage.getPaymentInformation("Expiration Date", strBraintreeTransactionIDStatus), strCardExpiryMonth+"/"+strCardExpiryYear);
		System.out.println("Expiration Date (ExpiryMonth/ExpiryYear) is verified saved");
		driver.close();
						
		System.out.println("End Test: generateCustomerDataWithDefaultCreditCard");
		
		}
	}
	
//	@Parameters({"environment"})
//	@Test(priority=6, enabled = true)
//	public void createTextFileForGreencodeAndCreditCardDetails (String environment) throws InterruptedException, IOException{
//		
//		Integer intMaxCount = arrDataMigrationGCAndCCDetails.size();
//		Integer intMinCount = null;
//		PrintWriter writer = new PrintWriter("GreencodeAndCreditCardDetails.txt", "UTF-8");
//		writer.println("green_code,card_digits,card_owner,card_expire_month,card_expire_year");
//		
//		
//		for(intMinCount = 0; intMinCount<intMaxCount; intMinCount++) {
//			writer.println(arrDataMigrationGCAndCCDetails.get(intMinCount));
//		}	
//		writer.close();
//		System.out.println("Done with Text File Creation");
//		
//		}
//	

	
	

	

}


