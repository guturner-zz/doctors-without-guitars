package org.guy.rpg.dwg.validators;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.validation.BindingResult;

public class CharacterValidator {

	@NotNull(message = "Set your character name.")
	private String name;

	private int classId;

	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> validate(BindingResult result, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		return errors;
	}
}
