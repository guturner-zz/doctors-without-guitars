package org.guy.rpg.dwg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for core application pages.
 * Main Menu, About, etc.
 * 
 * @author Guy
 */
@Controller
public class StreamController extends BaseController {
	
	@RequestMapping("/stream-beta")
	public String main(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		
		return "stream/main";
	}
	
}
