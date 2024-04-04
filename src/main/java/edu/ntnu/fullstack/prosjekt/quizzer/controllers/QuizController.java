package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for managing requests relating to quiz database operations.
 * Base endpoint is /api/quizzes/.
 */
@RestController
@RequestMapping("/api/quizzes")
@Log
public class QuizController {
  /**
   * Used for Dependency Injection.
   */
  private QuizService quizService;

  /**
   * Used for Dependency Injection.
   */
  private Mapper<QuizEntity, QuizDto> quizMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param quizService The injected QuizService object.
   * @param quizMapper The Injected QuizMapper object.
   */
  public QuizController(QuizService quizService, Mapper<QuizEntity, QuizDto> quizMapper) {
    this.quizService = quizService;
    this.quizMapper = quizMapper;
  }

  /**
   * Endpoint for creating a new quiz.
   *
   * @param quizDto The quiz to be created.
   * @return A response with a status code and message. Fails necessary fields are missing.
   */
  @PostMapping()
  public ResponseEntity<?> createQuiz(@RequestBody QuizDto quizDto) {
    log.info("Request to createQuiz received with quiz: " + quizDto);
    try {

      QuizEntity quizEntity = quizMapper.mapFrom(quizDto);

      if (quizEntity.getTitle() == null || quizEntity.getTitle().isEmpty()) {
        log.info("Missing or illegal quiz title");
        return ResponseEntity.badRequest().body("Invalid quiz name");
      }

      if (quizEntity.getOwner() == null) {
        log.info("Missing or invalid quiz owner");
        return ResponseEntity.badRequest().body("No user associated with quiz");
      }

      QuizEntity savedQuizEntity = quizService.createQuiz(quizEntity);
      log.info("Quiz added to database: " + savedQuizEntity);
      QuizDto savedQuizDto = quizMapper.mapTo(savedQuizEntity);

      return new ResponseEntity<>(savedQuizDto, HttpStatus.CREATED);
    } catch (Exception e) {
      log.info("An unforeseen error occurred");
      return ResponseEntity.badRequest().body("An unforeseen error occurred.");
    }
  }

  /**
   * Endpoint that gets a page of quizzes.
   *
   * @param pageable Pagination parameters such as page size, number and sorting.
   * @return Response with a status code and message.
   */
  @GetMapping()
  public ResponseEntity<?> getPageOfQuizzes(Pageable pageable) {
    log.info("Client requesting quiz page");
    Page<QuizEntity> quizEntityPage = quizService.findPageOfQuizzes(pageable);
    Page<QuizDto> quizDtoPage = quizEntityPage.map(quizMapper::mapTo);
    return new ResponseEntity<>(quizDtoPage, HttpStatus.OK);
  }

  /**
   * Endpoint that gets a specified quiz.
   *
   * @param quizId ID of the quiz
   * @return A quizDto object representing the quiz.
   */
  @GetMapping(path = "/{quizId}")
  public ResponseEntity<?> getQuiz(@PathVariable String quizId) {
    Long quizIdValue = Long.parseLong(quizId);
    if (quizService.findQuizById(quizIdValue) == null) {
      return ResponseEntity.notFound().build();
    }
    QuizEntity foundQuizEntity = quizService.findQuizById(quizIdValue);
    QuizDto respnseQuizDto = quizMapper.mapTo(foundQuizEntity);
    return new ResponseEntity<>(respnseQuizDto, HttpStatus.OK);
  }

}
