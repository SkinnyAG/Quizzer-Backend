package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;

/**
 * Interface providing services between the question database table and user requests.
 */
public interface QuestionService {
  /**
   * Service for creating a question in the database.
   *
   * @param questionEntity QuestionEntity that should be added.
   * @return The created QuestionEntity.
   */
  QuestionEntity createQuestion(QuestionEntity questionEntity);
}
