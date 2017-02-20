package org.guy.rpg.dwg.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.csv.CSVManager;
import org.guy.rpg.dwg.db.repositories.CharacterRepository;
import org.guy.rpg.dwg.models.KeyValuePair;
import org.guy.rpg.dwg.models.Tip;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.models.db.SkillBook;
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

import com.mysql.jdbc.StringUtils;

/**
 * Controller for the character sheet page.
 * 
 * @author Guy
 */
@Controller
public class CharacterSheetController extends BaseController {
	
	// Mapping of character class ID to hit die:
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
	
	@Autowired
	CSVManager csvManager;
	
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
		
		model.addAllAttributes(getCharacterSpecificAttributeMap(characterFromId, request));
		model.addAttribute("editMode", true);
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
			model.addAllAttributes(getCharacterSpecificAttributeMap(characterFromId, request));
			model.addAttribute("characterSheetValidator", characterSheetValidator);
			model.addAttribute("editMode", true);
			model.addAttribute("errors", errors);
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
		
		model.addAllAttributes(getCharacterSpecificAttributeMap(characterFromId, request));
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
		
		model.addAllAttributes(getCharacterSpecificAttributeMap(characterFromId, request));
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
		SkillBook skillBook = characterFromId.getSkillBook();
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
		
		modifySkillBookByValidator(skillBook, characterSheetValidator, request);
		dbManager.saveSkillBook(skillBook);
		
		model.addAllAttributes(getCharacterSpecificAttributeMap(characterFromId, request));
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
		
		model.addAllAttributes(getCharacterSpecificAttributeMap(characterFromId, request));
		model.addAttribute("editMode", true);
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
		if (!StringUtils.isNullOrEmpty(currentHp)) {
			characterSheet.setCurrentHp(Integer.parseInt(currentHp));
		}
		
		String maxHp = characterSheetValidator.getMaxHp();
		if (!StringUtils.isNullOrEmpty(maxHp)) {
			characterSheet.setMaxHp(Integer.parseInt(maxHp));
		}
		
		String strengthBase = characterSheetValidator.getStrengthBase();
		if (!StringUtils.isNullOrEmpty(strengthBase)) {
			characterSheet.setStrengthBase(Integer.parseInt(strengthBase));
		}
		
		String strengthEnhance = characterSheetValidator.getStrengthEnhance();
		if (!StringUtils.isNullOrEmpty(strengthEnhance)) {
			characterSheet.setStrengthEnhance(Integer.parseInt(strengthEnhance));
		}
		
		String dexterityBase = characterSheetValidator.getDexterityBase();
		if (!StringUtils.isNullOrEmpty(dexterityBase)) {
			characterSheet.setDexterityBase(Integer.parseInt(dexterityBase));
		}
		
		String dexterityEnhance = characterSheetValidator.getDexterityEnhance();
		if (!StringUtils.isNullOrEmpty(dexterityEnhance)) {
			characterSheet.setDexterityEnhance(Integer.parseInt(dexterityEnhance));
		}
		
		String constitutionBase = characterSheetValidator.getConstitutionBase();
		if (!StringUtils.isNullOrEmpty(constitutionBase)) {
			characterSheet.setConstitutionBase(Integer.parseInt(constitutionBase));
		}
		
		String constitutionEnhance = characterSheetValidator.getConstitutionEnhance();
		if (!StringUtils.isNullOrEmpty(constitutionEnhance)) {
			characterSheet.setConstitutionEnhance(Integer.parseInt(constitutionEnhance));
		}
		
		String intelligenceBase = characterSheetValidator.getIntelligenceBase();
		if (!StringUtils.isNullOrEmpty(intelligenceBase)) {
			characterSheet.setIntelligenceBase(Integer.parseInt(intelligenceBase));
		}
		
		String intelligenceEnhance = characterSheetValidator.getIntelligenceEnhance();
		if (!StringUtils.isNullOrEmpty(intelligenceEnhance)) {
			characterSheet.setIntelligenceEnhance(Integer.parseInt(intelligenceEnhance));
		}
		
		String wisdomBase = characterSheetValidator.getWisdomBase();
		if (!StringUtils.isNullOrEmpty(wisdomBase)) {
			characterSheet.setWisdomBase(Integer.parseInt(wisdomBase));
		}
		
