package org.guy.rpg.dwg.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

/**
 * This is a Selenium browser automation test. It will open Google Chrome windows.
 * DWG must be running for the test to pass.
 * 
 * @author Guy
 */

@Config(browser = Browser.CHROME, url = "http://localhost:8080/")
public class SeleniumTest extends Locomotive {
	
	private static final String INVALID_LOGIN_ERROR = "Invalid username or password.";
	private static final String PASSWORDS_DONT_MATCH_ERROR = "Passwords don't match.";
	private static final String INVALID_EMAIL_ERROR = "Your email is not well-formed.";

	@Before
	public void setup() {
		logoutBeforeStartingTest();
	}
	
	@Test
	public void testWelcomePageRenders() {
		By welcomeH2 = By.xpath("//div/h2[text()='Doctors without Guitars']");
		assertTrue(isPresent(welcomeH2));
	}
	
	/**
	 * Tests user registration and login.
	 * Tests form validators on 'Register' and 'Login' screens.
	 */
	@Test
	public void testRegisterUser() {
		By registerBtn = By.xpath("//a[text()='Register']");
		click(registerBtn);
		
		assertEquals("DWG : Register", driver.getTitle());
		
		By firstNameTxt = By.xpath("//input[@name='givenName']");
		setText(firstNameTxt, "Selenium");
		
		By lastNameTxt = By.xpath("//input[@name='surname']");
		setText(lastNameTxt, "Test");
		
		By emailTxt = By.xpath("//input[@name='email']");
		String email = "SeleniumTest-" + new Random().nextInt(100000);
		setText(emailTxt, email);
		
		By passwordTxt = By.xpath("//input[@name='password']");
		String password = "123456Ab";
		setText(passwordTxt, password);
		
		By confirmPasswordTxt = By.xpath("//input[@name='confirmPassword']");
		setText(confirmPasswordTxt, "12345");
		
		By registerSubmitBtn = By.xpath("//button[text()='Create Account']");
		click(registerSubmitBtn);
		
		List<WebElement> errorElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		List<String> errorMsgs = new ArrayList<String>();
		for (WebElement e : errorElements) {
			errorMsgs.add(e.getText());
		}
		
		assertTrue(errorMsgs.contains(INVALID_EMAIL_ERROR));
		assertTrue(errorMsgs.contains(PASSWORDS_DONT_MATCH_ERROR));
		
		email += "@test.com";
		setText(emailTxt, email);
		setText(passwordTxt, password);
		setText(confirmPasswordTxt, password);
		
		click(registerSubmitBtn);
		
		assertEquals("DWG : Home", driver.getTitle());
		
		List<WebElement> successElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		List<String> successMsgs = new ArrayList<String>();
		for (WebElement e : successElements) {
			successMsgs.add(e.getText());
		}
		
		assertTrue(successMsgs.contains("Account " + email + " was created! Please login."));
		
		By loginBtn = By.xpath("//a[text()='Login']");
		click(loginBtn);
		
		assertEquals("DWG : Login", driver.getTitle());
		
		By loginTxt = By.xpath("//input[@name='login']");
		setText(loginTxt, email);
		setText(passwordTxt, password + "abc");
		
		By loginSubmitBtn = By.xpath("//button[text()='Login']");
		click(loginSubmitBtn);
		
		errorElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		errorMsgs = new ArrayList<String>();
		for (WebElement e : errorElements) {
			errorMsgs.add(e.getText());
		}
		
		assertTrue(errorMsgs.contains(INVALID_LOGIN_ERROR));
		
		setText(passwordTxt, password);
		
		click(loginSubmitBtn);
		
		assertEquals("DWG : Home", driver.getTitle());
	}
	
	private void logoutBeforeStartingTest() {
		By welcomeH2 = By.xpath("//div/h2[text()='Getting Started']");
		if (isPresent(welcomeH2)) {
			By logoutBtn = By.xpath("//a[text()='Logout']");
		}
	}
}
