package org.guy.rpg.dwg.models.db;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Represents a user's character. Via the character object, a user can access a
 * character sheet, weapon, and more.
 * 
 * @author Guy
 */

@Entity
@Table(name = "characters")
public class Character implements Serializable {

	private static final String SMALL_UNARMED_DAMAGE = "1D2";
	private static final String MEDIUM_UNARMED_DAMAGE = "1D3";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;

	@OneToOne
	@JoinColumn(name = "classid")
	private Class charClass;

	@OneToOne
	@JoinColumn(name = "raceid")
	private Race race;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "charactersheetid")
	private CharacterSheet charSheet;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "skillbookid")
	private SkillBook skillBook;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "weaponid")
	private Weapon weapon;

	@Column(name = "name")
	private String name;

	@OneToOne
	@JoinColumn(name = "size_value")
	private Size size;

	@Column(name = "image")
	private String image;

	@Column(name = "new_character", columnDefinition = "INT")
	private boolean newCharacterFlag;

	public Character() {

	}

	public Character(User user, Class charClass, CharacterSheet charSheet, String name, String image) {
		this.user = user;
		this.charClass = charClass;
		this.charSheet = charSheet;
		this.name = name;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Class getCharClass() {
		return charClass;
	}

	public void setCharClass(Class charClass) {
		this.charClass = charClass;
	}

	public CharacterSheet getCharSheet() {
		return charSheet;
	}

	public void setCharSheet(CharacterSheet charSheet) {
		this.charSheet = charSheet;
	}

	public SkillBook getSkillBook() {
		return skillBook;
	}

	public void setSkillBook(SkillBook skillBook) {
		this.skillBook = skillBook;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isNewCharacterFlag() {
		return newCharacterFlag;
	}

	public void setNewCharacterFlag(boolean newCharacterFlag) {
		this.newCharacterFlag = newCharacterFlag;
	}

	/**
	 * Returns a die value corresponding to the character's unarmed damage.
	 * Small characters deal 1D2 damage, Medium and larger deal 1D3 damage.
	 */
	public String getUnarmedDamage(Long sizeId) {
		if (sizeId == 1L) {
			return SMALL_UNARMED_DAMAGE;
		} else {
			return MEDIUM_UNARMED_DAMAGE;
		}
	}
}