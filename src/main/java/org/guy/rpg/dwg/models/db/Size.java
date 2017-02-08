package org.guy.rpg.dwg.models.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sizes")
public class Size implements Serializable {

	private static Map<Long, Integer> valueMap = new HashMap<Long, Integer>() {{
		put(1L, 1);
		put(2L, 0);
		put(3L, -1);
		put(4L, -2);
	}};
	
	@Id
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Transient
	private int value;

	public Size() {

	}

	public Size(int id) {
		this.id = (long) id;
		
		switch (id) {
		case 1:
			this.name = "Small";
			break;
		case 2:
			this.name = "Medium";
			break;
		case 3:
			this.name = "Large";
			break;
		case 4:
			this.name = "Huge";
			break;
		default:
			this.name = "Medium";
		}
		
		setValue(id);
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

	public int getValue() {
		return valueMap.get(id);
	}

	public void setValue(int value) {
		this.value = valueMap.get(id);
	}
}