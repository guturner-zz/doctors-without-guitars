package org.guy.rpg.dwg.models.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "charactersheets")
public class CharacterSheet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "strength_base")
	private int strengthBase;

	@Column(name = "strength_enhance")
	private int strengthEnhance;

	@Column(name = "dexterity_base")
	private int dexterityBase;

	@Column(name = "dexterity_enhance")
	private int dexterityEnhance;

	@Column(name = "constitution_base")
	private int constitutionBase;

	@Column(name = "constitution_enhance")
	private int constitutionEnhance;

	@Column(name = "intelligence_base")
	private int intelligenceBase;

	@Column(name = "intelligence_enhance")
	private int intelligenceEnhance;

	@Column(name = "wisdom_base")
	private int wisdomBase;

	@Column(name = "wisdom_enhance")
	private int wisdomEnhance;

	@Column(name = "charisma_base")
	private int charismaBase;

	@Column(name = "charisma_enhance")
	private int charismaEnhance;

	@Column(name = "hitdie")
	private String hitDie;

	@Column(name = "current_hitpoints")
	private int currentHp;

	@Column(name = "max_hitpoints")
	private int maxHp;

	@Column(name = "base_attack_bonus")
	private int baseAttackBonus;

	@Column(name = "fortitude")
	private int fortitude;

	@Column(name = "reflex")
	private int reflex;

	@Column(name = "willpower")
	private int willpower;

	public CharacterSheet() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStrengthBase() {
		return strengthBase;
	}

	public void setStrengthBase(int strengthBase) {
		this.strengthBase = strengthBase;
	}

	public int getStrengthEnhance() {
		return strengthEnhance;
	}

	public void setStrengthEnhance(int strengthEnhance) {
		this.strengthEnhance = strengthEnhance;
	}

	public int getDexterityBase() {
		return dexterityBase;
	}

	public void setDexterityBase(int dexterityBase) {
		this.dexterityBase = dexterityBase;
	}

	public int getDexterityEnhance() {
		return dexterityEnhance;
	}

	public void setDexterityEnhance(int dexterityEnhance) {
		this.dexterityEnhance = dexterityEnhance;
	}

	public int getConstitutionBase() {
		return constitutionBase;
	}

	public void setConstitutionBase(int constitutionBase) {
		this.constitutionBase = constitutionBase;
	}

	public int getConstitutionEnhance() {
		return constitutionEnhance;
	}

	public void setConstitutionEnhance(int constitutionEnhance) {
		this.constitutionEnhance = constitutionEnhance;
	}

	public int getIntelligenceBase() {
		return intelligenceBase;
	}

	public void setIntelligenceBase(int intelligenceBase) {
		this.intelligenceBase = intelligenceBase;
	}

	public int getIntelligenceEnhance() {
		return intelligenceEnhance;
	}

	public void setIntelligenceEnhance(int intelligenceEnhance) {
		this.intelligenceEnhance = intelligenceEnhance;
	}

	public int getWisdomBase() {
		return wisdomBase;
	}

	public void setWisdomBase(int wisdomBase) {
		this.wisdomBase = wisdomBase;
	}

	public int getWisdomEnhance() {
		return wisdomEnhance;
	}

	public void setWisdomEnhance(int wisdomEnhance) {
		this.wisdomEnhance = wisdomEnhance;
	}

	public int getCharismaBase() {
		return charismaBase;
	}

	public void setCharismaBase(int charismaBase) {
		this.charismaBase = charismaBase;
	}

	public int getCharismaEnhance() {
		return charismaEnhance;
	}

	public void setCharismaEnhance(int charismaEnhance) {
		this.charismaEnhance = charismaEnhance;
	}

	public String getHitDie() {
		return hitDie;
	}

	public void setHitDie(String hitDie) {
		this.hitDie = hitDie;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getBaseAttackBonus() {
		return baseAttackBonus;
	}

	public void setBaseAttackBonus(int baseAttackBonus) {
		this.baseAttackBonus = baseAttackBonus;
	}

	public int getFortitude() {
		return fortitude;
	}

	public void setFortitude(int fortitude) {
		this.fortitude = fortitude;
	}

	public int getReflex() {
		return reflex;
	}

	public void setReflex(int reflex) {
		this.reflex = reflex;
	}

	public int getWillpower() {
		return willpower;
	}

	public void setWillpower(int willpower) {
		this.willpower = willpower;
	}
}