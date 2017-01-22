package org.guy.rpg.dwg.db.repositories;

import java.util.List;

import org.guy.rpg.dwg.models.Class;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<Class, Long> {

	List<Class> findAll();

}
