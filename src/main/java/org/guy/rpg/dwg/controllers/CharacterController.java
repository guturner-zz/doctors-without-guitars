package org.guy.rpg.dwg.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.db.repositories.CharacterRepository;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.models.db.Class;
import org.guy.rpg.dwg.models.db.Race;
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
	
	private static String[] raceList = {
			"Select Race", "Dwarf", "Elf", "Gnome", "Half-Elf",
			"Half-Orc", "Halfling", "Human"
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
		model.addAllAttributes(getEditCharacterAttributeMap(request));
		
		return "character/create";
	}
	
	@PostMapping("/createCharacter")
	public ModelAndView setCharacterCreate(@Valid @ModelAttribute CharacterValidator characterValidator, BindingResult result, HttpServletRequest request, RedirectAttributes redir, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		// Validate Form Result first:
		List<String> errors = characterValidator.validate(result, request);
		if (!errors.isEmpty()) {
			model.addAllAttributes(getEditCharacterAttributeMap(request));
			model.addAttribute("characterValidator", characterValidator);
			model.addAttribute("errors", errors);
			modelAndView.setViewName("character/create");
			return modelAndView;
		}
		
		Character newCharacter = generateNewCharacter(characterValidator, request);
		dbManager.saveCharacter(newCharacter);
		
		model.addAllAttributes(getAttributeMap(request));
		String successMsg = "Character " + newCharacter.getName() + " was created!";
		
		modelAndView.setViewName("redirect:/characters");
	    redir.addFlashAttribute("successMsg", successMsg);
	    return modelAndView;
	}
	
	@GetMapping("/editCharacter")
	public ModelAndView getEditCharacter(@RequestParam("id") Long characterId, HttpServletRequest request, Model model) {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		model.addAllAttributes(getEditCharacterAttributeMap(request));
		model.addAttribute("character", characterFromId);
		modelAndView.setViewName("character/edit");
		return modelAndView;
	}
	
	@PostMapping("/editCharacter")
	public ModelAndView setEditCharacter(@RequestParam("id") Long characterId, @Valid @ModelAttribute CharacterValidator characterValidator, BindingResult result, HttpServletRequest request, RedirectAttributes redir, Model model) {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		// Validate Form Result first:
		List<String> errors = characterValidator.validate(result, request);
		if (!errors.isEmpty()) {
			model.addAllAttributes(getEditCharacterAttributeMap(request));
			model.addAttribute("character", characterFromId);
			model.addAttribute("characterValidator", characterValidator);
			model.addAttribute("errors", errors);
			modelAndView.setViewName("character/edit");
			return modelAndView;
		}
		
		modifyCharacterByValidator(characterFromId, characterValidator, request);
		dbManager.saveCharacter(characterFromId);
		
		model.addAllAttributes(getAttributeMap(request));
		String successMsg = "Character " + characterFromId.getName() + " was updated!";
		
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
	
	/**
	 * Takes a character object and modifies it according to its characterValidator.
	 * Notice that this method uses side effects.
	 */
	private void modifyCharacterByValidator(Character character, CharacterValidator characterValidator, HttpServletRequest request) {
		character.setUser(dbManager.getCurrentUser(request));
		character.setName(characterValidator.getName());
		character.setSize(new Size(characterValidator.getSize()));
		character.setRace(new Race(characterValidator.getRaceId()));
		character.setCharClass(new Class(characterValidator.getClassId()));
		character.setImage(characterValidator.getImage());
	}
	
	/**
	 * Create a new character object according to a characterValidator.
	 */
	private Character generateNewCharacter(CharacterValidator characterValidator, HttpServletRequest request) {
		Character newCharacter = new Character();
		modifyCharacterByValidator(newCharacter, characterValidator, request);
		
		// Properties on new characters:
		newCharacter.setNewCharacterFlag(true);
		newCharacter.setCharSheet(new CharacterSheet());
		
		return newCharacter;
	}
	
	private Map<String, Object> getEditCharacterAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = getAttributeMap(request);
		attributeMap.put("characterValidator", new CharacterValidator());
		attributeMap.put("classList", classList);
		attributeMap.put("raceList", raceList);
		attributeMap.put("sizeList", sizeList);
		
		return attributeMap;
	}
	
	@Override
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = super.getAttributeMap(request);
		
		return attributeMap;
	}
	
}
