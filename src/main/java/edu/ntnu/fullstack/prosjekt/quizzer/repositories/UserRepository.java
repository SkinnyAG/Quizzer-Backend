package edu.ntnu.fullstack.prosjekt.quizzer.repositories;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides basic CRUD functionality for database operations against the user database table.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

}
