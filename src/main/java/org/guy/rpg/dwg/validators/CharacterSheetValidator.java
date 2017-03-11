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

	/** Skills **/
	private String acrobaticsBase;
	private String acrobaticsEnhance;
	private String appraiseBase;
	private String appraiseEnhance;
	private String bluffBase;
	private String bluffEnhance;
	private String climbBase;
	private String climbEnhance;
	private String diplomacyBase;
	private String diplomacyEnhance;
	private String disableDeviceBase;
	private String disableDeviceEnhance;
	private String disguiseBase;
	private String disguiseEnhance;
	private String escapeArtistBase;
	private String escapeArtistEnhance;
	private String flyBase;
	private String flyEnhance;
	private String handleAnimalBase;
	private String handleAnimalEnhance;
	private String healBase;
	private String healEnhance;
	private String intimidateBase;
	private String intimidateEnhance;
	private String linguisticsBase;
	private String linguisticsEnhance;
	private String perceptionBase;
	private String perceptionEnhance;
	private String performBase;
	private String performEnhance;
	private String rideBase;
	private String rideEnhance;
	private String senseMotiveBase;
	private String senseMotiveEnhance;
	private String sleightOfHandBase;
	private String sleightOfHandEnhance;
	private String spellcraftBase;
	private String spellcraftEnhance;
	private String stealthBase;
	private String stealthEnhance;
	private String survivalBase;
	private String survivalEnhance;
	private String swimBase;
	private String swimEnhance;
	private String useMagicDeviceBase;
	private String useMagicDeviceEnhance;
	
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
	
	public String getAppraiseBase() {
		return appraiseBase;
	}

	public void setAppraiseBase(String appraiseBase) {
		this.appraiseBase = appraiseBase;
	}

	public String getAppraiseEnhance() {
		return appraiseEnhance;
	}

	public void setAppraiseEnhance(String appraiseEnhance) {
		this.appraiseEnhance = appraiseEnhance;
	}

	public String getBluffBase() {
		return bluffBase;
	}

	public void setBluffBase(String bluffBase) {
		this.bluffBase = bluffBase;
	}

	public String getBluffEnhance() {
		return bluffEnhance;
	}

	public void setBluffEnhance(String bluffEnhance) {
		this.bluffEnhance = bluffEnhance;
	}

	public String getClimbBase() {
		return climbBase;
	}

	public void setClimbBase(String climbBase) {
		this.climbBase = climbBase;
	}

	public String getClimbEnhance() {
		return climbEnhance;
	}

	public void setClimbEnhance(String climbEnhance) {
		this.climbEnhance = climbEnhance;
	}

	public String getDiplomacyBase() {
		return diplomacyBase;
	}

	public void setDiplomacyBase(String diplomacyBase) {
		this.diplomacyBase = diplomacyBase;
	}

	public String getDiplomacyEnhance() {
		return diplomacyEnhance;
	}

	public void setDiplomacyEnhance(String diplomacyEnhance) {
		this.diplomacyEnhance = diplomacyEnhance;
	}

	public String getDisableDeviceBase() {
		return disableDeviceBase;
	}

	public void setDisableDeviceBase(String disableDeviceBase) {
		this.disableDeviceBase = disableDeviceBase;
	}

	public String getDisableDeviceEnhance() {
		return disableDeviceEnhance;
	}

	public void setDisableDeviceEnhance(String disableDeviceEnhance) {
		this.disableDeviceEnhance = disableDeviceEnhance;
	}

	public String getDisguiseBase() {
		return disguiseBase;
	}

	public void setDisguiseBase(String disguiseBase) {
		this.disguiseBase = disguiseBase;
	}

	public String getDisguiseEnhance() {
		return disguiseEnhance;
	}

	public void setDisguiseEnhance(String disguiseEnhance) {
		this.disguiseEnhance = disguiseEnhance;
	}

	public String getEscapeArtistBase() {
		return escapeArtistBase;
	}

	public void setEscapeArtistBase(String escapeArtistBase) {
		this.escapeArtistBase = escapeArtistBase;
	}

	public String getEscapeArtistEnhance() {
		return escapeArtistEnhance;
	}

	public void setEscapeArtistEnhance(String escapeArtistEnhance) {
		this.escapeArtistEnhance = escapeArtistEnhance;
	}

	public String getFlyBase() {
		return flyBase;
	}

	public void setFlyBase(String flyBase) {
		this.flyBase = flyBase;
	}

	public String getFlyEnhance() {
		return flyEnhance;
	}

	public void setFlyEnhance(String flyEnhance) {
		this.flyEnhance = flyEnhance;
	}

	public String getHandleAnimalBase() {
		return handleAnimalBase;
	}

	public void setHandleAnimalBase(String handleAnimalBase) {
		this.handleAnimalBase = handleAnimalBase;
	}

	public String getHandleAnimalEnhance() {
		return handleAnimalEnhance;
	}

	public void setHandleAnimalEnhance(String handleAnimalEnhance) {
		this.handleAnimalEnhance = handleAnimalEnhance;
	}
	
	public String getHealBase() {
		return healBase;
	}

	public void setHealBase(String healBase) {
		this.healBase = healBase;
	}

	public String getHealEnhance() {
		return healEnhance;
	}

	public void setHealEnhance(String healEnhance) {
		this.healEnhance = healEnhance;
	}

	public String getIntimidateBase() {
		return intimidateBase;
	}

	public void setIntimidateBase(String intimidateBase) {
		this.intimidateBase = intimidateBase;
	}

	public String getIntimidateEnhance() {
		return intimidateEnhance;
	}

	public void setIntimidateEnhance(String intimidateEnhance) {
		this.intimidateEnhance = intimidateEnhance;
	}

	public String getLinguisticsBase() {
		return linguisticsBase;
	}

	public void setLinguisticsBase(String linguisticsBase) {
		this.linguisticsBase = linguisticsBase;
	}

	public String getLinguisticsEnhance() {
		return linguisticsEnhance;
	}

	public void setLinguisticsEnhance(String linguisticsEnhance) {
		this.linguisticsEnhance = linguisticsEnhance;
	}

	public String getPerceptionBase() {
		return perceptionBase;
	}

	public void setPerceptionBase(String perceptionBase) {
		this.perceptionBase = perceptionBase;
	}

	public String getPerceptionEnhance() {
		return perceptionEnhance;
	}

	public void setPerceptionEnhance(String perceptionEnhance) {
		this.perceptionEnhance = perceptionEnhance;
	}

	public String getPerformBase() {
		return performBase;
	}

	public void setPerformBase(String performBase) {
		this.performBase = performBase;
	}

	public String getPerformEnhance() {
		return performEnhance;
	}

	public void setPerformEnhance(String performEnhance) {
		this.performEnhance = performEnhance;
	}

	public String getRideBase() {
		return rideBase;
	}

	public void setRideBase(String rideBase) {
		this.rideBase = rideBase;
	}

	public String getRideEnhance() {
		return rideEnhance;
	}

	public void setRideEnhance(String rideEnhance) {
		this.rideEnhance = rideEnhance;
	}

	public String getSenseMotiveBase() {
		return senseMotiveBase;
	}

	public void setSenseMotiveBase(String senseMotiveBase) {
		this.senseMotiveBase = senseMotiveBase;
	}

	public String getSenseMotiveEnhance() {
		return senseMotiveEnhance;
	}

	public void setSenseMotiveEnhance(String senseMotiveEnhance) {
		this.senseMotiveEnhance = senseMotiveEnhance;
	}

	public String getSleightOfHandBase() {
		return sleightOfHandBase;
	}

	public void setSleightOfHandBase(String sleightOfHandBase) {
		this.sleightOfHandBase = sleightOfHandBase;
	}

	public String getSleightOfHandEnhance() {
		return sleightOfHandEnhance;
	}

	public void setSleightOfHandEnhance(String sleightOfHandEnhance) {
		this.sleightOfHandEnhance = sleightOfHandEnhance;
	}

	public String getSpellcraftBase() {
		return spellcraftBase;
	}

	public void setSpellcraftBase(String spellcraftBase) {
		this.spellcraftBase = spellcraftBase;
	}

	public String getSpellcraftEnhance() {
		return spellcraftEnhance;
	}

	public void setSpellcraftEnhance(String spellcraftEnhance) {
		this.spellcraftEnhance = spellcraftEnhance;
	}

	public String getStealthBase() {
		return stealthBase;
	}

	public void setStealthBase(String stealthBase) {
		this.stealthBase = stealthBase;
	}

	public String getStealthEnhance() {
		return stealthEnhance;
	}

	public void setStealthEnhance(String stealthEnhance) {
		this.stealthEnhance = stealthEnhance;
	}

	public String getSurvivalBase() {
		return survivalBase;
	}

	public void setSurvivalBase(String survivalBase) {
		this.survivalBase = survivalBase;
	}

	public String getSurvivalEnhance() {
		return survivalEnhance;
	}

	public void setSurvivalEnhance(String survivalEnhance) {
		this.survivalEnhance = survivalEnhance;
	}

	public String getSwimBase() {
		return swimBase;
	}

	public void setSwimBase(String swimBase) {
		this.swimBase = swimBase;
	}

	public String getSwimEnhance() {
		return swimEnhance;
	}

	public void setSwimEnhance(String swimEnhance) {
		this.swimEnhance = swimEnhance;
	}

	public String getUseMagicDeviceBase() {
		return useMagicDeviceBase;
	}

	public void setUseMagicDeviceBase(String useMagicDeviceBase) {
		this.useMagicDeviceBase = useMagicDeviceBase;
	}

	public String getUseMagicDeviceEnhance() {
		return useMagicDeviceEnhance;
	}

	public void setUseMagicDeviceEnhance(String useMagicDeviceEnhance) {
		this.useMagicDeviceEnhance = useMagicDeviceEnhance;
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
