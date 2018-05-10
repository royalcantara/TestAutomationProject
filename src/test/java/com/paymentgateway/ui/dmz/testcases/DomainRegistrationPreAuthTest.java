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
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZDomainSearchPage;
import com.domainzwebsite.pages.DMZHostingAndExtrasPage;
import com.domainzwebsite.pages.DMZOnlineOrderPage;
import com.domainzwebsite.pages.DMZOrderCompletePage;
import com.domainzwebsite.pages.DMZRegistrantContactPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class DomainRegistrationPreAuthTest extends TestBase{
	
	DMZOnlineOrderPage dmzonlineorderpage;
	DMZDomainSearchPage dmzdomainsearchpage;
	DMZAddDomainPrivacyPage dmzadddomainprivacypage;
	DMZHostingAndExtrasPage dmzhostingandextraspage;
	DMZAddExtrasPage dmzaddextraspage;
	DMZAccountContactPage dmzaccountcontactpage;
	DMZRegistrantContactPage dmzregistrantcontactpage;
	DMZBillingPage dmzbillingpage;
	DMZOrderCompletePage dmzordercompletepage;
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public DomainRegistrationPreAuthTest(){
		super();
	}
	
	@BeforeMethod
	@Parameters({"environment"})
	public void setUp(String environment){
		initialization(environment, "cart");
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");

	}	
	
	@Test(priority=1, enabled = true)
	public void verifyPreAuthForNewCustomerSingleDomain() throws InterruptedException{
		//logger.log(LogStatus.INFO,"New Customer/New Credit Card/Single Domain");
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strWorkflowId = null;
		
		System.out.println("Test01");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setCustomerDefaultInformation();
		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		dmzbillingpage.setBTFormCreditCardDetails("PG-Domainz", "4111111111111111", "11", "2019", "123");
//		dmzbillingpage.tickAutoRenew();
//		dmzbillingpage.tickTermsAndConditions();
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
		
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=2, enabled = false)
	public void verifyPreAuthForExistingCustomerMultipleDomains() throws InterruptedException{
		//test.log(LogStatus.INFO,"Existing Customer/Existing Credit Card/Multiple Domains");
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strTld_02 = ".net";
		String strGreencode = "PAY-279";
		String strPassword = "ggBgtYs85";
		String[] arrWorkflowId;
		
		System.out.println("Test02");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzonlineorderpage.tickTld(strTld_02);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		/* Use default credit card */
//		dmzbillingpage.tickAutoRenew();
//		dmzbillingpage.tickTermsAndConditions();
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
//		
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		arrWorkflowId = dmzordercompletepage.getMultipleReferenceIDs(2);
//		System.out.println("Reference ID[0]:" + arrWorkflowId[0]);
//		System.out.println("Reference ID[1]:" + arrWorkflowId[1]);
	}
	
	@Test(priority=3, enabled = false)
	public void verifyPreAuthForNewCustomerSingleProduct() throws InterruptedException{
		//test.log(LogStatus.INFO,"New Customer/New Credit Card/Single Product");
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strProduct_01 = "Domain Privacy";
		String strWorkflowId = null;
		
		System.out.println("Test03");
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
//		dmzbillingpage.tickAutoRenew();
//		dmzbillingpage.tickTermsAndConditions();
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
//		
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=4, enabled = false)
	public void verifyPreAuthForExistingCustomerMultipleProducts() throws InterruptedException{
		//test.log(LogStatus.INFO,"Existing Customer/Existing Credit Card/Multiple Products");
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld_01 = ".com";
		String strProduct_01 = "Domain Privacy";
		String strProduct_02 = "SEO Assessment";
		String strGreencode = "PAY-279";
		String strPassword = "ggBgtYs85";
		String strWorkflowId = null;
		
		System.out.println("Test04");
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaddextraspage = dmzhostingandextraspage.clickAddExtrasButton();
		dmzhostingandextraspage = dmzaddextraspage.clickAddProduct(strProduct_01);
		dmzaddextraspage = dmzhostingandextraspage.clickAddExtrasButton();
		dmzhostingandextraspage = dmzaddextraspage.clickAddProduct(strProduct_02);
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		dmzaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
		dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		/* Use default credit card */
//		dmzbillingpage.tickAutoRenew();
//		dmzbillingpage.tickTermsAndConditions();
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
//		
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);
	}


	@AfterMethod
	public void tearDown(){
		driver.quit();
	}

}
