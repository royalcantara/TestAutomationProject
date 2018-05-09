package com.paymentgateway.ui.dmz.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consolesalesdb.pages.ConsoleSalesLoginPage;
import com.consolesalesdb.pages.NrCRMPage;
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

public class DomainzSalesDBJourneyTest extends TestBase{

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
	
	ConsoleSalesLoginPage consolesalesloginpage;
	NrCRMPage nrcrmpage;
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public DomainzSalesDBJourneyTest() {
		super();
	}
	
	@BeforeMethod
	@Parameters({"environment"})
	public void setUp(String environment){
		initialization(environment);
//		driver.manage().window().maximize();
//		driver.get("https://console-checkout-1.dev.netregistry.net/sales");
//		consolesalesloginpage = new ConsoleSalesLoginPage();
//		consolesalesloginpage.setDefaultLoginDetails("stage");
//		nrcrmpage = consolesalesloginpage.clickLoginButton();

	}	
	
	@Test(priority=1, enabled = true)
	public void verifyDomainRegistrationInSalesDBForNewCustomer() throws InterruptedException{
		
		System.out.println("Test01: Sales DB");
//		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
//		Date d = new Date();
//		String strDomainName = "TestPGDomainz" + df.format(d);
//		String strTld_01 = ".com";
//		String strWorkflowId = null;
//		
//		System.out.println("Test01: verifyDomainRegistrationForNewCustomer");
//		System.out.println("Domain Name: " + strDomainName);
//		System.out.println("TLD: " + strTld_01);
//		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld_01);
//		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
//		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
//		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
//		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
//		dmzaccountcontactpage.setCustomerDefaultInformation();
//		dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
//		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
//		dmzbillingpage.setBTFormCreditCardDetails("PG-Domainz", "4111111111111111", "11", "2019", "123");
//		dmzbillingpage.tickTermsAndConditions();
		
//		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();		
//		Assert.assertTrue(dmzordercompletepage.isOrderComplete(), "Order is not completed");
//		strWorkflowId = dmzordercompletepage.getSingleReferenceID();
//		System.out.println("Reference ID[0]:" + strWorkflowId);	
	}
	
	@Test(priority=2, enabled = true)
	public void verifyDomainRegistrationInSalesDBForExistingCustomer() throws InterruptedException{
		
		/* For creation of test steps */
	}
	
	@AfterMethod
	public void tearDown(){
//		driver.quit();
	}
	
	
}
