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
		return userRepository.getUserByEmail(userAccount.getEmail());
	}
	
	public Character getCurrentUserCharacter(HttpServletRequest request) {
		User user = getCurrentUser(request);
		return characterRepository.getCharacterByUser(user);
	}
}
