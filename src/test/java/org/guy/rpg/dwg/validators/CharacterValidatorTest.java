package org.guy.rpg.dwg.validators;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import javax.validation.constraints.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

public class CharacterValidatorTest {

MockHttpServletRequest mockRequest;
	
	@Before
	public void setup() {
		mockRequest = new MockHttpServletRequest();
		
	    MockHttpSession mockSession = new MockHttpSession();
	    mockRequest.setSession(mockSession);
	}
	
	@Test
	public void testInvalidImageUrl() throws Exception {
		imageRegex("", false);
		imageRegex("img/portraits/unknown.png", false);
		imageRegex("img/portraits/1.png", true);
		imageRegex("img/portraits/10.png", true);
		imageRegex("img/portraits/20.png", true);
	}
	
	@Test
	public void testInvalidName() throws Exception {
		nameRegex("", false);
		nameRegex("123", false);
		nameRegex("A Name", false);
		nameRegex("Nameone", true);
		nameRegex("NameTwo", true);
		nameRegex("NaMeThReE", true);
	}
	
	/**
	 * Helper method for testing Image validation annotation.
	 */
	private void imageRegex(String image, boolean valid) throws Exception {
	    Field field = CharacterValidator.class.getDeclaredField("image");
	    Pattern[] annotations = field.getDeclaredAnnotationsByType(Pattern.class);
	    assertEquals(valid, image.matches(annotations[0].regexp()));
	}
	
	/**
	 * Helper method for testing Name validation annotation.
	 */
	private void nameRegex(String name, boolean valid) throws Exception {
	    Field field = CharacterValidator.class.getDeclaredField("name");
	    Pattern[] annotations = field.getDeclaredAnnotationsByType(Pattern.class);
	    assertEquals(valid, name.matches(annotations[0].regexp()));
	}
	
}
