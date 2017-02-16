package org.guy.rpg.dwg.security;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.tenant.Tenant;

/**
 * Logic for requesting account data from Stormpath.
 * 
 * Note for localhost:
 * If ApplicationList has 0 size, check C:\Users\<user>\.stormpath\apiKey.properties
 * 
 * @author Guy
 */
public class AccountManager {

	private static final String APP_NAME = "doctors-without-guitars";
	
	/**
	 * Retrieves the Stormpath Account object.
	 */
	public static Application getApplication() {
		ClientBuilder builder = Clients.builder(); 
		Client client = builder.build();
		
		Tenant tenant = client.getCurrentTenant();
		ApplicationList applications = tenant.getApplications(
		        Applications.where(Applications.name().eqIgnoreCase(APP_NAME))
		);
		
		return applications.single();
	}
	
}
