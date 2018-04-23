package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WorkflowNotificationPage {


	WebDriver driver;
	
    //Initialize
    public WorkflowNotificationPage(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        Thread.sleep(2000);
        PageFactory.initElements(driver, this);

    }

  
	//Objects
    @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
    WebElement okButton;
    
    
    //Methods
    public String getWorkflowID() throws InterruptedException {

		Thread.sleep(5000);
		String workflowId = driver.findElement(By.xpath("//*[@class='ext-mb-text']")).getText();
		String []workid = workflowId.split(": ",2);												 		 
		System.out.println("Workflow Id = "+workid[1]);
		
		return (workid[1]);
		
	}
            
    public void clickOKButton() throws InterruptedException {

    	Thread.sleep(2000);
		okButton.click();
		
	}
	
}