		String wisdomEnhance = characterSheetValidator.getWisdomEnhance();
		if (!StringUtils.isNullOrEmpty(wisdomEnhance)) {
			characterSheet.setWisdomEnhance(Integer.parseInt(wisdomEnhance));
		}
		
		String charismaBase = characterSheetValidator.getCharismaBase();
		if (!StringUtils.isNullOrEmpty(charismaBase)) {
			characterSheet.setCharismaBase(Integer.parseInt(charismaBase));
		}
		
		String charismaEnhance = characterSheetValidator.getCharismaEnhance();
		if (!StringUtils.isNullOrEmpty(charismaEnhance)) {
			characterSheet.setCharismaEnhance(Integer.parseInt(charismaEnhance));
		}
		
		String baseAttackBonus = characterSheetValidator.getBaseAttackBonus();
		if (!StringUtils.isNullOrEmpty(baseAttackBonus)) {
			characterSheet.setBaseAttackBonus(Integer.parseInt(baseAttackBonus));
		}

		String fortitude = characterSheetValidator.getFortitude();
		if (!StringUtils.isNullOrEmpty(fortitude)) {
			characterSheet.setFortitude(Integer.parseInt(fortitude));
		}
		
		String reflex = characterSheetValidator.getReflex();
		if (!StringUtils.isNullOrEmpty(reflex)) {
			characterSheet.setReflex(Integer.parseInt(reflex));
		}
		
