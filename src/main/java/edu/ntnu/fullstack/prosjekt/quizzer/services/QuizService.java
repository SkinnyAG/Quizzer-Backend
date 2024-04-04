package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface providing services between the quiz database table and user requests.
 */
public interface QuizService {
  /**
   * Service for creating a quiz in the database.
   *
   * @param quizEntity QuizEntity that should be added.
   * @return The created QuizEntity.
   */
  QuizEntity createQuiz(QuizEntity quizEntity);

  /**
   * Service for finding a page of quizzes in the database.
   *
   * @return A page of quizzes
   */
  Page<QuizEntity> findPageOfQuizzes(Pageable pageable);

}
