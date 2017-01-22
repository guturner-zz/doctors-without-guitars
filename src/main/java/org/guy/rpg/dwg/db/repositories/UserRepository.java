package org.guy.rpg.dwg.db.repositories;

import org.guy.rpg.dwg.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User getUserByEmail(String email);
	
}
