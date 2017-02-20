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

public class AccountValidator {

	@NotNull(message = "Set your first name.")
	private String givenName;
	
	@NotNull(message = "Set your last name.")
	private String surname;
	
	@Email(message = "Your email is not well-formed.")
	@NotNull(message = "Set your email.")
	private String email;
	
	@NotNull(message = "Set your password.")
	private String password;
	
	@NotNull(message = "Confirm your password.")
	private String confirmPassword;
	
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public List<String> validate(BindingResult result, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		
		// Form violated validators:
		if (result.hasErrors()) {
    		for (ObjectError e : result.getAllErrors()) {
    			errors.add(e.getDefaultMessage());
    		}
    	}
    	
		// Duplicate e-mails:
    	if (UserManager.isEmailInUse(request, this.getEmail())) {
    		errors.add("That email is already in use.");
    	}
    	
    	// Passwords don't match:
    	if (!this.getPassword().equals(this.getConfirmPassword())) {
    		errors.add("Passwords don't match.");
    	}
    	
    	// Password is invalid:
    	Pattern p = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{8,100}$");
    	Matcher m = p.matcher(this.getPassword());
    	m.find();
    	if (!m.matches()) {
    		errors.add("Password doesn't meet requirements (8+ chars, 1 capital, 1 number).");
    	}
		
		return errors;
	}
}
