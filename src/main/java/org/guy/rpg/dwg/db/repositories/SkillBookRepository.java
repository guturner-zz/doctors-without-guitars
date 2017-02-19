package org.guy.rpg.dwg.db.repositories;

import org.guy.rpg.dwg.models.db.SkillBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillBookRepository extends CrudRepository<SkillBook, Long> {
	
	SkillBook getSkillBookById(long id);
	
}
