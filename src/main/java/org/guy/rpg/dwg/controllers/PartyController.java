package org.guy.rpg.dwg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the party page.
 * 
 * @author Guy
 */
@Controller
public class PartyController extends BaseController {

	@RequestMapping("/party")
	public String main(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		
		return "game/party";
	}
	
}
