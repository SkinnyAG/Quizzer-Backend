package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuestionRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * A class implementing the methods specified in its interface.
 * Provides services between user requests and database operations.
 */
@Service
@Log
public class QuestionServiceImpl implements QuestionService {
  /**
   * Used for Dependency Injection.
   */
  private QuestionRepository questionRepository;

  /**
   * Used for Dependency Injection.
   *
   * @param questionRepository The injected QuestionRepository object.
   */
  public QuestionServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  /**
   * Creates a question in the database table.
   *
   * @param questionEntity QuestionEntity that should be added.
   * @return The created QuestionEntity
   */
  @Override
  public QuestionEntity createQuestion(QuestionEntity questionEntity) {
    return questionRepository.save(questionEntity);
  }

}
