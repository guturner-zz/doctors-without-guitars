package org.guy.rpg.dwg.models.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skillbooks")
public class SkillBook implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "acrobatics_base")
	private int acrobaticsBase;

	@Column(name = "acrobatics_enhance")
	private int acrobaticsEnhance;
	
	@Column(name = "appraise_base")
	private int appraiseBase;
	
	@Column(name = "appraise_enhance")
	private int appraiseEnhance;
	
	@Column(name = "bluff_base")
	private int bluffBase;
	
	@Column(name = "bluff_enhance")
	private int bluffEnhance;
	
	@Column(name = "climb_base")
	private int climbBase;
	
	@Column(name = "climb_enhance")
	private int climbEnhance;
	
	@Column(name = "diplomacy_base")
	private int diplomacyBase;
	
	@Column(name = "diplomacy_enhance")
	private int diplomacyEnhance;
	
	@Column(name = "disable_device_base")
	private int disableDeviceBase;
	
	@Column(name = "disable_device_enhance")
	private int disableDeviceEnhance;
	
	@Column(name = "disguise_base")
	private int disguiseBase;
	
	@Column(name = "disguise_enhance")
	private int disguiseEnhance;
	
	@Column(name = "escape_artist_base")
	private int escapeArtistBase;
	
	@Column(name = "escape_artist_enhance")
	private int escapeArtistEnhance;
	
	@Column(name = "fly_base")
	private int flyBase;
	
	@Column(name = "fly_enhance")
	private int flyEnhance;
	
	@Column(name = "handle_animal_base")
	private int handleAnimalBase;
	
	@Column(name = "handle_animal_enhance")
	private int handleAnimalEnhance;
	
	@Column(name = "heal_base")
	private int healBase;
	
	@Column(name = "heal_enhance")
	private int healEnhance;
	
	@Column(name = "intimidate_base")
	private int intimidateBase;
	
	@Column(name = "intimidate_enhance")
	private int intimidateEnhance;
	
	@Column(name = "linguistics_base")
	private int linguisticsBase;
	
	@Column(name = "linguistics_enhance")
	private int linguisticsEnhance;
	
	@Column(name = "perception_base")
	private int perceptionBase;
	
	@Column(name = "perception_enhance")
	private int perceptionEnhance;
	
	@Column(name = "perform_base")
	private int performBase;
	
	@Column(name = "perform_enhance")
	private int performEnhance;
	
	@Column(name = "ride_base")
	private int rideBase;
	
	@Column(name = "ride_enhance")
	private int rideEnhance;
	
	@Column(name = "sense_motive_base")
	private int senseMotiveBase;
	
	@Column(name = "sense_motive_enhance")
	private int senseMotiveEnhance;
	
	@Column(name = "sleight_of_hand_base")
	private int sleightOfHandBase;
	
	@Column(name = "sleight_of_hand_enhance")
	private int sleightOfHandEnhance;
	
	@Column(name = "spellcraft_base")
	private int spellcraftBase;
	
	@Column(name = "spellcraft_enhance")
	private int spellcraftEnhance;
	
	@Column(name = "stealth_base")
	private int stealthBase;
	
	@Column(name = "stealth_enhance")
	private int stealthEnhance;
	
	@Column(name = "survival_base")
	private int survivalBase;
	
	@Column(name = "survival_enhance")
	private int survivalEnhance;
	
	@Column(name = "swim_base")
	private int swimBase;
	
	@Column(name = "swim_enhance")
	private int swimEnhance;
	
	@Column(name = "use_magic_device_base")
	private int useMagicDeviceBase;
	
	@Column(name = "use_magic_device_enhance")
	private int useMagicDeviceEnhance;

	public SkillBook() {

	}

	public int getAcrobaticsBase() {
		return acrobaticsBase;
	}

	public void setAcrobaticsBase(int acrobaticsBase) {
		this.acrobaticsBase = acrobaticsBase;
	}

	public int getAcrobaticsEnhance() {
		return acrobaticsEnhance;
	}

	public void setAcrobaticsEnhance(int acrobaticsEnhance) {
		this.acrobaticsEnhance = acrobaticsEnhance;
	}

	public int getAppraiseBase() {
		return appraiseBase;
	}

	public void setAppraiseBase(int appraiseBase) {
		this.appraiseBase = appraiseBase;
	}

	public int getAppraiseEnhance() {
		return appraiseEnhance;
	}

	public void setAppraiseEnhance(int appraiseEnhance) {
		this.appraiseEnhance = appraiseEnhance;
	}

	public int getBluffBase() {
		return bluffBase;
	}

	public void setBluffBase(int bluffBase) {
		this.bluffBase = bluffBase;
	}

	public int getBluffEnhance() {
		return bluffEnhance;
	}

	public void setBluffEnhance(int bluffEnhance) {
		this.bluffEnhance = bluffEnhance;
	}

	public int getClimbBase() {
		return climbBase;
	}

	public void setClimbBase(int climbBase) {
		this.climbBase = climbBase;
	}

	public int getClimbEnhance() {
		return climbEnhance;
	}

	public void setClimbEnhance(int climbEnhance) {
		this.climbEnhance = climbEnhance;
	}

	public int getDiplomacyBase() {
		return diplomacyBase;
	}

	public void setDiplomacyBase(int diplomacyBase) {
		this.diplomacyBase = diplomacyBase;
	}

	public int getDiplomacyEnhance() {
		return diplomacyEnhance;
	}

	public void setDiplomacyEnhance(int diplomacyEnhance) {
		this.diplomacyEnhance = diplomacyEnhance;
	}

	public int getDisableDeviceBase() {
		return disableDeviceBase;
	}

	public void setDisableDeviceBase(int disableDeviceBase) {
		this.disableDeviceBase = disableDeviceBase;
	}

	public int getDisableDeviceEnhance() {
		return disableDeviceEnhance;
	}

	public void setDisableDeviceEnhance(int disableDeviceEnhance) {
		this.disableDeviceEnhance = disableDeviceEnhance;
	}

	public int getDisguiseBase() {
		return disguiseBase;
	}

	public void setDisguiseBase(int disguiseBase) {
		this.disguiseBase = disguiseBase;
	}

	public int getDisguiseEnhance() {
		return disguiseEnhance;
	}

	public void setDisguiseEnhance(int disguiseEnhance) {
		this.disguiseEnhance = disguiseEnhance;
	}

	public int getEscapeArtistBase() {
		return escapeArtistBase;
	}

	public void setEscapeArtistBase(int escapeArtistBase) {
		this.escapeArtistBase = escapeArtistBase;
	}

	public int getEscapeArtistEnhance() {
		return escapeArtistEnhance;
	}

	public void setEscapeArtistEnhance(int escapeArtistEnhance) {
		this.escapeArtistEnhance = escapeArtistEnhance;
	}

	public int getFlyBase() {
		return flyBase;
	}

	public void setFlyBase(int flyBase) {
		this.flyBase = flyBase;
	}

	public int getFlyEnhance() {
		return flyEnhance;
	}

	public void setFlyEnhance(int flyEnhance) {
		this.flyEnhance = flyEnhance;
	}

	public int getHandleAnimalBase() {
		return handleAnimalBase;
	}

	public void setHandleAnimalBase(int handleAnimalBase) {
		this.handleAnimalBase = handleAnimalBase;
	}

	public int getHandleAnimalEnhance() {
		return handleAnimalEnhance;
	}

	public void setHandleAnimalEnhance(int handleAnimalEnhance) {
		this.handleAnimalEnhance = handleAnimalEnhance;
	}

	public int getHealBase() {
		return healBase;
	}

	public void setHealBase(int healBase) {
		this.healBase = healBase;
	}

	public int getHealEnhance() {
		return healEnhance;
	}

	public void setHealEnhance(int healEnhance) {
		this.healEnhance = healEnhance;
	}

	public int getIntimidateBase() {
		return intimidateBase;
	}

	public void setIntimidateBase(int intimidateBase) {
		this.intimidateBase = intimidateBase;
	}

	public int getIntimidateEnhance() {
		return intimidateEnhance;
	}

	public void setIntimidateEnhance(int intimidateEnhance) {
		this.intimidateEnhance = intimidateEnhance;
	}

	public int getLinguisticsBase() {
		return linguisticsBase;
	}

	public void setLinguisticsBase(int linguisticsBase) {
		this.linguisticsBase = linguisticsBase;
	}

	public int getLinguisticsEnhance() {
		return linguisticsEnhance;
	}

	public void setLinguisticsEnhance(int linguisticsEnhance) {
		this.linguisticsEnhance = linguisticsEnhance;
	}

	public int getPerceptionBase() {
		return perceptionBase;
	}

	public void setPerceptionBase(int perceptionBase) {
		this.perceptionBase = perceptionBase;
	}

	public int getPerceptionEnhance() {
		return perceptionEnhance;
	}

	public void setPerceptionEnhance(int perceptionEnhance) {
		this.perceptionEnhance = perceptionEnhance;
	}

	public int getPerformBase() {
		return performBase;
	}

	public void setPerformBase(int performBase) {
		this.performBase = performBase;
	}

	public int getPerformEnhance() {
		return performEnhance;
	}

	public void setPerformEnhance(int performEnhance) {
		this.performEnhance = performEnhance;
	}

	public int getRideBase() {
		return rideBase;
	}

	public void setRideBase(int rideBase) {
		this.rideBase = rideBase;
	}

	public int getRideEnhance() {
		return rideEnhance;
	}

	public void setRideEnhance(int rideEnhance) {
		this.rideEnhance = rideEnhance;
	}

	public int getSenseMotiveBase() {
		return senseMotiveBase;
	}

	public void setSenseMotiveBase(int senseMotiveBase) {
		this.senseMotiveBase = senseMotiveBase;
	}

	public int getSenseMotiveEnhance() {
		return senseMotiveEnhance;
	}

	public void setSenseMotiveEnhance(int senseMotiveEnhance) {
		this.senseMotiveEnhance = senseMotiveEnhance;
	}

	public int getSleightOfHandBase() {
		return sleightOfHandBase;
	}

	public void setSleightOfHandBase(int sleightOfHandBase) {
		this.sleightOfHandBase = sleightOfHandBase;
	}

	public int getSleightOfHandEnhance() {
		return sleightOfHandEnhance;
	}

	public void setSleightOfHandEnhance(int sleightOfHandEnhance) {
		this.sleightOfHandEnhance = sleightOfHandEnhance;
	}

	public int getSpellcraftBase() {
		return spellcraftBase;
	}

	public void setSpellcraftBase(int spellcraftBase) {
		this.spellcraftBase = spellcraftBase;
	}

	public int getSpellcraftEnhance() {
		return spellcraftEnhance;
	}

	public void setSpellcraftEnhance(int spellcraftEnhance) {
		this.spellcraftEnhance = spellcraftEnhance;
	}

	public int getStealthBase() {
		return stealthBase;
	}

	public void setStealthBase(int stealthBase) {
		this.stealthBase = stealthBase;
	}

	public int getStealthEnhance() {
		return stealthEnhance;
	}

	public void setStealthEnhance(int stealthEnhance) {
		this.stealthEnhance = stealthEnhance;
	}

	public int getSurvivalBase() {
		return survivalBase;
	}

	public void setSurvivalBase(int survivalBase) {
		this.survivalBase = survivalBase;
	}

	public int getSurvivalEnhance() {
		return survivalEnhance;
	}

	public void setSurvivalEnhance(int survivalEnhance) {
		this.survivalEnhance = survivalEnhance;
	}

	public int getSwimBase() {
		return swimBase;
	}

	public void setSwimBase(int swimBase) {
		this.swimBase = swimBase;
	}

	public int getSwimEnhance() {
		return swimEnhance;
	}

	public void setSwimEnhance(int swimEnhance) {
		this.swimEnhance = swimEnhance;
	}

	public int getUseMagicDeviceBase() {
		return useMagicDeviceBase;
	}

	public void setUseMagicDeviceBase(int useMagicDeviceBase) {
		this.useMagicDeviceBase = useMagicDeviceBase;
	}

	public int getUseMagicDeviceEnhance() {
		return useMagicDeviceEnhance;
	}

	public void setUseMagicDeviceEnhance(int useMagicDeviceEnhance) {
		this.useMagicDeviceEnhance = useMagicDeviceEnhance;
	}
	
}