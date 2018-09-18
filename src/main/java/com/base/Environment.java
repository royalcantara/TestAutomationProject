package com.base;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources(
	"file:src/main/java/com/config/${env}.properties" 
)

public interface Environment extends Config{
	
	String carturl();
	String cartloginurl();
	String customerportalurl_netregistry();
	String cart_domainsearchurl_domainz();
	String carturl_domainz();
	String salesdburl();
	String consoleadminurl();
	String username();
	String password();
	String browser();
	String braintreeurl();
	
	@Key("db.hostname")
	String getDBHostname();
	
	@Key("db.port")
	int getDBPort();
	
	@Key("db.username")
	String getDBUsername();
	
	@Key("db.password")
    String getDBPassword();
	
}
