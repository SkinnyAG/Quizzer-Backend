package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
   *
   * @param quizService The injected QuizService object.
   */
  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  /**
   * Endpoint for creating a new quiz.
   *
   * @param quizDto The quiz to be created.
   * @return A response with a status code and message. Fails necessary fields are missing.
   */
  @PostMapping()
  public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto quizDto) {
    log.info("Request to createQuiz received with quiz: " + quizDto);
    try {
      QuizDto responseDto = quizService.createQuiz(quizDto);
      return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    } catch (Exception e) {
      log.info("An unforeseen error occurred");
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
    }
  }

  /**
   * Endpoint that gets a page of quizzes.
   *
   * @param pageable Pagination parameters such as page size, number and sorting.
   * @return Response with a status code and message.
   */
  @CrossOrigin(origins = "*")
  @GetMapping()
  public ResponseEntity<Page<QuizDto>> getPageOfQuizzes(Pageable pageable) {
    log.info("Client requesting quiz page");
    Page<QuizDto> quizDtoPage = quizService.findPageOfQuizzes(pageable);
    return new ResponseEntity<>(quizDtoPage, HttpStatus.OK);
  }

  /**
   * Endpoint that gets a specified quiz.
   *
   * @param quizId ID of the quiz
   * @return A quizDto object representing the quiz.
   */
  @CrossOrigin(origins = "*")
  @GetMapping(path = "/{quizId}")
  public ResponseEntity<QuizDto> getQuiz(@PathVariable String quizId) {
    if (quizService.findQuizDtoById(quizId) == null) {
      return ResponseEntity.notFound().build();
    }
    QuizDto respnseQuizDto = quizService.findQuizDtoById(quizId);
    return new ResponseEntity<>(respnseQuizDto, HttpStatus.OK);
  }

}
