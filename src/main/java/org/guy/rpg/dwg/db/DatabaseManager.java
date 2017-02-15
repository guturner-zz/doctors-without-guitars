package org.guy.rpg.dwg.db;

import javax.servlet.http.HttpServletRequest;

import org.guy.rpg.dwg.db.repositories.CharacterRepository;
import org.guy.rpg.dwg.db.repositories.CharacterSheetRepository;
import org.guy.rpg.dwg.db.repositories.UserRepository;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.models.db.User;
import org.guy.rpg.dwg.security.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stormpath.sdk.account.Account;

/**
 * Helper for database interactions.
 * 
 * @author Guy
 */
@Component
public class DatabaseManager {

	@Autowired
	CharacterRepository characterRepository;
	
	@Autowired
	CharacterSheetRepository characterSheetRepository;
	
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
	
	public boolean saveCharacter(Character character) {
		boolean success = false;
		
		Character savedCharacter = characterRepository.save(character);
		
		if (savedCharacter != null) {
			success = false;
		}
		
		return success;
	}
	
	public boolean deleteCharacter(Character character) {
		boolean success = false;
		
		characterRepository.delete(character);
		success = true;
		
		return success;
	}
	
	public boolean saveCharacterSheet(CharacterSheet characterSheet) {
		boolean success = false;
		
		CharacterSheet savedCharacterSheet = characterSheetRepository.save(characterSheet);
		
		if (savedCharacterSheet != null) {
			success = false;
		}
		
		return success;
	}
	
}
