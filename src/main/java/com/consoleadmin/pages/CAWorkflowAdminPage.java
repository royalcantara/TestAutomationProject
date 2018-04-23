package com.consoleadmin.pages;

import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CAWorkflowAdminPage extends TestBase{

	//Objects
	
	//Initializing Page Objects
    public CAWorkflowAdminPage(){
    	PageFactory.initElements(driver, this);
    }
}
