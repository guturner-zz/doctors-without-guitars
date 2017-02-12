package org.guy.rpg.dwg.models.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "races")
public class Race implements Serializable {

	private static Map<Long, String> valueMap = new HashMap<Long, String>() {{
		put(1L, "Dwarf");
		put(2L, "Elf");
		put(3L, "Gnome");
		put(4L, "Half-Elf");
		put(5L, "Half-Orc");
		put(6L, "Halfling");
		put(7L, "Human");
	}};
	
	@Id
	private Long id;

	@Column(name = "name")
	private String name;

	public Race() {

	}

	public Race(int id) {
		this.id = (long) id;
		this.name = valueMap.get(id);
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