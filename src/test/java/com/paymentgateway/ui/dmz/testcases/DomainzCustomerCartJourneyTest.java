package com.paymentgateway.ui.dmz.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
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

public class DomainzCustomerCartJourneyTest extends TestBase{
	
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
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public DomainzCustomerCartJourneyTest(){
		super();
	}
	
	@BeforeMethod
	@Parameters({"environment"})
	public void setUp(String environment){
		initialization(environment);
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");

	}	
	
	@Test(priority=1, enabled = true)
	public void verifyDomainRegistrationForNewCustomer() throws InterruptedException{
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strWorkflowId = null;
		
		System.out.println("Test01: verifyDomainRegistrationForNewCustomer");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails("PG-Domainz", "4111111111111111", "11", "2019", "123");
		dmzbillingpage.tickTermsAndConditions();
		
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=2, enabled = true)
	public void verifyDomainRegistrationForExistingCustomer() throws InterruptedException{
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strTld_02 = ".net";
		String strGreencode = "PAY-279";
		String strPassword = "ggBgtYs85";
		String[] arrWorkflowId;
		
		System.out.println("Test02: verifyDomainRegistrationForExistingCustomer");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		/* Use default credit card */
		dmzbillingpage.tickTermsAndConditions();
		
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		arrWorkflowId = dmzordercompletepage.getMultipleReferenceIDs(2);
//		System.out.println("Reference ID[0]:" + arrWorkflowId[0]);
//		System.out.println("Reference ID[1]:" + arrWorkflowId[1]);
	}
	
	@Test(priority=3, enabled = true)
	public void verifyHostingSignupForNewCustomer() throws InterruptedException{
	
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strProduct_01 = "Basic cPanel Hosting";
		String strWorkflowId = null;

		System.out.println("Test03: verifyHostingSignupForNewCustomer");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddhostingpage = dmzhostingandextraspage.clickAddHostingButton();
		dmzhostingandextraspage = dmzaddhostingpage.clickAddProduct(strProduct_01);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails("PG-Domainz", "4111111111111111", "11", "2019", "123");
		dmzbillingpage.tickTermsAndConditions();
		
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=4, enabled = true)
	public void verifyHostingSignupForExistingCustomer() throws InterruptedException{
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strProduct_01 = "Basic Cloud Hosting";
		String strGreencode = "PAY-279";
		String strPassword = "ggBgtYs85";
		String strWorkflowId = null;
		
		System.out.println("Test04: verifyHostingSignupForExistingCustomer");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddhostingpage = dmzhostingandextraspage.clickAddHostingButton();
		dmzhostingandextraspage = dmzaddhostingpage.clickAddProduct(strProduct_01);

		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		/* Use default credit card */
		dmzbillingpage.tickTermsAndConditions();
		
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);
		
	}
	
	@Test(priority=5, enabled = false)
	public void verifyHostingAndWebsiteDoneForYouForNewCustomer() throws InterruptedException{
	
		/* Test scripts to be added */

	}
	
	@Test(priority=6, enabled = false)
	public void verifyHostingAndWebsiteDoneForYouForExistingCustomer() throws InterruptedException{

		/* Test scripts to be added */

	}
	
	@Test(priority=7, enabled = false)
	public void verifyWebDesignWordpressForNewCustomer() throws InterruptedException{

		/* Test scripts to be added */

	}
	
	@Test(priority=8, enabled = false)
	public void verifyWebDesignWordpressForExistingCustomer() throws InterruptedException{

		/* Test scripts to be added */
		
	}
	
	@Test(priority=9, enabled = false)
	public void verifySSLForNewCustomer() throws InterruptedException{

		/* Test scripts to be added */

	}
	
	@Test(priority=10, enabled = false)
	public void verifySSLForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=11, enabled = false)
	public void verifySocialMediaAdvertisingForNewCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=12, enabled = false)
	public void verifySocialMediaAdvertisingForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=13, enabled = true)
	public void verifySEOForNewCustomer() throws InterruptedException{
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strProduct_01 = "SEO Assessment";
		String strWorkflowId = null;
		
		System.out.println("Test13: verifySEOForNewCustomer");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);		
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddextraspage = dmzhostingandextraspage.clickAddExtrasButton();
		dmzhostingandextraspage = dmzaddextraspage.clickAddProduct(strProduct_01);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails("PG-Domainz", "4111111111111111", "11", "2019", "123");
		dmzbillingpage.tickTermsAndConditions();
		
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=14, enabled = true)
	public void verifySEOForExistingCustomer() throws InterruptedException{
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strProduct_01 = "SEO Assessment";
		String strGreencode = "PAY-279";
		String strPassword = "ggBgtYs85";
		String strWorkflowId = null;

		System.out.println("Test14: verifySEOForExistingCustomer");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddextraspage = dmzhostingandextraspage.clickAddExtrasButton();
		dmzhostingandextraspage = dmzaddextraspage.clickAddProduct(strProduct_01);

		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		/* Use default credit card */
		dmzbillingpage.tickTermsAndConditions();
		
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();	
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);
	}
	
	@Test(priority=15, enabled = false)
	public void verifyEmailMarketingForNewCustomer() throws InterruptedException{
		
		/* Test scripts to be added */
	
	}
	
	@Test(priority=16, enabled = false)
	public void verifyEmailMarketingForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=17, enabled = false)
	public void verifyOffice365ForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=18, enabled = false)
	public void verifyDomainRenewalForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=19, enabled = false)
	public void verifyDomainTransferForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=20, enabled = false)
	public void verifyProductRenewalForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=21, enabled = false)
	public void verifySSLRenewalForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@Test(priority=22, enabled = false)
	public void verifyOutstandingInvoicesForExistingCustomer() throws InterruptedException{
		
		/* Test scripts to be added */

	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}

}
