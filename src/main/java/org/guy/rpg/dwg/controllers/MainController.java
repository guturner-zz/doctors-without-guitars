package org.guy.rpg.dwg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stormpath.sdk.account.Account;

@Controller
public class MainController {

	@RequestMapping("/")
	public String main(HttpServletRequest request, Model model) {
		Account account = UserManager.getCurrentUser(request);
		
		if (account != null) {
			model.addAttribute("user", account.getUsername());
		}
		
		return "main/main";
	}
	
}
