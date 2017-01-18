package org.guy.rpg.dwg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "security/register";
	}
	
}
