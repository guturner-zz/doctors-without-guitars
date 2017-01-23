package org.guy.rpg.dwg.interceptors;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.guy.rpg.dwg.security.UserManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * A simple authentication interceptor that doesn't rely on spring-security.
 * 
 * @author Guy
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	// Collection of pages that should not require authentication:
	private final ArrayList<String> UNSECURED_PAGES = new ArrayList<String>() {{
		add("/");
		add("/error");
		add("/login");
		add("/logout");
		add("/register");
	}};

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

		// Avoid a redirect loop on unsecured pages:
		if (!UNSECURED_PAGES.contains(request.getRequestURI())) {
			
			// Only redirect if not logged in:
			if (UserManager.getCurrentUserAccount(request) == null) {
				response.sendRedirect("/login");
				return false;
			}
		}
		
		return true;
	}
}