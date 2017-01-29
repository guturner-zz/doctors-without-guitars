package org.guy.rpg.dwg.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.db.DatabaseManager;
import org.guy.rpg.dwg.validators.CharacterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the user profile page.
 * 
 * @author Guy
 */
@Controller
public class UserProfileController extends BaseController {

	@Autowired
	DatabaseManager dbManager;
	
	@GetMapping("/profile")
	public String getProfile(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", false);
		
		return "user/profile";
	}
	
	@PostMapping("/profile")
	public String setProfile(@Valid @ModelAttribute CharacterValidator characterValidator, BindingResult result, HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", false);
		
		return "user/profile";
	}
	
	@RequestMapping("/editProfile")
	public String setEditMode(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", true);
		model.addAttribute("characterValidator", new CharacterValidator());
		
		return "user/profile";
	}
	
	@Override
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = super.getAttributeMap(request);
		attributeMap.put("editMode", false);
		attributeMap.put("character", dbManager.getCurrentUserCharacter(request));
		
		return attributeMap;
	}
	
}
