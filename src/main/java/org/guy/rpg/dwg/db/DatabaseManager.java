package org.guy.rpg.dwg.db;

import org.guy.rpg.dwg.db.repositories.UserRepository;
import org.guy.rpg.dwg.models.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager {

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

}
