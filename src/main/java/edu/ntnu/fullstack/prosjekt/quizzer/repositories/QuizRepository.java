package edu.ntnu.fullstack.prosjekt.quizzer.repositories;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides basic CRUD functionality for database operations against the quiz database table.
 */
@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, Long>,
        PagingAndSortingRepository<QuizEntity, Long> {

}
