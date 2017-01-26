package org.guy.rpg.dwg.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Stormpath requires an HTTPS connection in order to authenticate.
 * This guarantees the user is connected via HTTPS.
 * 
 * @author Guy
 */
public class HTTPSInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

		String currentURL = request.getRequestURL().toString();
		
		if (currentURL.contains("herokuapp")) {
			String newURL = currentURL.replace("http://", "https://");
			request.getRequestDispatcher(newURL).forward(request, response);
		}
		
		return true;
	}
	
}
