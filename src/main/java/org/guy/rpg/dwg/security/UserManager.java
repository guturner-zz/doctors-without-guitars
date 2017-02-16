package org.guy.rpg.dwg.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.servlet.account.AccountResolver;

/**
 * Logic for requesting user data from Stormpath.
 * 
 * @author Guy
 */
public class UserManager {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

	/**
	 * Retrieves the currently authenticated user from Stormpath.
	 * 
	 * @return the currently authenticated Stormpath account OR null if no user is logged in.
	 */
	public static Account getCurrentUserAccount(HttpServletRequest request) {
		if (AccountResolver.INSTANCE.hasAccount(request)) {
    		Account account = AccountResolver.INSTANCE.getRequiredAccount(request);
	    	if (account != null) {
	    		return account;
	    	}
    	}
		
		return null;
	}
	
	public static String getUsernameFromAccount(Account account) {
		if (account != null) {
			return account.getUsername();
		}
		
		return null;
	}
	
	public static boolean isEmailInUse(HttpServletRequest request, String email) {
		Application app = AccountManager.getApplication();
		
		for (Account a : app.getAccounts()) {
			if (a.getEmail().equals(email)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Account createNewAccount(String firstName, String lastName, String email, String password) {
		Account account = null;
		
		try {
			ClientBuilder builder = Clients.builder(); 
			Client client = builder.build();
			Application app = AccountManager.getApplication();
			
			account = client.instantiate(Account.class)
					.setGivenName(firstName)
					.setEmail(email)
				    .setSurname(lastName)
				    .setPassword(password);

			app.createAccount(account);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
		
		return account;
	}
	
	/**
	 * Deletes the Stormpath account with the given email.
	 */
	public static boolean deleteAccount(String email) {
		Application app = AccountManager.getApplication();
		
		AccountList accounts = app.getAccounts();
		for (Account account : accounts) {
			if (account.getEmail().equals(email)) {
				account.delete();
				return true;
			}
		}
		
		return false;
	}
}
