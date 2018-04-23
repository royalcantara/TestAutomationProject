package com.kibana.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DiscoverTabPage {

	WebDriver driver;
	private static String PGLogsAfterRequest;
	private static String PGLogsBeforeRequest;
	private static String TraceID;
	
    //Initialize
    public DiscoverTabPage(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        Thread.sleep(3000);
        PageFactory.initElements(driver, this);

    }

    
	//Objects 
    @FindBy(how=How.XPATH, using = "//div[@class='kuiLocalSearch']/input")
    WebElement searchBox;
    
    @FindBy(how=How.XPATH, using = "//div[@class='kuiLocalSearch']/button")
    WebElement searchButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='kuiLocalMenu']/div[3]")
    WebElement openTab;


    //Methods
    public void searchText(String text) throws InterruptedException {
    
    	Thread.sleep(2000);
    	searchBox.clear();
    	searchBox.sendKeys(text);
    	Thread.sleep(2000);
    	searchButton.click();

    }
    
    public void clickOpenTab() throws InterruptedException {
        
    	Thread.sleep(3000);
    	openTab.click();

    }
    
    public void clickSavedSearch(String searchtext) throws InterruptedException {
        
    	Thread.sleep(2000);
    	if (searchtext.equals("PGW")) {
    		driver.findElement(By.xpath("//ul[@class='li-striped list-group list-group-menu']/li[4]")).click();
    	}

    }
    
    public Boolean isControlTrackingIDRecordCountCorrect() throws InterruptedException {
    
    	Boolean flag = false;
    	Thread.sleep(2000);
    	List<WebElement> logtablerowcount = driver.findElements(By.xpath("//table[@class='kbn-table table']/tbody/tr"));
    	int rowcount = (logtablerowcount.size() - 2);
    	
    	if (rowcount == 2) {
    		flag = true;
    	}
    	System.out.println("The number of records: " + String.valueOf(rowcount));
    	
    	return flag;

    }
    
    public Boolean isControlTrackingIDLogExists(String controltrackingid) throws InterruptedException {
        
    	Boolean flag = false;
    	Thread.sleep(2000);
    	PGLogsAfterRequest = driver.findElement(By.xpath("//table[@class='kbn-table table']/tbody/tr[1]/td[4]/div[contains(text(),'request with URI')]")).getText();
    	PGLogsBeforeRequest = driver.findElement(By.xpath("//table[@class='kbn-table table']/tbody/tr[3]/td[4]/div[contains(text(),'request with URI')]")).getText();
    	
    	if ((PGLogsAfterRequest.contains(controltrackingid)) && (PGLogsBeforeRequest.contains(controltrackingid))) {
    		System.out.println("Control Tracking ID Logged After Request: " + PGLogsAfterRequest);
    		System.out.println("Control Tracking ID Logged Before Request: " + PGLogsBeforeRequest);
    		flag = true;
    	}

    	return flag;

    }
    
    
//    public String getTraceIDForTransactionID(String transactionid) throws InterruptedException {
//        
//    	Boolean flag = false;
//    	
//    	Thread.sleep(2000);
//    	TraceID = driver.findElement(By.xpath("//table[@class='kbn-table table']/tbody/tr[1]/td[7]")).getText();
//    	System.out.println("The transaction ID is " + transactionid);
//    	System.out.println("The trace ID is " + TraceID);
//    	         
//    	return flag;
//
//    }
    
    public String getTraceIDForPreAuthRequest() throws InterruptedException {
 
    	String request_uri = null;
    	String preauthtraceid = null;
    	
		Thread.sleep(2000);
		request_uri = driver.findElement(By.xpath("//div[@class='discover-content']/div[2]/div[2]/doc-table/div/table/tbody/tr[3]/td[3]/div")).getText();
		System.out.println("Request_Uri: " + request_uri);
		if (request_uri.contentEquals("/pgw/preauth-new-customer")) {
			preauthtraceid = driver.findElement(By.xpath("//div[@class='discover-content']/div[2]/div[2]/doc-table/div/table/tbody/tr[3]/td[7]")).getText();
		}
		else {
			System.out.println("Request_Uri mismatch!");
		}
		
	         
		return preauthtraceid;

    }
    
    public String getTransactionIDForPreAuthTraceID(String traceid) throws InterruptedException {
    	 
    	String preauthtraceid = null;
    	String transactionid = null;
    	
		Thread.sleep(2000);
		preauthtraceid = driver.findElement(By.xpath("//div[@class='discover-content']/div[2]/div[2]/doc-table/div/table/tbody/tr[1]/td[7]")).getText();
		
		if (preauthtraceid.contentEquals(traceid)) {
			transactionid = driver.findElement(By.xpath("//div[@class='discover-content']/div[2]/div[2]/doc-table/div/table/tbody/tr[1]/td[9]")).getText();
		}
		else {
			System.out.println("Trace ID mismatch!");
		}
		
	         
		return transactionid;

    }
    
    
    public Boolean isClientTokenLogExists(String clienttoken) throws InterruptedException {
        
    	Boolean flag = false;
    	Thread.sleep(2000);
    	PGLogsAfterRequest = driver.findElement(By.xpath("//table[@class='kbn-table table']/tbody/tr[1]/td[4]/div[contains(text(),'request with URI')]")).getText();
    	
    	if (PGLogsAfterRequest.contains(clienttoken)) {
    		System.out.println("Client Token Logged After Request: " + PGLogsAfterRequest);
    		flag = true;
    	}

    	return flag;

    }
    
    public Boolean isTransactionIDExists(String transactionid) throws InterruptedException {
        
    	Boolean flag = false;
    	Thread.sleep(2000);
    	PGLogsAfterRequest = driver.findElement(By.xpath("//table[@class='kbn-table table']/tbody/tr[1]/td[4]/div[contains(text(),'request with URI')]")).getText();
    	
    	if (PGLogsAfterRequest.contains(transactionid)) {
    		System.out.println("Transaction ID Logged After Request: " + PGLogsAfterRequest);
    		flag = true;
    	}

    	return flag;

    }
    
    
    
}
