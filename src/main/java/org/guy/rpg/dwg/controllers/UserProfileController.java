package org.guy.rpg.dwg.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.models.db.User;
import org.guy.rpg.dwg.security.UserManager;
import org.guy.rpg.dwg.validators.UserProfileValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for the user profile page.
 * 
 * @author Guy
 */
@Controller
public class UserProfileController extends BaseController {

	@GetMapping("/profile")
	public String getProfile(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", false);
		model.addAttribute("account", UserManager.getCurrentUserAccount(request));
		
		return "user/profile";
	}
	
	@PostMapping("/profile")
	public String setProfile(@Valid @ModelAttribute UserProfileValidator profileValidator, BindingResult result, HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		
		return "user/profile";
	}
	
	@PostMapping("/editProfile")
	public String setEditMode(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", true);
		model.addAttribute("profileValidator", new UserProfileValidator());
		
		return "user/profile";
	}
	
	@Override
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = super.getAttributeMap(request);
		attributeMap.put("editMode", false);
		
		return attributeMap;
	}
	
}
