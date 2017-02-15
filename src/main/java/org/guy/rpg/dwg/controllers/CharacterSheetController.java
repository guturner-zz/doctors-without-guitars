package org.guy.rpg.dwg.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.db.repositories.CharacterRepository;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.models.db.User;
import org.guy.rpg.dwg.models.db.Weapon;
import org.guy.rpg.dwg.validators.CharacterSheetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the character sheet page.
 * 
 * @author Guy
 */
@Controller
public class CharacterSheetController extends BaseController {
	
	private static Map<Long, String> hitDieMapping = new HashMap<Long, String>() {{
		put(1L, "1D12");
		put(2L, "1D8");
		put(3L, "1D8");
		put(4L, "1D8");
		put(5L, "1D10");
		put(6L, "1D8");
		put(7L, "1D10");
		put(8L, "1D10");
		put(9L, "1D8");
		put(10L, "1D6");
		put(11L, "1D6");
	}};
	
	@Autowired
	CharacterRepository characterRepository;
	
	@GetMapping("/firstSteps")
	public ModelAndView getFirstSteps(@RequestParam("id") Long characterId, HttpServletRequest request, Model model) {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		// Validate that this character is in fact brand new:
		if (!characterFromId.isNewCharacterFlag()) {
			modelAndView.setViewName("redirect:/characterSheet?id=" + characterFromId.getId());
			return modelAndView;
		}
		
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", true);
		model.addAttribute("character", characterFromId);
		model.addAttribute("hitDieHint", hitDieMapping.get(characterFromId.getCharClass().getId()));
		modelAndView.setViewName("character/start_character");
		return modelAndView;
	}
	
	@PostMapping("/firstSteps")
	public ModelAndView setFirstSteps(@RequestParam("id") Long characterId, @Valid @ModelAttribute CharacterSheetValidator characterSheetValidator, BindingResult result, HttpServletRequest request, Model model) {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		// Validate Form Result first:
		List<String> errors = characterSheetValidator.validate(result, request);
		if (!errors.isEmpty()) {
			model.addAllAttributes(getAttributeMap(request));
			model.addAttribute("characterSheetValidator", characterSheetValidator);
			model.addAttribute("editMode", true);
			model.addAttribute("errors", errors);
			model.addAttribute("character", characterFromId);
			model.addAttribute("hitDieHint", hitDieMapping.get(characterFromId.getCharClass().getId()));
			modelAndView.setViewName("character/start_character");
			return modelAndView;
		}
		
		CharacterSheet characterSheet = new CharacterSheet();
		Weapon weapon = characterFromId.getWeapon();
		modifyCharacterSheetByValidator(characterSheet, weapon, characterSheetValidator, request);
		
		// Calculate starting HP:
		int maxHp = calculateMaxHpFromHitDie(characterSheetValidator.getHitDie());
		characterSheet.setCurrentHp(maxHp);
		characterSheet.setMaxHp(maxHp);
		
		characterFromId.setNewCharacterFlag(false);
		characterFromId.setCharSheet(characterSheet);
		dbManager.saveCharacter(characterFromId);
		
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("character", characterFromId);
		modelAndView.setViewName("character/character_sheet");
		return modelAndView;
	}
	
	@GetMapping("/characterSheet")
	public ModelAndView getCharacterSheet(@RequestParam("id") Long characterId, HttpServletRequest request, Model model) {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		// Validate that this isn't a brand new character:
		if (characterFromId.isNewCharacterFlag()) {
			modelAndView.setViewName("redirect:/firstSteps?id=" + characterFromId.getId());
			return modelAndView;
		}
		
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("character", characterFromId);
		modelAndView.setViewName("character/character_sheet");
		return modelAndView;
	}
	
	@PostMapping("/characterSheet")
	public ModelAndView setCharacterSheet(@RequestParam("id") Long characterId, @Valid @ModelAttribute CharacterSheetValidator characterSheetValidator, BindingResult result, HttpServletRequest request, Model model) {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		CharacterSheet characterSheet = characterFromId.getCharSheet();
		Weapon weapon = characterFromId.getWeapon();
		
		// Validate Form Result first:
		List<String> errors = characterSheetValidator.validate(result, request);
		if (!errors.isEmpty()) {
			model.addAllAttributes(getAttributeMap(request));
			model.addAttribute("characterSheetValidator", characterSheetValidator);
			model.addAttribute("editMode", true);
			model.addAttribute("errors", errors);
			model.addAttribute("character", characterFromId);
			modelAndView.setViewName("character/character_sheet");
			return modelAndView;
		}
		
		modifyCharacterSheetByValidator(characterSheet, weapon, characterSheetValidator, request);
		dbManager.saveCharacterSheet(characterSheet);
		
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("character", characterFromId);
		modelAndView.setViewName("character/character_sheet");
		return modelAndView;
	}
	
	@PostMapping("/editCharacterSheet")
	public ModelAndView setEditMode(@RequestParam("id") Long characterId, HttpServletRequest request, Model model) {
		// Validate that this is the current user's character:
		User user = dbManager.getCurrentUser(request);
		Character characterFromId = characterRepository.getCharacterById(characterId);
		
		ModelAndView modelAndView = new ModelAndView();
		if (!user.getCharacters().contains(characterFromId)) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", true);
		model.addAttribute("character", characterFromId);
		modelAndView.setViewName("character/character_sheet");
		return modelAndView;
	}
	
