package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizGeneralDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDetailsDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

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
  private Mapper<QuizEntity, QuizDetailsDto> quizMapper;

  /**
   * Used for Dependency Injection.
   */
  private UserService userService;

  private QuestionService questionService;


  /**
   * Used for Dependency Injection.
   *
   * @param quizRepository The Injected QuizRepository object.
   */
  public QuizServiceImpl(QuizRepository quizRepository, UserService userService,
                         QuestionService questionService, Mapper<QuizEntity, QuizDetailsDto> quizMapper) {
    this.quizRepository = quizRepository;
    this.quizMapper = quizMapper;
    this.userService = userService;
    this.questionService = questionService;
  }

  /**
   * Creates a quiz in the database table.
   *
   * @param quizDetailsDto QuizEntity that should be added.
   * @return The created QuizEntity
   */
  @Override
  public QuizDetailsDto createQuiz(QuizDetailsDto quizDetailsDto) {
    log.info("Creating quiz");

    if (quizDetailsDto.getTitle() == null || quizDetailsDto.getTitle().isEmpty()) {
      log.info("Undefined quiz title");
      throw new IllegalArgumentException("Undefined quiz title");
    }

    UserEntity userEntity = userService.findEntityByUsername(quizDetailsDto.getOwner().getUsername());
    if (userEntity == null) {
      log.info("Could not find user");
      throw new IllegalArgumentException("No user with username: " + quizDetailsDto.getOwner());
    }
    QuizEntity quizEntity = quizMapper.mapFrom(quizDetailsDto);
    quizEntity.setOwner(userEntity);

    QuizEntity savedQuizEntity = quizRepository.save(quizEntity);

    return quizMapper.mapTo(savedQuizEntity);
  }

  @Override
  public QuestionDto addQuestionToQuiz(String quizId, QuestionDto questionDto) throws JsonProcessingException {
    if (quizRepository.findById(Long.parseLong(quizId)).isPresent()) {
      QuizEntity quizEntity = quizRepository.findById(Long.parseLong(quizId)).get();
      return questionService.createQuestion(quizEntity, questionDto);
    }
    return null;
  }


  /**
   * Finds a page of quizzes in the database.
   *
   * @return A page of quizzes.
   */
  @Override
  public Page<QuizGeneralDto> findPageOfQuizzes(Pageable pageable) {
    Page<QuizEntity> quizEntityPage = quizRepository.findAll(pageable);
    Page<QuizGeneralDto> quizGeneralDtoPage = quizEntityPage.map(quizEntity -> mapEntityToGeneral(quizEntity));
    return quizGeneralDtoPage;
  }

  /**
   * Finds a specified quiz.
   *
   * @param quizId Unique identifier for the quiz.
   * @return A Dto of the queried quiz.
   */
  @Override
  public QuizDetailsDto findQuizDtoById(String quizId) {
    Long idValue = Long.parseLong(quizId);
    if (quizRepository.findById(idValue).isPresent()) {
      QuizEntity quizEntity = quizRepository.findById(idValue).get();
      QuizDetailsDto foundQuizDetailsDto = quizMapper.mapTo(quizEntity);
      return quizMapper.mapTo(quizEntity);
    }
    return null;
  }

  /**
   * Finds the entity of a specified quiz.
   *
   * @param quizId Unique identifier for the quiz to search for.
   * @return An Entity of the queried quiz.
   */
  @Override
  public QuizEntity findQuizEntityById(String quizId) {
    Long idValue = Long.parseLong(quizId);
    if (quizRepository.findById(idValue).isPresent()) {
      return quizRepository.findById(idValue).get();
    }
    return null;
  }

  @Override
  public QuizDetailsDto findQuizDetails(String quizId) {
    QuizEntity quizEntity = findQuizEntityById(quizId);
    List<QuestionDto> questions = questionService.getQuestionsByQuiz(quizEntity);
    QuizDetailsDto quizDetailsDto = quizMapper.mapTo(quizEntity);
    quizDetailsDto.setQuestions(questions);
    return quizDetailsDto;
  }

  public QuizGeneralDto mapEntityToGeneral(QuizEntity quizEntity) {
    QuizDetailsDto quizDetailsDto = quizMapper.mapTo(quizEntity);
    QuizGeneralDto quizGeneralDto = new QuizGeneralDto(quizEntity.getQuizId(), quizEntity.getTitle(),
            quizEntity.getDescription(), quizEntity.getImageLink(), questionService.getAmountOfQuestionsByQuiz(quizEntity)
              , quizDetailsDto.getOwner());
    return quizGeneralDto;
  }
}
