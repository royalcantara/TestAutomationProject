package com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.util.TestUtil;
//import com.util.WebEventListener;

public class TestBase{

	public static WebDriver driver;
	public static Properties prop;
//	public  static EventFiringWebDriver e_driver;
//	public static WebEventListener eventListener;
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void initialization(){
		
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")){
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
		

//		e_driver = new EventFiringWebDriver(driver);
//		// Now create object of EventListerHandler to register it with EventFiringWebDriver
//		eventListener = new WebEventListener();
//		e_driver.register(eventListener);
//		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		
	}
	
	
}
