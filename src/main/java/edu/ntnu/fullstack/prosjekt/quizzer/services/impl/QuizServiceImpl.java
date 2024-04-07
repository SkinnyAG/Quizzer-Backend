package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.*;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.*;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.AttemptRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.CategoryRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import lombok.extern.java.Log;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

  private Mapper<CategoryEntity, CategoryDto> categoryMapper;

  private ObjectMapper objectMapper;

  private QuestionService questionService;

  private CategoryRepository categoryRepository;

  private AttemptRepository attemptRepository;


  /**
   * Used for Dependency Injection.
   *
   * @param quizRepository The Injected QuizRepository object.
   */
  public QuizServiceImpl(QuizRepository quizRepository, CategoryRepository categoryRepository,
                         QuestionService questionService, Mapper<QuizEntity, QuizDetailsDto> quizMapper,
                         Mapper<CategoryEntity, CategoryDto> categoryMapper, ObjectMapper objectMapper,
                         AttemptRepository attemptRepository) {
    this.quizRepository = quizRepository;
    this.categoryRepository = categoryRepository;
    this.quizMapper = quizMapper;
    this.questionService = questionService;
    this.categoryMapper = categoryMapper;
    this.objectMapper = objectMapper;
    this.attemptRepository = attemptRepository;
  }

  /**
   * Creates a quiz in the database table.
   *
   * @param quizDetailsDto QuizEntity that should be added.
   * @return The created QuizEntity
   */
  @Override
  public void createQuiz(QuizDetailsDto quizDetailsDto, UserEntity userEntity) {
    log.info("Creating quiz");

    if (quizDetailsDto.getTitle() == null || quizDetailsDto.getTitle().isEmpty()) {
      log.info("Undefined quiz title");
      throw new IllegalArgumentException("Undefined quiz title");
    }

    //UserEntity userEntity = userService.findEntityByUsername(quizDetailsDto.getOwner().getUsername());
    if (userEntity == null) {
      log.info("Could not find user");
      throw new IllegalArgumentException("No user with username: " + quizDetailsDto.getOwner());
    }
    log.info("Quizdetails in service: " + quizDetailsDto);
    QuizEntity quizEntity = quizMapper.mapFrom(quizDetailsDto);
    log.info("Quizentity in service: " + quizEntity);
    quizEntity.setOwner(userEntity);
    log.info("owner set");
    log.info("questions: " + quizDetailsDto.getQuestions());
    QuizEntity savedQuizEntity = quizRepository.save(quizEntity);
    questionService.addListOfQuestions(quizDetailsDto.getQuestions(), quizEntity);

    log.info("added questions");
    log.info("Saved quiz entity: " + savedQuizEntity);

    QuizDetailsDto savedQuizDto = quizMapper.mapTo(savedQuizEntity);
    log.info("Saved quiz dto: " + savedQuizDto);
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

    return quizEntityPage.map(obj -> {
      QuizGeneralDto converted = new ModelMapper().map(obj, QuizGeneralDto.class);
      converted.setAmountOfQuestions(questionService.getAmountOfQuestionsByQuiz(obj));
      return converted;
    });
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
      log.info("Found QuizEntity");
      return quizRepository.findById(idValue).get();
    }
    return null;
  }

  @Transactional
  @Override
  public void updateQuizEntity(QuizDetailsDto quizDetailsDto) {
    log.info("Quiz id: " + quizDetailsDto.getQuizId());
    log.info("Quiz title: " + quizDetailsDto.getTitle());
    QuizEntity quizEntity = findQuizEntityById(quizDetailsDto.getQuizId().toString());
    questionService.clearQuestionsByQuizEntity(quizEntity);
    questionService.addListOfQuestions(quizDetailsDto.getQuestions(), quizEntity);
    QuizEntity updatedQuizEntity = new ModelMapper().map(quizDetailsDto, QuizEntity.class);
    quizRepository.save(updatedQuizEntity);
  }

  @Override
  public QuizDetailsDto findQuizDetails(String quizId) {
    QuizEntity quizEntity = findQuizEntityById(quizId);
    List<QuestionDto> questions = questionService.getQuestionsByQuiz(quizEntity);
    QuizDetailsDto quizDetailsDto = quizMapper.mapTo(quizEntity);
    quizDetailsDto.setQuestions(questions);
    return quizDetailsDto;
  }

  @Override
  public QuizAttemptDto checkAnswers(String quizId, QuizAttemptDto quizAttemptDto, UserEntity userEntity) {
    List<QuestionDto> quizQuestions = questionService.getQuestionsByQuiz(findQuizEntityById(quizId));
    List<QuestionAttemptDto> submittedAnswers = quizAttemptDto.getQuestionAttempts();
    log.info("Questions in quiz: " + quizQuestions);
    log.info("Quiz id: " + quizId);
    for (int i = 0; i < quizQuestions.size(); i++) {
      log.info("Inside first loop");
      log.info("Question alternatives: " + quizQuestions.get(i).getAlternatives());
      for (int j = 0; j < quizQuestions.get(i).getAlternatives().size(); j++) {
        Object alternative = quizQuestions.get(i).getAlternatives().get(j);
        if (alternative instanceof Map) {
          log.info("Inside second loop");
          Map<String, Object> alternativeMap = (Map<String, Object>) alternative;
          String answer = (String) alternativeMap.get("answer");
          Boolean isCorrect = (Boolean) alternativeMap.get("isCorrect");
          if (answer.equalsIgnoreCase(submittedAnswers.get(i).getAnswerLabel()) && isCorrect) {
            quizAttemptDto.setScore(quizAttemptDto.getScore() + 1);
            quizAttemptDto.getQuestionAttempts().get(i).setAnsweredCorrect(true);
          }
        }
      }
    }
    log.info("Quizattempt: " + quizAttemptDto.getQuestionAttempts());
    QuizAttemptEntity quizAttemptEntity = new ModelMapper().map(quizAttemptDto, QuizAttemptEntity.class);
    log.info("Found userENTITY: " + userEntity);
    quizAttemptEntity.setUser(userEntity);
    log.info("Not failed after setting user");
    log.info("Found quizentity: " + findQuizEntityById(quizId));
    quizAttemptEntity.setQuiz(findQuizEntityById(quizId));
    log.info("Saving attempt: " + quizAttemptEntity);
    attemptRepository.save(quizAttemptEntity);
    return quizAttemptDto;
  }

  @Override
  public Page<QuizGeneralDto> filterQuizzes(String searchQuery, Pageable pageable) {
    Page<QuizEntity> quizEntityPage = quizRepository.findByCategoriesInOrTitleContaining(searchQuery, pageable);
    return quizEntityPage.map(obj -> {
      QuizGeneralDto converted = new ModelMapper().map(obj, QuizGeneralDto.class);
      converted.setAmountOfQuestions(questionService.getAmountOfQuestionsByQuiz(obj));
      return converted;
    });
  }

  public List<CategoryDto> findAllCategories() {
    List<CategoryDto> categories = categoryRepository.findAll().stream().map(categoryEntity -> categoryMapper.mapTo(categoryEntity)).toList();
    return categories;
  }
}
