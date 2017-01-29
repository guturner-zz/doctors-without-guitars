package org.guy.rpg.dwg.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.guy.rpg.dwg.security.UserManager;
import org.hibernate.validator.constraints.Email;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CharacterValidator {

	@NotNull(message = "Set your character name.")
	private String name;

	private int classId;

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

	public List<String> validate(BindingResult result, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		return errors;
	}
}
