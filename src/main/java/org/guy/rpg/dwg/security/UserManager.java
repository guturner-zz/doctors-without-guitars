package org.guy.rpg.dwg.security;

import javax.servlet.http.HttpServletRequest;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;

public class UserManager {

	/**
	 * Retrieves the currently authenticated user from Stormpath.
	 * 
	 * @param request
	 * @return the currently authenticated Stormpath account OR null if no user is logged in.
	 */
	public static Account getCurrentUser(HttpServletRequest request) {
		if (AccountResolver.INSTANCE.hasAccount(request)) {
    		Account account = AccountResolver.INSTANCE.getRequiredAccount(request);
	    	if (account != null) {
	    		return account;
	    	}
    	}
		
		return null;
	}
	
}
