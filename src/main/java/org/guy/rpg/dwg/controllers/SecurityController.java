package org.guy.rpg.dwg.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.guy.rpg.dwg.db.repositories.UserRepository;
import org.guy.rpg.dwg.models.Field;
import org.guy.rpg.dwg.models.User;
import org.guy.rpg.dwg.security.UserManager;
import org.guy.rpg.dwg.validators.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stormpath.sdk.account.Account;

/**
 * Controller for user authentication pages.
 * 
 * @author Guy
 */
@Controller
public class SecurityController {
	
	@Autowired
	UserRepository userRepository;
	
	private Map<String, Object> getRegisterFieldAttributeMap() {
		Map<String, Object> attributeMap = new HashMap<String, Object>();

		List<Field> visibleFields = new ArrayList<Field>() {
			{
				add(new Field("First Name", "givenName", "text", "First Name", "true"));
				add(new Field("Last Name", "surname", "text", "Last Name", "true"));
				add(new Field("Email", "email", "text", "Email", "true"));
				add(new Field("Password", "password", "password", "Password", "true"));
				add(new Field("Confirm Password", "confirmPassword", "password", "Confirm Password", "true"));
			}
		};

		attributeMap.put("visibleFields", visibleFields);

		return attributeMap;
	}

	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegister(Model model) {
		model.addAllAttributes(getRegisterFieldAttributeMap());
		model.addAttribute("stormpathAccountValidator", new AccountValidator());

		return "security/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(@Valid @ModelAttribute AccountValidator accountValidator, BindingResult result,
			HttpServletRequest request, Model model) {
		boolean hasError = true;
		model.addAllAttributes(getRegisterFieldAttributeMap());

		List<String> errors = accountValidator.validate(result, request);

		Account account = null;
		if (errors.isEmpty()) {
			account = UserManager.createNewAccount(accountValidator.getGivenName(), accountValidator.getSurname(),
					accountValidator.getEmail(), accountValidator.getPassword());
			hasError = false;

			if (account == null) {
				errors.add("User creation failed. Please try again later.");
				hasError = true;
			}
		}

		if (hasError) {
			model.addAttribute("errors", errors);
			return "security/register";
		}

		model.addAttribute("status", "Account " + accountValidator.getEmail() + " was created! Please login.");
		userRepository.save(new User(accountValidator.getGivenName(), accountValidator.getSurname(), accountValidator.getEmail()));
		return "main/main";
	}

}
