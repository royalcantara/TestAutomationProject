package com.consoleadmin.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CAWorkflowAdminPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = "//table/tbody/tr/td/table[3]/tbody/tr[3]/td/input")
    WebElement executeActionButton;
    
    @FindBy(how=How.XPATH, using ="//th[contains(text(),'payment.preauth.preauth')]")
    WebElement preAuthVariableName;
	
	//Initializing Page Objects
    public CAWorkflowAdminPage(){
    	PageFactory.initElements(driver, this);
    }
    
    //Methods
    public void processDomainRegistrationWF(String strworkflowid) throws InterruptedException {

    	driver.findElement(By.linkText(strworkflowid)).click();			
    	Thread.sleep(3000);
    		
    	//Click Execute for Check ASIC
    	driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'check asic')]/parent::tr/td[3]/a[text()='Execute']")).click();
    	Thread.sleep(3000);
    		
    	//Click Execute Action
    	executeActionButton.click();
    	Thread.sleep(6000);

    	//Click Execute for Mark as Registered
    	driver.findElement(By.xpath("//a[@href='/admin/execute/workflow/getActionForm?action_id=7014&workflow_id="+strworkflowid+"']")).click();
    	Thread.sleep(5000);
    	
    	//Click Execute Action
    	executeActionButton.click();
    	Thread.sleep(5000);

    }
    	
    	public void processDelegateDomain() throws InterruptedException {
    		
    		//Click Execute for Delegate Domain
    		driver.findElement(By.xpath("//tbody/tr[4]/td[contains(text(),'I have manually delegated this domain')]/parent::tr/td[3]/a[text()='Execute']")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute Action
    		executeActionButton.click();
    		Thread.sleep(6000);

    	}
    	
    	public void processProductSetup2DIFM() throws InterruptedException {
    		
    		//Click DIFM workflowid
    		driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute for Run Setup
    		driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute Action
    		executeActionButton.click();
    		Thread.sleep(3000);
    			
    	}
    	
    	public void processProductSetup2() throws InterruptedException {
    		
    		//Click DIFM workflowid
    		driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute for Run Setup
    		driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute Action
    		executeActionButton.click();
    		Thread.sleep(3000);
    			
    	}
    	
    	public void processProductSetup2ByWFID(String strworkflowid) throws InterruptedException {
    		
    		//Click ProductSetup2 by Workflow ID
    		driver.findElement(By.linkText(strworkflowid)).click();			
    		Thread.sleep(3000);
    		
    		//Click Execute for Run Setup
    		driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute Action
    		executeActionButton.click();
    		Thread.sleep(3000);
    			
    	}
    	
    	public void processProductSetup2O365() throws InterruptedException {
    		
    		//Click Office365 workflowid
    		driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute for Run Setup
    		driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute Action
    		executeActionButton.click();
    		Thread.sleep(3000);
    			
    	}
    	
    	public void processProductSetup2DIFMUpgrade(String strworkflowid) throws InterruptedException {
    		
    		//Click DIFM Upgrade workflowid
    		driver.findElement(By.linkText(strworkflowid)).click();	
    		Thread.sleep(3000);
    		
    		//Click Execute for Run Setup
    		driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']")).click();
    		Thread.sleep(3000);
    		
    		//Click Execute Action
    		executeActionButton.click();
    		Thread.sleep(3000);
    			
    	}
    	
    	
    	public boolean verifyWorflowStatus(String strworkflowtype, String strworkflowstep) throws InterruptedException {
        	
        	String count;
        	String workflowtypetext;
        	String workflowsteptext;
        	Boolean workflowstepcheck = false;
        	
         	Thread.sleep(3000);
         	List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
         	int rowcount = workflowtablerows.size();

        	for (int i=2; i<=rowcount; i++) {
        		
        		count = String.valueOf(i);
        		Thread.sleep(1000);
        		workflowtypetext = driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[2]")).getText();		
        		System.out.println("Workflow Type Text: " + workflowtypetext); 
        		    		
        		if (workflowtypetext.equals(strworkflowtype)) {
        			System.out.println("Workflow Type Found");
        			
        			workflowsteptext = driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[5]")).getText();
        			System.out.println("Workflow Step Text: " + workflowsteptext); 
        			
        			if (workflowsteptext.equals(strworkflowstep)) {
        				workflowstepcheck = true;	
        			}
    		
        		}
    		}
        	
        	return workflowstepcheck;

        }
    	
    	public String getWorkflowStatus(String strworkflowtype) throws InterruptedException {
        	
        	String count;
        	String workflowtypetext;
        	String workflowsteptext = strworkflowtype + " status: ";
        	
         	Thread.sleep(3000);
         	List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
         	int rowcount = workflowtablerows.size();

        	for (int i=2; i<=rowcount; i++) {
        		
        		count = String.valueOf(i);
        		Thread.sleep(1000);
        		workflowtypetext = driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[2]")).getText();		
        		System.out.println("Workflow Type Text: " + workflowtypetext); 
        		
        		if (workflowtypetext.equals(strworkflowtype)) {
        			System.out.println("Workflow Type Found");
        			
        			workflowsteptext = driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[5]")).getText();
        			System.out.println("Workflow Step Text: " + workflowsteptext); 
        					
        		}
    		}
        	
        	return workflowsteptext;

        }
    	
    	
    	public boolean verifyWorflowStatusViaID(String strworkflowid, String strworkflowstep) throws InterruptedException {
        	
        	String count;
        	String workflowidnumber;
        	String workflowsteptext;
        	Boolean workflowstepcheck = false;
        	
         	Thread.sleep(3000);
         	List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
         	int rowcount = workflowtablerows.size();

        	for (int i=2; i<=rowcount; i++) {
        		
        		count = String.valueOf(i);
        		Thread.sleep(1000);
        		workflowidnumber = driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[1]")).getText();		
        		System.out.println("Workflow ID : " + workflowidnumber); 	
        		
        		if (workflowidnumber.equals(strworkflowid)) {
        			System.out.println("Workflow ID Found");
        			
        			workflowsteptext = driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[5]")).getText();
        			if (workflowsteptext.equals(strworkflowstep)) {
        				workflowstepcheck = true;	
        			}

        			System.out.println("Workflow Step Text: " + workflowsteptext); 		
        		}
    		}
        	
        	return workflowstepcheck;

        }
    	
    	public Boolean isWorkflowIDExist(String strworkflowid) throws InterruptedException {
            
        	Boolean flag = false;
        	Thread.sleep(2000);

        	if (driver.findElement(By.linkText(strworkflowid)).isDisplayed()) {
        		System.out.println("Workflow ID Found");
        		flag = true;
        	}

        	return flag;

        }
    	
    	public String getPreAuthNumber(String strworkflowid) throws InterruptedException {
            
    		String preauthnumber = null;
    		
        	Thread.sleep(2000);
        	driver.findElement(By.linkText(strworkflowid)).click();
        	Thread.sleep(1000);
        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", preAuthVariableName);
        	Thread.sleep(5000);
        	if (preAuthVariableName.isDisplayed()) {
        	
        		preauthnumber = driver.findElement(By.xpath("//th[contains(text(),'payment.preauth.preauth')]/parent::tr/td")).getText();
        		System.out.println("Preauth Number: "+ preauthnumber);
      
        	}

        	return preauthnumber;

        }
    	
//    	public String getWorkflowParameterValues(String workflowparamname) throws InterruptedException {
//            
//    		String preauthnumber = null;
//    		
//        	Thread.sleep(2000);
//        	driver.findElement(By.linkText(strworkflowid)).click();
//        	Thread.sleep(1000);
//        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", preAuthVariableName);
//        	Thread.sleep(5000);
//        	if (preAuthVariableName.isDisplayed()) {
//        	
//        		preauthnumber = driver.findElement(By.xpath("//th[contains(text(),'payment.preauth.preauth')]/parent::tr/td")).getText();
//        		System.out.println("Preauth Number: "+ preauthnumber);
//      
//        	}
//
//        	return preauthnumber;
//
//        }
    	
    	
    		
    }


