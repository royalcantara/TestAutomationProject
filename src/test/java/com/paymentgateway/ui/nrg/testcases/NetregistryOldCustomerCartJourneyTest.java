package com.paymentgateway.ui.nrg.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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

public class NetregistryOldCustomerCartJourneyTest extends TestBase{
	
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

	@BeforeMethod
	@Parameters({"environment"})
	public void setUp(String environment){
		initialization(environment, "cart");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");
		nrgonlineorderpage.tickTld(".net.au");
		nrgonlineorderpage.tickTld(".com");

	}	

	@Test(priority=1, enabled = false)
	public void verifyDomainRegistrationForNewCustomer() throws InterruptedException{
	
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGNetregistry" + df.format(d);
		String strTld_01 = ".com";
		String strWorkflowId = null;
		
		System.out.println("Test01: verifyDomainRegistrationForNewCustomer");
		System.out.println("Domain Name: " + strDomainName);
		System.out.println("TLD: " + strTld_01);
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();	
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		nrgbillingpage.setBTFormCreditCardDetails("PG-Domainz", "4111111111111111", "11", "2019", "123");
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();		
//		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=2, enabled = false)
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
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		/* Use default credit card */
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
//		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
//		arrWorkflowId = nrgordercompletepage.getMultipleReferenceIDs(2);
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
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		//nrghostingandextraspage.clickWebHostingRadioButton();
		nrghostingandextraspage.addCPanelStarter("3");
		

		
//		nrgaddhostingpage = nrghostingandextraspage.clickAddHostingButton();
//		nrghostingandextraspage = nrgaddhostingpage.clickAddProduct(strProduct_01);
//		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
//		nrgaccountcontactpage.setCustomerDefaultInformation();
//		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();		
//		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
//		nrgbillingpage.setBTFormCreditCardDetails("PG-Netregistry", "4111111111111111", "11", "2019", "123");
//		nrgbillingpage.tickTermsAndConditions();
//		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();
		
//		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=4, enabled = false)
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
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
//		nrgaddhostingpage = nrghostingandextraspage.clickAddHostingButton();
//		nrghostingandextraspage = nrgaddhostingpage.clickAddProduct(strProduct_01);
//
//		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
//		nrgaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
//		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
//		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
//		/* Use default credit card */
//		nrgbillingpage.tickTermsAndConditions();		
//		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();
		
//		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
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
	
	@Test(priority=13, enabled = false)
	public void verifySEOForNewCustomer() throws InterruptedException{
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strProduct_01 = "SEO Assessment";
		String strWorkflowId = null;
		
		System.out.println("Test13: verifySEOForNewCustomer");
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);		
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
//		nrgaddextraspage = nrghostingandextraspage.clickAddExtrasButton();
//		nrghostingandextraspage = nrgaddextraspage.clickAddProduct(strProduct_01);
//		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
//		nrgaccountcontactpage.setCustomerDefaultInformation();
//		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();		
//		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
//		nrgbillingpage.setBTFormCreditCardDetails("PG-Netregistry", "4111111111111111", "11", "2019", "123");
//		nrgbillingpage.tickTermsAndConditions();	
//		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();
		
//		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=14, enabled = false)
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
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
//		nrgaddextraspage = nrghostingandextraspage.clickAddExtrasButton();
//		nrghostingandextraspage = nrgaddextraspage.clickAddProduct(strProduct_01);
//		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
//		nrgaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
//		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
//		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
//		/* Use default credit card */
//		nrgbillingpage.tickTermsAndConditions();		
//		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();
		
//		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
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
		//driver.quit();
	}
}