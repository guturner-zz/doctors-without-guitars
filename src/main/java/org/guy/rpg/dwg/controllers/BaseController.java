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
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = new HashMap<String, Object>();
		
		// Account related:
		Account account = UserManager.getCurrentUserAccount(request);
		attributeMap.put("user", UserManager.getUsernameFromAccount(account));
		
		return attributeMap;
	}
	
}
