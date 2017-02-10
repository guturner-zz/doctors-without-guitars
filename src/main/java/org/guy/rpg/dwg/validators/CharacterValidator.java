package org.guy.rpg.dwg.validators;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CharacterValidator {

	@Pattern(regexp = "([a-z]|[A-Z])+", message = "Set your character name (no numbers or special characters).")
	private String name;

	@Min(value = 1, message = "Select a character size.")
	private int size;
	
	@Min(value = 1, message = "Select a character class.")
	private int classId;
	
	@Pattern(regexp = "^((?!unknown).)+$", message = "Select a character portrait.")
	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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

		// Form violated validators:
		if (result.hasErrors()) {
    		for (ObjectError e : result.getAllErrors()) {
    			errors.add(e.getDefaultMessage());
    		}
    	}
		
		return errors;
	}
}
