package org.guy.rpg.dwg.models.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "characters")
public class Character implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "userid")
	private User user;

	@OneToOne
	@JoinColumn(name = "classid")
	private Class charClass;

	@OneToOne
	@JoinColumn(name = "charactersheetid")
	private CharacterSheet charSheet;

	@Column(name = "name")
	private String name;

	@Column(name = "image")
	private String image;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}