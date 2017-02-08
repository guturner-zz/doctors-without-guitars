package org.guy.rpg.dwg.models.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classes")
public class Class implements Serializable {

	@Id
	private Long id;

	@Column(name = "name")
	private String name;

	public Class() {

	}

	public Class(int id) {
		this.id = (long) id;
		
		switch (id) {
		case 1:
			this.name = "Barbarian";
			break;
		case 2:
			this.name = "Bard";
			break;
		case 3:
			this.name = "Cleric";
			break;
		case 4:
			this.name = "Druid";
			break;
		case 5:
			this.name = "Fighter";
			break;
		case 6:
			this.name = "Monk";
			break;
		case 7:
			this.name = "Paladin";
			break;
		case 8:
			this.name = "Ranger";
			break;
		case 9:
			this.name = "Rogue";
			break;
		case 10:
			this.name = "Sorceror";
			break;
		case 11:
			this.name = "Wizard";
			break;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}