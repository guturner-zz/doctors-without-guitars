package org.guy.rpg.dwg.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.maps.MapManager;
import org.guy.rpg.dwg.models.Landmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.rossillo.spring.web.mvc.CacheControl;

/**
 * Controller for the party page.
 * 
 * @author Guy
 */
@Controller
public class PartyController extends BaseController {

	@Autowired
	MapManager mapManager;
	
	// OpenLayers Maps use hi-res images, we should cache when possible:
	@CacheControl(maxAge=86400)
	@RequestMapping("/party")
	public String main(HttpServletRequest request, Model model) {
		model.addAllAttributes(getAttributeMap(request));
		
		List<Landmark> landmarks = mapManager.getLandmarks();
		model.addAttribute("landmarks", landmarks);
		
		return "game/party";
	}
	
}
