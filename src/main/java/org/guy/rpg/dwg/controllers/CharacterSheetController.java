package org.guy.rpg.dwg.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.validators.CharacterSheetValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for the character sheet page.
 * 
 * @author Guy
 */
@Controller
public class CharacterSheetController extends BaseController {
	
	@GetMapping("/characterSheet")
	public String getCharacterSheet(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		// Redirect if no character:
		if (dbManager.getCurrentUserCharacter(request) == null) {
			response.sendRedirect("/profile");
		}
		
		model.addAllAttributes(getAttributeMap(request));
		return "user/character_sheet";
	}
	
	@PostMapping("/characterSheet")
	public String setCharacterSheet(@Valid @ModelAttribute CharacterSheetValidator characterSheetValidator, BindingResult result, HttpServletRequest request, Model model) {
		Character userCharacter = dbManager.getCurrentUserCharacter(request);
		CharacterSheet characterSheet = userCharacter.getCharSheet();
		
		if (!characterSheetValidator.getHitDie().equals("")) {
			characterSheet.setHitDie(characterSheetValidator.getHitDie());
		}
		
		String strengthBase = characterSheetValidator.getStrengthBase();
		if (!strengthBase.equals("")) {
			characterSheet.setStrengthBase(Integer.parseInt(strengthBase));
		}
		
		String strengthEnhance = characterSheetValidator.getStrengthEnhance();
		if (!strengthEnhance.equals("")) {
			characterSheet.setStrengthEnhance(Integer.parseInt(strengthEnhance));
		}
		
		String dexterityBase = characterSheetValidator.getDexterityBase();
		if (!dexterityBase.equals("")) {
			characterSheet.setDexterityBase(Integer.parseInt(dexterityBase));
		}
		
		String dexterityEnhance = characterSheetValidator.getDexterityEnhance();
		if (!dexterityEnhance.equals("")) {
			characterSheet.setDexterityEnhance(Integer.parseInt(dexterityEnhance));
		}
		
		String constitutionBase = characterSheetValidator.getConstitutionBase();
		if (!constitutionBase.equals("")) {
			characterSheet.setConstitutionBase(Integer.parseInt(constitutionBase));
		}
		
		String constitutionEnhance = characterSheetValidator.getConstitutionEnhance();
		if (!constitutionEnhance.equals("")) {
			characterSheet.setConstitutionEnhance(Integer.parseInt(constitutionEnhance));
		}
		
		String intelligenceBase = characterSheetValidator.getIntelligenceBase();
		if (!intelligenceBase.equals("")) {
			characterSheet.setIntelligenceBase(Integer.parseInt(intelligenceBase));
		}
		
		String intelligenceEnhance = characterSheetValidator.getIntelligenceEnhance();
		if (!intelligenceEnhance.equals("")) {
			characterSheet.setIntelligenceEnhance(Integer.parseInt(intelligenceEnhance));
		}
		
		String wisdomBase = characterSheetValidator.getWisdomBase();
		if (!wisdomBase.equals("")) {
			characterSheet.setWisdomBase(Integer.parseInt(wisdomBase));
		}
		
		String wisdomEnhance = characterSheetValidator.getWisdomEnhance();
		if (!wisdomEnhance.equals("")) {
			characterSheet.setWisdomEnhance(Integer.parseInt(wisdomEnhance));
		}
		
		String charismaBase = characterSheetValidator.getCharismaBase();
		if (!charismaBase.equals("")) {
			characterSheet.setCharismaBase(Integer.parseInt(charismaBase));
		}
		
		String charismaEnhance = characterSheetValidator.getCharismaEnhance();
		if (!charismaEnhance.equals("")) {
			characterSheet.setCharismaEnhance(Integer.parseInt(charismaEnhance));
		}
		
		dbManager.saveCharacterSheet(characterSheet);
		
		model.addAllAttributes(getAttributeMap(request));
		return "user/character_sheet";
	}
	
	@PostMapping("/editCharacterSheet")
	public String setEditMode(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		model.addAttribute("editMode", true);
		
		return "user/character_sheet";
	}
	
	@Override
	protected Map<String, Object> getAttributeMap(HttpServletRequest request) {
		Map<String, Object> attributeMap = super.getAttributeMap(request);
		attributeMap.put("editMode", false);
		attributeMap.put("characterSheetValidator", new CharacterSheetValidator());
		
		return attributeMap;
	}
	
}
