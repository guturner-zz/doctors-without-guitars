package org.guy.rpg.dwg.validators;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CharacterSheetValidator {

	private static final String HIT_DIE_ERROR_MSG = "Hit Die value must match pattern <num>d<num> as in 1d8.";
	
	private String strengthBase;
	private String strengthEnhance;
	private String dexterityBase;
	private String dexterityEnhance;
	private String constitutionBase;
	private String constitutionEnhance;
	private String intelligenceBase;
	private String intelligenceEnhance;
	private String wisdomBase;
	private String wisdomEnhance;
	private String charismaBase;
	private String charismaEnhance;
	
	@Pattern(regexp="(?i)\\dd\\d{1,2}", message=HIT_DIE_ERROR_MSG)
	private String hitDie;
	
	private String currentHp;

	public String getStrengthBase() {
		return strengthBase;
	}

	public void setStrengthBase(String strengthBase) {
		this.strengthBase = strengthBase;
	}

	public String getStrengthEnhance() {
		return strengthEnhance;
	}

	public void setStrengthEnhance(String strengthEnhance) {
		this.strengthEnhance = strengthEnhance;
	}

	public String getDexterityBase() {
		return dexterityBase;
	}

	public void setDexterityBase(String dexterityBase) {
		this.dexterityBase = dexterityBase;
	}

	public String getDexterityEnhance() {
		return dexterityEnhance;
	}

	public void setDexterityEnhance(String dexterityEnhance) {
		this.dexterityEnhance = dexterityEnhance;
	}

	public String getConstitutionBase() {
		return constitutionBase;
	}

	public void setConstitutionBase(String constitutionBase) {
		this.constitutionBase = constitutionBase;
	}

	public String getConstitutionEnhance() {
		return constitutionEnhance;
	}

	public void setConstitutionEnhance(String constitutionEnhance) {
		this.constitutionEnhance = constitutionEnhance;
	}

	public String getIntelligenceBase() {
		return intelligenceBase;
	}

	public void setIntelligenceBase(String intelligenceBase) {
		this.intelligenceBase = intelligenceBase;
	}

	public String getIntelligenceEnhance() {
		return intelligenceEnhance;
	}

	public void setIntelligenceEnhance(String intelligenceEnhance) {
		this.intelligenceEnhance = intelligenceEnhance;
	}

	public String getWisdomBase() {
		return wisdomBase;
	}

	public void setWisdomBase(String wisdomBase) {
		this.wisdomBase = wisdomBase;
	}

	public String getWisdomEnhance() {
		return wisdomEnhance;
	}

	public void setWisdomEnhance(String wisdomEnhance) {
		this.wisdomEnhance = wisdomEnhance;
	}

	public String getCharismaBase() {
		return charismaBase;
	}

	public void setCharismaBase(String charismaBase) {
		this.charismaBase = charismaBase;
	}

	public String getCharismaEnhance() {
		return charismaEnhance;
	}

	public void setCharismaEnhance(String charismaEnhance) {
		this.charismaEnhance = charismaEnhance;
	}

	public String getHitDie() {
		return hitDie;
	}

	public void setHitDie(String hitDie) {
		this.hitDie = hitDie;
	}

	public String getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(String currentHp) {
		this.currentHp = currentHp;
	}

	public List<String> validate(BindingResult result, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		// Form violated validators:
		if (result.hasErrors()) {
    		for (ObjectError e : result.getAllErrors()) {
    			String errorMsg = e.getDefaultMessage();
    			
    			if (errorMsg.equals(HIT_DIE_ERROR_MSG)) {
    				if (getHitDie().equals("")) {
    					// Not an error, Hit Die just wasn't updated:
    					continue;
    				}
    			}
    			errors.add(errorMsg);
    		}
    	}
		
		return errors;
	}
}
