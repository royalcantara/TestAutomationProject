package com.paymentgateway.uat.quest.tppwholesale.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAViewCreditCardsPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class ConsoleAdminJourneyTest extends TestBase{
		
		//Console Admin Pages
		CALoginPage caloginpage;
		CAHeaderPage caheaderpage;
		CAAccountReferencePage caaccountreferencepage;
		CAViewCreditCardsPage caviewcreditcardspage;
		
		TestUtil testUtil;
		String clienttoken;
		public static ExtentTest logger;
	
		public ConsoleAdminJourneyTest() {
			super();
		}

		@Parameters({"environment"})
		@Test(priority=1, enabled = true)
		public void QuestTPPWholesale_VerifyViewBillingInConsoleAdmin(String environment) throws InterruptedException{
		
			
		// Initialization (Test Data Creation and Assignment)
		String strAccountReference = null;

		if (environment.equals("uat2")) {
			strAccountReference = "570-03";
		}
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caviewcreditcardspage = caaccountreferencepage.clickViewBillingAccounts();
		Assert.assertTrue(caviewcreditcardspage.isViewCreditcardsPageDisplayed(), "View Creditcards Page is not displayed");
		driver.close();		
		}

}

		
