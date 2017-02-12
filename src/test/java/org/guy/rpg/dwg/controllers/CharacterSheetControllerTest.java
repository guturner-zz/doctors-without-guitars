package org.guy.rpg.dwg.controllers;

import static org.junit.Assert.assertEquals;

import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.validators.CharacterSheetValidator;
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
public class CharacterSheetControllerTest {

	@Autowired
	CharacterSheetController controller;

	MockHttpServletRequest mockRequest;

	@Before
	public void setup() {
		mockRequest = new MockHttpServletRequest();

		MockHttpSession mockSession = new MockHttpSession();
		mockRequest.setSession(mockSession);
	}

	@Test
	public void testModifyCharacterSheetByValidator() throws Exception {
		// Given
		String currentHp = "5";
		String maxHp = "30";
		String strengthBase = "5";
		String intelligenceEnhance = "10";

		
		CharacterSheetValidator validator = new CharacterSheetValidator();
		validator.setCurrentHp(currentHp);
		validator.setMaxHp(maxHp);
		validator.setStrengthBase(strengthBase);
		validator.setIntelligenceEnhance(intelligenceEnhance);
		
		CharacterSheet existingCharacterSheet = new CharacterSheet();
		existingCharacterSheet.setCurrentHp(10);
		existingCharacterSheet.setMaxHp(20);
		existingCharacterSheet.setStrengthBase(10);
		existingCharacterSheet.setDexterityEnhance(15);
		
		// When
		Whitebox.invokeMethod(controller, "modifyCharacterSheetByValidator", existingCharacterSheet, validator, mockRequest);
		
		// Then
		assertEquals(5, existingCharacterSheet.getCurrentHp());
		assertEquals(30, existingCharacterSheet.getMaxHp());
		assertEquals(5, existingCharacterSheet.getStrengthBase());
		assertEquals(10, existingCharacterSheet.getIntelligenceEnhance());
		assertEquals(15, existingCharacterSheet.getDexterityEnhance());
	}

	@Test
	public void testCalculateMaxHpFromHitDie() throws Exception {
		Integer hp1 = Whitebox.invokeMethod(controller, "calculateMaxHpFromHitDie", "1d1");
		Integer hp2 = Whitebox.invokeMethod(controller, "calculateMaxHpFromHitDie", "2d8");
		Integer hp3 = Whitebox.invokeMethod(controller, "calculateMaxHpFromHitDie", "3d10");
		Integer hp4 = Whitebox.invokeMethod(controller, "calculateMaxHpFromHitDie", "1d12");
		
		assertEquals(new Integer(1), hp1);
		assertEquals(new Integer(16), hp2);
		assertEquals(new Integer(30), hp3);
		assertEquals(new Integer(12), hp4);
	}
}
