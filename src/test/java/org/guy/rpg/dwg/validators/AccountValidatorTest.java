package org.guy.rpg.dwg.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.BindingResult;

public class AccountValidatorTest {

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
		AccountValidator validator = new AccountValidator();
		validator.setGivenName("User");
		validator.setSurname("One");
		validator.setEmail("AnEmailThatCouldn'tPossiblyBeInUse@abc.com");
		validator.setPassword("123456Ab");
		validator.setConfirmPassword("123456Ab");
		
		BindingResult result = mock(BindingResult.class);
	    
		// When
		when(result.hasErrors()).thenReturn(false);
		List<String> errors = validator.validate(result, mockRequest);
		
		// Then
		assertTrue("Happy Path should have no errors.", errors.isEmpty());
	}
	
	@Test
	public void testValidate_invalidPassword() throws Exception {
		// Given
		AccountValidator validator = new AccountValidator();
		validator.setGivenName("User");
		validator.setSurname("One");
		validator.setEmail("AnEmailThatCouldn'tPossiblyBeInUse@abc.com");
		validator.setPassword("123456");
		validator.setConfirmPassword("123456");
		
		BindingResult result = mock(BindingResult.class);
	    
		// When
		when(result.hasErrors()).thenReturn(false);
		List<String> errors = validator.validate(result, mockRequest);
		
		// Then
		assertTrue("Should only have 1 error.", errors.size() == 1);
		assertTrue("Should have an invalid password error message.", errors.contains("Password doesn't meet requirements (8+ chars, 1 capital, 1 number)."));
	}
	
	@Test
	public void testValidate_nonMatchingPasswords() throws Exception {
		// Given
		AccountValidator validator = new AccountValidator();
		validator.setGivenName("User");
		validator.setSurname("One");
		validator.setEmail("AnEmailThatCouldn'tPossiblyBeInUse@abc.com");
		validator.setPassword("123456Ab");
		validator.setConfirmPassword("123456Abc");
		
		BindingResult result = mock(BindingResult.class);
	    
		// When
		when(result.hasErrors()).thenReturn(false);
		List<String> errors = validator.validate(result, mockRequest);
		
		// Then
		assertTrue("Should only have 1 error.", errors.size() == 1);
		assertTrue("Should have a passwords don't match error message.", errors.contains("Passwords don't match."));
	}
	
	@Test
	public void testValidate_multipleErrors() throws Exception {
		// Given
		AccountValidator validator = new AccountValidator();
		validator.setGivenName("User");
		validator.setSurname("One");
		validator.setEmail("AnEmailThatCouldn'tPossiblyBeInUse@abc.com");
		validator.setPassword("1234");
		validator.setConfirmPassword("5678");
		
		BindingResult result = mock(BindingResult.class);
	    
		// When
		when(result.hasErrors()).thenReturn(false);
		List<String> errors = validator.validate(result, mockRequest);
		
		// Then
		assertTrue("Should only have 2 errors.", errors.size() == 2);
		assertTrue("Should have an invalid password error message.", errors.contains("Password doesn't meet requirements (8+ chars, 1 capital, 1 number)."));
		assertTrue("Should have a passwords don't match error message.", errors.contains("Passwords don't match."));
	}
}
