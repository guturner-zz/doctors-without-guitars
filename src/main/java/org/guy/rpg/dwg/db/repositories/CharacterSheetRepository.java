package org.guy.rpg.dwg.db.repositories;

import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterSheetRepository extends CrudRepository<CharacterSheet, Long> {
	
	CharacterSheet getCharacterSheetById(long id);
	
}
