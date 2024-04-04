package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;

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
}
