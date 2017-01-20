package org.guy.rpg.dwg.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.security.UserManager;

import com.stormpath.sdk.account.Account;

/**
 * Shared logic for Controllers.
 * 
 * @author Guy
 */
public abstract class BaseController {

	/**
	 * Returns a map of generic attributes.
	 * Should be overridden for specific Controllers.
	 * 
	 */
	protected Map<String, String> getAttributeMap(HttpServletRequest request) {
		Map<String, String> attributeMap = new HashMap<String, String>();
		
		// Account related:
		Account account = UserManager.getCurrentUserAccount(request);
		attributeMap.put("user", UserManager.getUsernameFromAccount(account));
		
		return attributeMap;
	}
	
}
