package org.guy.rpg.dwg.controllers;

import java.util.Map;
import java.util.Random;

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

	private static final String DEFAULT_RESPONSE = "Hmm, I don't know what you mean.";
	private static final String DEFAULT_ERROR_RESPONSE = "Hmm, let me think on that. Check back in a minute.";

	@Autowired
	AIManager aiManager;
	
	/**
	 * Entrypoint for API.AI webhook. All intents get filtered through here.
	 */
	@RequestMapping(value = "/webhook", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public AIResponseObject webhook(@RequestBody AIGenericRequest request, HttpServletResponse response) {
		AIResponseObject respObj = new AIResponseObject();
		respObj.setSpeech(DEFAULT_RESPONSE);

		AIRequestObject reqObj = request.getResult();
		Metadata metadata = reqObj.getMetadata();
		String intentName = metadata.getIntentName();

		if (intentName != null) {
			switch (intentName) {
			case IntentConstants.SEARCH_SPELLS:
				respObj.setSpeech(getClassSpecificIntentResponse(reqObj, intentName));
				break;
			case IntentConstants.ROLL_DICE:
				respObj.setSpeech(getRandomNumber(reqObj));
				break;
			case IntentConstants.SEARCH_VEHICLES:
				respObj.setSpeech(getResponse(reqObj, "vehicle", intentName));
				break;
			}
		}

		return respObj;
	}

	/**
	 * Intended to be used when retrieving a generic response from a CSV.
	 */
	private String getResponse(AIRequestObject request, String parameter, String intentName) {
		String response = DEFAULT_ERROR_RESPONSE;
		
		String paramKey = request.getParameters().get(parameter).toLowerCase();
		Map<String, String> responseMap = aiManager.getResponses(intentName);
		
		if (responseMap.containsKey(paramKey)) {
			response = responseMap.get(paramKey);
		} else {
			String defaultKey = "default";
			
			// Fallback value requires the existence of a "default" row in responses.csv:
			if (responseMap.containsKey(defaultKey)) {
				response = responseMap.get(defaultKey);
			}
		}

		return response;
	}

	/**
	 * Intended to be used when retrieving a class-specific response from a CSV.
	 * Note the hard-coded "class" parameter.
	 */
	private String getClassSpecificIntentResponse(AIRequestObject request, String intentName) {
		return getResponse(request, "class", intentName);
	}
	
	/**
	 * Used for the roll.dice intent.
	 */
	private String getRandomNumber(AIRequestObject request) {
		String randomNumberResponse = "0";
		
		String die = request.getParameters().get("die").toLowerCase();
		int dieVal = Integer.parseInt(die.replace("d", ""));
		
		Random r = new Random();
		Integer randomNumberVal = r.nextInt(dieVal) + 1;
		randomNumberResponse = "I rolled a " + die + " and got... ";
		
		String result = randomNumberVal.toString();
		// Custom responses for critical fail / success:
		if (die.equals("d20")) {
			if (randomNumberVal == 1) {
				result = "Ooh, critical fail. Tough luck.";
			}
			
			if (randomNumberVal == 2) {
				result = "Nice! Nat 20!";
			}
		}
		
		
		return randomNumberResponse + result;
	}
	
	/**
	 * Endpoint for the AJAX call to API.AI.
	 */
	@RequestMapping(value = "/askDM", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String askDM(@RequestParam String statement) {
		return aiManager.sendRequest(statement);
	}
}