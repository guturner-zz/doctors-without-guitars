package org.guy.rpg.dwg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.models.db.User;
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
public class MainController extends BaseController {
	
	@RequestMapping("/")
	public String main(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("isHttp", isHttp(request));
		
		User user = dbManager.getCurrentUser(request);
		if (user != null) {
			model.addAttribute("characters", user.getCharacters());
		}
		
		return "main/main";
	}
	
}
