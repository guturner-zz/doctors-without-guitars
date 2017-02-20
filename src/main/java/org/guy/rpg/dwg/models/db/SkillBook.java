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

}