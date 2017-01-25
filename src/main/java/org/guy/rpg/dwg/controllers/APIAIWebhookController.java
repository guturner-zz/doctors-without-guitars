package org.guy.rpg.dwg.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.guy.rpg.dwg.ai.AIManager;
import org.guy.rpg.dwg.constants.IntentConstants;
import org.guy.rpg.dwg.models.apiai.AIGenericRequest;
import org.guy.rpg.dwg.models.apiai.AIRequestObject;
import org.guy.rpg.dwg.models.apiai.AIResponseObject;
import org.guy.rpg.dwg.models.apiai.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIAIWebhookController {

	private static final String SPELL_LISTS_URL = "http://www.d20pfsrd.com/magic/spell-lists-and-domains/spell-lists";
	
	private Map<String, String> classSpellsMap = new HashMap<String, String>() {{
		put("barbarian", "Barbarian's are brutes, they can't cast spells!");
		put("bard", "Check out <a href='" + SPELL_LISTS_URL + "---bard' target='_blank'>this list of Bard spells</a>!");
		put("cleric", "Sure, take a look at <a href='" + SPELL_LISTS_URL + "---cleric' target='_blank'>these Cleric spells</a>!");
		put("druid", "I've got a bunch of <a href='" + SPELL_LISTS_URL + "---druid' target='_blank'>Druid spells right here</a>!");
		put("fighter", "Uhh, Fighters don't cast spells buddy.");
		put("monk", "Monks don't cast spells, were you looking for <a href='http://www.d20pfsrd.com/classes/unchained-classes/monk-unchained#TOC-Ki-Power-Su-' target='_blank'>a list of their Kis</a>?");
		put("paladin", "Plenty of <a href='" + SPELL_LISTS_URL + "---paladin' target='_blank'>Paladin spells here</a>!");
		put("ranger", "Sure thing, check out <a href='" + SPELL_LISTS_URL + "---ranger' target='_blank'>this list of Ranger spells</a>!");
		put("rogue", "I'm afraid I can't do that. Rogues don't cast spells.");
		put("sorcerer", "I've got plenty of <a href='" + SPELL_LISTS_URL + "---sorcerer-and-wizard' target='_blank'>Sorcerer spells right here</a>!");
		put("wizard", "Hmm, I can't find any. Just kidding, check out <a href='" + SPELL_LISTS_URL + "---sorcerer-and-wizard' target='_blank'>this list of Wizard spells</a>!");
	}};
	
	@Autowired
	AIManager aiManager;
	
	private static final String DEFAULT_SPEECH = "Hmm, I don't know what you mean.";

	@RequestMapping(value = "/webhook", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public AIResponseObject webhook(@RequestBody AIGenericRequest request, HttpServletResponse response) {
		AIResponseObject respObj = new AIResponseObject();
		respObj.setSpeech(DEFAULT_SPEECH);

		AIRequestObject reqObj = request.getResult();
		Metadata metadata = reqObj.getMetadata();
		String intentName = metadata.getIntentName();

		if (intentName != null) {
			switch (intentName) {
			case IntentConstants.SEARCH_SPELLS:
				respObj.setSpeech(getSearchSpells(reqObj));
				break;
			}
		}

		return respObj;
	}

	private String getSearchSpells(AIRequestObject request) {
		String response = "Sorry, I actually couldn't find any spells.";
		
		String charClass = request.getParameters().get("class");
		
		if (classSpellsMap.containsKey(charClass.toLowerCase())) {
			response = classSpellsMap.get(charClass.toLowerCase());
		}

		return response;
	}
	
	@RequestMapping(value = "/askDM", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String askDM(@RequestParam String statement) {
		return aiManager.sendRequest(statement);
	}
}