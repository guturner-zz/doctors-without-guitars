package org.guy.rpg.dwg.db.repositories;

import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long> {
	
	Character getCharacterByUser(User user);
	
	Character getCharacterById(Long id);
	
}
