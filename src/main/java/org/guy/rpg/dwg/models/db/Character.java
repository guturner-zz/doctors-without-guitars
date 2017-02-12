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

@Entity
@Table(name = "characters")
public class Character implements Serializable {

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
}