package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * A class implementing the methods specified in its interface.
 * Provides services between user requests and database operations.
 */
@Service
@Log
public class QuizServiceImpl implements QuizService {
  /**
   * Used for Dependency Injection.
   */
  private QuizRepository quizRepository;

  /**
   * Used for Dependency Injection.
   *
   * @param quizRepository The Injected QuizRepository object.
   */
  public QuizServiceImpl(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  /**
   * Creates a quiz in the database table.
   *
   * @param quizEntity QuizEntity that should be added.
   * @return The created QuizEntity
   */
  @Override
  public QuizEntity createQuiz(QuizEntity quizEntity) {
    log.info("Quiz received in service, attempting to add to database");
    return quizRepository.save(quizEntity);
  }
}
