package org.guy.rpg.dwg.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.db.repositories.CharacterRepository;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.models.db.Class;
import org.guy.rpg.dwg.models.db.Size;
import org.guy.rpg.dwg.models.db.User;
import org.guy.rpg.dwg.validators.CharacterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for character-related pages.
 * 
 * @author Guy
 */
@Controller
public class CharacterController extends BaseController {
	
	@Autowired
	CharacterRepository characterRepository;
	
	private static String[] classList = {
			"Select Class", "Barbarian", "Bard", "Cleric",
			"Druid", "Fighter", "Monk", "Paladin", "Ranger",
			"Rogue", "Sorceror", "Wizard"
	};
	
	private static String[] sizeList = {
			"Select Size", "Small", "Medium", "Large", "Huge"
	};
	
	@GetMapping("/characters")
	public String getProfile(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("characters", dbManager.getCurrentUser(request).getCharacters());
		
		return "character/main";
	}
	
	@GetMapping("/createCharacter")
	public String getCharacterCreate(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("characterValidator", new CharacterValidator());
		model.addAttribute("classList", classList);
		model.addAttribute("sizeList", sizeList);
		
		return "character/create";
	}
	
	@PostMapping("/createCharacter")
	public ModelAndView setCharacterCreate(@Valid @ModelAttribute CharacterValidator characterValidator, BindingResult result, HttpServletRequest request, RedirectAttributes redir, Model model) {
		Character newCharacter = new Character();
		newCharacter.setUser(dbManager.getCurrentUser(request));
		newCharacter.setName(characterValidator.getName());
		newCharacter.setSize(new Size(characterValidator.getSize()));
		newCharacter.setCharClass(new Class(characterValidator.getClassId()));
		newCharacter.setImage(characterValidator.getImage());
			
		newCharacter.setCharSheet(new CharacterSheet());			
		dbManager.saveCharacter(newCharacter);
		
		model.addAllAttributes(getAttributeMap(request));
		String successMsg = "Character " + newCharacter.getName() + " was created!";
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/characters");
	    redir.addFlashAttribute("successMsg", successMsg);
	    return modelAndView;
	}
	
	@PostMapping("/deleteCharacter")
	public ModelAndView deleteCharacter(@RequestParam("id") Long characterId, HttpServletRequest request, RedirectAttributes redir, Model model) throws IOException {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		dbManager.deleteCharacter(characterFromId);
		String successMsg = "Character " + characterFromId.getName() + " was successfully deleted!";
		
		modelAndView.setViewName("redirect:/characters");
	    redir.addFlashAttribute("successMsg", successMsg);
	    return modelAndView;
	}
	
	@Override
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = super.getAttributeMap(request);
		
		return attributeMap;
	}
	
}
