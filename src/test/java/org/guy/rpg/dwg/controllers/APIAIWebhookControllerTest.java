package org.guy.rpg.dwg.controllers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.guy.rpg.dwg.models.apiai.AIRequestObject;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class APIAIWebhookControllerTest {

	APIAIWebhookController controller = new APIAIWebhookController();
	AIRequestObject reqObject;
	
	@Before
	public void setup() {
		reqObject = mock(AIRequestObject.class);
	}
	
	@Test
	public void testGetRandomNumber_d4() throws Exception {
		// Given:
		Map<String, String> parameters = new HashMap<String, String>() {{ put("die", "d4"); }};
		
		// When:
		when(reqObject.getParameters()).thenReturn(parameters);
		
		// Then:
		// Since this is random, run it a few times to make sure we get no invalid response:
		for (int i = 0; i < 20; i++) {
			String randomNumberResponse = Whitebox.invokeMethod(controller, "getRandomNumber", reqObject);
			String prompt = randomNumberResponse.split("(\\.){3} ")[0];
			Integer num = Integer.parseInt(randomNumberResponse.split("(\\.){3} ")[1]);
			
			assertTrue(prompt.startsWith("I rolled a d4 and got"));
			assertTrue(num > 0 && num < 5);
		}
	}
	
	@Test
	public void testGetRandomNumber_d6() throws Exception {
		// Given:
		Map<String, String> parameters = new HashMap<String, String>() {{ put("die", "d6"); }};
		
		// When:
		when(reqObject.getParameters()).thenReturn(parameters);
		
		// Then:
		// Since this is random, run it a few times to make sure we get no invalid response:
		for (int i = 0; i < 20; i++) {
			String randomNumberResponse = Whitebox.invokeMethod(controller, "getRandomNumber", reqObject);
			String prompt = randomNumberResponse.split("(\\.){3} ")[0];
			Integer num = Integer.parseInt(randomNumberResponse.split("(\\.){3} ")[1]);
			
			assertTrue(prompt.startsWith("I rolled a d6 and got"));
			assertTrue(num > 0 && num < 7);
		}
	}
}
