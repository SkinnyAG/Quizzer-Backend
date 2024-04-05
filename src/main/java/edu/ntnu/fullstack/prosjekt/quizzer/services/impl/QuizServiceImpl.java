package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
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
   */
  private Mapper<QuizEntity, QuizDto> quizMapper;

  /**
   * Used for Dependency Injection.
   */
  private UserService userService;

  /**
   * Used for Dependency Injection.
   *
   * @param quizRepository The Injected QuizRepository object.
   */
  public QuizServiceImpl(QuizRepository quizRepository, UserService userService,
                         Mapper<QuizEntity, QuizDto> quizMapper) {
    this.quizRepository = quizRepository;
    this.quizMapper = quizMapper;
    this.userService = userService;
  }

  /**
   * Creates a quiz in the database table.
   *
   * @param quizDto QuizEntity that should be added.
   * @return The created QuizEntity
   */
  @Override
  public QuizDto createQuiz(QuizDto quizDto) {
    log.info("Creating quiz");

    if (quizDto.getTitle() == null || quizDto.getTitle().isEmpty()) {
      log.info("Undefined quiz title");
      throw new IllegalArgumentException("Undefined quiz title");
    }

    UserEntity userEntity = userService.findByUsername(quizDto.getOwner());
    if (userEntity == null) {
      log.info("Could not find user");
      throw new IllegalArgumentException("No user with username: " + quizDto.getOwner());
    }
    QuizEntity quizEntity = quizMapper.mapFrom(quizDto);
    quizEntity.setOwner(userEntity);

    QuizEntity savedQuizEntity = quizRepository.save(quizEntity);

    return quizMapper.mapTo(savedQuizEntity);
  }

  /**
   * Finds a page of quizzes in the database.
   *
   * @return A page of quizzes.
   */
  @Override
  public Page<QuizDto> findPageOfQuizzes(Pageable pageable) {
    return quizRepository.findAll(pageable).map(quizMapper::mapTo);
  }

  /**
   * Finds a specified quiz.
   *
   * @param quizId Unique identifier for the quiz.
   * @return A Dto of the queried quiz.
   */
  @Override
  public QuizDto findQuizDtoById(String quizId) {
    Long idValue = Long.parseLong(quizId);
    if (quizRepository.findById(idValue).isPresent()) {
      QuizEntity quizEntity = quizRepository.findById(idValue).get();
      QuizDto foundQuizDto = quizMapper.mapTo(quizEntity);
      return quizMapper.mapTo(quizEntity);
    }
    return null;
  }

  @Override
  public QuizEntity findQuizEntityById(String quizId) {
    Long idValue = Long.parseLong(quizId);
    if (quizRepository.findById(idValue).isPresent()) {
      return quizRepository.findById(idValue).get();
    }
    return null;
  }
}
