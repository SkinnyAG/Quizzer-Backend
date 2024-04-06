package edu.ntnu.fullstack.prosjekt.quizzer.repositories;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides basic CRUD functionality for database operations against the question database table.
 */
@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
  List<QuestionEntity> findQuestionEntitiesByQuiz(QuizEntity quizEntity);
}
