//package com.paymentgateway.uat.domainz.testcases;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.How;
//import org.testng.Assert;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.base.TestBase;
//import com.domainzwebsite.pages.DMZAccountPage;
//import com.domainzwebsite.pages.DMZBillingPage;
//import com.domainzwebsite.pages.DMZCreditCardsDetailsPage;
//import com.domainzwebsite.pages.DMZLoginPage;
//import com.domainzwebsite.pages.DMZOverviewPage;
//import com.domainzwebsite.pages.DMZPrepaidAccountPage;
//import com.domainzwebsite.pages.DMZSummaryOfServicesPage;
//import com.domainzwebsite.pages.DMZUpdateSubscriptionBillingDetailsPage;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.util.TestUtil;
//
//public class RegressionSMUI extends TestBase {
//
//		// Domainz shopping cart pages
//		DMZLoginPage dmzloginpage;
//		DMZAccountPage dmzaccountpage;
//		DMZBillingPage dmzbillingpage;
//		DMZCreditCardsDetailsPage dmzcreditcardsdetailspage;
//		DMZPrepaidAccountPage dmzprepaidaccountpage;
//		DMZUpdateSubscriptionBillingDetailsPage dmzupdatesubscriptionbillingdetailspage;
//		DMZSummaryOfServicesPage dmzsummaryofservicespage;
//		DMZOverviewPage dmzoverviewpage;
//	
//		// objects
//		@FindBy(how = How.LINK_TEXT, using = "Account")
//		WebElement lnkAccount;
//	
//		TestUtil testUtil;
//		public static ExtentTest logger;
//	
//		String straccountreference = null;
//		String strpassword = null;
//		String cardowner = null;
//		String cardtype = null;
//		String cardnumber = null;
//		String cardexpirymonth = null;
//		String cardexpiryyear = null;
//		String cardsecuritycode = null;
//		String strAmount = null;
//		
//	
//		public RegressionSMUI() {
//			super();
//		}
//	
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 1, enabled = true)
//		public void testAddNewCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
//	
//			strpassword = "comein22";
//			cardowner = "Test Mastercard";
//			cardtype = "MasterCard";
//			cardnumber = "5454545454545454";
//			cardexpirymonth = "02";
//			cardexpiryyear = "2020";
//			cardsecuritycode = "123";
//	
//			initialization(environment, "cartlogin");
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//	
//			if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//				straccountreference = "DOM-1218";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//				straccountreference = "DOM-1219";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			}
//	
//			dmzloginpage.clickLoginButton();
//	
//			Thread.sleep(2000);
//			driver.findElement(By.linkText("Account")).click();
//			Thread.sleep(2000);
//	
//			dmzaccountpage.clickEditCreditCardsOnFile();
//			dmzcreditcardsdetailspage.setQuestFormCreditCardDetails(cardowner, cardtype, cardnumber, cardexpirymonth,
//					cardexpiryyear, cardsecuritycode);
//			dmzcreditcardsdetailspage.clickAddCreditCard();
//			dmzcreditcardsdetailspage.isNewCreditCardAdded();
//	
//			driver.close();
//		}
//	
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 2, enabled = true)
//		public void testModifyExistingCreditCardInSMUI(String environment, String paymentgateway)
//				throws InterruptedException {
//	
//			initialization(environment, "cartlogin");
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//	
//			if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//				straccountreference = "DOM-1218";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//				straccountreference = "DOM-1219";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			}
//	
//			dmzloginpage.clickLoginButton();
//			Thread.sleep(2000);
//			driver.findElement(By.linkText("Account")).click();
//			Thread.sleep(2000);
//			dmzaccountpage.clickEditCreditCardsOnFile();
//	
//			// Modify existing card details
//			dmzcreditcardsdetailspage.clickOnExistingCard();
//			dmzcreditcardsdetailspage.modifyCreditCardDetails();
//			dmzcreditcardsdetailspage.getConfirmationMessage();
//	
//			driver.close();
//		}
//	
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 3, enabled = true)
//		public void testMakeCreditCardDefaultInSMUI(String environment, String paymentgateway) throws InterruptedException {
//			initialization(environment, "cartlogin");
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//	
//			if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//				straccountreference = "DOM-1218";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//				straccountreference = "DOM-1219";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			}
//	
//			dmzloginpage.clickLoginButton();
//			Thread.sleep(2000);
//			driver.findElement(By.linkText("Account")).click();
//			Thread.sleep(2000);
//			dmzaccountpage.clickEditCreditCardsOnFile();
//			dmzaccountpage.makeCardDefault();
//	
//			driver.close();
//	
//		}
//	
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 4, enabled = true)
//		public void testDeleteCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
//			initialization(environment, "cartlogin");
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//	
//			if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//				straccountreference = "DOM-1218";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//				straccountreference = "DOM-1219";
//				dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			}
//	
//			dmzloginpage.clickLoginButton();
//			Thread.sleep(2000);
//			driver.findElement(By.linkText("Account")).click();
//			Thread.sleep(2000);
//			dmzaccountpage.clickEditCreditCardsOnFile();
//			dmzcreditcardsdetailspage.deleteCreditCard();
//	
//			driver.close();
//	
//		}
//		
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 5, enabled = true)
//		public void testRechargePrepaidInSMUI(String environment, String paymentgateway) throws InterruptedException {
//	
//			straccountreference = "DOM-1218";
//			strpassword = "comein22";
//			cardowner = "Test Mastercard";
//			cardtype = "MasterCard";
//			cardnumber = "5454545454545454";
//			cardexpirymonth = "02";
//			cardexpiryyear = "2020";
//			cardsecuritycode = "123";
//			strAmount = "10";
//	
//			initialization(environment, "cartlogin");
//	
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//			dmzprepaidaccountpage = new DMZPrepaidAccountPage();
//	
//			dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			dmzloginpage.clickLoginButton();
//	
//			dmzbillingpage.clickBillingTab();
//			dmzbillingpage.clickEditPrepaidAccountLink();
//			dmzprepaidaccountpage.clickRechargeUsingCreditCard();
//			dmzprepaidaccountpage.enterRechargeAmount(strAmount);
//			dmzprepaidaccountpage.clickSubmitButton();
//	
//			driver.close();
//	
//		}
//	
//	
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 6, enabled = true)
//		public void testUpdateSubscriptionDetailsSMUI(String environment, String paymentgateway)
//				throws InterruptedException {
//	
//			straccountreference = "DOM-1218";
//			strpassword = "comein22";
//	
//			initialization(environment, "cartlogin");
//	
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//			dmzprepaidaccountpage = new DMZPrepaidAccountPage();
//			dmzoverviewpage = new DMZOverviewPage();
//			dmzupdatesubscriptionbillingdetailspage = new DMZUpdateSubscriptionBillingDetailsPage();
//			dmzsummaryofservicespage = new DMZSummaryOfServicesPage();
//	
//			dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			dmzloginpage.clickLoginButton();
//	
//			dmzoverviewpage.clickAllServicesLink();
//			dmzoverviewpage.selectPaymentLink();
//			dmzupdatesubscriptionbillingdetailspage.selectExistingCard();
//			dmzupdatesubscriptionbillingdetailspage.clickSelectCardButton();
//			dmzsummaryofservicespage.verifyConfirmationMessage();
//			dmzsummaryofservicespage.verifyPartialCCNumber();
//	
//			driver.close();
//		}
//	
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 7, enabled = true)
//		public void testUpdateSubscriptionDetailsNewCardSMUI(String environment, String paymentgateway)
//				throws InterruptedException {
//			//This test case is dependent on TestCase with priority: 3
//			straccountreference = "DOM-1218";
//			strpassword = "comein22";
//			cardowner = "Test Mastercard";
//			cardtype = "MasterCard";
//			cardnumber = "5454545454545454";
//			cardexpirymonth = "02";
//			cardexpiryyear = "2020";
//			cardsecuritycode = "123";
//	
//			initialization(environment, "cartlogin");
//	
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//			dmzprepaidaccountpage = new DMZPrepaidAccountPage();
//			dmzoverviewpage = new DMZOverviewPage();
//			dmzupdatesubscriptionbillingdetailspage = new DMZUpdateSubscriptionBillingDetailsPage();
//			dmzsummaryofservicespage = new DMZSummaryOfServicesPage();
//	
//			dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			dmzloginpage.clickLoginButton();
//	
//			dmzoverviewpage.clickAllServicesLink();
//			dmzoverviewpage.selectPaymentLink();
//	
//			dmzupdatesubscriptionbillingdetailspage.setNewCreditCardDetails(cardowner, cardtype, cardnumber,
//					cardexpirymonth, cardexpiryyear, cardsecuritycode);
//			dmzupdatesubscriptionbillingdetailspage.addCardButton();
//			dmzupdatesubscriptionbillingdetailspage.verifyNewBillingMethodIsUpdated();
//			dmzsummaryofservicespage.verifyPartialDetailsOfNewCard();
//			dmzupdatesubscriptionbillingdetailspage.resetDefaultCard();
//			dmzupdatesubscriptionbillingdetailspage.verifyNewBillingMethodIsUpdated();
//	
//			driver.close();
//		}
//	
//		@Parameters({ "environment", "paymentgateway" })
//		@Test(priority = 8, enabled = true)
//		public void testDeleteCreditCardFromSMUI(String environment, String paymentgateway) throws InterruptedException {
//			initialization(environment, "cartlogin");
//			dmzloginpage = new DMZLoginPage();
//			dmzaccountpage = new DMZAccountPage();
//			dmzbillingpage = new DMZBillingPage();
//			dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//	
//			dmzloginpage.setLoginDetails(straccountreference, strpassword);
//			dmzloginpage.clickLoginButton();
//			Thread.sleep(2000);
//			driver.findElement(By.linkText("Account")).click();
//			Thread.sleep(2000);
//			dmzaccountpage.clickEditCreditCardsOnFile();
//			dmzcreditcardsdetailspage.deleteCreditCard();
//			driver.close();
//	
//		}
//}
