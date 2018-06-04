package com.paymentgateway.ui.dmz.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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

public class DomainzPG69CartTest extends TestBase{
	
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
	
	//Braintree Pages
	BTLoginPage btloginpage;
	BTMainTabPage btmaintabpage;
	BTTransactionsSearchPage bttransactionssearchpage;
	BTFoundTransactionPage btfoundtransactionpage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public DomainzPG69CartTest(){
		super();
	}
	
	@BeforeMethod
	@Parameters({"environment"})
	public void setUp(String environment){
		initialization(environment, "cart");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");

	}	
	
	@Parameters({"environment"})
	@Test(priority=1, enabled = true)
	public void PG69_verifyBTFormFieldsUsingValidVisaCard (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "Test PG Domainz";
		String strCardNumber = "4111111111111111";
		String strCardMonthExpiry = "12";
		String strCardYearExpiry = "2020";
		String strCardSecurityCode = "123";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTFormFieldsUsingValidVisaCard");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();	
		
		//Disabled temporarily because of recaptcha
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
//		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		
//		//Verify if domain registration workflow is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
//		
//		//Get transaction id via pre-auth number in workflow
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
//		strtransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		System.out.println("Transaction ID: " + strtransactionid);
//		driver.close();
//		
//		//Verify if the transaction id status in Braintree is Settling
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage();
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strtransactionid);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strtransactionid), "Authorized", btfoundtransactionpage.getTransactionIDStatus(strtransactionid));
//		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=2, enabled = true)
	public void PG69_verifyBTFormFieldsUsingValidMasterCard (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "Test PG Domainz";
		String strCardNumber = "5454545454545454";
		String strCardMonthExpiry = "11";
		String strCardYearExpiry = "2021";
		String strCardSecurityCode = "456";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTFormFieldsUsingValidMasterCard");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();	
		
		//Disabled temporarily because of recaptcha
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
//		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		
//		//Verify if domain registration workflow is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
//		
//		//Get transaction id via pre-auth number in workflow
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
//		strtransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		System.out.println("Transaction ID: " + strtransactionid);
//		driver.close();
//		
//		//Verify if the transaction id status in Braintree is Settling
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage();
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strtransactionid);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strtransactionid), "Authorized", btfoundtransactionpage.getTransactionIDStatus(strtransactionid));
//		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=3, enabled = true)
	public void PG69_verifyBTCardOwnerFieldUsingAlphaNumericCharacters (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "AlphaNumeric0123456789";
		String strCardNumber = "4111111111111111";
		String strCardMonthExpiry = "11";
		String strCardYearExpiry = "2021";
		String strCardSecurityCode = "456";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTCardOwnerFieldUsingAlphaNumericCharacters");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();	
		
		//Disabled temporarily because of recaptcha
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
//		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		
//		//Verify if domain registration workflow is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
//		
//		//Get transaction id via pre-auth number in workflow
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
//		strtransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		System.out.println("Transaction ID: " + strtransactionid);
//		driver.close();
//		
//		//Verify if the transaction id status in Braintree is Settling
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage();
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strtransactionid);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strtransactionid), "Authorized", btfoundtransactionpage.getTransactionIDStatus(strtransactionid));
//		driver.close();
	}
	
	
	@Parameters({"environment"})
	@Test(priority=4, enabled = true)
	public void PG69_verifyBTCardOwnerFieldUsingSpecialCharacters (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "Test!*#%&()^:;<>=?[/]_";
		String strCardNumber = "4111111111111111";
		String strCardMonthExpiry = "11";
		String strCardYearExpiry = "2021";
		String strCardSecurityCode = "456";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTCardOwnerFieldUsingSpecialCharacters");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();	
		
		//Disabled temporarily because of recaptcha
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
//		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		
//		//Verify if domain registration workflow is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
//		
//		//Get transaction id via pre-auth number in workflow
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
//		strtransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		System.out.println("Transaction ID: " + strtransactionid);
//		driver.close();
//		
//		//Verify if the transaction id status in Braintree is Settling
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage();
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strtransactionid);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strtransactionid), "Authorized", btfoundtransactionpage.getTransactionIDStatus(strtransactionid));
//		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=5, enabled = true)
	public void PG69_verifyBTCardOwnerFieldUsingFullStopAndSpaces (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "Mr. John Doe";
		String strCardNumber = "4111111111111111";
		String strCardMonthExpiry = "11";
		String strCardYearExpiry = "2021";
		String strCardSecurityCode = "456";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTCardOwnerFieldUsingFullStopAndSpaces");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();	
		
		//Disabled temporarily because of recaptcha
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
//		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		
//		//Verify if domain registration workflow is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
//		
//		//Get transaction id via pre-auth number in workflow
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
//		strtransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		System.out.println("Transaction ID: " + strtransactionid);
//		driver.close();
//		
//		//Verify if the transaction id status in Braintree is Settling
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage();
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strtransactionid);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strtransactionid), "Authorized", btfoundtransactionpage.getTransactionIDStatus(strtransactionid));
//		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=6, enabled = true)
	public void PG69_verifyBTCardOwnerFieldUsing26CharactersMaximumLength (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "Mr. Payment Gateway Test01";
		String strCardNumber = "4111111111111111";
		String strCardMonthExpiry = "11";
		String strCardYearExpiry = "2021";
		String strCardSecurityCode = "456";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTCardOwnerFieldUsing26CharactersMaximumLength");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();	
		
		//Disabled temporarily because of recaptcha
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
//		
//		initialization(environment, "consoleadmin");
//		caloginpage = new CALoginPage();
//		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
//		
//		//Verify if domain registration workflow is completed
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
//		
//		//Get transaction id via pre-auth number in workflow
//		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
//		Assert.assertTrue(caworkflowadminpage.isWorkflowIDExist(strWorkflowId), "Workflow ID not found");
//		strtransactionid = caworkflowadminpage.getPreAuthNumber(strWorkflowId);
//		System.out.println("Transaction ID: " + strtransactionid);
//		driver.close();
//		
//		//Verify if the transaction id status in Braintree is Settling
//		initialization(environment, "braintree");
//		btloginpage = new BTLoginPage();
//		btloginpage.setDefaultLoginDetails("stage");
//		btmaintabpage = btloginpage.clickLoginButton();
//		bttransactionssearchpage = btmaintabpage.clickTransactionsLink();
//		bttransactionssearchpage.searchTransactionID(strtransactionid);
//		btfoundtransactionpage = bttransactionssearchpage.clickSearchButton();
//		Assert.assertTrue(btfoundtransactionpage.isTransactionIDFound(), "Transaction ID not found");	
//		Assert.assertEquals(btfoundtransactionpage.getTransactionIDStatus(strtransactionid), "Authorized", btfoundtransactionpage.getTransactionIDStatus(strtransactionid));
//		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=7, enabled = true)
	public void PG69_verifyBTCardNumberFieldUsingLessThan16Digits (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "Mr. Payment Gateway Test01";
		String strCardNumber = "411111111111111";
		String strCardMonthExpiry = "11";
		String strCardYearExpiry = "2021";
		String strCardSecurityCode = "456";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTCardNumberFieldUsingLessThan16Digits");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		
		//Verify if the textbox for the card field is highlighted in red
		Assert.assertTrue(dmzbillingpage.isCardDetailHighlightedInRed("Card Number"));
		
		//Verify if error message is displayed for the following credit card field
		Assert.assertTrue(dmzbillingpage.isErroMessageCorrect("Card Number", "Card Number is a required field"));

	}
	
	@Parameters({"environment"})
	@Test(priority=8, enabled = true)
	public void PG69_verifyBTCardNumberFieldUsingMoreThan16Digits (String environment) throws InterruptedException{
		
		//Initial test data assignment
		String strDomainName = null;
		String strTld_01 = null;
		String strWorkflowId = null;
		String strtransactionid = null;
		String strCardOwner = "Mr. Payment Gateway Test01";
		String strCardNumber = "41111111111111111";
		String strCardMonthExpiry = "11";
		String strCardYearExpiry = "2021";
		String strCardSecurityCode = "456";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPGDomainz" + df.format(d);
		strTld_01 = ".com";
	
		System.out.println("Test: PG69_verifyBTCardNumberFieldUsingMoreThan16Digits");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardMonthExpiry, strCardYearExpiry, strCardSecurityCode);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		
		//Verify if the textbox for the card field is highlighted in red
		Assert.assertTrue(dmzbillingpage.isCardDetailHighlightedInRed("Card Number"));
		
		//Verify if error message is displayed for the following credit card field
		Assert.assertTrue(dmzbillingpage.isErroMessageCorrect("Card Number", "Card Number is a required field"));
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
}
	