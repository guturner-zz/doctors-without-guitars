package org.guy.rpg.dwg.validators;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

public class UserProfileValidator {

	public List<String> validate(BindingResult result, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		return errors;
	}
}
