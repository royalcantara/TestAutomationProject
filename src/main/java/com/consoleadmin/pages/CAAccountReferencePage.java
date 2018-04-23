package com.consoleadmin.pages;

import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CAAccountReferencePage extends TestBase{

	//Objects
		
	//Initializing Page Objects
    public CAAccountReferencePage(){
    	PageFactory.initElements(driver, this);
    }
}
