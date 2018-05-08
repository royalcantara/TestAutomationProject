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
	
	public static void initialization(String environment){
		
		ConfigFactory.setProperty("env", environment);
		testEnvironment = ConfigFactory.create(Environment.class);
		System.out.println("Environment: "+ environment);
		String browserName = testEnvironment.browser();
		
		if(browserName.equals("chrome")){
			ChromeOptions options = new ChromeOptions(); 
			options.setBinary("browser/chrome/GoogleChromePortable.exe"); 
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
		
		driver.get(testEnvironment.url());
		
	}
	
	
}
