package org.guy.rpg.dwg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
 * Controller for WebCam functionality.
 * Still researching the best approach.
 * 
 * @author Guy
 */
//@Controller
public class StreamController extends BaseController {
	
	//@RequestMapping("/stream")
	public String main(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		
		return "stream/main";
	}
	
}