	/**
	 * Calculates starting HP by Hit Die.
	 * Starting HP can be calculated as 1 * 8 given 1d8.
	 */
	private int calculateMaxHpFromHitDie(String hitDie) {
		String numHitDie = hitDie.substring(0, 1);
		String hitDieVal = hitDie.substring(2);
		
		return Integer.parseInt(numHitDie) * Integer.parseInt(hitDieVal);
	}
	
	/**
	 * Takes a CharacterSheet object and modifies it according to its characterSheetValidator.
	 * Notice that this method uses side effects.
	 */
	private void modifyCharacterSheetByValidator(CharacterSheet characterSheet, Weapon weapon, CharacterSheetValidator characterSheetValidator, HttpServletRequest request) {
		String hitDie = characterSheetValidator.getHitDie();
		if (hitDie != null && !hitDie.equals("")) {
			characterSheet.setHitDie(characterSheetValidator.getHitDie());
		}
		
		String currentHp = characterSheetValidator.getCurrentHp();
		if (currentHp != null && !currentHp.equals("")) {
			characterSheet.setCurrentHp(Integer.parseInt(currentHp));
		}
		
		String maxHp = characterSheetValidator.getMaxHp();
		if (maxHp != null && !maxHp.equals("")) {
			characterSheet.setMaxHp(Integer.parseInt(maxHp));
		}
		
		String strengthBase = characterSheetValidator.getStrengthBase();
		if (strengthBase != null && !strengthBase.equals("")) {
			characterSheet.setStrengthBase(Integer.parseInt(strengthBase));
		}
		
		String strengthEnhance = characterSheetValidator.getStrengthEnhance();
		if (strengthEnhance != null && !strengthEnhance.equals("")) {
			characterSheet.setStrengthEnhance(Integer.parseInt(strengthEnhance));
		}
		
		String dexterityBase = characterSheetValidator.getDexterityBase();
		if (dexterityBase != null && !dexterityBase.equals("")) {
			characterSheet.setDexterityBase(Integer.parseInt(dexterityBase));
		}
		
		String dexterityEnhance = characterSheetValidator.getDexterityEnhance();
		if (dexterityEnhance != null && !dexterityEnhance.equals("")) {
			characterSheet.setDexterityEnhance(Integer.parseInt(dexterityEnhance));
		}
		
		String constitutionBase = characterSheetValidator.getConstitutionBase();
		if (constitutionBase != null && !constitutionBase.equals("")) {
			characterSheet.setConstitutionBase(Integer.parseInt(constitutionBase));
		}
		
		String constitutionEnhance = characterSheetValidator.getConstitutionEnhance();
		if (constitutionEnhance != null && !constitutionEnhance.equals("")) {
			characterSheet.setConstitutionEnhance(Integer.parseInt(constitutionEnhance));
		}
		
		String intelligenceBase = characterSheetValidator.getIntelligenceBase();
		if (intelligenceBase != null && !intelligenceBase.equals("")) {
			characterSheet.setIntelligenceBase(Integer.parseInt(intelligenceBase));
		}
		
		String intelligenceEnhance = characterSheetValidator.getIntelligenceEnhance();
		if (intelligenceEnhance != null && !intelligenceEnhance.equals("")) {
			characterSheet.setIntelligenceEnhance(Integer.parseInt(intelligenceEnhance));
		}
		
		String wisdomBase = characterSheetValidator.getWisdomBase();
		if (wisdomBase != null && !wisdomBase.equals("")) {
			characterSheet.setWisdomBase(Integer.parseInt(wisdomBase));
		}
		
		String wisdomEnhance = characterSheetValidator.getWisdomEnhance();
		if (wisdomEnhance != null && !wisdomEnhance.equals("")) {
			characterSheet.setWisdomEnhance(Integer.parseInt(wisdomEnhance));
		}
		
		String charismaBase = characterSheetValidator.getCharismaBase();
		if (charismaBase != null && !charismaBase.equals("")) {
			characterSheet.setCharismaBase(Integer.parseInt(charismaBase));
		}
		
		String charismaEnhance = characterSheetValidator.getCharismaEnhance();
		if (charismaEnhance != null && !charismaEnhance.equals("")) {
			characterSheet.setCharismaEnhance(Integer.parseInt(charismaEnhance));
		}
		
		String baseAttackBonus = characterSheetValidator.getBaseAttackBonus();
		if (baseAttackBonus != null && !baseAttackBonus.equals("")) {
			characterSheet.setBaseAttackBonus(Integer.parseInt(baseAttackBonus));
		}

		String fortitude = characterSheetValidator.getFortitude();
		if (fortitude != null && !fortitude.equals("")) {
			characterSheet.setFortitude(Integer.parseInt(fortitude));
		}
		
		String reflex = characterSheetValidator.getReflex();
		if (reflex != null && !reflex.equals("")) {
			characterSheet.setReflex(Integer.parseInt(reflex));
		}
		
		String willpower = characterSheetValidator.getWillpower();
		if (willpower != null && !willpower.equals("")) {
			characterSheet.setWillpower(Integer.parseInt(willpower));
		}
		
		String weaponName = characterSheetValidator.getWeaponName();
		if (weaponName != null) {
			weapon.setName(weaponName);
		}
		
		String weaponDamage = characterSheetValidator.getWeaponDamage();
		if (weaponDamage != null) {
			weapon.setDamage(weaponDamage);
		}
		
		String weaponCrit = characterSheetValidator.getWeaponCrit();
		if (weaponCrit != null) {
			weapon.setCrit(weaponCrit);
		}
	}
	
	@Override
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = super.getAttributeMap(request);
		attributeMap.put("editMode", false);
		attributeMap.put("characterSheetValidator", new CharacterSheetValidator());
		
		return attributeMap;
	}
	
}
