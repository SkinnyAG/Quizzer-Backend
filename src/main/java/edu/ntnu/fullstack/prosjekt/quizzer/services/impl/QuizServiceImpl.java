package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    return quizRepository.save(quizEntity);
  }

  /**
   * Find a page of quizzes in the database.
   *
   * @return A page of quizzes.
   */
  @Override
  public Page<QuizEntity> findPageOfQuizzes(Pageable pageable) {
    return quizRepository.findAll(pageable);
  }

  @Override
  public QuizEntity findQuizById(Long quizId) {
    if (quizRepository.findById(quizId).isPresent()) {
      return quizRepository.findById(quizId).get();
    }
    return null;
  }
}
