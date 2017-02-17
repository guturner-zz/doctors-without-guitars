package org.guy.rpg.dwg.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.guy.rpg.dwg.db.DatabaseManager;
import org.guy.rpg.dwg.security.UserManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeleniumTest extends Locomotive {
	
	private static final String INVALID_LOGIN_ERROR = "Invalid username or password.";
	private static final String PASSWORDS_DONT_MATCH_ERROR = "Passwords don't match.";
	private static final String INVALID_EMAIL_ERROR = "Your email is not well-formed.";
	private static final String NO_PORTRAIT_ERROR = "Select a character portrait.";
	private static final String NO_CHARACTER_SIZE_ERROR = "Select a character size.";
	private static final String NO_CHARACTER_CLASS_ERROR = "Select a character class.";
	private static final String NO_CHARACTER_RACE_ERROR = "Select a character race.";
	private static final String INVALID_CHARACTER_NAME_ERROR = "Set your character name (no numbers or special characters).";
	private static final String HIT_DIE_ERROR = "Hit Die value must match pattern <num>d<num> as in 1d8.";

	private static String email;
	
	@Autowired
	DatabaseManager dbManager;
	
	@Before
	public void setup() {
		email = null;
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
		email = "SeleniumTest-" + new Random().nextInt(100000);
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
	
	@Test
	public void testCreateCharacter() {
		createUserFromHomePage();
		
		By charactersBtn = By.xpath("//a[text()='Characters']");
		click(charactersBtn);
		
		assertEquals("DWG : Character Select", driver.getTitle());
		
		By createCharacterBtn = By.xpath("//button[text()='Create New Character']");
		click(createCharacterBtn);
		
		assertEquals("DWG : Create Character", driver.getTitle());
		
		By saveCharacterBtn = By.xpath("//button[text()='Create Character']");
		click(saveCharacterBtn);
		
		List<WebElement> errorElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		List<String> errorMsgs = new ArrayList<String>();
		for (WebElement e : errorElements) {
			errorMsgs.add(e.getText());
		}
		
		assertTrue(errorMsgs.contains(INVALID_CHARACTER_NAME_ERROR));
		assertTrue(errorMsgs.contains(NO_CHARACTER_CLASS_ERROR));
		assertTrue(errorMsgs.contains(NO_CHARACTER_RACE_ERROR));
		assertTrue(errorMsgs.contains(NO_CHARACTER_SIZE_ERROR));
		assertTrue(errorMsgs.contains(NO_PORTRAIT_ERROR));
		
		String characterName = "1";
		By characterNameTxt = By.xpath("//input[@name='name']");
		setText(characterNameTxt, characterName);
		
		click(saveCharacterBtn);
		
		errorElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		errorMsgs = new ArrayList<String>();
		for (WebElement e : errorElements) {
			errorMsgs.add(e.getText());
		}
		
		assertTrue(errorMsgs.contains(INVALID_CHARACTER_NAME_ERROR));
		
		characterName = "Hero";
		String characterClass = "Fighter";
		String characterRace = "Half-Orc";
		String characterSize = "Medium";
		String imagePath = "img/portraits/12.png";
		
		setText(characterNameTxt, characterName);
		
		By characterClassDrp = By.xpath("//select[@name='classId']");
		Select characterClassSelect = new Select(driver.findElement(characterClassDrp));
		characterClassSelect.selectByVisibleText(characterClass);
		
		By characterRaceDrp = By.xpath("//select[@name='raceId']");
		Select characterRaceSelect = new Select(driver.findElement(characterRaceDrp));
		characterRaceSelect.selectByVisibleText(characterRace);
		
		By characterSizeDrp = By.xpath("//select[@name='size']");
		Select characterSizeSelect = new Select(driver.findElement(characterSizeDrp));
		characterSizeSelect.selectByVisibleText(characterSize);
		
		By portraitImg = By.xpath("//img[contains(@src,'" + imagePath + "')]");
		click(portraitImg);
		
		click(saveCharacterBtn);
		
		assertEquals("DWG : Character Select", driver.getTitle());
		
		List<WebElement> successElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		List<String> successMsgs = new ArrayList<String>();
		for (WebElement e : successElements) {
			successMsgs.add(e.getText());
		}
		
		assertTrue(successMsgs.contains("Character " + characterName + " was created!"));
		
		By characterRowSpn = By.xpath("//span[contains(@class,'name-plate')]");
		assertEquals(characterName + " the " + characterClass, getText(characterRowSpn));
	}
	
	@Test
	public void testFirstStepsScreen() {
		createUserFromHomePage();
		createNewCharacterFromHomePage();
		
		By characterActionsDrp = By.xpath("//select[@name='actionsDrp']");
		Select characterActionsSelect = new Select(driver.findElement(characterActionsDrp));
		characterActionsSelect.selectByVisibleText("Character Sheet");
		
		assertEquals("DWG : Character Sheet", driver.getTitle());
		
		By h2 = By.xpath("//div[contains(@class,'container')]/h2");
		assertEquals("Hello Adventurer!", getText(h2));
		
		By saveCharacterBtn = By.xpath("//button[text()='Save Character']");
		click(saveCharacterBtn);
		
		List<WebElement> errorElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		List<String> errorMsgs = new ArrayList<String>();
		for (WebElement e : errorElements) {
			errorMsgs.add(e.getText());
		}
		
		assertTrue(errorMsgs.contains(HIT_DIE_ERROR));
		
		String hitDieVal = "1";
		
		By hitDieTxt = By.xpath("//input[@name='hitDie']");
		setText(hitDieTxt, hitDieVal);
		
		click(saveCharacterBtn);
		
		errorElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		errorMsgs = new ArrayList<String>();
		for (WebElement e : errorElements) {
			errorMsgs.add(e.getText());
		}
		
		assertTrue(errorMsgs.contains(HIT_DIE_ERROR));
		
		hitDieVal = "1D8";
		setText(hitDieTxt, hitDieVal);
		
		By strengthBaseTxt = By.xpath("//input[@name='strengthBase']");
		By strengthModTd = By.xpath("//td[@name='strMod']");
		
		assertEquals("-5", getText(strengthModTd));
		setText(strengthBaseTxt, "10");
		click(strengthModTd);
		assertEquals("+0", getText(strengthModTd));
		
		By dexterityEnhanceTxt = By.xpath("//input[@name='dexterityEnhance']");
		By dexModTd = By.xpath("//td[@name='dexMod']");
		
		assertEquals("-5", getText(dexModTd));
		setText(dexterityEnhanceTxt, "15");
		click(dexModTd);
		assertEquals("+2", getText(dexModTd));
		
		assertEquals("+0", getText(strengthModTd));
		setText(strengthBaseTxt, "abc");
		click(strengthModTd);
		assertEquals("-5", getText(strengthModTd));
		
		click(saveCharacterBtn);
		
		assertEquals("DWG : Character Sheet", driver.getTitle());
		assertTrue(driver.findElements(h2).isEmpty());
	}
	
	@After
	public void cleanup() {
		if (email != null) {
			// Delete Stormpath account:
			UserManager.deleteAccount(email);
		
			// Delete database object:
			dbManager.deleteUser(email);
		}
	}
	
	/**
	 * Helper method that creates a new account and logs in.
	 */
	private void createUserFromHomePage() {
		By registerBtn = By.xpath("//a[text()='Register']");
		click(registerBtn);
		
		assertEquals("DWG : Register", driver.getTitle());
		
		By firstNameTxt = By.xpath("//input[@name='givenName']");
		setText(firstNameTxt, "Selenium");
		
		By lastNameTxt = By.xpath("//input[@name='surname']");
		setText(lastNameTxt, "Test");
		
		By emailTxt = By.xpath("//input[@name='email']");
		email = "SeleniumTest-" + new Random().nextInt(100000) + "@test.com";
		setText(emailTxt, email);
		
		By passwordTxt = By.xpath("//input[@name='password']");
		String password = "123456Ab";
		setText(passwordTxt, password);
		
		By confirmPasswordTxt = By.xpath("//input[@name='confirmPassword']");
		setText(confirmPasswordTxt, password);
		
		By registerSubmitBtn = By.xpath("//button[text()='Create Account']");
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
		setText(passwordTxt, password);
		
		By loginSubmitBtn = By.xpath("//button[text()='Login']");
		click(loginSubmitBtn);
		
		assertEquals("DWG : Home", driver.getTitle());
	}
	
	/**
	 * Helper method that creates a new character assuming already logged in.
	 */
	private void createNewCharacterFromHomePage() {
		assertEquals("DWG : Home", driver.getTitle());
		
		By charactersBtn = By.xpath("//a[text()='Characters']");
		click(charactersBtn);
		
		assertEquals("DWG : Character Select", driver.getTitle());
		
		By createCharacterBtn = By.xpath("//button[text()='Create New Character']");
		click(createCharacterBtn);
		
		assertEquals("DWG : Create Character", driver.getTitle());
		
		String characterName = "Hero";
		String characterClass = "Fighter";
		String characterRace = "Half-Orc";
		String characterSize = "Medium";
		String imagePath = "img/portraits/12.png";
		
		By characterNameTxt = By.xpath("//input[@name='name']");
		setText(characterNameTxt, characterName);
		
		By characterClassDrp = By.xpath("//select[@name='classId']");
		Select characterClassSelect = new Select(driver.findElement(characterClassDrp));
		characterClassSelect.selectByVisibleText(characterClass);
		
		By characterRaceDrp = By.xpath("//select[@name='raceId']");
		Select characterRaceSelect = new Select(driver.findElement(characterRaceDrp));
		characterRaceSelect.selectByVisibleText(characterRace);
		
		By characterSizeDrp = By.xpath("//select[@name='size']");
		Select characterSizeSelect = new Select(driver.findElement(characterSizeDrp));
		characterSizeSelect.selectByVisibleText(characterSize);
		
		By portraitImg = By.xpath("//img[contains(@src,'" + imagePath + "')]");
		click(portraitImg);
		
		By saveCharacterBtn = By.xpath("//button[text()='Create Character']");
		click(saveCharacterBtn);
		
		assertEquals("DWG : Character Select", driver.getTitle());
		
		List<WebElement> successElements = driver.findElements(By.xpath("//div[contains(@class,'alert')]/p"));
		List<String> successMsgs = new ArrayList<String>();
		for (WebElement e : successElements) {
			successMsgs.add(e.getText());
		}
		
		assertTrue(successMsgs.contains("Character " + characterName + " was created!"));
		
		By characterRowSpn = By.xpath("//span[contains(@class,'name-plate')]");
		assertEquals(characterName + " the " + characterClass, getText(characterRowSpn));
	}
	
	private void logoutBeforeStartingTest() {
		By welcomeH2 = By.xpath("//div/h2[text()='Getting Started']");
		if (isPresent(welcomeH2)) {
			By logoutBtn = By.xpath("//a[text()='Logout']");
		}
	}
}