		String willpower = characterSheetValidator.getWillpower();
		if (!StringUtils.isNullOrEmpty(willpower)) {
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
	
	private void modifySkillBookByValidator(SkillBook skillBook, CharacterSheetValidator characterSheetValidator, HttpServletRequest request) {
		String acrobaticsBase = characterSheetValidator.getAcrobaticsBase();
		if (!StringUtils.isNullOrEmpty(acrobaticsBase)) {
			skillBook.setAcrobaticsBase(Integer.parseInt(acrobaticsBase));
		}
		
		String acrobaticsEnhance = characterSheetValidator.getAcrobaticsEnhance();
		if (!StringUtils.isNullOrEmpty(acrobaticsEnhance)) {
			skillBook.setAcrobaticsEnhance(Integer.parseInt(acrobaticsEnhance));
		}
		
		String appraiseBase = characterSheetValidator.getAppraiseBase();
		if (!StringUtils.isNullOrEmpty(appraiseBase)) {
			skillBook.setAppraiseBase(Integer.parseInt(appraiseBase));
		}
		
		String appraiseEnhance = characterSheetValidator.getAppraiseEnhance();
		if (!StringUtils.isNullOrEmpty(appraiseEnhance)) {
			skillBook.setAppraiseEnhance(Integer.parseInt(appraiseEnhance));
		}
		
		String bluffBase = characterSheetValidator.getBluffBase();
		if (!StringUtils.isNullOrEmpty(bluffBase)) {
			skillBook.setBluffBase(Integer.parseInt(bluffBase));
		}
		
		String bluffEnhance = characterSheetValidator.getBluffEnhance();
		if (!StringUtils.isNullOrEmpty(bluffEnhance)) {
			skillBook.setBluffEnhance(Integer.parseInt(bluffEnhance));
		}
		
		String climbBase = characterSheetValidator.getClimbBase();
		if (!StringUtils.isNullOrEmpty(climbBase)) {
			skillBook.setClimbBase(Integer.parseInt(climbBase));
		}
		
		String climbEnhance = characterSheetValidator.getClimbEnhance();
		if (!StringUtils.isNullOrEmpty(climbEnhance)) {
			skillBook.setClimbEnhance(Integer.parseInt(climbEnhance));
		}
		
		String diplomacyBase = characterSheetValidator.getDiplomacyBase();
		if (!StringUtils.isNullOrEmpty(diplomacyBase)) {
			skillBook.setDiplomacyBase(Integer.parseInt(diplomacyBase));
		}
		
		String diplomacyEnhance = characterSheetValidator.getDiplomacyEnhance();
		if (!StringUtils.isNullOrEmpty(diplomacyEnhance)) {
			skillBook.setDiplomacyEnhance(Integer.parseInt(diplomacyEnhance));
		}
		
		String disableDeviceBase = characterSheetValidator.getDisableDeviceBase();
		if (!StringUtils.isNullOrEmpty(disableDeviceBase)) {
			skillBook.setDisableDeviceBase(Integer.parseInt(disableDeviceBase));
		}
		
		String disableDeviceEnhance = characterSheetValidator.getDisableDeviceEnhance();
		if (!StringUtils.isNullOrEmpty(disableDeviceEnhance)) {
			skillBook.setDisableDeviceEnhance(Integer.parseInt(disableDeviceEnhance));
		}
		
		String disguiseBase = characterSheetValidator.getDisguiseBase();
		if (!StringUtils.isNullOrEmpty(disguiseBase)) {
			skillBook.setDisguiseBase(Integer.parseInt(disguiseBase));
		}
		
		String disguiseEnhance = characterSheetValidator.getDisguiseEnhance();
		if (!StringUtils.isNullOrEmpty(disguiseEnhance)) {
			skillBook.setDisguiseEnhance(Integer.parseInt(disguiseEnhance));
		}
		
		String escapeArtistBase = characterSheetValidator.getEscapeArtistBase();
		if (!StringUtils.isNullOrEmpty(escapeArtistBase)) {
			skillBook.setEscapeArtistBase(Integer.parseInt(escapeArtistBase));
		}
		
		String escapeArtistEnhance = characterSheetValidator.getEscapeArtistEnhance();
		if (!StringUtils.isNullOrEmpty(escapeArtistEnhance)) {
			skillBook.setEscapeArtistEnhance(Integer.parseInt(escapeArtistEnhance));
		}
		
		String flyBase = characterSheetValidator.getFlyBase();
		if (!StringUtils.isNullOrEmpty(flyBase)) {
			skillBook.setFlyBase(Integer.parseInt(flyBase));
		}
		
		String flyEnhance = characterSheetValidator.getFlyEnhance();
		if (!StringUtils.isNullOrEmpty(flyEnhance)) {
			skillBook.setFlyEnhance(Integer.parseInt(flyEnhance));
		}
		
		String handleAnimalBase = characterSheetValidator.getHandleAnimalBase();
		if (!StringUtils.isNullOrEmpty(handleAnimalBase)) {
			skillBook.setHandleAnimalBase(Integer.parseInt(handleAnimalBase));
		}
		
		String handleAnimalEnhance = characterSheetValidator.getHandleAnimalEnhance();
		if (!StringUtils.isNullOrEmpty(handleAnimalEnhance)) {
			skillBook.setHandleAnimalEnhance(Integer.parseInt(handleAnimalEnhance));
		}
	}
	
	/**
	 * Returns a map of model attributes including those specific to the current character.
	 * For example: character, isUnarmed, etc.
	 */
	private Map<String, Object> getCharacterSpecificAttributeMap(Character character, HttpServletRequest request) {
		Map<String, Object> attributeMap = getAttributeMap(request);
		attributeMap.put("character", character);
		
		Boolean isUnarmed = true;
		if (character.getWeapon() != null) {
			Weapon weapon = character.getWeapon();
			if (weapon.getName() != null && !weapon.getName().equals("")) {
				isUnarmed = false;
			}
		}
		attributeMap.put("isUnarmed", isUnarmed);
		
		Long sizeId = character.getSize().getId();
		attributeMap.put("unarmedDamage", character.getUnarmedDamage(sizeId));
		
		String raceTipsCSV = "/properties/tips/race_tips.csv";
		List<Tip> raceTips = csvManager.getTipsByKey(character.getRace().getName(), raceTipsCSV);
		
		String classTipsCSV = "/properties/tips/class_tips.csv";
		List<Tip> classTips = csvManager.getTipsByKey(character.getCharClass().getName(), classTipsCSV);
		
		
		attributeMap.put("raceTips", raceTips);
		attributeMap.put("classTips", classTips);
		
		return attributeMap;
	}
	
	@Override
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = super.getAttributeMap(request);
		attributeMap.put("editMode", false);
		attributeMap.put("characterSheetValidator", new CharacterSheetValidator());
		
		return attributeMap;
	}
	
}
