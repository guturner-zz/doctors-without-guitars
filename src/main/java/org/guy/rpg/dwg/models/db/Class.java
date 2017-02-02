package org.guy.rpg.dwg.models.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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