package org.guy.rpg.dwg.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.validators.CharacterValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CharacterControllerTest {

	@Autowired
	CharacterController controller;

	MockHttpServletRequest mockRequest;

	@Before
	public void setup() {
		mockRequest = new MockHttpServletRequest();

		MockHttpSession mockSession = new MockHttpSession();
		mockRequest.setSession(mockSession);
	}

	@Test
	public void testGenerateNewCharacter() throws Exception {
		// Given
		String characterName = "Hero";
		String imagePath = "img/portrait/1.png";

		CharacterValidator validator = new CharacterValidator();
		validator.setName(characterName);
		validator.setImage(imagePath);

		// When
		Character character = Whitebox.invokeMethod(controller, "generateNewCharacter", validator, mockRequest);

		// Then
		assertEquals(characterName, character.getName());
		assertEquals(imagePath, character.getImage());
		assertTrue(character.isNewCharacterFlag());
	}

	@Test
	public void testModifyCharacterByValidator() throws Exception {
		// Given
		String characterName = "Oreh";
		String imagePath = "img/portrait/5.png";

		CharacterValidator validator = new CharacterValidator();
		validator.setName(characterName);
		validator.setImage(imagePath);
		
		Character existingCharacter = new Character();
		existingCharacter.setName("Hero");
		existingCharacter.setImage("none");
		
		// When
		Whitebox.invokeMethod(controller, "modifyCharacterByValidator", existingCharacter, validator, mockRequest);
		
		// Then
		assertEquals(characterName, existingCharacter.getName());
		assertEquals(imagePath, existingCharacter.getImage());
		assertFalse(existingCharacter.isNewCharacterFlag());
	}

}
