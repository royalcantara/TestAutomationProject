package com.consoleregression.testcases.production;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class CustomerPortalJourneyTest extends TestBase{

	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public CustomerPortalJourneyTest() {
		super();
	}

	@Parameters({"environment"})
	@Test(priority=1, enabled = true)
	public void verifyDomainRegistrationOrderForNewCustomerInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strWorkflowId = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("uat1")) {
			strTld = ".com";
		}
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();	
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
        nrgbillingpage.setQuestFormCreditCardDetails("Test Console Regression", "Visa", "4111111111111111", "11", "2019", "123");
        nrgbillingpage.tickTermsAndConditions();
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=2, enabled = true)
	public void verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strWorkflowId = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("uat1")) {
			strTld = ".com";
		}
			
		//Test Step 1: Login to customer portal and place an order for domain registration and domain privacy
		System.out.println("Start Test: verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickAddToCart();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts("MEL-6007", "comein22");
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Select existing credit card details and submit the order 
		nrgbillingpage.selectExistingCreditCard("Prepaid credit: Current Balance: AU$19222.10 Available Balance: AU$19222.10");
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		driver.close();
	}
	
}
