package org.guy.rpg.dwg.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.models.db.Class;
import org.guy.rpg.dwg.validators.CharacterValidator;
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
		
		return "user/profile";
	}
	
	@PostMapping("/profile")
	public String setProfile(@Valid @ModelAttribute CharacterValidator characterValidator, BindingResult result, HttpServletRequest request, Model model) {
		Character userCharacter = dbManager.getCurrentUserCharacter(request);
		if (userCharacter == null) {
			Character newCharacter = new Character();
			newCharacter.setUser(dbManager.getCurrentUser(request));
			newCharacter.setName(characterValidator.getName());
			newCharacter.setCharClass(new Class(characterValidator.getClassId()));
			
			String imagePath = characterValidator.getImage();
			if (!imagePath.equals("")) {
				newCharacter.setImage(imagePath);
			}
			
			newCharacter.setCharSheet(new CharacterSheet());
			
			dbManager.saveCharacter(newCharacter);
		} else {
			String name = characterValidator.getName();
			if (!name.equals("")) {
				userCharacter.setName(name);
			}
			
			int classId = characterValidator.getClassId();
			if (classId != 0) {
				userCharacter.setCharClass(new Class(classId));
			}
			
			String imagePath = characterValidator.getImage();
			if (!imagePath.equals("")) {
				userCharacter.setImage(imagePath);
			}
			
			dbManager.saveCharacter(userCharacter);
		}
		
		model.addAllAttributes(getAttributeMap(request));
		
		return "user/profile";
	}
	
	@PostMapping("/editProfile")
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
		
		return attributeMap;
	}
	
}
