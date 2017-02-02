package org.guy.rpg.dwg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.db.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	DatabaseManager dbManager;
	
	@RequestMapping("/")
	public String main(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("character", dbManager.getCurrentUserCharacter(request));
		
		return "main/main";
	}
	
}
