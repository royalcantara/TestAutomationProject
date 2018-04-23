package com.paymentgateway.ui.dmz.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.domainzwebsite.pages.DMZAccountContactPage;
import com.domainzwebsite.pages.DMZAddDomainPrivacyPage;
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZDomainSearchPage;
import com.domainzwebsite.pages.DMZHostingAndExtrasPage;
import com.domainzwebsite.pages.DMZOnlineOrderPage;
import com.domainzwebsite.pages.DMZOrderCompletePage;
import com.domainzwebsite.pages.DMZRegistrantContactPage;
import com.util.TestUtil;

public class DomainRegistrationPreAuth extends TestBase{
	
	DMZOnlineOrderPage dmzonlineorderpage;
	DMZDomainSearchPage dmzdomainsearchpage;
	DMZAddDomainPrivacyPage dmzadddomainprivacypage;
	DMZHostingAndExtrasPage dmzhostingandextraspage;
	DMZAccountContactPage dmzaccountcontactpage;
	DMZRegistrantContactPage dmzregistrantcontactpage;
	DMZBillingPage dmzbillingpage;
	DMZOrderCompletePage dmzordercompletepage;
	TestUtil testUtil;
	String clienttoken;
	String worksheetName = "PreAuth";
	String workflowid;

	
	public DomainRegistrationPreAuth(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		dmzonlineorderpage = new DMZOnlineOrderPage();
		dmzonlineorderpage.tickTld(".com.au");

	}
	
	@DataProvider
	public Object[][] getPaymentGatewayTestData(){
		Object data[][] = TestUtil.getTestData(worksheetName);
		return data;
		
	}
	
	@Test(priority=1, dataProvider = "getPaymentGatewayTestData")
	public void verifyPreAuthorization(String strCustomerCategory, String strCardCategory, String strGreencode, String strPassword) throws InterruptedException{
		
		DateFormat df = new SimpleDateFormat("DDMMYYYYHHMMSS");
		Date d = new Date();
		String strDomainName = "TestPGDomainz" + df.format(d);
		String strTld = ".com";
		
		dmzonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
		dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
		dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
		dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
		
		if (strCustomerCategory.equals("New/New Customer")){
			dmzaccountcontactpage.setCustomerDefaultInformation();
			dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();		
		}
		else if ((strCustomerCategory.equals("New/Returning Customer"))|| (strCustomerCategory.equals("Non-Migrated Customer"))) {
			dmzaccountcontactpage.setReturningCustomerContacts(strGreencode, strPassword);
			dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
		}
		
		dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
		
		if (strCustomerCategory.equals("New/New Customer")){
			dmzbillingpage.setBTFormCreditCardDetails("PG-Domainz", "4111111111111111", "11", "2019", "123");
		}
		else if ((strCustomerCategory.equals("New/Returning Customer"))|| (strCustomerCategory.equals("Non-Migrated Customer"))) {
			/* Use default credit card */
		}
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
		//Thread.sleep(10000);
		//Assert.assertFalse(dmzordercompletepage.isOrderComplete(), "Order completion status");
		//Thread.sleep(10000);
		workflowid = dmzordercompletepage.getReferenceID();
	
	}

	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	

}
