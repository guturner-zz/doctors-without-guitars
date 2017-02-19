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

}