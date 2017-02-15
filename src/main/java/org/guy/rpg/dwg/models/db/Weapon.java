package org.guy.rpg.dwg.models.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weapons")
public class Weapon implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String damage;
	private String crit;

	public Weapon() {

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

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public String getCrit() {
		return crit;
	}

	public void setCrit(String crit) {
		this.crit = crit;
	}

}