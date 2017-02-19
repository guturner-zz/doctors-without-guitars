package org.guy.rpg.dwg.validators;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.validators.annotations.HitDie;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.mysql.jdbc.StringUtils;

/**
 * Used to validate character sheets upon form submit.
 * This includes Weapon values.
 * 
 * @author Guy
 */
public class CharacterSheetValidator {

	private static final String WEAPON_DAMAGE_ERROR = "Weapon Damage value must match pattern <num>d<num> as in 1d8.";
	
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

	@HitDie
	private String hitDie;

	private String currentHp;
	private String maxHp;

	private String baseAttackBonus;
	private String fortitude;
	private String reflex;
	private String willpower;

	private String weaponName;
	
	@HitDie(message=WEAPON_DAMAGE_ERROR)
	private String weaponDamage;
	private String weaponCrit;

	private String acrobaticsBase;
	private String acrobaticsEnhance;
	
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

	public String getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(String maxHp) {
		this.maxHp = maxHp;
	}

	public String getBaseAttackBonus() {
		return baseAttackBonus;
	}

	public void setBaseAttackBonus(String baseAttackBonus) {
		this.baseAttackBonus = baseAttackBonus;
	}

	public String getFortitude() {
		return fortitude;
	}

	public void setFortitude(String fortitude) {
		this.fortitude = fortitude;
	}

	public String getReflex() {
		return reflex;
	}

	public void setReflex(String reflex) {
		this.reflex = reflex;
	}

	public String getWillpower() {
		return willpower;
	}

	public void setWillpower(String willpower) {
		this.willpower = willpower;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public String getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(String weaponDamage) {
		this.weaponDamage = weaponDamage;
	}

	public String getWeaponCrit() {
		return weaponCrit;
	}

	public void setWeaponCrit(String weaponCrit) {
		this.weaponCrit = weaponCrit;
	}

	public String getAcrobaticsBase() {
		return acrobaticsBase;
	}

	public void setAcrobaticsBase(String acrobaticsBase) {
		this.acrobaticsBase = acrobaticsBase;
	}

	public String getAcrobaticsEnhance() {
		return acrobaticsEnhance;
	}

	public void setAcrobaticsEnhance(String acrobaticsEnhance) {
		this.acrobaticsEnhance = acrobaticsEnhance;
	}

	/**
	 * Validates character sheet objects on form submit.
	 */
	public List<String> validate(BindingResult result, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		// Form violated validators:
		if (result.hasErrors()) {
			for (ObjectError e : result.getAllErrors()) {
				if (StringUtils.isNullOrEmpty(getWeaponName()) &&
					e.getDefaultMessage().equals(WEAPON_DAMAGE_ERROR)) {
					// Ignore - this is a valid scenario.
					continue;
				}
				
				errors.add(e.getDefaultMessage());
			}
		}
		
		// Weapon damage and crit cannot be null if weapon is set:
		if (!StringUtils.isNullOrEmpty(getWeaponName())) {
			Boolean isWeaponDamageSet = !StringUtils.isNullOrEmpty(getWeaponDamage());
			Boolean isWeaponCritSet = !StringUtils.isNullOrEmpty(getWeaponCrit());
			
			if (!isWeaponDamageSet || !isWeaponCritSet) {
				errors.add("Weapon damage and crit values must be set.");
			}
		}

		
		return errors;
	}
}
