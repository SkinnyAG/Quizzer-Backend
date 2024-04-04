package edu.ntnu.fullstack.prosjekt.quizzer.repositories;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides basic CRUD functionality for database operations against the question database table.
 */
@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
}
