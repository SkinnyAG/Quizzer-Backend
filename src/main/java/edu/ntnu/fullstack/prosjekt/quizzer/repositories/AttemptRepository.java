package edu.ntnu.fullstack.prosjekt.quizzer.repositories;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizAttemptEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides basic CRUD functionality for database operations against the quiz_attempt database table.
 */
@Repository
public interface AttemptRepository extends JpaRepository<QuizAttemptEntity, Long> {
  Page<QuizAttemptEntity> findQuizAttemptEntitiesByUser(UserEntity userEntity, Pageable pageable);
}
