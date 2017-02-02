package org.guy.rpg.dwg.db;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.db.repositories.CharacterRepository;
import org.guy.rpg.dwg.db.repositories.UserRepository;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.User;
import org.guy.rpg.dwg.security.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stormpath.sdk.account.Account;

/**
 * Logic for database interactions.
 * 
 * @author Guy
 */
@Component
public class DatabaseManager {

	@Autowired
	CharacterRepository characterRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public boolean saveNewUser(User user) {
		boolean success = false;

		User savedUser = userRepository.save(user);

		if (savedUser != null) {
			success = true;
		}

		return success;
	}

	public User getCurrentUser(HttpServletRequest request) {
		Account userAccount = UserManager.getCurrentUserAccount(request);
		User user = null;
		
		if (userAccount != null) {
			user = userRepository.getUserByEmail(userAccount.getEmail());
		}
		
		return user;
	}
	
	public Character getCurrentUserCharacter(HttpServletRequest request) {
		User user = getCurrentUser(request);
		Character character = null;
		
		if (user != null) {
			character = characterRepository.getCharacterByUser(user);
		}
		
		return character;
	}
	
	public boolean saveCharacter(Character character) {
		boolean success = false;
		
		Character savedCharacter = characterRepository.save(character);
		
		if (savedCharacter != null) {
			success = false;
		}
		
		return success;
	}
	
}
