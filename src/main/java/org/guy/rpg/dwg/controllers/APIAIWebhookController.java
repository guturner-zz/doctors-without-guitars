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

	private Map<String, String> classSpellsMap = new HashMap<String, String>() {{
		put("bard", "Check out <a href='http://www.d20pfsrd.com/magic/spell-lists-and-domains/spell-lists---bard' _target='blank'>this list of Bard spells</a>!");
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