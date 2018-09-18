package com.base;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.util.TestUtil;
import com.util.WebEventListener;

public class TestBase{

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	static Environment testEnvironment;
	
	public TestBase(){

	}
	
	public static void initialization(String environment, String entrypoint){
		
		ConfigFactory.setProperty("env", environment);
		testEnvironment = ConfigFactory.create(Environment.class);
		System.out.println("Environment: "+ environment);
		String browserName = testEnvironment.browser();
		
		if(browserName.equals("chrome")){
//			ChromeOptions options = new ChromeOptions(); 
//			options.setBinary("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"); 
			System.setProperty("webdriver.chrome.driver", "seleniumwebdriver/chromedriver/chromedriver.exe");	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", "seleniumwebdriver/firefoxdriver/geckodriver.exe");	
			driver = new FirefoxDriver(); 
		}
		else if(browserName.equals("edge")){
			System.setProperty("webdriver.edge.driver", "seleniumwebdriver/edgedriver/MicrosoftWebDriver.exe");	
			driver = new EdgeDriver();
		}
		

		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		if(entrypoint.equals("cart")){
			driver.get(testEnvironment.carturl());
		}
		else if(entrypoint.equals("cartlogin")){
			driver.get(testEnvironment.cartloginurl());
		}
		else if(entrypoint.equals("customerportalurl_netregistry")){
			driver.get(testEnvironment.customerportalurl_netregistry());
		}
		else if(entrypoint.equals("cart_domainsearchurl_domainz")){
			driver.get(testEnvironment.cart_domainsearchurl_domainz());
		}
		else if(entrypoint.equals("salesdb")){
			driver.get(testEnvironment.salesdburl());
		}
		else if(entrypoint.equals("consoleadmin")){
			driver.get(testEnvironment.consoleadminurl());
		}
		else if(entrypoint.equals("braintree")){
			driver.get(testEnvironment.braintreeurl());
		}
		else{
			/* for any url */
		}
	}
	
	
}
