package org.guy.rpg.dwg.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.guy.rpg.dwg.validators.annotations.HitDie;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.BindingResult;

public class CharacterSheetValidatorTest {

MockHttpServletRequest mockRequest;
	
	@Before
	public void setup() {
		mockRequest = new MockHttpServletRequest();
		
	    MockHttpSession mockSession = new MockHttpSession();
	    mockRequest.setSession(mockSession);
	}
	
	@Test
	public void testValidate_happyPath() throws Exception {
		// Given
		CharacterSheetValidator validator = new CharacterSheetValidator();
		validator.setHitDie("1d8");
		
		BindingResult result = mock(BindingResult.class);
	    
		// When
		when(result.hasErrors()).thenReturn(false);
		List<String> errors = validator.validate(result, mockRequest);
		
		// Then
		assertTrue("Happy Path should have no errors.", errors.isEmpty());
	}
	
	@Test
	public void testInvalidHitDie() throws Exception {
		hitDieRegex("", false);
		hitDieRegex("abc", false);
		hitDieRegex("1d500", false);
		hitDieRegex("1d8", true);
		hitDieRegex("1D20", true);
	}
	
	/**
	 * Helper method for testing Hit Die validation annotation.
	 */
	private void hitDieRegex(String hitDie, boolean valid) throws Exception {
	    Field field = CharacterSheetValidator.class.getDeclaredField("hitDie");
	    HitDie[] annotations = field.getDeclaredAnnotationsByType(HitDie.class);
	    assertEquals(valid, hitDie.matches(annotations[0].regexp()));
	}
	
}
